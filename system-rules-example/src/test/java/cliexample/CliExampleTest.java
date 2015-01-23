package cliexample;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.StandardErrorStreamLog;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class CliExampleTest {
	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();
	@Rule
	public final StandardErrorStreamLog stdErrLog = new StandardErrorStreamLog();
	@Rule
	public final StandardOutputStreamLog stdOutLog = new StandardOutputStreamLog();
	@Rule
	public final TextFromStandardInputStream systemInMock =  emptyStandardInputStream();

	@Test
	public void testSuccessfulExecution() {
		systemInMock.provideText("2\n3\n");
		CliExample.main(new String[]{});
		assertThat(stdOutLog.getLog(), is("Please enter a number:\r\nPlease enter a number:\r\n5\r\n"));
	}

	@Test
	public void testInvalidInput() throws IOException {
		systemInMock.provideText("a\n");
		CliExample.main(new String[]{});
		assertThat(stdErrLog.getLog(), is("The input is not a valid integer.\r\n"));
	}
}
