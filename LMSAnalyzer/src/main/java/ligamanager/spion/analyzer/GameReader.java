package ligamanager.spion.analyzer;

import ligamanager.spion.analyzer.hibernate.LmGameHibernateBean;
import ligamanager.spion.analyzer.pages.LmGamePage;
import ligamanager.spion.analyzer.useCases.BasicActions;
import ligamanager.spion.analyzer.util.LmIllegalGameException;
import ligamanager.spion.analyzer.util.LmIllegalPageException;
import ligamanager.spion.analyzer.webdriver.DriverFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by jpralle on 22.06.2016.
 */
public class GameReader {
	private static final Logger LOGGER = Logger.getLogger(GameReader.class);

	private int lastLoginDay;
	private int maxRecusionDepth = 4;

	private GameReaderParameters params;

	public GameReader(GameReaderParameters params) {
		this.params = params;
	}

	public int readGames() {

		if(BasicActions.loginAndChooseFirstTeam(params.user, params.password)) {

			lastLoginDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);

		} else {
			String msg ="Error during login.";
			LOGGER.error(msg);
			System.out.println(msg);
			return -1;
		}

		for(int currentSeason = params.firstSeason; currentSeason <= params.lastSeason; currentSeason++) {
			for(int currentGame = params.firstGameNumber; currentGame <= params.lastGameNumber; currentGame++) {

				waitIfAuswertung();
				if(isJustAfterMidnight() && lastLoginNotToday()) {
					relogin();
				}

				readAndSaveSingleGame(currentSeason, currentGame);
			}
		}

		BasicActions.logout();

		return 0;
	}

	private void readAndSaveSingleGame(int currentSeason, int currentGame) {

		StringBuilder growableLogMessage = new StringBuilder("S: \"" + currentSeason + "\", G: \"" + currentGame + "\". ");

		if(readRecursive(currentGame, currentSeason, 0, growableLogMessage)) {
			LOGGER.info(growableLogMessage);
		} else {
			LOGGER.warn(growableLogMessage);
		}
	}

	private boolean readRecursive(int currentGame, int currentSeason, int currentRecusionDepth, StringBuilder growableLogMessage) {
		boolean ret = false;

		if(currentRecusionDepth >= maxRecusionDepth) {
			growableLogMessage.append("=== FAILURE === at depth " + currentRecusionDepth + ".");
			ret = false;
		} else {
			ret = tryRead(currentGame, currentSeason, growableLogMessage);

			if(!ret) {
				growableLogMessage.append(currentRecusionDepth + ": ");
				waitSeconds(10);
				if(currentRecusionDepth == 2) {
					relogin();
				}

				readRecursive(currentGame, currentSeason, currentRecusionDepth+1, growableLogMessage);
			}
		}

		return ret;
	}

	private static boolean tryRead(int currentGame, int currentSeason, StringBuilder growableLogMessage) {
		boolean cancelRecursion = true;

		LmGamePage gamePage = new LmGamePage(currentGame, currentSeason);

		try {

			gamePage.navigateToPageAndCheck();
			LmGameHibernateBean gameBean = new LmGameHibernateBean(gamePage);
			gameBean.save();
			growableLogMessage.append("Success.");

		} catch (LmIllegalGameException ex) {
			growableLogMessage.append("Message: " + ex.getMessage() + " === FAILURE ===");
			cancelRecursion = true;
		} catch (LmIllegalPageException ex) {
			growableLogMessage.append("Message: " + ex.getMessage() + " ");
			cancelRecursion = false;
		} catch (Exception ex) {
			growableLogMessage.append("Message: " + ex.getMessage() + " ");
			cancelRecursion = false;
		}

		return cancelRecursion;
	}

	private void relogin() {
		try {
			BasicActions.logout();
			BasicActions.loginAndChooseFirstTeam(params.user, params.password);
			lastLoginDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		} catch (NoSuchElementException ex) {
			LOGGER.warn("Relogin failed. Message: " + ex.getMessage() + " at " + DriverFactory.getInstance().getCurrentUrl());
		} catch (Exception ex) {
			LOGGER.warn("Relogin failed. Message: " + ex.getMessage() + ".");
		}
	}

	private boolean lastLoginNotToday() {
		int day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		return day != lastLoginDay;
	}

	private static void waitIfAuswertung() {
		Calendar cal = Calendar.getInstance();
		Integer hours = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);
		List<Integer> hoursAuswertung = new ArrayList<>();

		hoursAuswertung.add(15);
		hoursAuswertung.add(17);
		hoursAuswertung.add(19);
		hoursAuswertung.add(21);
		hoursAuswertung.add(0);

		if(hoursAuswertung.contains(hours) && minutes <= 11) {
			waitMinutes(11);

		} else if(hoursAuswertung.contains(hours-1) && minutes >= 58) {
			waitMinutes(13);
		}
	}

	private static boolean isJustAfterMidnight() {
		Calendar cal = Calendar.getInstance();
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);

		return hours == 0 && minutes < 20;
	}

	private static void waitMinutes(int minutes) {
		waitSeconds(minutes * 60);
	}

	private static void waitSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);

		} catch (InterruptedException e) {
			LOGGER.debug("Thread sleeping failed.");
		}
	}

}
