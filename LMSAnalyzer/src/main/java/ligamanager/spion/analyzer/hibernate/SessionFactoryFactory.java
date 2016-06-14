package ligamanager.spion.analyzer.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

/**
 * Created by jpralle on 14.06.2016.
 */
public abstract class SessionFactoryFactory {

    private static final Logger LOGGER = Logger.getLogger(SessionFactoryFactory.class);

    private static SessionFactory factory;

    public static void setFactory(SessionFactory newFactory) {
        factory = newFactory;
    }

    public static SessionFactory getFactory() {
        if (factory == null) {
            createFactory();
        }
        return factory;
    }

    private synchronized static void createFactory() {
        if(factory == null) {
            try {

                ClassLoader classLoader = SessionFactoryFactory.class.getClassLoader();
                File cfgXml = new File(classLoader.getResource("hibernate.cfg.xml").getFile());
                Configuration config = new Configuration().configure(cfgXml.getAbsoluteFile());
                //TODO: maybe the timezone hast to be changed at some point, as "EST" is just SUMMER time and will be switched to
                //something else when summer time ends.
                factory = config.addAnnotatedClass(LmGameHibernateBean.class).buildSessionFactory();

            } catch (Throwable ex) {
                LOGGER.error("Failed to create sessionFactory object. Exception: " + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
    }

}
