package ligamanager.spion.reader.useCases;

import ligamanager.spion.reader.TestData;
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