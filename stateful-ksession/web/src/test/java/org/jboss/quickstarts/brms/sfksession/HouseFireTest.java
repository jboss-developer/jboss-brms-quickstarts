/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the 
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.quickstarts.brms.sfksession;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.quickstarts.brms.sfksession.model.Fire;
import org.jboss.quickstarts.brms.sfksession.model.House;
import org.jboss.quickstarts.brms.sfksession.model.Room;
import org.jboss.quickstarts.brms.sfksession.model.Sprinkler;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

/**
 * @author rafaelbenevides
 * 
 */
@RunWith(Arquillian.class)
public class HouseFireTest {

    /**
     * The location of the WebApp source folder so we know where to find the web.xml when deploying using Arquillian.
     */
    private static final String WEBAPP_SRC = "src/main/webapp";

    @Inject
    private KieSession kieSession;

    @Inject
    private House house;

    @Deployment
    public static Archive<?> getDeployment() {
        Logger.getLogger("org.jboss.shrinkwrap.resolver").setLevel(Level.SEVERE);
        File pom = new File("pom.xml");
        File[] libs = Maven.resolver()
            .loadPomFromFile(pom)
            .resolve("org.kie:kie-api", "org.drools:drools-compiler", "org.jboss.quickstarts.brms:brms-stateful-ksession-kmodule")
            // Avoid using org.jboss.quickstarts.brms:brms-stateful-ksession-kmodule from reactor
            .withClassPathResolution(false)
            .withTransitivity().asFile();

        Archive<?> archive = ShrinkWrap
            .create(WebArchive.class, "test.war")
            .addAsLibraries(libs)
            .addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/beans.xml"));
        return archive;
    }

    @Test
    public void assertAlarmWorkingAndSprinklerWorkingOnFire() {
        System.out.println("** Testing if Sprinkler and Alarm is working **");
        Room room = new Room("testRoom");
        Sprinkler sprinkler = new Sprinkler(room);
        kieSession.insert(house);
        kieSession.insert(sprinkler);
        kieSession.insert(room);
        // Put fire in the room
        FactHandle fireHandle = kieSession.insert(new Fire(room));
        kieSession.fireAllRules();
        // Sprinkler should be On
        assertTrue(sprinkler.isOn());
        // Alarm should be On
        assertTrue(house.isAlarmOn());
        
        //Remove the fire
        kieSession.delete(fireHandle);
        // There's no fire on the House
        kieSession.fireAllRules();
        // Sprinkler should be Off
        assertFalse(sprinkler.isOn());
        // Alarm should be Off
        assertFalse(house.isAlarmOn());
        
    }

    @Test
    public void assertSprinklerOffWihoutFire() {
        System.out.println("** Testing if Sprinkler and Alarm is off without fire **");
        Room room = new Room("testRoom");
        Sprinkler sprinkler = new Sprinkler(room);
        kieSession.insert(house);
        kieSession.insert(sprinkler);
        kieSession.insert(room);
        // There's no fire on the House
        kieSession.fireAllRules();
        // Sprinkler should be Off
        assertFalse(sprinkler.isOn());
        // Alarm should be Off
        assertFalse(house.isAlarmOn());
    }

}
