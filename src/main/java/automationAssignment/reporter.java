package automationAssignment;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class reporter {
	
	public static String capture(WebDriver driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File(".\\reports\\images\\"+ System.currentTimeMillis() + ".png");
		String errflpath = Dest.getCanonicalPath();
		errflpath = ".\\"+ errflpath.substring(errflpath.indexOf("images"));
		FileUtils.copyFile(scrFile, Dest);
		return errflpath;
	}

}
