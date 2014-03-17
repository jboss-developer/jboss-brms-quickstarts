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
package org.jboss.quickstarts.planner.model.solver;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jboss.quickstarts.planner.model.Customer;

/**
 * 
 * Some optimization algorithms work more efficiently if they have an estimation of which planning entities are more difficult to plan.
 * 
 * This class compares the difficult between them
 * 
 */
public class VrpCustomerDifficultyComparator implements Comparator<Customer>, Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Customer a, Customer b) {
        return new CompareToBuilder()
            // TODO experiment with (aLatitude - bLatitude) % 10
            .append(a.getLocation().getLatitude(), b.getLocation().getLatitude())
            .append(a.getLocation().getLongitude(), b.getLocation().getLongitude())
            .append(a.getDemand(), b.getDemand())
            .toComparison();
    }

}
