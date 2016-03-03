package ligamanager.spion.analyzer.pages;

import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class LMStartPage extends LMBasePage {

	private String pageUrl = "http://www.liga-manager.de/index.php";
	
	private WebElement userField;
	private WebElement passwordField;
	private WebElement loginButton;
	private WebElement logoutButton;

	public boolean navigateToPageAndCheck() {
		boolean ret = false;
		
		driver.get(pageUrl);
		
		ret = isOnCorrectPage();
		
		return ret;
	}

	public Optional<LMTeamChoicePage> login(String user, String password) {
		Optional<LMTeamChoicePage> ret = Optional.empty();

		if((userField == null) || (passwordField == null) || (loginButton == null)) {
			return ret;
		}

		userField.sendKeys(user);
		passwordField.sendKeys(password);
		loginButton.click();

		WebElement teamChoiceDropdown = driver.findElement(By.xpath("//select[@name=\"manager\"]"));

		if(teamChoiceDropdown != null) {
			LMTeamChoicePage teamChoicePage = new LMTeamChoicePage();
			if (teamChoicePage.isOnCorrectPage()) {
				ret = Optional.of(teamChoicePage);
			}
		}

		return ret;
	}

	public Optional<LMStartPage> logout() {
		Optional<LMStartPage> ret = Optional.empty();

		if(logoutButton == null) {
			return ret;
		}

		logoutButton.click();
		ret = Optional.of(this);

		initElements();

		return ret;
	}

	protected boolean isOnCorrectPageBody() {
		boolean ret = false;
		
		String title = driver.getTitle();
		
		if(title.contains("Liga-Manager | Der Fussballmanager im Internet!")) {
			ret = true;
		}

		try {
			WebElement kostenlosSpielenButton = driver.findElement(By.xpath("//*[@id=\"content_land\"]/div[2]/div[2]/a/img"));

			if(kostenlosSpielenButton != null) {
				ret = ret && true;
			}

			initElements();

		} catch (NoSuchElementException ex) {
			ret = false;
		}
		
		return ret;
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
