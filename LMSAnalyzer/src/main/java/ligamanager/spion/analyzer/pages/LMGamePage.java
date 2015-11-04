package ligamanager.spion.analyzer.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LMGamePage extends LMBasePage {

	private String pageUrl = "http://www.liga-manager.de/inc/spiel_info.php?id=";
	private int gameId = -1;
	
	private WebElement homeTeamName;
	private WebElement awayTeamName;
	
	public LMGamePage(int gameId) {
		this.gameId = gameId;
	}
	
	public int getGameId() {
		return gameId;
	}
	
	@Override
	public boolean navigateToPageAndCheck() {
		
		if(gameId < 0) {
			return false;
		}

		boolean ret = false;

		driver.get(pageUrl + gameId);
		
		ret = isOnCorrectPage();
		
		return ret;
	}

	@Override
	public boolean isOnCorrectPage() {
		boolean ret = false;
		
		String title = driver.getTitle();
		
		if(title.contains("Liga-Manager | Der Fussballmanager im Internet!")) {
			ret = true;
		}
		
		WebElement aufstellungLink = driver.findElement(By.xpath("//a[@href=\"javascript:open_window('"
				+ "spiel_aufstellung.php?id=2328&show_saison=119','Fenster2','scrollbars=yes,width=1022,height=725');\"]"));
		
		if(aufstellungLink != null && aufstellungLink.getText().equalsIgnoreCase("Aufstellung")) {
			ret = ret && true;
		}
		
		initElements();

		return ret;
	}
	
	public String getHomeTeamName() {
		
		String ret = extractTeamNameFromWebElementText(homeTeamName);
		return ret;
	}

	public String getAwayTeamName() {
		String ret = extractTeamNameFromWebElementText(awayTeamName);
		return ret;
	}

	private void initElements() {
		homeTeamName = driver.findElement(By.xpath("//div[@class='mannschaft'][1]"));
		awayTeamName = driver.findElement(By.xpath("//div[@class='mannschaft'][2]"));
	}

	private String extractTeamNameFromWebElementText(WebElement elem) {
		
		String ret = elem.getText();
		String startMarker = ".)";
		String endMarker = "(i)";
		
		int startIndex = ret.indexOf(startMarker);
		if(startIndex < 0) {
			startIndex = 0;
		}

		int endIndex = ret.lastIndexOf(endMarker);
		if(endIndex < 0) {
			endIndex = ret.length() - 1;
		}

		ret = ret.substring(startIndex + 3, endIndex);
		
		return ret;
	}

}
