package ligamanager.spion.reader.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ligamanager.spion.reader.util.LmIllegalPageException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class LmTeamChoicePage extends LmBasePage {

	private String pageUrl = "https://www.liga-manager.de/account_teams";
	private String activeTeamPrefix = "- ";
	private String chooseTeamText = "Team wählen";

	private Select teamChoiceDropdown;
	private WebElement logoutButton;

	public void navigateToPageAndCheck() throws LmIllegalPageException {

		getDriver().get(pageUrl);
		isOnCorrectPage();
	}

	protected void isOnCorrectPageWithException() throws LmIllegalPageException {

		checkTitle();
		
		WebElement teamChoiceDropdown = getDriver().findElement(By.xpath("//*[@id=\"manager\"]"));
		
		if(teamChoiceDropdown == null) {
			throw new LmIllegalPageException("No team choice dropdown found: " + getDriver().getCurrentUrl());
		}
		
		initElements();
	}
	
	public boolean chooseFirstTeam() {
		boolean ret = false;
		
		if(getAllTeams().size() > 0) {
			teamChoiceDropdown.selectByIndex(1);
			ret = true;
		}
		
		return ret;
	}
	
	public List<String> getAllTeams() {
		List<String> ret = new ArrayList<String>();
		
		for(WebElement team : teamChoiceDropdown.getOptions()) {
			String teamName = team.getText();

			if(!teamName.contains(chooseTeamText)) {

				if (teamName.startsWith(activeTeamPrefix)) {
					teamName = teamName.substring(activeTeamPrefix.length()).trim();
				}
				ret.add(teamName);

			}
		}
		
		return ret;
	}
	
	public boolean selectTeam(int index) {
		boolean ret = false;
		
		if(teamChoiceDropdown.getOptions().size() > index) {
			teamChoiceDropdown.selectByIndex(index);
			ret = true;
		}
		
		return ret;
	}


	public Optional<LmStartPage> logout() {
		Optional<LmStartPage> ret = Optional.empty();

		if(logoutButton == null) {
			return ret;
		}

		logoutButton.click();
		LmStartPage startPage = new LmStartPage();
		try {
			startPage.navigateToPageAndCheck();
			ret = Optional.of(startPage);
		} catch (LmIllegalPageException e) {
			e.printStackTrace();
		}

		return ret;
	}

	private void tryInitElementsWhenUserIsLoggedIn() {
	}

	private void initElements() {
		teamChoiceDropdown = new Select(getDriver().findElement(By.xpath("//*[@id=\"manager\"]")));
		logoutButton = getDriver().findElements((By.xpath("//*[@href=\"/logout\"]"))).get(1);
	}

}
