package de.cloneworks.ligamanger.spion.backend.scheduler.jobs;

import ligamanager.spion.common.webdriver.DriverFactory;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

class AutomaticLinUpJobTest {

    public void testDoLineUpSuccessfull() {
		WebDriver driver = DriverFactory.getInstance();

		AutomaticLineUpJob subject = new AutomaticLineUpJob(driver);
        boolean success = subject.doLineUp();

        assertThat(success, is(true));
    }

}