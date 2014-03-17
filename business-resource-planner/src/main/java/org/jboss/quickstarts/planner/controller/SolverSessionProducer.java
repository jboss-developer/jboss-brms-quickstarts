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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.servlet.http.HttpSession;

import org.jboss.quickstarts.planner.model.VehicleRoutingSolution;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;
import org.optaplanner.core.config.solver.XmlSolverFactory;

/**
 * This class holds {@link Solver} on HTTPSession.
 * 
 * We can't use it with {@link Produces} annotation because {@link Solver} isn't {@link Serializable}
 * 
 * @author rafaelbenevides
 * 
 */
public class SolverSessionProducer {

    private static Map<String, Solver> solvers = new HashMap<String, Solver>();

    public static Solver getSolver(final HttpSession session) {
        Solver solver = solvers.get(session.getId());
        if (solver == null) {
            SolverFactory solverFactory = new XmlSolverFactory("/config/vehicleRoutingSolverConfig.xml");
            solver = solverFactory.buildSolver();
            solver.addEventListener(new SolverEventListener() {

                @Override
                public void bestSolutionChanged(BestSolutionChangedEvent event) {
                    VehicleRoutingSolution bestSolution = (VehicleRoutingSolution) event.getNewBestSolution();
                    session.setAttribute("solution", bestSolution);
                }
            });
            solvers.put(session.getId(), solver);
        }
        return solver;
    }

}
