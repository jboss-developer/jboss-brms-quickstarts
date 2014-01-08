helloworld-drl: BRMS Basic DRL example
======================================
Author: Rafael Benevides
Level: Beginner  
Technologies: Drools
Summary: Shows how to use Drools 6
Target Product: BRMS
Source: <https://github.com/jboss-developer/jboss-brms-quickstarts/>  

What is it?
-----------

This project demonstrates how to use Drools 6 to execute rules. The rules are used to approve and apply discounts on a sale based on the customer type. It applies 50% disccount for VIP customers and denies the sale for BAD customers.


This quickstart does not contain a user interface layer. 


System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or later, Maven 3.0 or later.
 
Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](../README.md#configure-maven) before testing the quickstarts.


Run the Tests 
-------------

1. Open a command prompt and navigate to the root directory of this quickstart.
2. Type the following command to run the test goal with the following profile activated:

        mvn clean test


Investigate the Console Output
----------------------------

When you run the tests, JUnit will present you test report summary:

    -------------------------------------------------------
     T E S T S
    -------------------------------------------------------
    Running org.jboss.quickstarts.brms.helloworlddrl.HelloWorldDRLTest
    Jan 08, 2014 10:36:40 AM org.drools.compiler.kie.builder.impl.ClasspathKieProject notifyKieModuleFound
    INFO: Found kmodule: file:/Users/rafaelbenevides/projetos/jdf/Quickstarts/jboss-brms-quickstarts/helloworld-drl/target/classes/META-INF/kmodule.xml
    Jan 08, 2014 10:36:40 AM org.drools.compiler.kie.builder.impl.KieRepositoryImpl addKieModule
    INFO: KieModule was added:FileKieModule[ ReleaseId=org.jboss.quickstarts.brms:brms-helloworld-drl:6.1.0-build-SNAPSHOTfile=/Users/rafaelbenevides/projetos/jdf/Quickstarts/jboss-brms-quickstarts/helloworld-drl/target/classes]
    Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.082 sec

Test the Quickstart in JBoss Developer Studio or Eclipse
-------------------------------------
You can also start the server and deploy the quickstarts from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](../README.md#use-jboss-developer-studio-or-eclipse-to-run-the-quickstarts) 


Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc

