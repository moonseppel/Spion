package ligamanager.spion.analyzer;

/**
 * Created by jpralle on 02.03.2016.
 */
public abstract class TestData {

    public static String USERNAME = "moonseppel";
    public static String PASSWORD = getPassword();

    private static String innerPassword = null;

    private static String getPassword() {

        if(innerPassword == null) {
            System.out.println("======> Please enter moonseppel's LM password:");
            innerPassword = System.console().readLine();
        }


        return innerPassword;
    }
}
