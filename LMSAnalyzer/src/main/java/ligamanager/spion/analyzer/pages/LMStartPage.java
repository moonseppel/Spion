package ligamanager.spion.analyzer.pages;

import java.util.Optional;

import ligamanager.spion.analyzer.util.LmIllegalPageException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class LmStartPage extends LmBasePage {

	private String pageUrl = "https://www.liga-manager.de/de";
	
	private WebElement userField;
	private WebElement passwordField;
	private WebElement loginButton;
	private WebElement logoutButton;

	public void navigateToPageAndCheck() throws LmIllegalPageException {

		getDriver().get(pageUrl);
		isOnCorrectPage();
	}

	public Optional<LmTeamChoicePage> login(String user, String password) {
		Optional<LmTeamChoicePage> ret = Optional.empty();

		if((userField == null) || (passwordField == null) || (loginButton == null)) {
			return ret;
		}

		userField.sendKeys(user);
		passwordField.sendKeys(password);
		loginButton.click();

		WebElement teamChoiceDropdown = getDriver().findElement(By.xpath("//*[@id=\"manager\"]"));

		if(teamChoiceDropdown != null) {
			LmTeamChoicePage teamChoicePage = new LmTeamChoicePage();

			try {
				teamChoicePage.isOnCorrectPage();
				ret = Optional.of(teamChoicePage);

			} catch (LmIllegalPageException e) {
				ret = Optional.empty();
			}
		}

		return ret;
	}

	public Optional<LmStartPage> logout() {
		Optional<LmStartPage> ret = Optional.empty();

		if(logoutButton == null) {
			return ret;
		}

		logoutButton.click();
		ret = Optional.of(this);

		initElements();

		return ret;
	}

	protected void isOnCorrectPageWithException() throws LmIllegalPageException {

		checkTitle();

		WebElement kostenlosSpielenButton = getDriver().findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/div[2]/a/img"));

		if(kostenlosSpielenButton == null) {
			throw new LmIllegalPageException("No \"kostenlos spielen\" button found: " + getDriver().getCurrentUrl());
		}

		initElements();

	}

	private void initElements() {
		try {
			tryInitElementsWhenUserIsLoggedOut();

		} catch (NoSuchElementException ex) {
			tryInitElementsWhenUserIsLoggedIn();
		}

	}
	private void tryInitElementsWhenUserIsLoggedOut() {
		userField = getDriver().findElement(By.xpath("//*[@id=\"user\"]"));
		passwordField = getDriver().findElement(By.xpath("//*[@id=\"pass\"]"));
		loginButton = getDriver().findElement(By.xpath("//input[@name=\"Go\"]"));
	}

	private void tryInitElementsWhenUserIsLoggedIn() {
		logoutButton = getDriver().findElement((By.xpath("//*[@id=\"loginarea_left\"]/div[5]/a/img")));
	}


}
