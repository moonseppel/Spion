package ligamanager.spion.analyzer.useCases;

import java.util.Optional;

import ligamanager.spion.analyzer.pages.LMStartPage;
import ligamanager.spion.analyzer.pages.LMTeamChoicePage;

public class BasicActions {
	
	public static boolean loginAndChooseFirstTeam(String user, String password) {
		
		boolean ret = false;
		LMStartPage startPage = new LMStartPage();
		
		Optional<LMTeamChoicePage> teamChoicePage = Optional.empty();
		if(startPage.navigateToPageAndCheck()) {
			teamChoicePage = startPage.login(user, password);
		}
		
		if(teamChoicePage.isPresent()) {
			ret = teamChoicePage.get().chooseFirstTeam();
		}
		
		return ret;
	}

}
