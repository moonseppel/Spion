package ligamanager.spion.learningTests.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jpralle on 28.05.2016.
 */
public class EmployeeCRUD {

    private static final Logger LOGGER = Logger.getLogger(EmployeeCRUD.class);

    private SessionFactory factory;

    public EmployeeCRUD(SessionFactory factory) {
        this.factory = factory;
    }

    /* Method to CREATE an employee in the database */
    public Integer addEmployee(String fname, String lname, int salary) {

        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;

        try {
            tx = session.beginTransaction();

            Employee employee = new Employee(fname, lname, salary);
            employeeID = (Integer) session.save(employee);
            tx.commit();

        } catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            throw ex;

        } finally {
            session.close();
        }

        return employeeID;
    }

    /* Method to  READ all the employees */
    public List<Employee> listEmployees() {

        List<Employee> ret = new ArrayList<Employee>();
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            List employees = session.createQuery("FROM Employee").list();

            for (Iterator iterator = employees.iterator(); iterator.hasNext(); ) {

                Employee employee = (Employee) iterator.next();
                LOGGER.info("First Name: " + employee.getFirstName());
                LOGGER.info("  Last Name: " + employee.getLastName());
                LOGGER.info("  Salary: " + employee.getSalary());
                ret.add(employee);
            }

            tx.commit();

        } catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            throw ex;

        } finally {
            session.close();
        }

        return ret;
    }

    /* Method to UPDATE salary for an employee */
    public void updateEmployee(Integer EmployeeID, int salary) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Employee employee = (Employee) session.get(Employee.class, EmployeeID);
            employee.setSalary(salary);
            session.update(employee);

            tx.commit();

        } catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
    }

    /* Method to DELETE an employee from the records */
    public void deleteEmployee(Integer EmployeeID) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Employee employee = (Employee) session.get(Employee.class, EmployeeID);
            session.delete(employee);

            tx.commit();

        } catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            throw ex;

        } finally {
            session.close();
        }
    }

}
