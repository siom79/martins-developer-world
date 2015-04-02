import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class RobotLang {
	private static final Logger LOGGER = Logger.getLogger(RobotLang.class.getName());

	public static void main(String[] args) throws IOException, AWTException {
		if (args.length != 1) {
			System.err.println("Please provide input file as parameter.");
		}
		String inputFileName = args[0];
		Path path = Paths.get(inputFileName);
		if (!Files.exists(path)) {
			System.err.println("Given input file does not exist: " + path);
		}
		RobotLang robotLang = new RobotLang();
		robotLang.execute(path);
	}

	public void execute(Path inputPath) throws IOException, AWTException {
		RobotLexer lexer = new RobotLexer(new ANTLRInputStream(new FileInputStream(inputPath.toFile())));
		RobotParser parser = new RobotParser(new CommonTokenStream(lexer));
		final Robot robot = new Robot();
		parser.addParseListener(new RobotBaseListener() {
			@Override
			public void exitInstructionDelay(@NotNull RobotParser.InstructionDelayContext ctx) {
				int delayParam = Integer.parseInt(ctx.paramMs.getText());
				LOGGER.info("delay(" + delayParam + ")");
				robot.delay(delayParam);
			}

			@Override
			public void exitInstructionMouseMove(@NotNull RobotParser.InstructionMouseMoveContext ctx) {
				int x = Integer.parseInt(ctx.x.getText());
				int y = Integer.parseInt(ctx.y.getText());
				LOGGER.info("mouseMove(" + x + "," + y + ")");
				robot.mouseMove(x, y);
			}

				@Override
				public void exitInstructionCreateScreenCapture(@NotNull RobotParser.InstructionCreateScreenCaptureContext ctx) {
					int x = Integer.parseInt(ctx.x.getText());
					int y = Integer.parseInt(ctx.y.getText());
					int w = Integer.parseInt(ctx.w.getText());
					int h = Integer.parseInt(ctx.h.getText());
					LOGGER.info("Rectangle rectangle = new Rectangle(" + x + "," + y + "," + w + "," + h + ")");
					Rectangle rectangle = new Rectangle(x, y, w, h);
					LOGGER.info("createScreenCapture(rectangle);");
					BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
					File output = new File(ctx.file.getText());
					LOGGER.info("Save file to " + output.getAbsolutePath());
					try {
						ImageIO.write(bufferedImage, "png", output);
					} catch (IOException e) {
						throw new RuntimeException("Failed to write image file: " + e.getMessage(), e);
					}
				}

			@Override
			public void exitInstructionMouseClick(@NotNull RobotParser.InstructionMouseClickContext ctx) {
				String button = ctx.button.getText();
				int buttonMask = 0;
				if ("button1".equals(button)) {
					buttonMask = InputEvent.BUTTON1_MASK;
				} else if ("button2".equals(button)) {
					buttonMask = InputEvent.BUTTON2_MASK;
				} else if ("button3".equals(button)) {
					buttonMask = InputEvent.BUTTON3_MASK;
				}
				LOGGER.info("mousePress(" + button + ")");
				robot.mousePress(buttonMask);
				robot.delay(50);
				robot.mouseRelease(buttonMask);
			}

			@Override
			public void exitInstructionKeyboardInput(@NotNull RobotParser.InstructionKeyboardInputContext ctx) {
				String input = ctx.input.getText();
				if (input.startsWith("\"") && input.length() > 1) {
					input = input.substring(1, input.length());
				}
				if (input.endsWith("\"") && input.length() > 1) {
					input = input.substring(0, input.length() - 1);
				}
				for (int i=0; i<input.length(); i++) {
					char c = input.charAt(i);
					int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
					robot.keyPress(keyCode);
					robot.keyRelease(keyCode);
				}
			}
		});
		parser.instructions();
	}
}
