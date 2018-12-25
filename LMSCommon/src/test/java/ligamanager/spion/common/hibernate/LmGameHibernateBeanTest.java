package ligamanager.spion.common.hibernate;

import ligamanager.spion.common.GameIds;
import ligamanager.spion.reader.TestData;
import ligamanager.spion.common.useCases.BasicActions;
import ligamanager.spion.common.GameFormation;
import ligamanager.spion.common.pages.LmGamePage;
import ligamanager.spion.common.util.LmIllegalPageException;
import ligamanager.spion.common.Tactic;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by jpralle on 07.06.2016.
 */
public class LmGameHibernateBeanTest {

    private static final Logger LOGGER = Logger.getLogger(LmGameHibernateBeanTest.class);

    @BeforeClass
    public static void beforeClass() {
        try {

            ClassLoader classLoader = LmGameHibernateBeanTest.class.getClassLoader();
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

//        initialize();
    }

    @Test
    public void testSaveSingleGame() throws LmIllegalPageException {

        assertThat(BasicActions.loginAndChooseFirstTeam(TestData.USERNAME, TestData.PASSWORD), is(true));
        int gameId = 1;
        int seasonNumber = 132;
        LmGamePage gamePage = new LmGamePage(gameId, seasonNumber);
        gamePage.navigateToPageAndCheck();

        LmGameHibernateBean subjectAndExpected = gamePage.extractGameValues();
        GameIds ids = subjectAndExpected.save();

        LmGameHibernateBean actual = LmGameHibernateBean.read(gameId, seasonNumber);
        subjectAndExpected.delete();

        assertThat(BasicActions.logout(), is(true));

        assertThat(ids.getSeasonNumber(), is(seasonNumber));
        assertThat(ids.getGameId(), is(gameId));
        assertThat(actual, is(subjectAndExpected));
    }

    private static void initilizeDb() {

        Session session = SessionFactoryFactory.getFactory().openSession();
        Transaction tx = null;
        GameIds ids = null;

        try {
            tx = session.beginTransaction();

            session.save(GameFormation.EMPTY);
            for (GameFormation formation : GameFormation.ALL) {
                session.save(formation);
            }

            session.save(Tactic.EMPTY);
            for (Tactic tactic : Tactic.ALL) {
                session.save(tactic);
            }

            tx.commit();

        } catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            throw ex;

        } finally {
            session.close();
        }


    }

}