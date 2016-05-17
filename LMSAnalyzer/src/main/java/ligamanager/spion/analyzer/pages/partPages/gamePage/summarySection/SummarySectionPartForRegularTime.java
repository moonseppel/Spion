package ligamanager.spion.analyzer.pages.partPages.gamePage.summarySection;

import ligamanager.spion.analyzer.pages.LMGamePage;
import ligamanager.spion.analyzer.pages.util.StringParsingHelper;
import ligamanager.spion.analyzer.util.GameResult;
import ligamanager.spion.analyzer.util.GameValues;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by jpralle on 16.05.2016.
 */
public class SummarySectionPartForRegularTime extends SummarySectionGamePagePart {

    public SummarySectionPartForRegularTime(WebDriver driver, LMGamePage fullPage) {
        super(driver, fullPage);
    }

    @Override
    public GameResult getEndResult() {
        String xpathToEndResult = ".//tr[1]/td[2]";
        WebElement endResultElement = summaryBox.findElement(By.xpath(xpathToEndResult));
        return StringParsingHelper.parseEndResult(endResultElement);
    }

    @Override
    public GameValues<GameResult> getResults() {
        GameValues<GameResult> ret = new GameValues<GameResult>();

        String xpathToEndResult = ".//tr[1]/td[2]";
        WebElement resultElement = summaryBox.findElement(By.xpath(xpathToEndResult));
        String fullResultString = resultElement.getText();

        ret.firstHalf = StringParsingHelper.parseResultStringForPartTimeResult(fullResultString);
        ret.secondHalf = StringParsingHelper.parseResultStringForEndResult(fullResultString);

        ret.extraTime = GameResult.EMPTY;
        ret.penalityShooting = GameResult.EMPTY;

        return ret;
    }

}
