package com.wordpress.mdw;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Options {
	private Path csvPath;
	private Path parquetPath;

	public static Options fromCliArgs(String[] args) {
		if (args.length != 2) {
			throw new IllegalArgumentException("Please provide these arguments: <csv-file> <parquet-file>");
		}
		Options options = new Options();
		Path csvPath = Paths.get(args[0]);
		if (!Files.exists(csvPath)) {
			throw new IllegalArgumentException("Path for <csv-file> must exist.");
		}
		options.csvPath = csvPath;
		Path parquetPath = Paths.get(args[1]);
		if (Files.exists(parquetPath)) {
			throw new IllegalArgumentException("Path for <parquet-file> must not exist.");
		}
		options.parquetPath = parquetPath;
		return options;
	}

	public Path getCsvPath() {
		return csvPath;
	}

	public Path getParquetPath() {
		return parquetPath;
	}
}
