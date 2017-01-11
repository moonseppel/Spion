package ligamanager.spion.analyzer;

import ligamanager.spion.analyzer.hibernate.LmGameHibernateBean;
import ligamanager.spion.analyzer.hibernate.SessionFactoryFactory;
import ligamanager.spion.analyzer.util.GameFormation;
import ligamanager.spion.analyzer.util.Tactic;
import ligamanager.spion.analyzer.webdriver.DriverFactory;
import ligamanager.spion.analyzer.webdriver.WebDriverType;
import org.apache.log4j.Logger;
import org.hamcrest.core.IsNot;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by jpralle on 15.06.2016.
 */
public class MainIntegrationTest {

    private static final Logger LOGGER = Logger.getLogger(MainIntegrationTest.class);

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
            if(gameBean != null) {
                gameBean.delete();
            }
        }
    }

    @Test
    public void testMainwithWrongSystemProperty() {
        initHibernateForTest();
        //this has to be called early before calling Main.innerMain(), otherwise the test fails.
        //there seems to a timing thing with system properties or so.
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\blabla\\something\\chromedriver_win32\\chromedriver.exe");
        //this works only with google chrome
        DriverFactory.type = WebDriverType.Chrome;
        int season = 122;
        int maxGameNumber = 1;
        String[] args = {String.valueOf(season), String.valueOf(maxGameNumber), TestData.USERNAME, TestData.PASSWORD};
        int actual = Main.innerMain(args);

        System.clearProperty("webdriver.chrome.driver");

        assertThat(actual, is(-1));

        try {
            for (int currentGame = 1; currentGame <= maxGameNumber; currentGame++) {
                LmGameHibernateBean gameBean = LmGameHibernateBean.read(currentGame, season);
                gameBean.delete();
            }
        } catch (Exception ex) {
            LOGGER.warn("Non-critical Exception while deleting DB entries: " + ex.getMessage());
            ex.printStackTrace();
            LOGGER.warn("=== If the above test failed you need to empty the database manually. ===");
        }
    }

    @Test
    public void testMainNoGame() {
        initHibernateForTest();
        int season = 122;
        int gameNumber = 25129;
        String gameRange = gameNumber + "-" + gameNumber;
        String[] args = {String.valueOf(season), gameRange, TestData.USERNAME, TestData.PASSWORD};
        int actual = Main.innerMain(args);

        assertThat(actual, is(0));

        LmGameHibernateBean gameBean = LmGameHibernateBean.read(gameNumber, season);

        assertThat(gameBean, is(nullValue()));

        if(gameBean != null) {
            gameBean.delete();
        }
    }

    @Test
    public void testMainWithRange() {
        initHibernateForTest();

        GameReaderParameters params = new GameReaderParameters();
        params.firstSeason = 123;
        params.lastSeason = 124;
        params.firstGameNumber = 1;
        params.lastGameNumber = 2;
        fillFromTestData(params);
        int actual = new GameReader(params).readGames();

        assertThat(actual, is(0));

        for(int currentSeason = params.firstSeason; currentSeason <= params.lastSeason; currentSeason++) {
            for (int currentGame = params.firstGameNumber; currentGame <= params.lastGameNumber; currentGame++) {
                LmGameHibernateBean gameBean = LmGameHibernateBean.read(currentGame, currentSeason);
                gameBean.delete();
            }
        }
    }

    public static void initHibernateForTest() {
        try {

            ClassLoader classLoader = MainIntegrationTest.class.getClassLoader();
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

    private static void fillFromTestData(GameReaderParameters params) {
        params.user = TestData.USERNAME;
        params.password = TestData.PASSWORD;
    }

}