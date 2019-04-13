package test.ci.bamba.regis.momoapi;

import org.junit.Before;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.fail;

public class BaseTest {

    protected String type;
    protected String subscriptionKey;

    @Before
    public void init() {
        Properties props = new Properties();
        try {
            InputStream in = new FileInputStream("src/test/resources/momoapi.properties");
            props.load(in);
            subscriptionKey = props.getProperty(type + ".subscriptionKey");
            if (subscriptionKey == null) {
                fail("no subscription key");
            }
        } catch (IOException e) {
            fail("no property file " + e.getMessage());
        }
    }

}
