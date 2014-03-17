helloworld-cep: A Complex Event Processing Hello World
======================================================
Author: Rafael Benevides  
Level: Intermediate  
Technologies: CDI, JSF, Drools  
Summary: This Quickstart shows a basic example for CEP  
Target Product: BRMS  
Product Versions: EAP 6.1, EAP 6.2  
Source: <https://github.com/jboss-developer/jboss-brms-quickstarts>  

What is it?
-----------

This project demonstrates a basic example of CEP - Complex Event Processing. Each financial transaction, for example `Credit Card`, `Deposit` or `Withdrawal`, is an event that occurs on the user account.

For demonstration purposes, 3 rules are defined to evaluate and detect the following fraudulent transactions:

1. More than 3 transactions occur in less than 5 seconds from any entry point.
2. A transaction amount is more than twice the average of the last 4 Credit Card transactions
3. A withdrawal occurs less than 10 seconds after a Credit card transaction


This Quickstarts contains two modules:

- The Kmodule project contains the model classes `Transaction` and `TransactionType`. It also contains the rules file `fraud-detection-rules.drl`.

- The Web project contains a web application that allows you to simulate Credit Card, withdrawal, and deposit transactions.

_Note: You do not need to download and configure BRMS to run this quickstart._


System requirements
-------------------

The application this project produces is designed to be run on Red Hat JBoss Enterprise Application Platform 6.1 or later.

All you need to build this project is Java 6.0 (Java SDK 1.6) or later, Maven 3.0 or later.


Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CONFIGURE_MAVEN.md#configure-maven-to-build-and-deploy-the-quickstarts) before testing the quickstart.


Start the JBoss EAP Server
----------------------

1. Open a command prompt and navigate to the root of the JBoss EAP directory.
2. The following shows the command line to start the server:

        For Linux:   JBOSS_HOME/bin/standalone.sh
        For Windows: JBOSS_HOME\bin\standalone.bat


Build and Deploy the Quickstart
-------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line._

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command prompt and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean install jboss-as:deploy

4. This deploys `web/target/brms-helloworld-cep-web.war` to the running instance of the server.


Access the application
---------------------


Access the running application in a browser at the following URL:  <http://localhost:8080/brms-helloworld-cep-web/>

You are presented to a form that allows you to create finance transactions. Test the rules by doing the following:

- Leave the default values (10, CREDIT_CARD) and click on the `Register Transaction` button.
A message `CREDIT_CARD transaction of USD 10 registered!` is displayed.

- Click more 4 times in less than 5 seconds on the `Register Transaction` button.
A message `Transaction Denied! More than 3 transactions in less than 5 seconds` is displayed. This demonstrates the first rule was applied to this transaction event.

- Now set the amount to 100 and click on the `Register Transaction` button.
A message `Transaction Denied! This Credit Card transaction amount of USD 100 is more than twice the average amount ( USD 32.5 ) of the last 4 Credit Card Transactions` is displayed. This demonstrates that the second rule was applied to this transaction event.

- Click again on `Register Transaction` and in less than 10 seconds change the type to `WITHDRAW` and click on the `Register Transaction` button.
A message `Transaction Denied! A withdrawal transaction is not allowed less than 10 seconds after a Credit Card transaction` is displayed. This demonstrates that the third rule was applied to the WITHDRAW transaction event.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy


Run the Arquillian Tests
-------------------------

This quickstart provides Arquillian tests. By default, these tests are configured to be skipped as Arquillian tests require the use of a container.

_NOTE: The following commands assume you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. See [Run the Arquillian Tests](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/RUN_ARQUILLIAN_TESTS.md#run-the-arquillian-tests) for complete instructions and additional options._

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
