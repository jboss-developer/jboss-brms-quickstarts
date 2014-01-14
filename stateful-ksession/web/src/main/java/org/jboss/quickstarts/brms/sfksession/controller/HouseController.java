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
package org.jboss.quickstarts.brms.sfksession.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.quickstarts.brms.sfksession.model.Fire;
import org.jboss.quickstarts.brms.sfksession.model.House;
import org.jboss.quickstarts.brms.sfksession.model.Room;
import org.jboss.quickstarts.brms.sfksession.model.Sprinkler;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

/**
 * @author rafaelbenevides
 * 
 */
@Named
@SessionScoped
public class HouseController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private KieSession kieSession;

    @Inject
    private House house;

    private Map<String, FactHandle> roomFactHandles = new HashMap<String, FactHandle>();

    @PostConstruct
    public void init() {
        kieSession.insert(house);
        for (Room room : house.getRooms()) {
            kieSession.insert(room);
        }
        for (Sprinkler sprinkler : house.getSprinklers()) {
            kieSession.insert(sprinkler);
        }
    }

    public void initiateFire(Room room) {
        // insert a new fire instance if there isn't any fire yet
        if (roomFactHandles.get(room.getName()) == null) {
            FactHandle factHandle = kieSession.insert(new Fire(room));
            // store room's FactHandle
            roomFactHandles.put(room.getName(), factHandle);
        }
        kieSession.fireAllRules();
    }

    public void extinguishFire(Room room) {
        FactHandle handle = roomFactHandles.get(room.getName());
        if (handle != null) {
            // remove fire factHandle from KieSession
            kieSession.delete(handle);
            // remove room's FactHandle
            roomFactHandles.remove(room.getName());
        }
        kieSession.fireAllRules();
    }

}
