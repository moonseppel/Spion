package ligamanager.spion.analyzer.webdriver;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


//Check this: https://sonar.netpioneer.de/sonar/coding_rules#rule_key=squid:S2151
public abstract class DriverFactory {
	private static final Logger LOGGER = Logger.getLogger(DriverFactory.class);

	private static Optional<WebDriver> instance = Optional.empty();
	
	public static WebDriver getInstance() {
		if(!instance.isPresent()) {
			createInstance();
		}
		return instance.get();
	}

	private static synchronized void createInstance() {
		
		if((instance == null) || !instance.isPresent()) {
			instance = Optional.of(getDriver());
		}
	}

	private static WebDriver getDriver() {

		LOGGER.info("Using Chrome web driver from \"C:\\Users\\jpralle\\MyApps\\chromedriver_win32\\chromedriver.exe\".");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\jpralle\\MyApps\\chromedriver_win32\\chromedriver.exe");
		WebDriver ret = new ChromeDriver();
		
		addShutdownHook();
		
		return ret;
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
