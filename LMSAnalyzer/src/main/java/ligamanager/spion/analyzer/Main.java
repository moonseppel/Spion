package ligamanager.spion.analyzer;

import java.util.StringTokenizer;

import ligamanager.spion.analyzer.hibernate.GameIds;
import ligamanager.spion.analyzer.hibernate.LmGameHibernateBean;
import ligamanager.spion.analyzer.hibernate.SessionFactoryFactory;
import ligamanager.spion.analyzer.util.*;
import ligamanager.spion.analyzer.useCases.BasicActions;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.openqa.selenium.NoSuchElementException;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class);

	public static int firstSeason = -1;
	public static int lastSeason = -1;
	public static int maxGameNumber = -1;

	public static void main(String[] args) {
		int result = innerMain(args);
		System.exit(result);
	}

	public static int innerMain(String[] args) {
		return innerMainWithOffset(args, 0);
	}

	public static int innerMainWithOffset(String[] args, int gameOffset) {

		if(args.length == 1 && args[0].equalsIgnoreCase("initDB")) {
			LOGGER.info("Initilizing database...");
			initilizeDb();
			LOGGER.info("Initilizing database finished");
			return -1;
		}

		if(!parseParameters(args) && args.length == 4) {

			System.out.println("Error parsing parameters.");
			System.out.println();
			System.out.println();
			System.out.println("Syntax: java -jar lmsanalyzer.jar [seasons] [numberOfGames] [LM username] [LM password]");
			System.out.println("OR: java -jar lmsanalyzer.jar initdb");
			System.out.println("[seasons] maybe a range (\"10-12\") or a single season (\"1\").");
			System.out.println("[numberOfGames] is the number of games to retrieve for each season, always starting with game 1.");
			System.out.println("\"initdb\" initilizes a fresh db for runnign this application.");
			System.out.println("");
			return -1;
		}

		if(!BasicActions.loginAndChooseFirstTeam(args[2], args[3])) {
			System.out.println("Error during login.");
			return -1;
		}

		for(int currentSeason = firstSeason; currentSeason <= lastSeason; currentSeason++) {
			for(int currentGame = gameOffset+1; currentGame-gameOffset <= maxGameNumber; currentGame++) {

				LmGamePage gamePage = new LmGamePage(currentGame, currentSeason);
				String msg = "S: \"" + currentSeason + "\", G: \"" + currentGame + "\". ";

				try {
					gamePage.navigateToPageAndCheck();
					LmGameHibernateBean gameBean = new LmGameHibernateBean(gamePage);
					gameBean.save();
					msg += "Success.";

				} catch(LmIllegalGameException ex) {
					LOGGER.warn(msg);
					msg += "=== FAILURE ==== Game type \"" + ex.getGameType() + "\". Message: " + ex.getMessage();
				} catch(LmIllegalPageException ex) {
					LOGGER.warn(msg);
					msg += "=== FAILURE ==== " + ex.getMessage();
				} catch(Exception ex) {
					msg += "=== FAILURE ==== General error. Message: " + ex.getMessage();
					LOGGER.info(msg);
					msg = null;
					retry(currentGame, currentSeason);
				}

			}
		}
		
		BasicActions.logout();

		return 0;
	}

	public static boolean parseParameters(String args[]) {
		boolean ret = false;

		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			LOGGER.info("Input parameter no. " + i + ": " + arg);
		}

		try {
			if (args[0].contains("-")) {
				StringTokenizer tokenizer = new StringTokenizer(args[0], "-");

				ret = true;
				if(tokenizer.hasMoreTokens()) {
					firstSeason = Integer.parseInt(tokenizer.nextToken());
					if(tokenizer.hasMoreTokens()) {
						lastSeason = Integer.parseInt(tokenizer.nextToken());
					} else {
						ret = false;
					}
				} else {
					ret = false;
				}

				ret = ret && true;
				LOGGER.info("Parsed season range.");

			} else {
				firstSeason = Integer.parseInt(args[0]);
				lastSeason = firstSeason;
				ret = true;
				LOGGER.info("Parsed season number.");
			}

			maxGameNumber = Integer.parseInt(args[1]);
			ret = ret && true;
			LOGGER.info("Parsed maximum game number.");

		} catch (NumberFormatException ex) {
			LOGGER.error("Can't Parse input parameters. Message: " + ex.getMessage());
			ret = false;
		} catch (NoSuchElementException ex) {
			LOGGER.error("Can't Parse season range parameter. Message: " + ex.getMessage());
			ret = false;
		}

		return ret;
	}

	private static void retry(int currentGame, int currentSeason) {

		LmGamePage gamePage = new LmGamePage(currentGame, currentSeason);
		String msg = "S: \"" + currentSeason + "\", G: \"" + currentGame + "\". ";

		try {
			gamePage.navigateToPageAndCheck();
			LmGameHibernateBean gameBean = new LmGameHibernateBean(gamePage);
			gameBean.save();
			msg += "Success.";

		} catch(LmIllegalGameException ex) {
			msg += "=== FAILURE ==== Game type \"" + ex.getGameType() + "\". Message: " + ex.getMessage();
		} catch(LmIllegalPageException ex) {
			msg += "=== FAILURE ==== " + ex.getMessage();
		} catch(Exception ex) {
			msg += "=== FAILURE ==== General error. Message: " + ex.getMessage();
		}

		LOGGER.warn(msg);
	}

	private static void initilizeDb() {

		Session session = SessionFactoryFactory.getFactory().openSession();
		Transaction tx = null;
		GameIds ids = null;

		try {
			tx = session.beginTransaction();

			session.save(GameFormation.EMPTY);
			for (GameFormation formation : GameFormation.ALL) {
				session.save(formation);
			}

			session.save(Tactic.EMPTY);
			for (Tactic tactic : Tactic.ALL) {
				session.save(tactic);
			}

			tx.commit();

		} catch (HibernateException ex) {
			if (tx != null) tx.rollback();
			throw ex;

		} finally {
			session.close();
		}


	}
}
