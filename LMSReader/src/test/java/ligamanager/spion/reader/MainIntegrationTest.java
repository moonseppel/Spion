package ligamanager.spion.reader;

import ligamanager.spion.common.hibernate.LmGameHibernateBean;
import ligamanager.spion.common.hibernate.SessionFactoryFactory;
import ligamanager.spion.common.GameFormation;
import ligamanager.spion.common.Tactic;
import ligamanager.spion.common.webdriver.DriverFactory;
import ligamanager.spion.common.webdriver.WebDriverType;
import org.apache.log4j.Logger;
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

        for(int currentGame = 1; currentGame <= maxGameNumber; currentGame++) {
            LmGameHibernateBean gameBean = LmGameHibernateBean.read(currentGame, season);
            if(gameBean != null) {
                gameBean.delete();
            }
        }

        assertThat(actual, is(0));

    }

    @Test
    public void testMainwithWrongSystemProperty() {
        initHibernateForTest();
        //this has to be called early before calling Main.innerMain(), otherwise the test fails.
        //there seems to a timing thing with system properties or so.
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\blabla\\something\\chromedriver_win32\\chromedriver.exe");
        //this works only with google chrome
        DriverFactory.destroyInstance();
        DriverFactory.type = WebDriverType.Chrome;
        int season = 122;
        int maxGameNumber = 1;
        String[] args = {String.valueOf(season), String.valueOf(maxGameNumber), TestData.USERNAME, TestData.PASSWORD};
        int actual = Main.innerMain(args);

        System.clearProperty("webdriver.chrome.driver");

        try {
            for (int currentGame = 1; currentGame <= maxGameNumber; currentGame++) {
                LmGameHibernateBean gameBean = LmGameHibernateBean.read(currentGame, season);
                if(gameBean != null) {
                    gameBean.delete();
                }
            }
        } catch (Exception ex) {
            LOGGER.warn("Non-critical Exception while deleting DB entries: " + ex.getMessage());
            ex.printStackTrace();
        }

        assertThat(actual, is(-1));

        DriverFactory.type = null;
        DriverFactory.destroyInstance();

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
        params.firstSeason = 132;
        params.lastSeason = 133;
        params.firstGameNumber = 1;
        params.lastGameNumber = 2;
        fillFromTestData(params);
        int actual = new GameReader(params).readGames();

        for(int currentSeason = params.firstSeason; currentSeason <= params.lastSeason; currentSeason++) {
            for (int currentGame = params.firstGameNumber; currentGame <= params.lastGameNumber; currentGame++) {
                LmGameHibernateBean gameBean = LmGameHibernateBean.read(currentGame, currentSeason);

                assertThat(gameBean, is(notNullValue()));
                gameBean.delete();
            }

        }

        assertThat(actual, is(0));

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