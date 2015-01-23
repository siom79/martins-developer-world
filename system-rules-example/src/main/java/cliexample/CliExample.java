package cliexample;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CliExample {
	private Scanner scanner = new Scanner(System.in, "UTF-8");

	public static void main(String[] args) {
		CliExample cliExample = new CliExample();
		cliExample.run();
	}

	private void run() {
		try {
			int a = readInNumber();
			int b = readInNumber();
			int sum = a + b;
			System.out.println(sum);
		} catch (InputMismatchException e) {
			System.err.println("The input is not a valid integer.");
		} catch (IOException e) {
			System.err.println("An input/output error occurred: " + e.getMessage());
		}
	}

	private int readInNumber() throws IOException {
		System.out.println("Please enter a number:");
		String nextInput = scanner.next();
		try {
			return Integer.valueOf(nextInput);
		} catch(Exception e) {
			throw new InputMismatchException();
		}
	}
}
