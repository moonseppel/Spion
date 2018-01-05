package ligamanager.spion.reader.pages;

import ligamanager.spion.reader.util.LmIllegalPageException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import ligamanager.spion.reader.webdriver.DriverFactory;

import java.util.Optional;

public abstract class LmBasePage {

	protected static WebDriver getDriver() {
		return DriverFactory.getInstance();
	}

	abstract public void navigateToPageAndCheck() throws LmIllegalPageException;
	
	public void isOnCorrectPage() throws LmIllegalPageException {

		try {
			isOnCorrectPageWithException();
		} catch (NoSuchElementException ex) {
			throw new LmIllegalPageException("Element not found on: " + getDriver().getCurrentUrl(), ex);
		}
	}

	abstract protected void isOnCorrectPageWithException() throws NoSuchElementException, LmIllegalPageException;

	protected static void checkTitle() throws LmIllegalPageException {

		String title = getDriver().getTitle();
		if(title.length() > 0 && !title.contains("Liga-Manager | Der Fussballmanager im Internet!")) {
			throw new LmIllegalPageException("No or incorrect title found: " + getDriver().getCurrentUrl());
		}
	}

}
