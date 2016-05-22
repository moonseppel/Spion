package ligamanager.spion.analyzer.pages.partPages.gamePage.gameHalfs;

import ligamanager.spion.analyzer.util.GameFormation;
import ligamanager.spion.analyzer.util.GameValues;
import ligamanager.spion.analyzer.util.GameValuesInclPenalties;
import ligamanager.spion.analyzer.util.Tactic;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.StringTokenizer;

/**
 * Created by jpralle on 14.05.2016.
 */
public abstract class HalfGamePagePart {

    protected WebDriver driver;
    protected WebElement halfsWebElement = null;

    protected GameFormation halfsHomeFormation = null;
    protected GameFormation halfsAwayFormation = null;

    protected Tactic halfsHomeTactic = null;
    protected Tactic halfsAwayTactic = null;

    public HalfGamePagePart(WebDriver driver, String xpathToHalfsElement) {
        this.driver = driver;
        if(xpathToHalfsElement != null) {
            halfsWebElement = driver.findElement(By.xpath(xpathToHalfsElement));
        }
    }

    public abstract void parseValues();

    public abstract void saveHomeFormationTo(GameValues<GameFormation> homeFormationsForSaving);
    public abstract void saveAwayFormationTo(GameValues<GameFormation> awayFormationsForSaving);

    public abstract void saveHomeTacticTo(GameValues<Tactic> homeTacticsForSaving);
    public abstract void saveAwayTacticTo(GameValues<Tactic> awayTacticsForSaving);

    protected void parseFormation(String xpathToHomeFormationAndTactic, String xpathToAwayFormationAndTactic) {

        WebElement homeFormationElement = halfsWebElement.findElement(By.xpath(xpathToHomeFormationAndTactic));
        String homeFormationAndTactic = homeFormationElement.getText();

        StringTokenizer tokenizer = new StringTokenizer(homeFormationAndTactic, "\n");
        String homeFormationId = tokenizer.nextToken();
        String homeTacticName = tokenizer.nextToken();

        halfsHomeFormation = GameFormation.getFormationFrom(homeFormationId);
        halfsHomeTactic = Tactic.getTacticFrom(homeTacticName);


        WebElement awayFormationElement = halfsWebElement.findElement(By.xpath(xpathToAwayFormationAndTactic));
        String awayFormationAndTactic = awayFormationElement.getText();

        tokenizer = new StringTokenizer(awayFormationAndTactic, "\n");
        String awayFormationId = tokenizer.nextToken();
        String awayTacticName = tokenizer.nextToken();

        halfsAwayFormation = GameFormation.getFormationFrom(awayFormationId);
        halfsAwayTactic = Tactic.getTacticFrom(awayTacticName);
    }

}
