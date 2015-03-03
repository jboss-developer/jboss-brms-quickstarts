helloworld-drl: BRMS Basic DRL example
======================================
Author: Rafael Benevides  
Level: Beginner  
Technologies: Drools  
Summary: The `helloworld-drl` quickstart shows how to use Drools 6  
Target Product: BRMS  
Source: <https://github.com/jboss-developer/jboss-brms-quickstarts/>  

What is it?
-----------

This project demonstrates how to use Drools 6 to execute rules. The rules are used to approve and apply discounts on a sale based on the customer type. This example applies a 50% discount for VIP customers and denies the sale for BAD customers.

The `HelloworldDRLTest` class creates 3 Sales object instances: `vipSale`, `regularSale`, and `badSale`. These Sales objects are passed to the `StatelessKieSession` class, which runs the rules against them to verify, apply discounts, or deny the sale.

This quickstart does not contain a user interface layer. The rules are described in `src/main/resource/META-INF/helloworld.drl`.


System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or later, Maven 3.0 or later.


Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CONFIGURE_MAVEN.md#configure-maven-to-build-and-deploy-the-quickstarts) before testing the quickstarts.


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
    17:35:06.378 [main] INFO  o.d.c.k.b.impl.ClasspathKieProject - Found kmodule: file:/Users/rafaelbenevides/projetos/jdf/Quickstarts/jboss-brms-quickstarts/helloworld-drl/target/classes/META-INF/kmodule.xml
    17:35:06.384 [main] DEBUG o.d.c.k.b.impl.ClasspathKieProject - KieModule URL type=file url=/Users/rafaelbenevides/projetos/jdf/Quickstarts/jboss-brms-quickstarts/helloworld-drl/target/classes
    17:35:06.571 [main] WARN  o.d.c.k.b.impl.ClasspathKieProject - Unable to load pom.properties tried recursing down from/Users/rafaelbenevides/projetos/jdf/Quickstarts/jboss-brms-quickstarts/helloworld-drl/target/classes
    null
    17:35:06.595 [main] INFO  o.d.c.k.b.impl.ClasspathKieProject - Recursed up folders,  found and used pom.xml /Users/rafaelbenevides/projetos/jdf/Quickstarts/jboss-brms-quickstarts/helloworld-drl/pom.xml
    17:35:06.599 [main] DEBUG o.d.c.k.b.impl.ClasspathKieProject - Discovered classpath module org.jboss.quickstarts.brms:brms-helloworld-drl:1.0.0-build-SNAPSHOT
    17:35:06.610 [main] INFO  o.d.c.k.b.impl.KieRepositoryImpl - KieModule was added:FileKieModule[ ReleaseId=org.jboss.quickstarts.brms:brms-helloworld-drl:1.0.0-build-SNAPSHOTfile=/Users/rafaelbenevides/projetos/jdf/Quickstarts/jboss-brms-quickstarts/helloworld-drl/target/classes]
    17:35:07.716 [main] DEBUG o.drools.core.reteoo.ReteooRuleBase - Starting Engine in PHREAK mode
    ** Testing VIP customer **
    VIP discount applied
    Sale approved
    ** Testing regular customer **
    Sale approved
    ** Testing BAD customer **
    BAD customer. Sale denied
    Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.105 sec
    
    Results :
    
    Tests run: 3, Failures: 0, Errors: 0, Skipped: 0

Test the Quickstart in JBoss Developer Studio or Eclipse
-------------------------------------

You can also start the server and deploy the quickstarts from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](../README.md#use-jboss-developer-studio-or-eclipse-to-run-the-quickstarts) 


Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc

