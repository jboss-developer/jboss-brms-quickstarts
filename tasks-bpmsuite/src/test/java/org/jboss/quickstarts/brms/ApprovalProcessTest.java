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
import java.util.List;
import java.util.Map;

import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

/**
 * @author rafaelbenevides
 * 
 */
public class ApprovalProcessTest extends JbpmJUnitBaseTestCase {

    public ApprovalProcessTest() {
        // to execute with persistent process management with human tasks persistence
        super(true, true);
    }

    @Test
    public void testProcessLowAmmountApproved() {
        createRuntimeManager(Strategy.PROCESS_INSTANCE, "manager", "approval.bpmn2");
        RuntimeEngine runtime = getRuntimeEngine(ProcessInstanceIdContext.get());

        KieSession kSession = runtime.getKieSession();

        // Map to be passed to the startProcess.
        Map<String, Object> params = new HashMap<String, Object>();
        System.out.println("Amount asked: 100 USD");
        params.put("amount", 100F);

        // Fire it up!
        System.out.println("==============================");
        System.out.println("= Starting Process Approval. =");
        System.out.println("==============================");

        // start the process
        ProcessInstance processInstance = kSession.startProcess("bpms-project.approval", params);

        // check whether the process instance has completed successfully
        assertProcessInstanceCompleted(processInstance.getId(), kSession);
        // Finished.
        assertNodeTriggered(processInstance.getId(), "Approved");

        disposeRuntimeManager();
    }
    
    @Test
    public void testProcessHighAmmountApproved() {
        createRuntimeManager(Strategy.PROCESS_INSTANCE, "manager", "approval.bpmn2");
        RuntimeEngine runtime = getRuntimeEngine(ProcessInstanceIdContext.get());

        KieSession kSession = runtime.getKieSession();

        // Map to be passed to the startProcess.
        Map<String, Object> params = new HashMap<String, Object>();
        System.out.println("Amount asked: 10500 USD");
        params.put("amount", 10500F);

        // Fire it up!
        System.out.println("==============================");
        System.out.println("= Starting Process Approval. =");
        System.out.println("==============================");

        // start the process
        ProcessInstance processInstance = kSession.startProcess("bpms-project.approval", params);

        assertNodeTriggered(processInstance.getId(), "Amount approval");

        // Process should be active waiting for user to approve
        assertProcessInstanceActive(processInstance.getId(), kSession);

        TaskService taskService = runtime.getTaskService();
        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("rafaelbenevides", "en-UK");
        for (TaskSummary taskSummary : list) {
            System.out.println("There's a Task Assigned");
            // Claim Task
            System.out.println("Task Claimed");
            taskService.claim(taskSummary.getId(), "rafaelbenevides");
            // Start Task
            System.out.println("Task Started");
            taskService.start(taskSummary.getId(), "rafaelbenevides");

            params.put("approvedOut", true);
            System.out.println("Task completed");
            taskService.complete(taskSummary.getId(), "rafaelbenevides", params);
        }

        // check whether the process instance has completed successfully
        assertProcessInstanceCompleted(processInstance.getId(), kSession);
        // Finished.
        assertNodeTriggered(processInstance.getId(), "Approved");

    }
    
    @Test
    public void testProcessHighAmmountDenied() {
        createRuntimeManager(Strategy.PROCESS_INSTANCE, "manager", "approval.bpmn2");
        RuntimeEngine runtime = getRuntimeEngine(ProcessInstanceIdContext.get());

        KieSession kSession = runtime.getKieSession();

        // Map to be passed to the startProcess.
        Map<String, Object> params = new HashMap<String, Object>();
        System.out.println("Amount asked: 10500 USD");
        params.put("amount", 10500F);

        // Fire it up!
        System.out.println("==============================");
        System.out.println("= Starting Process Approval. =");
        System.out.println("==============================");

        // start the process
        ProcessInstance processInstance = kSession.startProcess("bpms-project.approval", params);

        assertNodeTriggered(processInstance.getId(), "Amount approval");

        // Process should be active waiting for user to approve
        assertProcessInstanceActive(processInstance.getId(), kSession);

        TaskService taskService = runtime.getTaskService();
        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("rafaelbenevides", "en-UK");
        for (TaskSummary taskSummary : list) {
            System.out.println("There's a Task Assigned");
            // Claim Task
            System.out.println("Task Claimed");
            taskService.claim(taskSummary.getId(), "rafaelbenevides");
            // Start Task
            System.out.println("Task Started");
            taskService.start(taskSummary.getId(), "rafaelbenevides");

            params.put("approvedOut", false);
            System.out.println("Task completed");
            taskService.complete(taskSummary.getId(), "rafaelbenevides", params);
        }

        // check whether the process instance has completed successfully
        assertProcessInstanceCompleted(processInstance.getId(), kSession);
        // Finished.
        assertNodeTriggered(processInstance.getId(), "Not approved");

    }
}
