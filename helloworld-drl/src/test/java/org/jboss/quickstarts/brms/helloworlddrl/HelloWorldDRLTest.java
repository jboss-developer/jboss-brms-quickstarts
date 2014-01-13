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
package org.jboss.quickstarts.brms.helloworlddrl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

public class HelloWorldDRLTest {

    private static StatelessKieSession kSession;

    private Customer vipCustomer = new Customer("Mr. Good Customer", CustomerType.VIP);
    private Customer regularCustomer = new Customer("Mr. Typical Customer", CustomerType.REGULAR);
    private Customer badCustomer = new Customer("Mr. Bad Customer", CustomerType.BAD);

    // A sale for a VIP customer
    private Sale vipSale = new Sale(vipCustomer, new BigDecimal(1000));
    // A sale for a regular customer
    private Sale regularSale = new Sale(regularCustomer, new BigDecimal(1000));
    // A sale for a Bad customer
    private Sale badSale = new Sale(badCustomer, new BigDecimal(50));

    @BeforeClass
    public static void setup() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        kSession = kContainer.newStatelessKieSession();
    }

    @Test
    public void testGoodCustomer() {
        kSession.execute(vipSale);
        assertTrue(vipSale.isApproved());
        assertEquals(vipSale.getDiscount(), 0.50F, 0.0);
    }

    @Test
    public void testRegularCustomer() {
        kSession.execute(regularSale);
        assertTrue(regularSale.isApproved());
        assertEquals(regularSale.getDiscount(), 0.0, 0.0);
    }

    @Test
    public void testBadCustomer() {
        kSession.execute(badSale);
        assertFalse(badSale.isApproved());
    }

}
