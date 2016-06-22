package ligamanager.spion.analyzer;

import ligamanager.spion.analyzer.hibernate.LmGameHibernateBean;
import ligamanager.spion.analyzer.pages.LmGamePage;
import ligamanager.spion.analyzer.useCases.BasicActions;
import ligamanager.spion.analyzer.util.LmIllegalGameException;
import ligamanager.spion.analyzer.util.LmIllegalPageException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jpralle on 22.06.2016.
 */
public class GameReader {
	private static final Logger LOGGER = Logger.getLogger(GameReader.class);

	private int lastLoginDay;
	private int maxRecusionDepth = 3;

	public int readGames(GameReaderParameters params) {

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
					relogin(params);
				}

				readAndSaveSingleGame(currentSeason, currentGame);
			}
		}

		BasicActions.logout();

		return 0;
	}

	private void readAndSaveSingleGame(int currentSeason, int currentGame) {

		LmGamePage gamePage = new LmGamePage(currentGame, currentSeason);
		String msg = "S: \"" + currentSeason + "\", G: \"" + currentGame + "\". ";


		if(readRecursive(currentGame, currentSeason, 0, msg)) {
			LOGGER.info(msg);
		} else {
			LOGGER.warn(msg);
		}
	}

	private boolean readRecursive(int currentGame, int currentSeason, int currentRecusionDepth, String growableLogMessage) {
		boolean ret = false;

		if(currentRecusionDepth >= maxRecusionDepth) {
			growableLogMessage += "Aborting recursion at depth " + currentRecusionDepth + ".";
			ret = false;
		} else {
			ret = tryRead(currentGame, currentSeason, growableLogMessage);
			if(!ret) {
				readRecursive(currentGame, currentSeason, currentRecusionDepth++, growableLogMessage);
			}
		}

		return ret;
	}

	private static boolean tryRead(int currentGame, int currentSeason, String growableLogMessage) {
		boolean ret = true;

		LmGamePage gamePage = new LmGamePage(currentGame, currentSeason);

		try {

			gamePage.navigateToPageAndCheck();
			LmGameHibernateBean gameBean = new LmGameHibernateBean(gamePage);
			gameBean.save();
			growableLogMessage += "Success.";

		} catch (LmIllegalGameException ex) {
			growableLogMessage += "=== FAILURE ==== Game type \"" + ex.getGameType() + "\". Message: " + ex.getMessage();
			ret = false;
		} catch (LmIllegalPageException ex) {
			growableLogMessage += "=== FAILURE ==== " + ex.getMessage();
			ret = false;
		} catch (Exception ex) {
			growableLogMessage += "=== FAILURE ==== General error. Message: " + ex.getMessage();
			ret = false;
		}

		return ret;
	}

	private void relogin(GameReaderParameters params) {
		BasicActions.logout();
		BasicActions.loginAndChooseFirstTeam(params.user, params.password);
		lastLoginDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
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

		if(hoursAuswertung.contains(hours)) {
			while(minutes <= 11) {
				waitSeconds(60);
				minutes = cal.get(Calendar.MINUTE);
			}
		}
	}

	private static boolean isJustAfterMidnight() {
		Calendar cal = Calendar.getInstance();
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);

		return hours == 0 && minutes < 20;
	}

	private static void waitSeconds(int seconds
	) {
		try {
			Thread.sleep(seconds * 1000);

		} catch (InterruptedException e) {
			LOGGER.debug("Thread sleeping failed.");
		}
	}

}
