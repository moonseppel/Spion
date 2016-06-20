package ligamanager.spion.analyzer.pages.partPages.gamePage.summarySection;

import ligamanager.spion.analyzer.pages.LmGamePage;
import ligamanager.spion.analyzer.pages.util.StringParsingHelper;
import ligamanager.spion.analyzer.util.GameResult;
import ligamanager.spion.analyzer.util.GameValuesInclPenalties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.StringTokenizer;

/**
 * Created by jpralle on 16.05.2016.
 */
public class SummarySectionPartWithPenaltyShooting extends SummarySectionGamePagePart {

    public SummarySectionPartWithPenaltyShooting(WebDriver driver, LmGamePage fullPage) {
        super(driver, fullPage);
    }

    @Override
    public GameResult getEndResult() {
        String xpathToEndResult = ".//tr[2]/td[2]";
        WebElement endResultElement = summaryBox.findElement(By.xpath(xpathToEndResult));
        return StringParsingHelper.parseEndResult(endResultElement);
    }

    @Override
    public GameValuesInclPenalties<GameResult> getResults() {
        GameValuesInclPenalties<GameResult> ret = new GameValuesInclPenalties<GameResult>();

        String xpathToEndResult = ".//tr[2]/td[2]";
        WebElement resultElement = summaryBox.findElement(By.xpath(xpathToEndResult));
        String fullResultString = resultElement.getText();

        ret.firstHalf = StringParsingHelper.parseResultStringForPartTimeResult(fullResultString);

        String xpathToAllOtherResults = ".//tr[1]/td[2]";
        WebElement allOtherResultsElement = summaryBox.findElement(By.xpath(xpathToAllOtherResults));
        String allOtherResultsString = allOtherResultsElement.getText();

        StringTokenizer tokenizer = new StringTokenizer(allOtherResultsString, "\n");

        ret.secondHalf       = new GameResult(tokenizer.nextToken());
        ret.extraTime        = new GameResult(tokenizer.nextToken());
        ret.penalityShooting = new GameResult(tokenizer.nextToken());

        return ret;
    }
}
