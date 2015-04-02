import org.junit.Test;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class RobotLangTest {

	@Test
	public void testBasic() throws IOException, AWTException {
		RobotLang robotLang = new RobotLang();
		robotLang.execute(Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "basic.txt"));
	}
}