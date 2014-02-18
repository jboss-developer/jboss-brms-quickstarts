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
package org.jboss.quickstarts.brms.cep;

import java.io.File;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.drools.core.time.SessionPseudoClock;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.quickstarts.brms.cep.model.Transaction;
import org.jboss.quickstarts.brms.cep.model.TransactionType;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

/**
 * @author rafaelbenevides
 * 
 */
@RunWith(Arquillian.class)
public class TransactionsTest {

    /**
     * The location of the WebApp source folder so we know where to find the web.xml when deploying using Arquillian.
     */
    private static final String WEBAPP_SRC = "src/main/webapp";

    @Inject
    @KSession("myKSessionTest")
    // use configured myKSession
    private KieSession kieSession;

    @Deployment
    public static Archive<?> getDeployment() {
        File pom = new File("pom.xml");
        File[] libs = Maven.resolver()
            .loadPomFromFile(pom)
            .resolve("org.kie:kie-api", "org.drools:drools-compiler", "org.jboss.quickstarts.brms:brms-helloworld-cep-kmodule")
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
    public void test3TransactionsIn5Seconds() {
        System.out.println("** Placing more than 3 transactions in less than 5 seconds **");
        Transaction t1 = new Transaction();
        Transaction t2 = new Transaction();
        Transaction t3 = new Transaction();
        Transaction t4 = new Transaction();
        kieSession.insert(t1);
        kieSession.insert(t2);
        kieSession.insert(t3);
        kieSession.insert(t4);
        kieSession.fireAllRules();
        // t4 should be denied
        Assert.assertTrue(t4.isDenied());
        System.out.println("* Transaction denied: " + t4.isDenied());
    }

    @Test
    public void test3TransactionsIn10Seconds() {
        System.out.println("** Placing more than 3 transaction is more than 5 seconds **");
        Transaction t1 = new Transaction();
        Transaction t2 = new Transaction();
        Transaction t3 = new Transaction();
        Transaction t4 = new Transaction();
        kieSession.insert(t1);
        kieSession.insert(t2);
        kieSession.insert(t3);

        // here we use a pseudoclock so we can forward session clock in 10 seconds before inserting t4
        SessionPseudoClock clock = kieSession.getSessionClock();
        clock.advanceTime(10, TimeUnit.SECONDS);

        kieSession.insert(t4);
        kieSession.fireAllRules();
        // t4 is not denied because it was inserted after 10 seconds
        Assert.assertFalse(t4.isDenied());
        System.out.println("* Transaction denied: " + t4.isDenied());
    }

    @Test
    public void test4CreditCardTransactions() {
        System.out.println("** Placing a Credit Card where its value is twice the medium of last 4 **");
        Transaction t1 = new Transaction(new BigInteger("10"), TransactionType.CREDIT_CARD);
        Transaction t2 = new Transaction(new BigInteger("10"), TransactionType.CREDIT_CARD);
        Transaction t3 = new Transaction(new BigInteger("10"), TransactionType.CREDIT_CARD);
        Transaction t4 = new Transaction(new BigInteger("10"), TransactionType.CREDIT_CARD);
        Transaction t5 = new Transaction(new BigInteger("100"), TransactionType.CREDIT_CARD);
        kieSession.insert(t1);
        kieSession.insert(t2);
        kieSession.insert(t3);
        kieSession.insert(t4);
        kieSession.insert(t5);
        kieSession.fireAllRules();
        // t5 is denied because it is twice the medium of last 4 transactions (t1, t2, t3, t4)
        Assert.assertTrue(t5.isDenied());
        System.out.println("* Transaction denied: " + t5.isDenied());
    }

    @Test
    public void testWithdrawIn10SecondAfterCreditCard() {
        System.out.println("** Placing a Withdraw after a Credit Card transaction in less than 10 seconds **");
        Transaction t1 = new Transaction(new BigInteger("10"), TransactionType.CREDIT_CARD);
        Transaction t2 = new Transaction(new BigInteger("10"), TransactionType.WITHDRAW);
        EntryPoint entryPoint = kieSession.getEntryPoint("Credit Card");
        entryPoint.insert(t1);
        kieSession.insert(t2);
        kieSession.fireAllRules();
        Assert.assertTrue(t2.isDenied());
        System.out.println("* Transaction denied: " + t2.isDenied());
    }

    @Test
    public void testWithdrawIn20SecondAfterCreditCard() {
        System.out.println("** Placing a Withdraw after a Credit Card transaction in more than 10 seconds **");
        Transaction t1 = new Transaction(new BigInteger("10"), TransactionType.CREDIT_CARD);
        Transaction t2 = new Transaction(new BigInteger("10"), TransactionType.WITHDRAW);
        EntryPoint entryPoint = kieSession.getEntryPoint("Credit Card");
        entryPoint.insert(t1);
        // here we use a pseudoclock so we can forward session clock in 20 seconds before inserting t2
        SessionPseudoClock clock = kieSession.getSessionClock();
        clock.advanceTime(20, TimeUnit.SECONDS);

        kieSession.insert(t2);
        kieSession.fireAllRules();
        Assert.assertFalse(t2.isDenied());
        System.out.println("* Transaction denied: " + t2.isDenied());
    }
}
