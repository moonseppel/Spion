package ligamanager.spion.analyzer.useCases;

import ligamanager.spion.analyzer.TestData;
import ligamanager.spion.analyzer.pages.LMStartPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jpralle on 01.03.2016.
 */
public class BasicActionsTest {

    @Test
    public void testLoginAndChooseFirstTeam() throws Exception {
        BasicActions.logout();
        assertTrue(BasicActions.loginAndChooseFirstTeam(TestData.USERNAME, TestData.PASSWORD));
    }

    @Test
    public void testLogout() throws Exception {
        BasicActions.loginAndChooseFirstTeam(TestData.USERNAME, TestData.PASSWORD);
        assertTrue(BasicActions.logout());
    }
}