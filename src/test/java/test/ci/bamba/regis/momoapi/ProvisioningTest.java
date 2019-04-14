package test.ci.bamba.regis.momoapi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import ci.bamba.regis.Environment;
import ci.bamba.regis.MoMo;
import ci.bamba.regis.Provisioning;
import ci.bamba.regis.exceptions.RequestException;
import io.reactivex.disposables.Disposable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class ProvisioningTest extends BaseTest {

    private Disposable disposable;

    public void init() {
        this.type = "collections";
        super.init();
    }

    @After
    public void takeDown() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Test
    public void testCreateProvisioning() {
        MoMo momo = new MoMo(Environment.SANDBOX);
        Provisioning provisioning = momo.createProvisioning(subscriptionKey);
        assertNotNull(provisioning);
        assertEquals(provisioning.getSubscriptionKey(), subscriptionKey);
        assertNotNull(provisioning.getBaseUrl());
        assertEquals(provisioning.getEnvironment(), momo.getEnvironment());
    }

    @Test
    public void testProvisioningCreateApiUserSuccess() {
        MoMo momo = new MoMo(Environment.SANDBOX);
        Provisioning provisioning = momo.createProvisioning(subscriptionKey);
        disposable = provisioning.createApiUser().subscribe(
                referenceId -> {
                    assertNotNull(referenceId);
                    assertEquals(36, referenceId.length());
                }, throwable -> {
                    RequestException e = (RequestException) throwable;
                    fail(String.format("\nCode: %s\nMessage: %s", e.getCode(), e.getMessage()));
                }
        );
    }

    @Test
    public void testProvisioningGetApiUser() {
        MoMo momo = new MoMo(Environment.SANDBOX);
        Provisioning provisioning = momo.createProvisioning(subscriptionKey);
        disposable = provisioning.createApiUser("www.example.com").flatMap(provisioning::getApiUser).subscribe(
                apiUser -> {
                    assertNotNull(apiUser);
                    assertEquals("sandbox", apiUser.getTargetEnvironment());
                }, throwable -> {
                    RequestException e = (RequestException) throwable;
                    fail(String.format("\nCode: %s\nMessage: %s", e.getCode(), e.getMessage()));
                }
        );
    }

    @Test
    public void testProvisioningCreateApiUserError() {
        MoMo momo = new MoMo(Environment.SANDBOX);
        Provisioning provisioning = momo.createProvisioning("");
        disposable = provisioning.createApiUser().subscribe(Assert::assertNull, throwable -> {
            RequestException e = (RequestException) throwable;
            assertEquals(401, e.getCode());
        });
    }

    @Test
    public void testProvisioningCreateApiKeySuccess() {
        MoMo momo = new MoMo(Environment.SANDBOX);
        Provisioning provisioning = momo.createProvisioning(subscriptionKey);
        disposable = provisioning.createApiUser().flatMap(referenceId -> {
            assertNotNull(referenceId);
            return provisioning.createApiKey(referenceId);
        }).subscribe(apiCredentials -> {
            assertNotNull(apiCredentials);
            assertNotEquals(0, apiCredentials.getKey().length());
        });
    }

    @Test
    public void testProvisioningCreateApiKeyError() {
        MoMo momo = new MoMo(Environment.SANDBOX);
        Provisioning provisioning = momo.createProvisioning(subscriptionKey);
        disposable = provisioning.createApiUser().flatMap(referenceId -> {
            assertNotNull(referenceId);
            return provisioning.createApiKey("err" + referenceId);
        }).subscribe(Assert::assertNull, throwable -> {
            RequestException e = (RequestException) throwable;
            assertEquals(400, e.getCode());
        });
    }

}
