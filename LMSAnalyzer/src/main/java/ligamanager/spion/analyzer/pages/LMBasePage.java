package ligamanager.spion.analyzer.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import ligamanager.spion.analyzer.webdriver.DriverFactory;

public abstract class LMBasePage {

	protected static WebDriver driver = DriverFactory.getInstance();
	
	abstract public boolean navigateToPageAndCheck();
	
	abstract public boolean isOnCorrectPage();

}
