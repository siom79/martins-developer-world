import ceylon.io {
	OpenFile,
	newOpenFile
}
import ceylon.file {
	parsePath,
	Path
}
import ceylon.io.buffer {
	ByteBuffer,
	newByteBuffer
}
import java.util {
	ArrayList
}
import java.lang {
	IllegalStateException
}

shared class BrainFuckInterpreter(ArrayList<Byte> buffer) {
	ArrayList<Integer> field = ArrayList<Integer>();
	variable Integer ptr = 0;
	
	shared void run() {
		variable Integer bufferPos = 0;
		while (bufferPos >= 0 && bufferPos < buffer.size() - 1) {
			value oldBufferPos = bufferPos;
			value byte = buffer.get(bufferPos);
			switch (byte.unsigned)
			//+
			case (43) {
				ensureFieldCapacity();
				value oldValue = field.get(ptr);
				field.set(ptr, oldValue + 1);
			}
			//-
			case (45) {
				ensureFieldCapacity();
				value oldValue = field.get(ptr);
				field.set(ptr, oldValue - 1);
			}
			//<
			case (60) {
				if (ptr == 0) {
					throw IllegalStateException("Cannot decrement pointer because it is already 0." + stateToString(bufferPos));
				}
				ptr = ptr -1;
			}
			//>
			case (62) {
				ptr = ptr + 1;
			}
			//[
			case (91) {
				value oldValue = field.get(ptr);
				if (oldValue == 0) {
					variable Integer openLoop = 0;
					for (j in bufferPos + 1 .. buffer.size() - 1) {
						value curValue = buffer.get(j).unsigned;
						if (curValue == 91) { //[
							openLoop++;
						} else if (curValue == 93) { //]
							if (openLoop == 0) {
								bufferPos = j + 1;
								break;
							}
							openLoop--;
						}
					}
				}
			}
			//]
			case (93) {
				value oldValue = field.get(ptr);
				if (oldValue != 0) {
					variable Integer openLoop = 0;
					for (j in bufferPos - 1 .. 0) {
						value curValue = buffer.get(j).unsigned;
						if (curValue == 91) { //[
							if (openLoop == 0) {
								bufferPos = j - 1;
								break;
							}
							openLoop--;
						} else if (curValue == 93) { //]
							openLoop++;
						}
					}
				}
			}
			//,
			case (44) {
				value line = process.readLine();
				if (exists line) {
					if (exists firstChar = line.first) {
						field.set(ptr, firstChar.integer);
					} else {
						throw IllegalStateException("Input does not have at least one character.");
					}
				} else {
					throw IllegalStateException("No input available.");
				}
			}
			//.
			case (46) {
				Integer curValue = field.get(ptr);
				process.write(curValue.character.string);
			}
			else {
				//print("Ignoring char " + byte.string);
			}
			print(stateToString(oldBufferPos));
			bufferPos++;
		}
	}
	
	void ensureFieldCapacity() {
		value sizeBefore = field.size();
		if (ptr >= sizeBefore) {
			field.ensureCapacity(ptr + 1);
			for (c in sizeBefore..(field.size() - 1)) {
				field.add(0);
			}
		}
	}
	
	String stateToString(Integer bufferPos) {
		ensureFieldCapacity();
		StringBuilder sb = StringBuilder();
		sb.append("ptr=").append(ptr.string).append(";");
		sb.append("field[").append(ptr.string).append("]=").append(field.get(ptr).string).append(";");
		sb.append("bufferPos=").append(bufferPos.string).append(";");
		value bufferValue = buffer.get(bufferPos).unsigned;
		sb.append("buffer[").append(bufferPos.string).append("]=").append(bufferValue.string).append(" (");
		switch(bufferValue)
		case (43) {
			sb.append("+ : ++*ptr;");
		}
		case (45) {
			sb.append("- : --*ptr;");
		}
		case (60) {
			sb.append("< : --ptr;");
		}
		case (62) {
			sb.append("> : ++ptr;");
		}
		case (91) {
			sb.append("[ : while (*ptr) {");
		}
		case (93) {
			sb.append("] : }");
		}
		case (44) {
			sb.append(", : *ptr = getchar();");
		}
		case (46) {
			sb.append(". : putchar(*ptr);");
		}
		else {
			sb.append("#");
		}
		sb.append(")");
		return sb.string;
	}
}

"Run the module `brainfuck`."
shared void run() {
	String? programFile = process.namedArgumentValue("program");
	if (exists programFile) {
		value bytes = readFile(programFile);
		BrainFuckInterpreter interpreter = BrainFuckInterpreter(bytes);
		interpreter.run();
	} else {
		print("Please provide the argument 'program'.");
	}
}

ArrayList<Byte> readFile(String programFile) {
	value bytes = ArrayList<Byte>();
	print("Reading file '" + programFile + "'.");
	Path path = parsePath(programFile);
	OpenFile openfile = newOpenFile(path.resource);
	ByteBuffer buffer = newByteBuffer(1024);
	variable Integer bytesRead = openfile.read(buffer);
	print("bytesRead=" + bytesRead.string);
	while (bytesRead != -1) {
		buffer.flip();
		for (Byte byte in buffer) {
			bytes.add(byte);
		}
		buffer.clear();
		bytesRead = openfile.read(buffer);
	}
	openfile.close();
	return bytes;
}
