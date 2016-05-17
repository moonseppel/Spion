package ligamanager.spion.analyzer.pages.partPages.gamePage.summarySection;

import ligamanager.spion.analyzer.pages.LMGamePage;
import ligamanager.spion.analyzer.util.GameResult;
import ligamanager.spion.analyzer.util.GameValues;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.StringTokenizer;

/**
 * Created by jpralle on 16.05.2016.
 */
public abstract class SummarySectionGamePagePart {

    private static final String xpathToSummaryBox = "//*[@id=\"content_chat\"]/div[2]/table/tbody";

    protected WebDriver driver = null;
    protected LMGamePage fullPage = null;

    protected WebElement summaryBox = null;

    public SummarySectionGamePagePart(WebDriver driver, LMGamePage fullPage) {
        this.driver = driver;
        this.fullPage = fullPage;

        this.summaryBox = driver.findElement(By.xpath(xpathToSummaryBox));
    }

    public abstract GameResult getEndResult();
    public abstract GameValues<GameResult> getResults();

}
