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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

/**
 * @author rafaelbenevides
 * 
 */
public class StoreBRMSTest {

    private static StatelessKieSession kSession;

    // A sale for a VIP customer
    private static Sale vipSale;
    // A sale for a regular customer
    private static Sale regularSale;
    // A sale for a Bad customer
    private static Sale badSale;
    // A sale for a young customer
    private static Sale youngSale;

    // The salesMan
    private static Salesman salesman = new Salesman();

    @BeforeClass
    public static void setup() {
        // A birthDate for a non-young customer
        Date oldDate = new GregorianCalendar(1977, 06, 10).getTime();
        // A birthDate for a non legal age customer
        Date youngDate = new GregorianCalendar(2010, 06, 10).getTime();

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        // Use a specified KSession from org.jboss.quickstarts.brms:my-store-brms-kmodule:6.1.0
        kSession = kContainer.newStatelessKieSession("storeKSession");
        // Setup the global variable with the Salesman
        salesman.setAllSales(new ArrayList<Sale>());
        kSession.getGlobals().set("$salesman", salesman);

        CustomerType regular = new CustomerType();
        regular.setType("regular");

        CustomerType vip = new CustomerType();
        vip.setType("VIP");

        CustomerType bad = new CustomerType();
        bad.setType("BAD");

        Customer young = new Customer();
        young.setBirthDate(youngDate);
        young.setCustomerType(regular);
        youngSale = new Sale();
        youngSale.setCustomer(young);

        Customer vipCustomer = new Customer();
        vipCustomer.setCustomerType(vip);
        vipCustomer.setBirthDate(oldDate);
        vipSale = new Sale();
        vipSale.setCustomer(vipCustomer);

        Customer regularCustomer = new Customer();
        regularCustomer.setCustomerType(regular);
        regularCustomer.setBirthDate(oldDate);
        regularSale = new Sale();
        regularSale.setCustomer(regularCustomer);

        Customer badCustomer = new Customer();
        badCustomer.setCustomerType(bad);
        badCustomer.setBirthDate(oldDate);
        badSale = new Sale();
        badSale.setCustomer(badCustomer);

    }

    @Test
    public void testGoodCustomer() {
        System.out.println("** Testing VIP customer **");
        kSession.execute(vipSale);
        // Sale approved
        assertTrue(vipSale.getApproved().booleanValue());
        // Discount of 0.5
        assertEquals(vipSale.getDiscount(), 0.50F, 0.0);
    }

    @Test
    public void testRegularCustomer() {
        System.out.println("** Testing regular customer **");
        kSession.execute(regularSale);
        // Sale approved
        assertTrue(regularSale.getApproved().booleanValue());
        // No Discount
        assertNull(regularSale.getDiscount());
    }

    @Test
    public void testBadCustomer() {
        System.out.println("** Testing BAD customer **");
        kSession.execute(badSale);
        // Sale denied
        assertFalse(badSale.getApproved().booleanValue());
    }

    @Test
    public void testYoungCustomer() {
        System.out.println("** Testing Young customer **");
        kSession.execute(youngSale);
        // Sale denied for young customer
        assertFalse(youngSale.getApproved());
    }

    @AfterClass
    public static void testSalesfromSalesMan() {
        System.out.println("** Testing if all sales were registered to salesman **");
        // Assert that all sales (bad,vip,regular,young) was registered after all
        assertEquals(4, salesman.getAllSales().size());
    }
}
