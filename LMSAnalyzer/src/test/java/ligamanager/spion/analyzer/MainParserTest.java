package ligamanager.spion.analyzer;

import org.apache.commons.lang3.Range;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by jpralle on 22.06.2016.
 */
public class MainParserTest {
    private static final Logger LOGGER = Logger.getLogger(MainIntegrationTest.class);

    @Test
    public void testParseParametersValue() {

        Optional<Range<Integer>> actual = Main.parseValueOrRangeParameter("1");

        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get().getMinimum(), is(1));
        assertThat(actual.get().getMaximum(), is(1));

    }

    @Test
    public void testParseParametersRange() {

        Optional<Range<Integer>> actual = Main.parseValueOrRangeParameter("1-2");

        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get().getMinimum(), is(1));
        assertThat(actual.get().getMaximum(), is(2));

    }

    @Test
    public void testParseParametersIllegalRange() {

        Optional<Range<Integer>> actual = Main.parseValueOrRangeParameter("1-");

        assertThat(actual.isPresent(), is(false));
    }

    @Test
    public void testParseParametersIllegalValue() {

        Optional<Range<Integer>> actual = Main.parseValueOrRangeParameter("broken");

        assertThat(actual.isPresent(), is(false));
    }

}
