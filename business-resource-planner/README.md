business-resource-planner: Shows how to solve a vehicle routing problem
=======================================================================
Author: Geoffrey De Smet, adapted by Rafael Benevides   
Level: Advanced  
Technologies: BRMS, Drools, Optaplanner  
Summary: This Quickstarts shows how to solve a vehicle routing problem  
Target Product: BPM-SUITE  
Source: <https://github.com/jboss-developer/jboss-brms-quickstarts/>  


What is it?
-----------

This project demonstrates the use of Business Resource Planner to solve a use case where a fleet of vehicles needs to pick up the objects of each customer and bring them to the depot. Each vehicle can service multiple customers, but it has a limited capacity.

A planning problem has a number of solutions. There are several categories of solutions:

A `possible solution` is any solution, whether or not it breaks any number of constraints. Planning problems tend to have an incredibly large number of possible solutions. Many of those solutions are worthless.
A `feasible solution` is a solution that does not break any (negative) hard constraints. The number of feasible solutions tends to be relative to the number of possible solutions. Sometimes there are no feasible solutions. Every feasible solution is a possible solution.
An `optimal solution` is a solution with the highest score. Planning problems tend to have 1 or a few optimal solutions. There is always at least 1 optimal solution, even in the case that there are no feasible solutions and the optimal solution isn't feasible.
The `best solution` found is the solution with the highest score found by an implementation in a given amount of time. The best solution found is likely to be feasible and, given enough time, it's an optimal solution.


System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or later, Maven 3.0 or later.

The application this project produces is designed to be run on EAP 6

 
Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](../README.md#configure-maven) before testing the quickstart.


Start the JBoss EAP Server
-------------------------

If you have not yet done so, you must [Configure BRMS](../README.md#configure-brms) before testing the quickstart.

1. Open a command line and navigate to the root of the BRMS directory.
2. The following shows the command line to start the server:

        For Linux:   EAP_HOME/bin/standalone.sh
        For Windows: EAP_HOME\bin\standalone.bat


Build and Deploy the Quickstart
-------------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line._

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean package jboss-as:deploy

4. This will deploy `target/brms-business-resource-planner.war`  to the running instance of the server.
 

Access the application 
---------------------

Access the running application in a browser at the following URL:  <http://localhost:8080/brms-business-resource-planner>

You are presented to a form that allows you to create random customers and vehicles.

- Specify any ammount of Customers and Vehicles and click on `Solve` button.
You will be presented to a page where you can see how Business Resource Planner improves the vehicle routing to consume less fuel.
The best solution found is the solution that consume less fuel (on the botton of the page) in a given amount of time. The best solution found is likely to be feasible and, given enough time, it's an optimal solution.

- Click on `Terminate Early` button.
A message `The solver has been terminated. Below is the final solution` is displayed.

- Click on `Back` button.
You can change the values of Customers and Vehicles. If the number of Customers is higher than the number of Vehicles you can see a red `Not feasible` displayed on the botton of the page.


Undeploy the Archive
--------------------

Contributor: For example: 

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy

Run the Quickstart in JBoss Developer Studio or Eclipse
-------------------------------------
Contributor: For example: 

You can also start the server and deploy the quickstarts from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](../README.md#use-jboss-developer-studio-or-eclipse-to-run-the-quickstarts) 

Debug the Application
------------------------------------

Contributor: For example: 

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

    mvn dependency:sources
    mvn dependency:resolve -Dclassifier=javadoc



