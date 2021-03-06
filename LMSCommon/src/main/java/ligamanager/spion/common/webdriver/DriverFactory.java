package ligamanager.spion.common.webdriver;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;


//Check this: https://sonar.netpioneer.de/sonar/coding_rules#rule_key=squid:S2151
public abstract class DriverFactory {
	private static final Logger LOGGER = Logger.getLogger(DriverFactory.class);
	private static final String DEFAULT_CHROME_DRIVER_PATH = "chromedriver/chromedriver_win32/chromedriver.exe";

	private static Optional<WebDriver> instance = Optional.empty();
	private static boolean setSystemProperty = true;

	public static WebDriverType type = null;

	public static WebDriver getInstance() {
		if(!instance.isPresent()) {
			createInstance();
		}
		return instance.get();
	}

	public static void destroyInstance() {

		if(instance.isPresent()) {
			instance.get().close();
			instance.get().quit();
		}
		instance = Optional.empty();
	}

	public static void setSystemProperty(boolean doSet) {
		setSystemProperty = doSet;
	}

	private static synchronized void createInstance() {

		if((instance == null) || !instance.isPresent()) {
			instance = Optional.of(getDriver());
		}
	}

	private static WebDriver getDriver() {
		WebDriver ret = null;

		if(type == null) {

			readDriverTypeFromSystemProperty();
			type = WebDriverType.Chrome;
		}

		switch (type) {
			case Chrome:
				ret = getChromeDriver();
				break;
			case Firefox:
				ret = new FirefoxDriver();
				break;
			case HtmlUnit_headless:
				ret = getHtmlUnitDriver();
				break;
			case PhantomJS_headless:
			default:
				ret = new PhantomJSDriver(adjustCapabilities());
				break;
		}

		ret.get("http://www.google.de");
		String originalHandle = ret.getWindowHandle();

		for(String handle : ret.getWindowHandles()) {
			if (!handle.equals(originalHandle)) {
				ret.switchTo().window(handle);
				ret.close();
			}
		}

		ret.switchTo().window(originalHandle);

		addShutdownHook();
		
		return ret;
	}

	private static void readDriverTypeFromSystemProperty() {

		String driverTypeProperty = System.getProperty("lmsreader.webdriver.type");

	}

	private static WebDriver getHtmlUnitDriver() {
		WebDriver ret;
		ret = new HtmlUnitDriver();
		((HtmlUnitDriver) ret).setJavascriptEnabled(true);
		ret.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return ret;
	}

	private static WebDriver getChromeDriver() {
		WebDriver ret;

		String chromeDriverSystemPropertyValue = System.getProperty("webdriver.chrome.driver");
		if(StringUtils.isEmpty(chromeDriverSystemPropertyValue)) {

			LOGGER.info("Chrome driver path not setting, falling back to default.");
            System.setProperty("webdriver.chrome.driver", DEFAULT_CHROME_DRIVER_PATH);
			chromeDriverSystemPropertyValue = System.getProperty("webdriver.chrome.driver");
        }
		LOGGER.info("Using Chrome web driver from \"" + chromeDriverSystemPropertyValue + "\".");

		ChromeOptions options = new ChromeOptions();
		File adblockCrx = new File("AdBlock_2_59.crx");
		options.addExtensions(adblockCrx);
		ret = new ChromeDriver(options);

		return ret;
	}

	private static DesiredCapabilities adjustCapabilities() {
		final DesiredCapabilities ret = new DesiredCapabilities();
		ret.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, getPhantomJSBinaryPath());
//		// Set tlsv1 ssl protocol. Default is sslv3, but
//		// https://denkanstoss.netpioneer.de/ supports tlsv1 only.
//		ret.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] { "--ssl-protocol=tlsv1" }); //
		return ret;
	}


	/**
	 * Returns the path of the PhantomJS binary. Set the system property "webdriver.phantomjs.driver" the th elocation of th edriver's executable
	 * if PhantomJS does not work.
	 *
	 * @return Path of the PhantomJS binary
	 */
	private static String getPhantomJSBinaryPath() {

		String phantomJSBinaryPath = System.getProperty("webdriver.phantomjs.driver");

		if(StringUtils.isEmpty(phantomJSBinaryPath)) {

			LOGGER.info("PahntomJS driver path not set, falling back to default.");
			if (SystemUtils.IS_OS_WINDOWS) {
				phantomJSBinaryPath = "phantomjs/phantomjs-2.1.1-windows/bin/phantomjs.exe";
			} else {
				phantomJSBinaryPath = "phantomjs/phantomjs-2.1.1-linux-x86_64/bin/phantomjs";
			}

		}
		LOGGER.info("Using PhantomJS web driver from \"" + phantomJSBinaryPath + "\".");

		return phantomJSBinaryPath;
	}

	private static void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				instance.get().close();
				instance.get().quit();
			}
		});
	}

}
