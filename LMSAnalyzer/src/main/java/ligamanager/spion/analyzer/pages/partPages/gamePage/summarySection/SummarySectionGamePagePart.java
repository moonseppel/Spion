package ligamanager.spion.analyzer.pages.partPages.gamePage.summarySection;

import ligamanager.spion.analyzer.pages.LmGamePage;
import ligamanager.spion.analyzer.util.GameResult;
import ligamanager.spion.analyzer.util.GameValuesInclPenalties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by jpralle on 16.05.2016.
 */
public abstract class SummarySectionGamePagePart {

    private static final String xpathToSummaryBox = "//*[@id=\"content_chat\"]/div[2]/table/tbody";

    protected WebDriver driver = null;
    protected LmGamePage fullPage = null;

    protected WebElement summaryBox = null;

    public SummarySectionGamePagePart(WebDriver driver, LmGamePage fullPage) {
        this.driver = driver;
        this.fullPage = fullPage;

        this.summaryBox = driver.findElement(By.xpath(xpathToSummaryBox));
    }

    public abstract GameResult getEndResult();
    public abstract GameValuesInclPenalties<GameResult> getResults();

}
