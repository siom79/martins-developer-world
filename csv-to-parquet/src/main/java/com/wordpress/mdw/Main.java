package com.wordpress.mdw;

import com.google.common.io.Files;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import parquet.column.ColumnDescriptor;
import parquet.hadoop.api.WriteSupport;
import parquet.io.ParquetEncodingException;
import parquet.io.api.Binary;
import parquet.io.api.RecordConsumer;
import parquet.schema.MessageType;
import parquet.schema.MessageTypeParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Main {
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	static final String SEPARATOR = ";";

	public static void main(String[] args) {
		Main main = new Main();
		main.run(args);
	}

	public void run(String[] args) {
		Options options = Options.fromCliArgs(args);
		Path path = new Path(options.getParquetPath().toUri());
		try {
			String firstLine = Files.readFirstLine(options.getCsvPath().toFile(), Charset.defaultCharset());
			MessageType messageType = MessageTypeParser.parseMessageType(firstLine);
			WriteSupport<List<String>> writeSupport = new CsvWriteSupport(messageType);
			String line;
			try (CsvParquetWriter writer = new CsvParquetWriter(path, writeSupport);
				 BufferedReader br = new BufferedReader(new FileReader(options.getCsvPath().toFile()))) {
				while ((line = br.readLine()) != null) {
					String[] fields = line.split(Pattern.quote(SEPARATOR));
					writer.write(Arrays.asList(fields));
				}
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to write parquet file: " + e.getMessage(), e);
		}
	}
}
