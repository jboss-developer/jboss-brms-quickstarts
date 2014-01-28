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
package org.jboss.quickstarts.brms;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

/**
 * @author rafaelbenevides
 * 
 */
public class DecisionTableTest {

    private static StatelessKieSession kSession;

    private static final String QUOTATION_GLOBAL = "$priceQuotation";
    private static final String GENDER_MALE = "MALE";
    private static final String GENDER_FEMALE = "FEMALE";

    @BeforeClass
    public static void setup() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        kSession = kContainer.newStatelessKieSession();
    }

    @Before
    public void beforeTest() throws InstantiationException, IllegalAccessException {
        PriceQuotation priceQuotation = new PriceQuotation();
        kSession.setGlobal(QUOTATION_GLOBAL, priceQuotation);
    }

    @Test
    public void testRuleStandardJunior() {
        CarProfile carProfile = new CarProfile();
        carProfile.setHasAlarm(false);
        carProfile.setHasGarage(true);
        DriverProfile driverProfile = new DriverProfile();
        driverProfile.setAge(20);
        driverProfile.setGender(GENDER_MALE);
        driverProfile.setHasPreviousIncidents(false);
        List<Serializable> objects = new ArrayList<Serializable>();
        objects.add(carProfile);
        objects.add(driverProfile);
        kSession.execute(objects);
        PriceQuotation priceQuotation = (PriceQuotation) kSession.getGlobals().get(QUOTATION_GLOBAL);
        assertEquals(1800, priceQuotation.getPrice().intValue());
    }

    @Test
    public void testRuleStandardJuniorRisk1() {
        CarProfile carProfile = new CarProfile();
        carProfile.setHasAlarm(false);
        carProfile.setHasGarage(false);
        DriverProfile driverProfile = new DriverProfile();
        driverProfile.setAge(20);
        driverProfile.setGender(GENDER_MALE);
        driverProfile.setHasPreviousIncidents(false);
        List<Serializable> objects = new ArrayList<Serializable>();
        objects.add(carProfile);
        objects.add(driverProfile);
        kSession.execute(objects);
        PriceQuotation priceQuotation = (PriceQuotation) kSession.getGlobals().get(QUOTATION_GLOBAL);
        assertEquals(2000, priceQuotation.getPrice().intValue());
    }

    @Test
    public void testRuleStandardJuniorRisk2() {
        CarProfile carProfile = new CarProfile();
        carProfile.setHasAlarm(false);
        carProfile.setHasGarage(false);
        DriverProfile driverProfile = new DriverProfile();
        driverProfile.setAge(20);
        driverProfile.setGender(GENDER_MALE);
        driverProfile.setHasPreviousIncidents(true);
        List<Serializable> objects = new ArrayList<Serializable>();
        objects.add(carProfile);
        objects.add(driverProfile);
        kSession.execute(objects);
        PriceQuotation priceQuotation = (PriceQuotation) kSession.getGlobals().get(QUOTATION_GLOBAL);
        assertEquals(2500, priceQuotation.getPrice().intValue());
    }

    @Test
    public void testRuleStandardYoungLady1() {
        CarProfile carProfile = new CarProfile();
        carProfile.setHasAlarm(false);
        carProfile.setHasGarage(false);
        DriverProfile driverProfile = new DriverProfile();
        driverProfile.setAge(20);
        driverProfile.setGender(GENDER_FEMALE);
        driverProfile.setHasPreviousIncidents(true);
        List<Serializable> objects = new ArrayList<Serializable>();
        objects.add(carProfile);
        objects.add(driverProfile);
        kSession.execute(objects);
        PriceQuotation priceQuotation = (PriceQuotation) kSession.getGlobals().get(QUOTATION_GLOBAL);
        assertEquals(1500, priceQuotation.getPrice().intValue());
    }

    @Test
    public void testRuleStandardM1() {
        CarProfile carProfile = new CarProfile();
        carProfile.setHasAlarm(false);
        carProfile.setHasGarage(false);
        DriverProfile driverProfile = new DriverProfile();
        driverProfile.setAge(30);
        driverProfile.setGender(GENDER_MALE);
        driverProfile.setHasPreviousIncidents(false);
        List<Serializable> objects = new ArrayList<Serializable>();
        objects.add(carProfile);
        objects.add(driverProfile);
        kSession.execute(objects);
        PriceQuotation priceQuotation = (PriceQuotation) kSession.getGlobals().get(QUOTATION_GLOBAL);
        assertEquals(1000, priceQuotation.getPrice().intValue());
    }

    @Test
    public void testRuleStandardM1Risk() {
        CarProfile carProfile = new CarProfile();
        carProfile.setHasAlarm(false);
        carProfile.setHasGarage(false);
        DriverProfile driverProfile = new DriverProfile();
        driverProfile.setAge(30);
        driverProfile.setGender(GENDER_MALE);
        driverProfile.setHasPreviousIncidents(true);
        List<Serializable> objects = new ArrayList<Serializable>();
        objects.add(carProfile);
        objects.add(driverProfile);
        kSession.execute(objects);
        PriceQuotation priceQuotation = (PriceQuotation) kSession.getGlobals().get(QUOTATION_GLOBAL);
        assertEquals(1100, priceQuotation.getPrice().intValue());
    }

    @Test
    public void testRuleStandardF() {
        CarProfile carProfile = new CarProfile();
        carProfile.setHasAlarm(false);
        carProfile.setHasGarage(false);
        DriverProfile driverProfile = new DriverProfile();
        driverProfile.setAge(30);
        driverProfile.setGender(GENDER_FEMALE);
        driverProfile.setHasPreviousIncidents(true);
        List<Serializable> objects = new ArrayList<Serializable>();
        objects.add(carProfile);
        objects.add(driverProfile);
        kSession.execute(objects);
        PriceQuotation priceQuotation = (PriceQuotation) kSession.getGlobals().get(QUOTATION_GLOBAL);
        assertEquals(900, priceQuotation.getPrice().intValue());
    }

    @Test
    public void testRuleSeniorStatePromotion() {
        CarProfile carProfile = new CarProfile();
        carProfile.setHasAlarm(false);
        carProfile.setHasGarage(false);
        DriverProfile driverProfile = new DriverProfile();
        driverProfile.setAge(60);
        driverProfile.setGender(GENDER_MALE);
        driverProfile.setHasPreviousIncidents(true);
        driverProfile.setState("NC");
        List<Serializable> objects = new ArrayList<Serializable>();
        objects.add(carProfile);
        objects.add(driverProfile);
        kSession.execute(objects);
        PriceQuotation priceQuotation = (PriceQuotation) kSession.getGlobals().get(QUOTATION_GLOBAL);
        assertEquals(200, priceQuotation.getPrice().intValue());
    }

    @Test
    public void testRuleSeniorStatePromotionPlus() {
        CarProfile carProfile = new CarProfile();
        carProfile.setHasAlarm(false);
        carProfile.setHasGarage(false);
        DriverProfile driverProfile = new DriverProfile();
        driverProfile.setAge(60);
        driverProfile.setGender(GENDER_MALE);
        driverProfile.setHasPreviousIncidents(false);
        driverProfile.setState("NC");
        List<Serializable> objects = new ArrayList<Serializable>();
        objects.add(carProfile);
        objects.add(driverProfile);
        kSession.execute(objects);
        PriceQuotation priceQuotation = (PriceQuotation) kSession.getGlobals().get(QUOTATION_GLOBAL);
        assertEquals(100, priceQuotation.getPrice().intValue());
    }
}
