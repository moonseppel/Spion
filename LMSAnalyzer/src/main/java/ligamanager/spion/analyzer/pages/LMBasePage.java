package ligamanager.spion.analyzer.pages;

import ligamanager.spion.analyzer.util.LmIllegalGameException;
import ligamanager.spion.analyzer.util.LmIllegalPageException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import ligamanager.spion.analyzer.webdriver.DriverFactory;

public abstract class LmBasePage {

	protected static WebDriver driver = DriverFactory.getInstance();
	
	abstract public void navigateToPageAndCheck() throws LmIllegalPageException;
	
	public void isOnCorrectPage() throws LmIllegalPageException {

		try {
			isOnCorrectPageWithException();
		} catch (NoSuchElementException ex) {
			throw new LmIllegalPageException(driver.getCurrentUrl());
		}
	}

	abstract protected void isOnCorrectPageWithException() throws NoSuchElementException, LmIllegalPageException;

}
