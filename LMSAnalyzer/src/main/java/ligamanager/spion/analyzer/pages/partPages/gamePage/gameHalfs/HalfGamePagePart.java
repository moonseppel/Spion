package ligamanager.spion.analyzer.pages.partPages.gamePage.gameHalfs;

import ligamanager.spion.analyzer.util.*;
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

    protected GameResult halfsStrengthsBeginOfHalf;
    protected GameResult halfsStrengthsEndOfHalf;
    protected GameResult halfsStrengthsAverageOfHalf;


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

    public abstract void saveStrengthsBeginOfHalfTo(GameValues<GameResult> homeStrengthsBeginOfHalfs);
    public abstract void saveStrengthsEndOfHalfTo(GameValues<GameResult> homeStrengthsEndOfHalfs);
    public abstract void saveStrengthsAvergageOfHalfTo(GameValues<GameResult> homeStrengthsAverageOfHalfs);

    protected void parseFormationAndTactic() {

        String xpathToHomeFormationAndTactic = ".//td[2]/font";
        String xpathToAwayFormationAndTactic = ".//td[5]/font";

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

    protected void parseStrengths() {
        String xpathToAwayStrengths = ".//td[4]/font/div[2]";
        parseStrengths(xpathToAwayStrengths, false);
    }

    protected void parseStrengths(String xpathToAwayStrengths, boolean isExtraTime) {
        String xpathToHomeStrengths = ".//td[3]/font/div[2]"; //use strength with home bonus

        WebElement homeStrengthsElement = halfsWebElement.findElement(By.xpath(xpathToHomeStrengths));
        String homeStrengthsText = homeStrengthsElement.getText();
        StringTokenizer tokenizer = new StringTokenizer(homeStrengthsText, "\n");
        String homeAverageStrength = tokenizer.nextToken();//.replace("Ø", "").trim();
        homeAverageStrength = homeAverageStrength.substring(1).trim();
        String homeBeginStrength = tokenizer.nextToken().trim();
        if(isExtraTime) {
            tokenizer.nextToken();
        }
        String homeEndStrength = tokenizer.nextToken().trim();

        WebElement awayStrengthsElement = halfsWebElement.findElement(By.xpath(xpathToAwayStrengths));
        String awayStrengthsText = awayStrengthsElement.getText();
        tokenizer = new StringTokenizer(awayStrengthsText, "\n");
        String awayAverageStrength = tokenizer.nextToken();//.replace("Ø", "").trim();
        awayAverageStrength = awayAverageStrength.substring(1).trim();
        String awayBeginStrength = tokenizer.nextToken().trim();
        if(isExtraTime) {
            tokenizer.nextToken();
        }
        String awayEndStrength = tokenizer.nextToken().trim();

        halfsStrengthsBeginOfHalf = new GameResult(getIntFromDoubleString(homeBeginStrength), getIntFromDoubleString(awayBeginStrength));
        halfsStrengthsEndOfHalf = new GameResult(getIntFromDoubleString(homeEndStrength), getIntFromDoubleString(awayEndStrength));
        halfsStrengthsAverageOfHalf = new GameResult(getIntFromDoubleString(homeAverageStrength), getIntFromDoubleString(awayAverageStrength));
    }

    private int getIntFromDoubleString(String doubleString) {
        long ret = -1;

        String tmp = doubleString.replace(".", "").replace(",", ".");
        double d = Double.valueOf(tmp);
        ret = Math.round(d);

        return (int)ret;
    }

}
