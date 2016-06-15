package ligamanager.spion.analyzer.pages;

import java.util.Optional;

import ligamanager.spion.analyzer.util.LmIllegalGameException;
import ligamanager.spion.analyzer.util.LmIllegalPageException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class LmStartPage extends LmBasePage {

	private String pageUrl = "http://www.liga-manager.de/index.php";
	
	private WebElement userField;
	private WebElement passwordField;
	private WebElement loginButton;
	private WebElement logoutButton;

	public void navigateToPageAndCheck() throws LmIllegalPageException {

		driver.get(pageUrl);
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

		WebElement teamChoiceDropdown = driver.findElement(By.xpath("//select[@name=\"manager\"]"));

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

		String title = driver.getTitle();
		
		if(!title.contains("Liga-Manager | Der Fussballmanager im Internet!")) {
			throw new LmIllegalPageException("Error while checking page: " + driver.getCurrentUrl());
		}

		WebElement kostenlosSpielenButton = driver.findElement(By.xpath("//*[@id=\"content_land\"]/div[2]/div[2]/a/img"));

		if(kostenlosSpielenButton == null) {
			throw new LmIllegalPageException("Error while checking page: " + driver.getCurrentUrl());
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
		userField = driver.findElement(By.xpath("//input[@id=\"user\"]"));
		passwordField = driver.findElement(By.xpath("//input[@id=\"pass\"]"));
		loginButton = driver.findElement(By.xpath("//input[@name=\"Go\"]"));
	}

	private void tryInitElementsWhenUserIsLoggedIn() {
		logoutButton = driver.findElement((By.xpath("//*[@id=\"loginarea_left\"]/div[5]/a/img")));
	}


}
