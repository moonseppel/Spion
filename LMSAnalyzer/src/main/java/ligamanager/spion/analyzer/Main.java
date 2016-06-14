package ligamanager.spion.analyzer;

import java.util.List;
import java.util.StringTokenizer;

import ligamanager.spion.analyzer.util.LmIllegalGameException;
import ligamanager.spion.analyzer.util.LmGamePage;
import ligamanager.spion.analyzer.pages.LmTeamChoicePage;
import ligamanager.spion.analyzer.useCases.BasicActions;
import ligamanager.spion.analyzer.util.LmIllegalPageException;

public class Main {
	
	public static void main(String[] args) {

		int startSeason = -1;
		int endSeason = -1;
		int maxGameNumber = -1;

		try {
			parseSeasonNumber(args[0], startSeason, endSeason);
			maxGameNumber = Integer.parseInt(args[1]);

		} catch (NumberFormatException ex) {
			System.out.println("Error parsing parameters. Message: " + ex.getMessage());
			ex.printStackTrace();

			System.out.println();
			System.out.println();
			System.out.println("Syntax: java -jar lmsanalyzer.jar [seasons] [numberOfGames] [LM username] [LM password]");
			System.out.println("[seasons] maybe a range (\"10-12\") or a single season (\"1\").");
			System.out.println("[numberOfGames] is the number of games to retrieve for each season, always starting with game 1.");
			System.out.println("");
			return;
		}

		try {

			BasicActions.loginAndChooseFirstTeam("user", "password");
			LmGamePage gamePage = new LmGamePage(2328, 121);
			gamePage.navigateToPageAndCheck();
			System.out.println("Game Page found.");

		} catch(LmIllegalPageException ex) {
			System.out.println("Error navigating the page. Message: " + ex.getMessage());
			ex.printStackTrace();
			return;
		}
	}

	private static void parseSeasonNumber(String seasons, int startSeason, int endSeason) {

		if(seasons.contains("-")) {
			StringTokenizer tokenizer = new StringTokenizer(seasons, "-");

			startSeason = Integer.parseInt(tokenizer.nextToken());
			endSeason = Integer.parseInt(tokenizer.nextToken());

		} else {
			startSeason = Integer.parseInt(seasons);
			endSeason = startSeason;
		}
	}

}
