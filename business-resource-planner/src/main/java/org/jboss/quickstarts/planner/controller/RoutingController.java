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
package org.jboss.quickstarts.planner.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.jboss.quickstarts.planner.model.Customer;
import org.jboss.quickstarts.planner.model.Depot;
import org.jboss.quickstarts.planner.model.Location;
import org.jboss.quickstarts.planner.model.Vehicle;
import org.jboss.quickstarts.planner.model.VehicleRoutingSolution;
import org.optaplanner.core.api.solver.Solver;

/**
 * @author rafaelbenevides
 * 
 */
// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6
@Model
public class RoutingController {

    @Inject
    private FacesContext facesContext;

    private int numberOfCustomers = 50;

    private int numberOfVehicles = 6;

    private static ExecutorService solvingExecutor = Executors.newFixedThreadPool(4);

    /**
     * @return the numberOfCustomer
     */
    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    /**
     * @return the numberOfVehicles
     */
    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    /**
     * @param numberOfCustomer the numberOfCustomer to set
     */
    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    /**
     * @param numberOfVehicles the numberOfVehicles to set
     */
    public void setNumberOfVehicles(int numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
    }

    /**
     * Runs the Solver
     * 
     * @return
     */
    public String solve() {
        final Solver solver = SolverSessionProducer.getSolver(((HttpSession) facesContext.getExternalContext().getSession(false)));
        // shutdown any previous running solver
        solver.terminateEarly();
        // setup solver
        solver.setPlanningProblem(createRoutingSolution());
        // run solver on a separated Thread
        solvingExecutor.submit(new Runnable() {
            public void run() {
                solver.solve();
            }
        });
        facesContext.addMessage(null, new FacesMessage("Solving... Below is a temporary solution"));
        return "vehicleroutingsolution.xhtml";
    }

    /**
     * Terminates the Solver
     */
    public void terminateEarly() {
        final Solver solver = SolverSessionProducer.getSolver(((HttpSession) facesContext.getExternalContext().getSession(false)));
        // shutdown any previous running solver
        solver.terminateEarly();
        facesContext.addMessage(null, new FacesMessage("The solver has been terminated. Below is the final solution"));
    }

    /**
     * Creates an instance of {@link VehicleRoutingSolution} with the defined number of {@link Customer} and {@link Vehicle}
     * based on {@link #numberOfCustomers} and {@link #numberOfVehicles}
     */
    private VehicleRoutingSolution createRoutingSolution() {
        Depot depot = new Depot();
        VehicleRoutingSolution routingSolution = new VehicleRoutingSolution();
        routingSolution.getCustomerList().addAll(createCustomers(numberOfCustomers));
        routingSolution.getVehicleList().addAll(createVehicles(numberOfVehicles, depot));
        routingSolution.setDepot(depot);
        return routingSolution;

    }

    /**
     * Create as many {@link Vehicle} as specified in qtd
     * 
     * @param qtd Quantity
     * @param depot the Depot of origin
     * @return list of vehicles
     */
    public List<Vehicle> createVehicles(int qtd, Depot depot) {
        Random random = new Random(System.currentTimeMillis());
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        for (int i = 0; i < qtd; i++) {
            // Capacity can be 2000 or 1500 randomly
            int x = random.nextInt(10);
            int capacity = x % 2 == 0 ? 2000 : 1500;
            Vehicle v = new Vehicle();
            v.setCapacity(capacity);
            v.setDepot(depot);
            vehicles.add(v);
        }
        return vehicles;
    }

    /**
     * Create as many {@link Customer} as specified in qtd
     * 
     * @param qtd
     * @return list of customers
     */
    private List<Customer> createCustomers(int qtd) {
        Random random = new Random(System.currentTimeMillis());
        List<Customer> customers = new ArrayList<Customer>();
        for (int i = 0; i < qtd; i++) {
            Customer c = new Customer();
            c.setDemand(random.nextInt(300));

            double longitude = random.nextInt((200) + 1) - 100;
            double latitude = random.nextInt((200) + 1) - 100;
            Location location = new Location("Location " + i, latitude, longitude);
            c.setLocation(location);

            customers.add(c);
        }
        return customers;
    }

}
