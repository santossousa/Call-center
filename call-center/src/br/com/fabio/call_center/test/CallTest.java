package br.com.fabio.call_center.test;

import org.junit.Test;

import br.com.fabio.call_center.model.Call;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CallTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCallCreationWithInvalidParameter() {
        new Call(-1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testRandomCallCreationWithInvalidFirstParameter() {
        Call.buildRandomCall(-1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomCallCreationWithInvalidSecondParameter() {
        Call.buildRandomCall(1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomCallCreationWithInvalidParameterOrder() {
        Call.buildRandomCall(2, 1);
    }

    @Test
    public void testRandomCallCreationWithValidParameters() {
        Integer min = 5;
        Integer max = 10;
        Call call = Call.buildRandomCall(min, max);

        assertNotNull(call);
        assertTrue(min <= call.getDuration());
        assertTrue(call.getDuration() <= max);
    }

}