package com.hull.api;

import com.hull.service.PersonService;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.mockito.Mock;

import javax.ws.rs.core.Application;

public class RecordsTest extends JerseyTest {

    @Mock
    private PersonService personService;

    @Override
    protected Application configure() {
        return new ResourceConfig(Records.class);
    }


}
