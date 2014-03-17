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
package org.jboss.quickstarts.brms.sfksession.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class House implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Room> rooms = new ArrayList<Room>();
    private List<Sprinkler> sprinklers = new ArrayList<Sprinkler>();

    private boolean alarmOn;

    @PostConstruct
    public void buildHouse() {
        Room kitchen = new Room("kitchen");
        Room bedroom = new Room("bedroom");
        Room office = new Room("office");
        Room livingroom = new Room("livingroom");

        Sprinkler sprinklerKitchen1 = new Sprinkler(kitchen);
        Sprinkler sprinklerKitchen2 = new Sprinkler(kitchen);
        Sprinkler sprinklerKitchen3 = new Sprinkler(kitchen);
        Sprinkler sprinklerBedroom1 = new Sprinkler(bedroom);
        Sprinkler sprinklerBedroom2 = new Sprinkler(bedroom);
        Sprinkler sprinklerBedroom3 = new Sprinkler(bedroom);
        Sprinkler sprinklerOffice1 = new Sprinkler(office);
        Sprinkler sprinklerOffice2 = new Sprinkler(office);
        Sprinkler sprinklerOffice3 = new Sprinkler(office);
        Sprinkler sprinklerLivingRoom1 = new Sprinkler(livingroom);
        Sprinkler sprinklerLivingRoom2 = new Sprinkler(livingroom);
        Sprinkler sprinklerLivingRoom3 = new Sprinkler(livingroom);

        rooms.add(office);
        rooms.add(bedroom);
        rooms.add(kitchen);
        rooms.add(livingroom);

        sprinklers.add(sprinklerOffice1);
        sprinklers.add(sprinklerOffice2);
        sprinklers.add(sprinklerOffice3);
        sprinklers.add(sprinklerBedroom1);
        sprinklers.add(sprinklerBedroom2);
        sprinklers.add(sprinklerBedroom3);
        sprinklers.add(sprinklerKitchen1);
        sprinklers.add(sprinklerKitchen2);
        sprinklers.add(sprinklerKitchen3);
        sprinklers.add(sprinklerLivingRoom1);
        sprinklers.add(sprinklerLivingRoom2);
        sprinklers.add(sprinklerLivingRoom3);
    }

    /**
     * @return the sprinklers
     */
    public List<Sprinkler> getSprinklers() {
        return sprinklers;
    }

    /**
     * @return the rooms
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * @return the alarmOn
     */
    public boolean isAlarmOn() {
        return alarmOn;
    }

    /**
     * @param alarmOn the alarmOn to set
     */
    public void setAlarmOn(boolean alarmOn) {
        this.alarmOn = alarmOn;
    }

}
