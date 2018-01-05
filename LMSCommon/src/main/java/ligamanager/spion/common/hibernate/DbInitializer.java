package ligamanager.spion.common.hibernate;

import ligamanager.spion.common.GameFormation;
import ligamanager.spion.common.Tactic;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by jpralle on 22.06.2016.
 */
public class DbInitializer {

	private static final Logger LOGGER = Logger.getLogger(DbInitializer.class);

	public static void initialize() {

		LOGGER.info("Initilizing database...");

		Session session = SessionFactoryFactory.getFactory().openSession();
		Transaction tx = null;

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

		LOGGER.info("Initilizing database finished");
	}
}
