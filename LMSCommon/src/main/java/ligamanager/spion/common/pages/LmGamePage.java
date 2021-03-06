package ligamanager.spion.common.pages;

import ligamanager.spion.common.*;
import ligamanager.spion.common.hibernate.LmGameHibernateBean;
import ligamanager.spion.common.pages.partPages.gamePage.gameHalfs.*;
import ligamanager.spion.common.pages.partPages.gamePage.summarySection.SummarySectionGamePagePart;
import ligamanager.spion.common.pages.partPages.gamePage.summarySection.SummarySectionPartWithExtraTime;
import ligamanager.spion.common.pages.partPages.gamePage.summarySection.SummarySectionPartWithPenaltyShooting;
import ligamanager.spion.common.pages.partPages.gamePage.summarySection.SummarySectionPartForRegularTime;
import ligamanager.spion.common.pages.util.StringParsingHelper;
import ligamanager.spion.common.util.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
public class LmGamePage extends LmBasePage {
	private static final Logger LOGGER = Logger.getLogger(LmGamePage.class);

	public static final String REGULAR_TIME_IDENTIFICATION_TEXT = "reguläre Spielzeit";
	public static final String EXTRA_TIME_IDENTIFICATION_TEXT = "Verlängerung";
	public static final String PENALTY_SHOOTING_IDENTIFICATIONTEXT = "Elfmeterschiessen";

	private SummarySectionGamePagePart summaryPart = null;
	private List<HalfGamePagePart> gameHalfParts = null;

	private String pageUrl = "https://www.liga-manager.de/spiel_info/?id={0}&show_saison={1}";
	private int gameId = -1;
	private int seasonNumber = -1;

	private boolean showsHomeBonus = false;

	private boolean hasExtraTime = false;
	private boolean hasPenaltyShooting = false;

	private String homeTeamName = null;
	private String awayTeamName = null;
	private GameResult endResult = null;
	private GameValuesInclPenalties<GameResult> results = null;
	private GameValues<GameFormation> homeFormations = null;
	private GameValues<GameFormation> awayFormations = null;
	private GameValues<Tactic> homeTactics = null;
	private GameValues<Tactic> awayTactics = null;
	private GameValues<GameResult> strengthsBeginOfHalfs = null;
	private GameValues<GameResult> strengthsAverageOfHalfs = null;
	private GameValues<GameResult> strengthsEndOfHalfs = null;

//	private GameValues<GameResult> homeAngriffe = null;
//	private GameValues<GameResult> awayAngriffe = null;
//	private GameValues<GameResult> homeChancen = null;
//	private GameValues<GameResult> awayChancen = null;
//	private GameValues<GameResult> homeBallPossession = null;
//	private GameValues<GameResult> awayBallPossession = null;
//	private GameResult homeBallPossessionTotal = null;
//	private GameResult awayBallPossessionTotal = null;
//	private GameValues<GameResult> homeZweikaempfe = null;
//	private GameValues<GameResult> awayZweikaempfe = null;
//	private GameResult homeZweikaempfeTotal = null;
//	private GameResult awayZweikaempfeTotal = null;


	public LmGamePage(int gameId, int seasonNo) {
		this.gameId = gameId;
		this.seasonNumber = seasonNo;
	}

	public int getGameId() {
		return gameId;
	}

	public int getSeasonNumber() {
		return seasonNumber;
	}

	public boolean showsHomeBonus() {
		return showsHomeBonus;
	}

	@Override
	public void navigateToPageAndCheck() throws LmIllegalPageException {

		if(seasonNumber < 0) {
			throw new LmIllegalPageException("Illegal season number \"" + seasonNumber + "\".");
		}

		if(gameId < 0) {
			throw new LmIllegalPageException("Illegal game id \"" + gameId + "\".");
		}

		String gameIdAsString = new Integer(gameId).toString();
		String seasonNoAsString = new Integer(seasonNumber).toString();
		String formattedPageUrl = MessageFormat.format(pageUrl, gameIdAsString, seasonNoAsString);
		LOGGER.info("Opening url: " + formattedPageUrl);
		getDriver().get(formattedPageUrl);

		isOnCorrectPage();

		//the variant with home bonus will be the usaually used, as those strength values are normally more interesting
		switchHomeBonus();

		checkForExtraTimeAndPenaltyShooting();

		gameHalfParts = new ArrayList<HalfGamePagePart>();
		gameHalfParts.add(new FirstHalfGamePagePart(getDriver()));
		gameHalfParts.add(new SecondHalfGamePagePart(getDriver()));

		if(hasPenyltyShooting()) {
			summaryPart = new SummarySectionPartWithPenaltyShooting(getDriver(), this);
			gameHalfParts.add(new ExtraTimeGamePagePart(getDriver()));

		} else if(hasExtraTime()) {
			summaryPart = new SummarySectionPartWithExtraTime(getDriver(), this);
			gameHalfParts.add(new ExtraTimeGamePagePart(getDriver()));

		} else {
			summaryPart = new SummarySectionPartForRegularTime(getDriver(), this);
			gameHalfParts.add(new NoExtraTimeGamePart(getDriver()));
		}

	}

	private void switchHomeBonus() {

		String xpathToHomeBonusSwitchLink = "//*[@id=\"content_popup\"]/div[1]/table[1]/tbody/tr/td[2]/a";
		WebElement switchHomeBonusLink = getDriver().findElement(By.xpath(xpathToHomeBonusSwitchLink));
		switchHomeBonusLink.click();

		showsHomeBonus = !showsHomeBonus;
	}

	public boolean hasPenyltyShooting() {
		return hasPenaltyShooting;
	}

	public boolean hasExtraTime() {
		return hasExtraTime;
	}

	public GameResult getEndResult() {
		if(endResult == null) {
			endResult = summaryPart.getEndResult();
		}
		return endResult;
	}

	public String getHomeTeamName() {
		if(homeTeamName == null) {
			WebElement homeTeamNameElement = getDriver().findElement(By.xpath("//div[@class='mannschaft'][1]"));
			homeTeamName = StringParsingHelper.extractTeamNameFromWebElementText(homeTeamNameElement).trim();
		}
		return homeTeamName;
	}

	public String getAwayTeamName() {
		if(awayTeamName == null) {
			WebElement awayTeamNameElement = getDriver().findElement(By.xpath("//div[@class='mannschaft'][2]"));
			awayTeamName = StringParsingHelper.extractTeamNameFromWebElementText(awayTeamNameElement).trim();
		}
		return awayTeamName;
	}

	public GameValuesInclPenalties<GameResult> getResults() {
		if(results == null) {
			results = summaryPart.getResults();
		}
		return results;
	}

	public GameValues<GameFormation> getHomeFormations() {
		if(homeFormations == null) {
			parseValuesFromGameHalfs();
		}
		return homeFormations;
	}

	public GameValues<GameFormation> getAwayFormations() {
		if(awayFormations == null) {
			parseValuesFromGameHalfs();
		}
		return awayFormations;
	}

	public GameValues<Tactic> getHomeTactics() {
		if(homeTactics == null) {
			parseValuesFromGameHalfs();
		}
		return homeTactics;
	}

	public GameValues<Tactic> getAwayTactics() {
		if(awayTactics == null) {
			parseValuesFromGameHalfs();
		}
		return awayTactics;
	}

	public GameValues<GameResult> getStrengthsBeginOfHalfs() {
		if(strengthsBeginOfHalfs == null) {
			parseValuesFromGameHalfs();
		}
		return strengthsBeginOfHalfs;
	}

	public GameValues<GameResult> getStrengthsAverageOfHalfs() {
		if(strengthsAverageOfHalfs == null) {
			parseValuesFromGameHalfs();
		}
		return strengthsAverageOfHalfs;
	}

	public GameValues<GameResult> getStrengthsEndOfHalfs() {
		if(strengthsEndOfHalfs == null) {
			parseValuesFromGameHalfs();
		}
		return strengthsEndOfHalfs;
	}

	public LmGameHibernateBean extractGameValues(LmGamePage this) {

		LmGameHibernateBean game = new LmGameHibernateBean();

		game.gameId = this.getGameId();
		game.seasonNumber = this.getSeasonNumber();

		game.hasExtraTime = this.hasExtraTime();
		game.hasPenaltyShooting = this.hasPenyltyShooting();

		game.homeTeamName = this.getHomeTeamName();
		game.awayTeamName = this.getAwayTeamName();
		game.endResultHome = this.getEndResult().getHome();
		game.endResultAway = this.getEndResult().getAway();
		game.resultFirstHalfHome = this.getResults().firstHalf.getHome();
		game.resultFirstHalfAway = this.getResults().firstHalf.getAway();
		game.resultSecondHalfHome = this.getResults().secondHalf.getHome();
		game.resultSecondHalfAway = this.getResults().secondHalf.getAway();
		game.resultExtraTimeHome = this.getResults().extraTime.getHome();
		game.resultExtraTimeAway = this.getResults().extraTime.getAway();
		game.resultPenaltyShootingHome = this.getResults().penalityShooting.getHome();
		game.resultPenaltyShootingAway = this.getResults().penalityShooting.getAway();
		game.formationFirstHalfHome = this.getHomeFormations().firstHalf;
		game.formationSecondHalfHome = this.getHomeFormations().secondHalf;
		game.formationExtraTimeHome = this.getHomeFormations().extraTime;
		game.formationFirstHalfAway = this.getAwayFormations().firstHalf;
		game.formationSecondHalfAway = this.getAwayFormations().secondHalf;
		game.formationExtraTimeAway = this.getAwayFormations().extraTime;
		game.tacticFirstHalfHome = this.getHomeTactics().firstHalf;
		game.tacticSecondHalfHome = this.getHomeTactics().secondHalf;
		game.tacticExtraTimeHome = this.getHomeTactics().extraTime;
		game.tacticFirstHalfAway = this.getAwayTactics().firstHalf;
		game.tacticSecondHalfAway = this.getAwayTactics().secondHalf;
		game.tacticExtraTimeAway = this.getAwayTactics().extraTime;
		game.strengthBeginOfFirstHalfHome = this.getStrengthsBeginOfHalfs().firstHalf.getHome();
		game.strengthBeginOfFirstHalfAway = this.getStrengthsBeginOfHalfs().firstHalf.getAway();
		game.strengthBeginOfSecondHalfHome = this.getStrengthsBeginOfHalfs().secondHalf.getHome();
		game.strengthBeginOfSecondHalfAway = this.getStrengthsBeginOfHalfs().secondHalf.getAway();
		game.strengthBeginOfExtraTimeHome = this.getStrengthsBeginOfHalfs().extraTime.getHome();
		game.strengthBeginOfExtraTimeAway = this.getStrengthsBeginOfHalfs().extraTime.getAway();
		game.strengthAverageOfFirstHalfHome = this.getStrengthsAverageOfHalfs().firstHalf.getHome();
		game.strengthAverageOfFirstHalfAway = this.getStrengthsAverageOfHalfs().firstHalf.getAway();
		game.strengthAverageOfSecondHalfHome = this.getStrengthsAverageOfHalfs().secondHalf.getHome();
		game.strengthAverageOfSecondHalfAway = this.getStrengthsAverageOfHalfs().secondHalf.getAway();
		game.strengthAverageOfExtraTimeHome = this.getStrengthsAverageOfHalfs().extraTime.getHome();
		game.strengthAverageOfExtraTimeAway = this.getStrengthsAverageOfHalfs().extraTime.getAway();
		game.strengthEndOfFirstHalfHome = this.getStrengthsEndOfHalfs().firstHalf.getHome();
		game.strengthEndOfFirstHalfAway = this.getStrengthsEndOfHalfs().firstHalf.getAway();
		game.strengthEndOfSecondHalfHome = this.getStrengthsEndOfHalfs().secondHalf.getHome();
		game.strengthEndOfSecondHalfAway = this.getStrengthsEndOfHalfs().secondHalf.getAway();
		game.strengthEndOfExtraTimeHome = this.getStrengthsEndOfHalfs().extraTime.getHome();
		game.strengthEndOfExtraTimeAway = this.getStrengthsEndOfHalfs().extraTime.getAway();

		return game;
	}

	@Override
	protected void isOnCorrectPageWithException() throws LmIllegalPageException {

		checkTitle();

		WebElement firstHalfFormationAndTacticsElement;
		try {
			firstHalfFormationAndTacticsElement = getDriver().findElement(By.xpath("//*[@id=\"content_popup\"]/div[1]/table[2]/tbody/tr[1]/td[2]/span"));

		} catch (NoSuchElementException ex) {

			//Non-existing game has an empty page
			String msg = "Illegal game found.";
			LmIllegalGameException illegalGameEx = new LmIllegalGameException(msg, gameId, seasonNumber, IllegalGameType.NoGame);
			throw illegalGameEx;
		}

		if(firstHalfFormationAndTacticsElement != null) {
			String firstHalfFormationAndTacticsText = firstHalfFormationAndTacticsElement.getText();

			int linesInText = 0;
			StringTokenizer tokenizer = new StringTokenizer(firstHalfFormationAndTacticsText, "\n\r\f");
			while(tokenizer.hasMoreTokens()) {
				tokenizer.nextToken();
				linesInText++;
			}

			if(linesInText < 3) {

				LmIllegalGameException ex;
				String msg = "Illegal game found.";
				IllegalGameType gameType = IllegalGameType.UnknownGameType;

				if(linesInText == 1) {
					gameType = IllegalGameType.AmateurGame;
				}

				ex = new LmIllegalGameException(msg, gameId, seasonNumber, gameType);
				throw ex;
			}

		}
	}

	private void parseValuesFromGameHalfs() {
		homeFormations = new GameValues<GameFormation>();
		awayFormations = new GameValues<GameFormation>();
		homeTactics = new GameValues<Tactic>();
		awayTactics = new GameValues<Tactic>();
		strengthsBeginOfHalfs = new GameValues<GameResult>();
		strengthsAverageOfHalfs = new GameValues<GameResult>();
		strengthsEndOfHalfs = new GameValues<GameResult>();

		for (HalfGamePagePart halfGamePart : gameHalfParts) {

			halfGamePart.parseValues();

			halfGamePart.saveHomeFormationTo(homeFormations);
			halfGamePart.saveAwayFormationTo(awayFormations);
			halfGamePart.saveHomeTacticTo(homeTactics);
			halfGamePart.saveAwayTacticTo(awayTactics);
			halfGamePart.saveStrengthsBeginOfHalfTo(strengthsBeginOfHalfs);
			halfGamePart.saveStrengthsEndOfHalfTo(strengthsEndOfHalfs);
			halfGamePart.saveStrengthsAvergageOfHalfTo(strengthsAverageOfHalfs);
		}
	}

	private void checkForExtraTimeAndPenaltyShooting() {
		WebElement firstRowOfSummarySection = getDriver().findElement(By.xpath("//*[@id=\"content_popup\"]/div[2]/table/tbody/tr[1]/td"));

		hasExtraTime       = firstRowOfSummarySection.getText().contains(REGULAR_TIME_IDENTIFICATION_TEXT);
		hasPenaltyShooting = firstRowOfSummarySection.getText().contains(PENALTY_SHOOTING_IDENTIFICATIONTEXT);
	}

}
