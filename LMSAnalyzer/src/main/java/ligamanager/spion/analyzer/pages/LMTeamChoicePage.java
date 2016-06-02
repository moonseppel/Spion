package ligamanager.spion.analyzer.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class LmTeamChoicePage extends LmBasePage {

	private String pageUrl = "http://www.liga-manager.de/index.php?menue=eigene_teams";
	private String activeTeamPrefix = "- ";
	private String chooseTeamText = "Team wählen";

	private Select teamChoiceDropdown;
	
	public boolean navigateToPageAndCheck() {
		boolean ret = false;
		
		driver.get(pageUrl);
		
		ret = isOnCorrectPage();
		
		return ret;
	}

	protected boolean isOnCorrectPageWithException() {
		boolean ret = false;
		
		String title = driver.getTitle();
		
		if(title.contains("Liga-Manager | Der Fussballmanager im Internet!")) {
			ret = true;
		}
		
		WebElement teamChoiceDropdown = driver.findElement(By.xpath("//select[@name=\"manager\"]"));
		
		if(teamChoiceDropdown == null) {
			ret = ret && true;
		}
		
		initElements();

		return ret;
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
	
	
	private void initElements() {
		teamChoiceDropdown = new Select(driver.findElement(By.xpath("//select[@name=\"manager\"]")));
	}

}
