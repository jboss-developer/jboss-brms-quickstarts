stateful-ksession: Stateful Kie Session Quickstart
==================================================
Author: Rafael Benevides  
Level: Intermediate  
Technologies: CDI, JSF, Drools  
Summary: This Quickstart shows how to use Stateful Kie Session  
Target Product: BRMS  
Source: <https://github.com/jboss-developer/jboss-brms-quickstarts>  

What is it?
-----------

This projects demonstrates the use of Drools Stateful KIE session. Stateful KIE sessions are longer lived and allow iterative changes over time.

In this use case we see a 'House' with a fire alarm. The house contains 'Rooms' with fire 'Sprinklers' in each room.

This Quickstarts contains two modules:

- The Kmodule project contains the model classes (House, Room, Alarm, Sprinkler, and Fire) and the rules file (houserules.drl).

- The Web project contains a web application that allows you to simulate a fire in each room.

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or later, Maven 3.0 or later.

The application this project produces is designed to be run on BRMS 6.


Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CONFIGURE_MAVEN.md#configure-maven-to-build-and-deploy-the-quickstarts) before testing the quickstarts.



Start the JBoss Server
----------------------

1. Open a command prompt and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server:

        For Linux:   EAP_HOME/bin/standalone.sh
        For Windows: EAP_HOME\bin\standalone.bat


Build and Deploy the Quickstart
-------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line._

1. Make sure you have started the JBoss Server as described above.
2. Open a command prompt and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean install jboss-as:deploy

4. This deploys `web/target/brms-stateful-ksession-web.war` to the running instance of the server.


Access the application
---------------------


Access the running application in a browser at the following URL:  <http://localhost:8080/brms-stateful-ksession-web/>

You are presented to a Dashboard that shows two tables:

- The action table on the left lists the rooms in the house and provides buttons to initiate or extinguish a fire in each room.

- The status table on the right lists the sprinklers in the rooms and the current status for each sprinkler. The green sprinkler status 'OFF' indicates there is no fire in the room.

To simulate a fire in a room, click the `Initiate Fire` button. The text 'FIRE ALARM RINGING' appears below the action table to indicate a room is on fire. Also notice the status for that room's sprinklers turn red and change to 'ON' in the status table on the right.

To put out the fire, click the `Extinguish Fire` button for the room. The status for the sprinklers in that room are set to 'OFF' and the color changes to green.

If there no fire in any room, the 'FIRE ALARM RINGING' text disappears.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy


Run the Arquillian Tests
-------------------------

This quickstart provides Arquillian tests. By default, these tests are configured to be skipped as Arquillian tests require the use of a container.

_NOTE: The following commands assume you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. See [Run the Arquillian Tests](../README.md#run-the-arquillian-tests) for complete instructions and additional options._

1. Make sure you have started the JBoss Server as described above.
2. Open a command prompt and navigate to the root directory of this quickstart.
3. Type the following command to run the test goal with the following profile activated:

        mvn clean install test -Parq-jbossas-remote


Run the Quickstart in JBoss Developer Studio or Eclipse
-------------------------------------

You can also start the server and deploy the quickstarts from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](../README.md#use-jboss-developer-studio-or-eclipse-to-run-the-quickstarts)

Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

    mvn dependency:sources
    mvn dependency:resolve -Dclassifier=javadoc
