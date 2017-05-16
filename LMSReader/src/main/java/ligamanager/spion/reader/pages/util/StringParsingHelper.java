package ligamanager.spion.reader.pages.util;

import ligamanager.spion.common.GameResult;
import org.openqa.selenium.WebElement;

/**
 * Created by jpralle on 17.05.2016.
 */
public class StringParsingHelper {

    public static GameResult parseEndResult(WebElement resultElement) {

        String endResultCompleteString = resultElement.getText();
        int endOfResultIndex = endResultCompleteString.indexOf("(");
        String endResultAsString = endResultCompleteString.substring(0, endOfResultIndex).trim();

        return new GameResult(endResultAsString);
    }

    public static GameResult parseResultStringForPartTimeResult(String fullResultString) {
        int splitIndex = fullResultString.indexOf("(");
        int endIndex = fullResultString.indexOf(")");
        String partTimeResult = fullResultString.substring(splitIndex+1, endIndex);

        return new GameResult(partTimeResult);
    }

    public static GameResult parseResultStringForEndResult(String fullResultString) {
        int splitIndex = fullResultString.indexOf("(");
        String endResult = fullResultString.substring(0, splitIndex);

        return new GameResult(endResult);
    }

    public static String extractTeamNameFromWebElementText(WebElement elem) {

        String ret = elem.getText();
        String startMarker = ".)";
        String endMarker = "(i)";

        int startIndex = ret.indexOf(startMarker);
        if(startIndex < 0) {
            startIndex = 0;
        }

        int endIndex = ret.lastIndexOf(endMarker);
        if(endIndex < 0) {
            endIndex = ret.length() - 1;
        }

        ret = ret.substring(startIndex + 3, endIndex);

        return ret;
    }
}
