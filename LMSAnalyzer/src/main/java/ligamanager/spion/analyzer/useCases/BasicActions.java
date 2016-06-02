package ligamanager.spion.analyzer.useCases;

import java.util.Optional;

import ligamanager.spion.analyzer.pages.LmStartPage;
import ligamanager.spion.analyzer.pages.LmTeamChoicePage;

public class BasicActions {
	
	public static boolean loginAndChooseFirstTeam(String user, String password) {
		
		boolean ret = false;
		LmStartPage startPage = new LmStartPage();
		
		Optional<LmTeamChoicePage> teamChoicePage = Optional.empty();
		if(startPage.navigateToPageAndCheck()) {
			teamChoicePage = startPage.login(user, password);
		}
		
		if(teamChoicePage.isPresent()) {
			ret = teamChoicePage.get().chooseFirstTeam();
		}
		
		return ret;
	}

	public static boolean logout() {
		boolean ret = false;

		LmStartPage startPage = new LmStartPage();
		startPage.navigateToPageAndCheck();
		Optional<LmStartPage> loggedOutStartPage = startPage.logout();

		ret = loggedOutStartPage.isPresent();

		return ret;
	}

}
