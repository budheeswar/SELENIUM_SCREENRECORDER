package com.rb.screenrecording;

import java.awt.*;
import java.io.File;
import java.time.Duration;

import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SimpleAutomation {
	
	public ScreenRecorder screenRecorder;

	public static void main(String[] args) throws Exception {
		
		SimpleAutomation simpleAutomation= new SimpleAutomation();
		simpleAutomation.startRecording();
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://practicetestautomation.com/practice-test-login/");
		Thread.sleep(1000);
		
		driver.findElement(By.id("username")).sendKeys("student");
		driver.findElement(By.id("password")).sendKeys("Password123");
		Thread.sleep(1000);
		driver.findElement(By.id("submit")).click();
		Thread.sleep(2000);
		driver.quit();
		simpleAutomation.stopRecording();

	}
	public void startRecording() throws Exception {
		
		File file = new File("Videos");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width=screenSize.width;
		int height = screenSize.height;
		
		Rectangle captureSize =  new Rectangle(0,0,width,height);
		
		GraphicsConfiguration gc = GraphicsEnvironment
	               .getLocalGraphicsEnvironment()
	               .getDefaultScreenDevice()
	               .getDefaultConfiguration();

		this.screenRecorder = new SpecializedScreenRecorder(gc, captureSize,
	               new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
	               new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
	                    CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
	                    DepthKey, 24, FrameRateKey, Rational.valueOf(15),
	                    QualityKey, 1.0f,
	                    KeyFrameIntervalKey, 15 * 60),
	               new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
	                    FrameRateKey, Rational.valueOf(30)),
	               null, file, "RB_AutomationRecord");
	          this.screenRecorder.start();
	}
	public void stopRecording() throws Exception
    {
      this.screenRecorder.stop();
    }

}
