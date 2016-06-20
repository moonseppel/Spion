package ligamanager.spion.analyzer.useCases;

import java.util.Optional;

import ligamanager.spion.analyzer.pages.LmStartPage;
import ligamanager.spion.analyzer.pages.LmTeamChoicePage;
import ligamanager.spion.analyzer.util.LmIllegalPageException;

public class BasicActions {
	
	public static boolean loginAndChooseFirstTeam(String user, String password) {
		
		boolean ret = false;
		LmStartPage startPage = new LmStartPage();
		
		Optional<LmTeamChoicePage> teamChoicePage = Optional.empty();

		try {
			startPage.navigateToPageAndCheck();
			teamChoicePage = startPage.login(user, password);

			if (teamChoicePage.isPresent()) {
				ret = teamChoicePage.get().chooseFirstTeam();
			}

		} catch (LmIllegalPageException ex) {
			ret = false;
		}
		
		return ret;
	}

	public static boolean logout() {
		boolean ret = false;

		try {
			LmStartPage startPage = new LmStartPage();
			startPage.navigateToPageAndCheck();
			Optional<LmStartPage> loggedOutStartPage = startPage.logout();

			ret = loggedOutStartPage.isPresent();

		} catch (LmIllegalPageException ex) {
			ret = false;
		}

		return ret;
	}

}
