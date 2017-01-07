package com.itinapi;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTestNg;
import org.testng.annotations.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.testng.Assert.assertEquals;

/**
 * Created by hamehta3 on 10/11/16.
 */
public class MyResourceTest extends JerseyTestNg.ContainerPerClassTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(MyResource.class);
    }

    @Test(priority = 1)
    public void first() throws Exception {
        test("Got it!");
    }

    private void test(final String expected) {
        final Response response = target("myresource").request().get();

        assertEquals(response.getStatus(), 200);
        assertEquals(response.readEntity(String.class), expected);
    }
}
