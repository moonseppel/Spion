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
	
	public boolean navigateToPageAndCheck() {
		boolean ret = false;
		
		driver.get(pageUrl);
		
		ret = isOnCorrectPage();
		
		return ret;
	}

	public boolean isOnCorrectPage() {
		boolean ret = false;
		
		String title = driver.getTitle();
		
		if(title.contains("Liga-Manager | Der Fussballmanager im Internet!")) {
			ret = true;
		}

		try {
			WebElement demoLoginButton = driver.findElement(By.xpath("//img[@src=\"_media/images/buttons/button_demologin.gif\"]"));

			if(demoLoginButton != null) {
				ret = ret && true;
			}

			initElements();

		} catch (NoSuchElementException ex) {
			ret = false;
		}
		
		return ret;
	}
	
	public Optional<LMTeamChoicePage> login(String user, String password) {
		Optional<LMTeamChoicePage> ret = Optional.empty();
		
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
	
	
	private void initElements() {
		userField = driver.findElement(By.xpath("//input[@id=\"user\"]"));
		passwordField = driver.findElement(By.xpath("//input[@id=\"pass\"]"));
		loginButton = driver.findElement(By.xpath("//input[@name=\"Go\"]"));
		
	}

}
