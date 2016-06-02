package ligamanager.spion.analyzer.pages.partPages.gamePage.summarySection;

import ligamanager.spion.analyzer.pages.LmGamePage;
import ligamanager.spion.analyzer.pages.util.StringParsingHelper;
import ligamanager.spion.analyzer.util.GameResult;
import ligamanager.spion.analyzer.util.GameValuesInclPenalties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by jpralle on 16.05.2016.
 */
public class SummarySectionPartWithExtraTime extends SummarySectionGamePagePart {

    public SummarySectionPartWithExtraTime(WebDriver driver, LmGamePage fullPage) {
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
        ret.extraTime = StringParsingHelper.parseResultStringForEndResult(fullResultString);

        String xpathToRegularTimeResult = ".//tr[1]/td[1]";
        WebElement regularTimeResultElement = summaryBox.findElement(By.xpath(xpathToRegularTimeResult));
        String fullregularTimeResultString = regularTimeResultElement.getText();

        int resultStartIndex = fullregularTimeResultString.indexOf(LmGamePage.REGULAR_TIME_IDENTIFICATION_TEXT);
        String regularTimeResultString = fullregularTimeResultString.substring(
                                                resultStartIndex + LmGamePage.REGULAR_TIME_IDENTIFICATION_TEXT.length() + 1);

        ret.secondHalf = new GameResult(regularTimeResultString);

        ret.penalityShooting = GameResult.EMPTY;

        return ret;
    }
}
