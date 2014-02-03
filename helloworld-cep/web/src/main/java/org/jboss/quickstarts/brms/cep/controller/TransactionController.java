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
package org.jboss.quickstarts.brms.cep.controller;

import java.io.Serializable;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.quickstarts.brms.cep.model.Transaction;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

/**
 * @author rafaelbenevides
 * 
 */
// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6
@Model
public class TransactionController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext facesContext;

    private Transaction transaction = new Transaction();

    @Inject
    @KSession("myKSession")
    // use configured myKSession
    private KieSession kieSession;

    /**
     * @return the transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    public void registerTransaction() {
        // Select an entry point based on transaction type
        EntryPoint entryPoint = null;
        switch (transaction.getType()) {
            case CREDIT_CARD:
                entryPoint = kieSession.getEntryPoint("Credit Card");
                break;
            case DEPOSIT:
                entryPoint = kieSession.getEntryPoint("Deposit");
            case WITHDRAW:
                entryPoint = kieSession.getEntryPoint("Withdraw");
                break;
        }

        // If the entry point exists, place the transaction on entry point also
        if (entryPoint != null) {
            entryPoint.insert(transaction);
        }
        // Always place the object in kieSession for rules that doesn't declare an entry point
        kieSession.insert(transaction);
        // Fire the rules
        kieSession.fireAllRules();

        // Check if the transaction was denied
        if (transaction.isDenied()) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, transaction.getDeniedCause(), null));
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, transaction.getType() + " transaction of USD " + transaction.getValue() + " registered!",
                null));
        }
    }

}
