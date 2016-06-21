package ligamanager.spion.analyzer;

import ligamanager.spion.analyzer.hibernate.LmGameHibernateBean;
import ligamanager.spion.analyzer.hibernate.SessionFactoryFactory;
import ligamanager.spion.analyzer.util.GameFormation;
import ligamanager.spion.analyzer.util.Tactic;
import ligamanager.spion.analyzer.webdriver.DriverFactory;
import ligamanager.spion.analyzer.webdriver.WebDriverType;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by jpralle on 15.06.2016.
 */
public class MainTest {

    private static final Logger LOGGER = Logger.getLogger(MainTest.class);

    @Before
    public void setUpTests() {
        Main.firstSeason = -1;
        Main.lastSeason = -1;
        Main.firstGameNumber = -1;
        Main.lastGameNumber = -1;
    }

    @Test
    public void testParseParametersSimple() {


        String seasons = "122";
        String maxGameNumber = "0";
        String[] args = {seasons, maxGameNumber};

        boolean succesful = Main.parseParameters(args);

        assertThat(succesful, is(true));
        assertThat(Main.firstSeason, is(122));
        assertThat(Main.lastSeason, is(122));
        assertThat(Main.firstGameNumber, is(1));
        assertThat(Main.lastGameNumber, is(0));

    }

    @Test
    public void testParseParametersSeasonRange() {

        String seasons = "122-126";
        String maxGameNumber = "1";
        String[] args = {seasons, maxGameNumber};

        boolean succesful = Main.parseParameters(args);

        assertThat(succesful, is(true));
        assertThat(Main.firstSeason, is(122));
        assertThat(Main.lastSeason, is(126));
        assertThat(Main.firstGameNumber, is(1));
        assertThat(Main.lastGameNumber, is(1));

    }

    @Test
    public void testParseParametersIllegal1() {

        String seasons = "122-";
        String maxGameNumber = "1";
        String[] args = {seasons, maxGameNumber};

        boolean succesful = Main.parseParameters(args);

        assertThat(succesful, is(false));
        assertThat(Main.firstSeason, is(122));
        assertThat(Main.lastSeason, is(-1));
        assertThat(Main.firstGameNumber, is(1));
        assertThat(Main.lastGameNumber, is(1));

    }

    @Test
    public void testParseParametersillegal2() {

        String seasons = "122-126";
        String maxGameNumber = "no";
        String[] args = {seasons, maxGameNumber};

        boolean succesful = Main.parseParameters(args);

        assertThat(succesful, is(false));
        assertThat(Main.firstSeason, is(122));
        assertThat(Main.lastSeason, is(126));
        assertThat(Main.firstGameNumber, is(1));
        assertThat(Main.lastGameNumber, is(-1));

    }

    @Test
    public void testParseParametersWithGameRange() {

        String seasons = "122-126";
        String maxGameNumber = "12-3556";
        String[] args = {seasons, maxGameNumber};

        boolean succesful = Main.parseParameters(args);

        assertThat(succesful, is(true));
        assertThat(Main.firstSeason, is(122));
        assertThat(Main.lastSeason, is(126));
        assertThat(Main.firstGameNumber, is(12));
        assertThat(Main.lastGameNumber, is(3556));

    }

    @Test
    public void testMain() {
        initHibernateForTest();
        int season = 122;
        int maxGameNumber = 10;
        String[] args = {String.valueOf(season), String.valueOf(maxGameNumber), TestData.USERNAME, TestData.PASSWORD};
        int actual = Main.innerMain(args);

        assertThat(actual, is(0));

        for(int currentGame = 1; currentGame <= maxGameNumber; currentGame++) {
            LmGameHibernateBean gameBean = LmGameHibernateBean.read(currentGame, season);
            gameBean.delete();
        }
    }

    @Test
    public void testMainwithWrongSystemProperty() {
        initHibernateForTest();
        //this works only with google chrome
        DriverFactory.type = WebDriverType.Chrome;
        int season = 122;
        int maxGameNumber = 10;
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\blabla\\something\\chromedriver_win32\\chromedriver.exe");
        String[] args = {String.valueOf(season), String.valueOf(maxGameNumber), TestData.USERNAME, TestData.PASSWORD};
        int actual = Main.innerMain(args);

        System.clearProperty("webdriver.chrome.driver");

        assertThat(actual, is(-1));

    }

    @Test
    public void testMainWithRange() {
        initHibernateForTest();
        int firstSeason = 123;
        int lastSeason = 124;
        String season = firstSeason + "-" + lastSeason;
        int firstGame = 1;
        int lastGame = 2;
        String gameRange = firstGame + "-" + lastGame;
        String[] args = {season, gameRange, TestData.USERNAME, TestData.PASSWORD};
        int actual = Main.innerMainWithExceptions(args);

        assertThat(actual, is(0));

        for(int currentSeason = firstSeason; currentSeason <= lastSeason; currentSeason++) {
            for (int currentGame = firstGame; currentGame <= lastGame; currentGame++) {
                LmGameHibernateBean gameBean = LmGameHibernateBean.read(currentGame, currentSeason);
                gameBean.delete();
            }
        }
    }

    public static void initHibernateForTest() {
        try {

            ClassLoader classLoader = MainTest.class.getClassLoader();
            File cfgXml = new File(classLoader.getResource("hibernate_test.cfg.xml").getFile());
            Configuration config = new Configuration().configure(cfgXml.getAbsoluteFile());
            //TODO: maybe the timezone hast to be changed at some point, as "EST" is just SUMMER time and will be switched to
            //something else when summer time ends.
            config.setProperty("hibernate.connection.url",
                    "jdbc:mysql://www.cloneworks.de/d0223db7?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EST");

            config = config.addAnnotatedClass(LmGameHibernateBean.class)
                    .addAnnotatedClass(GameFormation.class)
                    .addAnnotatedClass(Tactic.class);
            SessionFactory factory = config.buildSessionFactory();
            SessionFactoryFactory.setFactory(factory);

        } catch (Throwable ex) {
            LOGGER.error("Failed to create sessionFactory object. Exception: " + ex);
            throw new ExceptionInInitializerError(ex);
        }

    }

}