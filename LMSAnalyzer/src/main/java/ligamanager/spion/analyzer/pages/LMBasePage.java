package ligamanager.spion.analyzer.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import ligamanager.spion.analyzer.webdriver.DriverFactory;

public abstract class LmBasePage {

	protected static WebDriver driver = DriverFactory.getInstance();
	
	abstract public boolean navigateToPageAndCheck();
	
	public boolean isOnCorrectPage() {
		boolean ret = false;

		try {
			ret = isOnCorrectPageWithException();
		} catch (NoSuchElementException ex) {
			ret = false;
		}

		return ret;
	}

	abstract protected boolean isOnCorrectPageWithException() throws NoSuchElementException;

}
