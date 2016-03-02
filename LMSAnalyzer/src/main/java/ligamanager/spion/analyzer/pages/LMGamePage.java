package ligamanager.spion.analyzer.pages;

import ligamanager.spion.analyzer.util.GameResult;
import ligamanager.spion.analyzer.util.GameValues;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.MessageFormat;

/**
 * Ruft aus einem Spiel die folgenden Informationen ab:
 *  - Spiel-Id
 *  - Teamnamen (heim und auswärts)
 *  - Saisonnummer
 *  (ist im Link zur aufstellung versteckt: <a href="javascript:open_window('spiel_aufstellung.php?id=2426&amp;show_saison=119','Fenster2',
 *  'scrollbars=yes,width=1022,height=725');" class="grosser_link color_gruen">Aufstellung</a></h3>)
 *  - Stärke in Min 0, 45, 46, 90, 91, 120 & Durchschnitt
 *  - System HZ 1 und 2 und Verlängerung
 *  - Taktik HZ 1 und 2 und Verlängerung
 *  - Ergebnis nach 45, 90 und 120 min
 *  - Angriffe HZ 1 und 2 und Verlängerung
 *  - Torchancen HZ 1 und 2 und Verlängerung
 *  - Ballbesitz HZ 1 und 2 und Verlängerung & Total
 *  - Zweikämpfe  HZ 1 und 2 und Verlängerung & Total
 **/
public class LMGamePage extends LMBasePage {

	private static String seasonNoStartIdentifier = "show_saison=";
	private static String seasonNoEndIdentifier = "'";

	private String pageUrl = "http://www.liga-manager.de/inc/spiel_info.php?id={0}&show_saison={1}";
	private int gameId = -1;
	private int seasonNo = -1;

	private WebElement homeTeamName;
	private WebElement awayTeamName;
	private WebElement endResult;
	private GameValues<WebElement> results;
	private GameValues<WebElement> homeStrengthsBeginOfHalfs;
	private GameValues<WebElement> homeStrengthsAverageOfHalfs;
	private GameValues<WebElement> homeStrengthsEndOfHalfs;
	private GameValues<WebElement> awayStrengthsBeginOfHalfs;
	private GameValues<WebElement> awayStrengthsAverageOfHalfs;
	private GameValues<WebElement> awayStrengthsEndOfHalfs;
	private GameValues<WebElement> homeSystems;
	private GameValues<WebElement> awaySystems;
	private GameValues<WebElement> homeTactics;
	private GameValues<WebElement> awayTactocs;
	private GameValues<WebElement> homeAngriffe;
	private GameValues<WebElement> awayAngriffe;
	private GameValues<WebElement> homeChancen;
	private GameValues<WebElement> awayChancen;
	private GameValues<WebElement> homeBallPossession;
	private GameValues<WebElement> awayBallPossession;
	private WebElement homeBallPossessionTotal;
	private WebElement awayBallPossessionTotal;
	private GameValues<WebElement> homeZweikaempfe;
	private GameValues<WebElement> awayZweikaempfe;
	private WebElement homeZweikaempfeTotal;
	private WebElement awayZweikaempfeTotal;


	public LMGamePage(int gameId, int seasonNo) {
		this.gameId = gameId;
		this.seasonNo = seasonNo;
	}

	public int getGameId() {
		return gameId;
	}

	public GameResult getEndResult() {
		return new GameResult(4, 1);
	}

	@Override
	public boolean navigateToPageAndCheck() {
		
		if(gameId < 0) {
			return false;
		}

		boolean ret = false;

		String gameIdAsString = new Integer(gameId).toString();
		String seasonNoAsString = new Integer(seasonNo).toString();
		String formattedPageUrl = MessageFormat.format(pageUrl, gameIdAsString, seasonNoAsString);
		driver.get(formattedPageUrl);
		
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
		
		WebElement spielberichtText = driver.findElement(By.xpath("//*[@id=\"magazin_chat\"]/div/div[1]/strong"));

		if(spielberichtText != null && spielberichtText.getText().equalsIgnoreCase("Spielbericht")) {
			ret = ret && true;
		}
		
		initElements();

		return ret;
	}
	
	public String getHomeTeamName() {
		
		String ret = extractTeamNameFromWebElementText(homeTeamName).trim();
		return ret;
	}

	public String getAwayTeamName() {
		String ret = extractTeamNameFromWebElementText(awayTeamName).trim();
		return ret;
	}

	public int getSeasonNumber() {
		return seasonNo;
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
