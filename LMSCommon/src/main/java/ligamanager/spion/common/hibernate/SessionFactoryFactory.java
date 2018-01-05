package ligamanager.spion.common.hibernate;

import ligamanager.spion.common.GameFormation;
import ligamanager.spion.common.Tactic;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
                //File cfgXml = new File(classLoader.getResource("hibernate.cfg.xml").getFile());
                Configuration config = new Configuration().configure();//cfgXml);
                config = config.addAnnotatedClass(LmGameHibernateBean.class)
                                .addAnnotatedClass(GameFormation.class)
                                .addAnnotatedClass(Tactic.class);
                factory = config.buildSessionFactory();

            } catch (Throwable ex) {
                LOGGER.error("Failed to create sessionFactory object. Exception: " + ex);
                ex.printStackTrace();
                throw new ExceptionInInitializerError(ex);
            }
        }
    }

}
