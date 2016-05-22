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
public class ExtraTimeGamePagePart extends HalfGamePagePart {

    private static final String xpathToExtraTimeElement = "//*[@id=\"content_chat\"]/div[1]/table[2]/tbody/tr[3]";

    public ExtraTimeGamePagePart(WebDriver driver) {
        super(driver, xpathToExtraTimeElement);
    }

    @Override
    public void parseValues() {
        String xpathToHomeFormation = ".//td[2]/strong/font";
        String xpathToAwayFormation = ".//td[5]/strong/font";

//        parseFormation(xpathToHomeFormation, xpathToAwayFormation);
    }

    @Override
    public void saveHomeFormationTo(GameValues<GameFormation> homeFormationsForSaving) {
//        homeFormationsForSaving.extraTime = halfsHomeFormation;
    }

    @Override
    public void saveAwayFormationTo(GameValues<GameFormation> awayFormationsForSaving) {
//        awayFormationsForSaving.extraTime = halfsAwayFormation;
    }

    @Override
    public void saveHomeTacticTo(GameValues<Tactic> homeTacticsForSaving) {
        //this is the same value as in the second half and set there
    }

    @Override
    public void saveAwayTacticTo(GameValues<Tactic> awayTacticsForSaving) {
        //this is the same value as in the second half and set there
    }
}
