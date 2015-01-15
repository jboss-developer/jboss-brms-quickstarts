business-resource-planner: Shows how to solve a vehicle routing problem
=======================================================================
Author: Geoffrey De Smet, adapted by Rafael Benevides   
Level: Advanced  
Technologies: BRMS, Drools, Optaplanner  
Summary: This Quickstarts shows how to solve a vehicle routing problem  
Target Product: BRMS
Source: <https://github.com/jboss-developer/jboss-brms-quickstarts/>  


What is it?
-----------

Business Resource Planner a lightweight, embeddable planning engine that helps organizations manage sets of constrained resources such as employees, assets, and time. It helps optimize resource usage to allow a business to increase productivity using fewer resources.

This project demonstrates how to use Business Resource Planner to solve resource planning issues related to package pickup. A limited fleet of vehicles must pick up packages from multiple customers and deliver them to a central location. Each vehicle can service multiple customers but is limited by time, distance, and fuel cost constraints. To make it more interesting, this example uses randomly generated numbers to simulate how demand, distance, and truck capacity constraints can also impact the routing plans.

This planning problem has a number of solutions that can be categorized as follows:

* A `possible solution` is any solution, whether or not it breaks any number of constraints. Planning problems tend to have an incredibly large number of possible solutions. Many of those solutions are worthless.
* A `feasible solution` is a solution that does not break any (negative) hard constraints. The number of feasible solutions tends to be relative to the number of possible solutions. Sometimes there are no feasible solutions. Every feasible solution is a possible solution.
* An `optimal solution` is a solution with the highest score. Planning problems tend to have 1 or a few optimal solutions. There is always at least 1 optimal solution, even in the case that there are no feasible solutions and the optimal solution isn't feasible.
* The `best solution` found is the solution with the highest score found by an implementation in a given amount of time. The best solution found is likely to be feasible and, given enough time, it's an optimal solution.


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

You are presented with a form that allows you to enter the number of Customers and Vehicles to use in the Routing Problem Definition.

- Specify any number of Customers and Vehicles, then click the `Solve` button.

    * You are presented with a page that displays diagrams representing potential Routing Problem Solutions. Business Resource Planner continues to process the data and display diagrams of solutions that consume less and less fuel.
    * The best solution, the one that consumes the least fuel in a given amount of time, is finally displayed at the bottom of the page. The best solution found is likely to be feasible and, given enough time, an optimal solution.

- Click the `Terminate Early` button. A message `The solver has been terminated. Below is the final solution` is displayed.

- Click the `Back` button to change the number of Customers and Vehicles. If the Customer demand exceeds the capacity of the Vehicles, the message `Not Feasible` is displayed in red at the bottom of the page.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy

Run the Quickstart in JBoss Developer Studio or Eclipse
-------------------------------------

You can also start the server and deploy the quickstarts from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](../README.md#use-jboss-developer-studio-or-eclipse-to-run-the-quickstarts) 

Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

    mvn dependency:sources
    mvn dependency:resolve -Dclassifier=javadoc



