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

import java.util.HashMap;
import java.util.Map;

import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

/**
 * @author rafaelbenevides
 * 
 */
public class HelloworldProcessTest extends JbpmJUnitBaseTestCase {

    private static RuntimeEngine runtime;

    @Before
    public void setUp() {
        Map<String, ResourceType> resources = new HashMap<String, ResourceType>();
        resources.put("helloworld.bpmn2", ResourceType.BPMN2);
        resources.put("helloworldrule.drl", ResourceType.DRL);

        createRuntimeManager(Strategy.SINGLETON, resources);
        runtime = getRuntimeEngine(ProcessInstanceIdContext.get());
    }

    @After
    public void tearDown() {
        disposeRuntimeManager();
    }

    @Test
    public void testProcess() {

        KieSession kSession = runtime.getKieSession();

        // Fire it up!
        System.out.println("================================");
        System.out.println("= Starting Process Helloworld. =");
        System.out.println("================================");

        // start the process
        ProcessInstance processInstance = kSession.startProcess("bpms-project.helloworld");

        // check whether the process instance has completed successfully
        assertProcessInstanceActive(processInstance.getId(), kSession);
        kSession.insert(processInstance);
        kSession.fireAllRules();

        // check whether the given nodes were executed during the process execution
        assertNodeTriggered(processInstance.getId(), "StartProcess", "ScriptTask", "BusinessRules");

        assertNodeActive(processInstance.getId(), kSession, "Timer");
        
    }
}
