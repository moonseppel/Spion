package ligamanager.spion.analyzer;

import java.util.List;

import ligamanager.spion.analyzer.pages.LMGamePage;
import ligamanager.spion.analyzer.pages.LMStartPage;
import ligamanager.spion.analyzer.pages.LMTeamChoicePage;
import ligamanager.spion.analyzer.useCases.BasicActions;

public class Main {
	
	public static void main(String[] args) {
		
		System.out.println("Login and team selection sucessfull: " + BasicActions.loginAndChooseFirstTeam("someone", "password"));
		LMTeamChoicePage teamChoicePage = new LMTeamChoicePage();
		if(teamChoicePage.navigateToPageAndCheck()) {
			List<String> teamNames = teamChoicePage.getAllTeams();
			System.out.print("Teams: ");
			for(String teamName : teamNames) {
				System.out.print(teamName + ", ");
			}
			System.out.println();
		}
		
		LMGamePage gamePage = new LMGamePage(2328, 121);
		System.out.println("Game Page found: " + gamePage.navigateToPageAndCheck());
		System.out.println("Game Id: " + gamePage.getGameId());
		System.out.println("Home Team Name: " + gamePage.getHomeTeamName());
		System.out.println("Away Team Name: " + gamePage.getAwayTeamName());
		
		System.exit(0);
	}

}
