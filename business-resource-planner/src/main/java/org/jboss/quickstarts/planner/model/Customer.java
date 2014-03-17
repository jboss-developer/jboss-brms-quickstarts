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
package org.jboss.quickstarts.planner.model;

import org.jboss.quickstarts.planner.model.solver.VehicleUpdatingVariableListener;
import org.jboss.quickstarts.planner.model.solver.VrpCustomerDifficultyComparator;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity(difficultyComparatorClass = VrpCustomerDifficultyComparator.class)
public class Customer implements Standstill {

    private Location location;
    private int demand;

    // Planning variables: changes during planning, between score calculations.
    private Standstill previousStandstill;

    // Shadow variables
    private Customer nextCustomer;
    private Vehicle vehicle;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    @PlanningVariable(chained = true, valueRangeProviderRefs = { "vehicleRange", "customerRange" },
        variableListenerClasses = { VehicleUpdatingVariableListener.class })
    public Standstill getPreviousStandstill() {
        return previousStandstill;
    }

    public void setPreviousStandstill(Standstill previousStandstill) {
        this.previousStandstill = previousStandstill;
    }

    public Customer getNextCustomer() {
        return nextCustomer;
    }

    public void setNextCustomer(Customer nextCustomer) {
        this.nextCustomer = nextCustomer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    /**
     * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    public int getMilliDistanceToPreviousStandstill() {
        if (previousStandstill == null) {
            return 0;
        }
        return getMilliDistanceTo(previousStandstill);
    }

    /**
     * @param standstill never null
     * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    public int getMilliDistanceTo(Standstill standstill) {
        return location.getMilliDistance(standstill.getLocation());
    }

    @Override
    public String toString() {
        return "Customer on " + location + " (after " + (previousStandstill == null ? "null" : previousStandstill.getLocation()) + ")";
    }

}
