package test.ci.bamba.regis.momoapi;

import org.junit.Test;

import ci.bamba.regis.Environment;
import ci.bamba.regis.MoMo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MoMoTest {

    @Test
    public void testNewMoMo() {
        MoMo momo = new MoMo(Environment.SANDBOX);
        assertNotNull(momo);
        assertEquals(Environment.SANDBOX, momo.getEnvironment());

        MoMo momo2 = new MoMo(Environment.PRODUCTION);
        assertNotNull(momo2);
        assertEquals(Environment.PRODUCTION, momo2.getEnvironment());
    }

}
