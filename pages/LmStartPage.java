package ligamanager.spion.common.pages;

import java.util.Optional;

import ligamanager.spion.reader.util.LmIllegalPageException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LmStartPage extends LmBasePage {

	private String pageUrl = "https://www.liga-manager.de";
	
	private WebElement userField;
	private WebElement passwordField;
	private WebElement loginButton;

	public void navigateToPageAndCheck() throws LmIllegalPageException {

		getDriver().get(pageUrl);
		isOnCorrectPage();
	}

	public Optional<LmTeamChoicePage> login(String user, String password) {
		Optional<LmTeamChoicePage> ret = Optional.empty();

		if((userField == null) || (passwordField == null) || (loginButton == null)) {
			return ret;
		}

		userField.clear();
		userField.sendKeys(user);
		passwordField.clear();
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

	protected void isOnCorrectPageWithException() throws LmIllegalPageException {

		checkTitle();

		WebElement kostenlosSpielenButton = getDriver().findElement(By.xpath("//*[@id=\"form-login\"]/div[3]"));

		if(kostenlosSpielenButton == null) {
			throw new LmIllegalPageException("No \"kostenlos spielen\" button found: " + getDriver().getCurrentUrl());
		}

		initElements();

	}

	private void initElements() {

		tryInitElementsWhenUserIsLoggedOut();
	}
	private void tryInitElementsWhenUserIsLoggedOut() {
		userField = getDriver().findElement(By.xpath("//*[@id=\"user\"]"));
		passwordField = getDriver().findElement(By.xpath("//*[@id=\"pass\"]"));
		loginButton = getDriver().findElement(By.xpath("//*[@id=\"form-login\"]/div[3]"));
	}


}
