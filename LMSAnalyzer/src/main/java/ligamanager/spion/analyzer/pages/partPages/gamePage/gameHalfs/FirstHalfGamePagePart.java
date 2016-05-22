package ligamanager.spion.analyzer.pages.partPages.gamePage.gameHalfs;

import ligamanager.spion.analyzer.util.GameFormation;
import ligamanager.spion.analyzer.util.GameValues;
import ligamanager.spion.analyzer.util.Tactic;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by jpralle on 14.05.2016.
 */
public class FirstHalfGamePagePart extends HalfGamePagePart {

    private static final String xpathToFirstHalfElement = "//*[@id=\"content_chat\"]/div[1]/table[2]/tbody/tr[1]";

    public FirstHalfGamePagePart(WebDriver driver) {
        super(driver, xpathToFirstHalfElement);
    }

    @Override
    public void parseValues() {
        String xpathToHomeFormationAndTactic = ".//td[2]/font";
        String xpathToAwayFormationAndTactic = ".//td[5]/font";

        parseFormation(xpathToHomeFormationAndTactic, xpathToAwayFormationAndTactic);
    }

    @Override
    public void saveHomeFormationTo(GameValues<GameFormation> homeFormationsForSaving) {
        homeFormationsForSaving.firstHalf = halfsHomeFormation;
    }

    @Override
    public void saveAwayFormationTo(GameValues<GameFormation> awayFormationsForSaving) {
        awayFormationsForSaving.firstHalf = halfsAwayFormation;
    }

    @Override
    public void saveHomeTacticTo(GameValues<Tactic> homeTacticsForSaving) {
        homeTacticsForSaving.firstHalf = halfsHomeTactic;
    }

    @Override
    public void saveAwayTacticTo(GameValues<Tactic> awayTacticsForSaving) {
        awayTacticsForSaving.firstHalf = halfsAwayTactic;
    }
}
