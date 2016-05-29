package ligamanager.spion.learningTests.hibernate;

import com.fasterxml.classmate.AnnotationConfiguration;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by jpralle on 28.05.2016.
 */
public class TransactionTests {

    private static final Logger LOGGER = Logger.getLogger(TransactionTests.class);

    private static SessionFactory factory;
    private static EmployeeCRUD crud;

    @BeforeClass
    public static void beforeClass() {
        try {

            ClassLoader classLoader = TransactionTests.class.getClassLoader();
            File cfgXml = new File(classLoader.getResource("learningTests/hibernate_test.cfg.xml").getFile());
            Configuration config = new Configuration().configure(cfgXml.getAbsoluteFile());
            //maybe the timezone hast to be changed at some point, as "EST" is just SUMMER time and will be switched to
            //something else when summer time ends.
            config.setProperty("hibernate.connection.url",
                    "jdbc:mysql://www.cloneworks.de/d0223db7?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EST");
            factory = config.buildSessionFactory();
            crud = new EmployeeCRUD(factory);

        } catch (Throwable ex) {
            LOGGER.error("Failed to create sessionFactory object. Exception: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Test
    public void testAddEmployee() {

        int newSalary2 = 5000;

        try {

            /* Add few employee records in database */
            Integer empID1 = crud.addEmployee("Zara", "Ali", 1000);
            Integer empID2 = crud.addEmployee("Daisy", "Das", 5000);
            Integer empID3 = crud.addEmployee("John", "Paul", 10000);

            /* List down all the employees */
            List<Employee> employees = crud.listEmployees();
            for (Employee employee : employees) {
                assertTrue("Id of returned EMployee must match one of the three afore created.",
                        (employee.getId() == empID1) || (employee.getId() == empID2) || (employee.getId() == empID3));
            }

            /* Update employee's records */
            crud.updateEmployee(empID1, newSalary2);

            /* Delete an employee from the database */
            crud.deleteEmployee(empID2);

            /* List down new list of the employees */
            employees = crud.listEmployees();
            for (Employee employee : employees) {
                assertTrue("Id of returned EMployee must match one of the two afore created and not be number two, which was deleted.",
                        ((employee.getId() == empID1) || (employee.getId() == empID3)) && (employee.getId() != empID2));

                if(employee.getId() == empID1) {
                    assertTrue("Salary of number one must be the new value.",
                            employee.getSalary() == newSalary2);
                }
            }

            //cleanup
            crud.deleteEmployee(empID1);
            crud.deleteEmployee(empID3);
            employees = crud.listEmployees();

            assertEquals("Employee list must be empty in the end.", 0, employees.size());

        } catch (Exception ex) {
            ex.printStackTrace();
            fail("May not throw any exception, but threw: " + ex);
        }
    }

//    @Test
//    public void writeSchemaCreationForEMployee() {
//        AnnotationConfiguration configuration = new AnnotationConfiguration();
//
//        configuration.addAnnotatedClass(Employee.class)
//        .setProperty(Environment.USER, <TYPE_YOUR_USER>)
//        .setProperty(Environment.PASS, <TYPE_YOUR_PASSWORD>)
//        .setProperty(Environment.URL, <TYPE_YOUR_URL>)
//        .setProperty(Environment.DIALECT, <TYPE_YOUR_DIALECT>)
//        .setProperty(Environment.DRIVER, <TYPE_YOUR_DRIVER>);
//
//        SchemaExport schema = new SchemaExport(configuration);
//        schema.setOutputFile("schema.sql");
//
//        schema.create(<DO_YOU_WANT_TO_PRINT_TO_THE_CONSOLE>, <DO_YOU_WANT_TO_EXPORT_THE_SCRIPT_TO_THE_DATABASE>);
//    }
}
