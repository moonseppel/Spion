package ligamanager.spion.reader.util;

/**
 * Created by jpralle on 09.06.2016.
 */
public class LmIllegalPageException extends Exception {

    public LmIllegalPageException(String msg) {
        super(msg);
    }

    public LmIllegalPageException(String msg ,Exception ex) {
        super(msg, ex);
    }
}
