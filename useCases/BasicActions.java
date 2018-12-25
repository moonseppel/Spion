package ligamanager.spion.common.useCases;

import java.util.Optional;

import ligamanager.spion.common.pages.LmStartPage;
import ligamanager.spion.common.pages.LmTeamChoicePage;
import ligamanager.spion.reader.util.LmIllegalPageException;

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
			LmTeamChoicePage teamChoicePage = new LmTeamChoicePage();
			teamChoicePage.navigateToPageAndCheck();
			Optional<LmStartPage> loggedOutStartPage = teamChoicePage.logout();

			ret = loggedOutStartPage.isPresent();

		} catch (LmIllegalPageException ex) {
			ret = false;
		}

		return ret;
	}

}
