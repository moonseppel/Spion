package ligamanager.spion.analyzer.util;

/**
 * Created by jpralle on 09.06.2016.
 */
public class LmIllegalPageException extends Exception {
    public LmIllegalPageException(String urlOfPage) {
        super("Error while checking page: " + urlOfPage);
    }
}
