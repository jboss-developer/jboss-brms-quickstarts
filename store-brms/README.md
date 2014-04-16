store-brms: A Store BRMS example
================================
Author: Rafael Benevides  
Level: Advanced  
Technologies: Drools, BRMS    
Summary: Shows how to use a KModule from a BRMS server with functions, globals and DSL    
Target Product: BRMS  
Source: <https://github.com/jboss-developer/jboss-brms-quickstarts/>  

What is it?
-----------

This project demonstrates how to use BRMS to manage and deploy modules that contain model classes and rules.

* The `StoreBRMSTest` class creates 4 Sales object instances: `vipSale`, `regularSale`, `badSale` and `youngSale`.
* The `StoreBRMSTest` class also creates a SalesMan object instance that is used as a global variable inside the `StatelessKieSession`
* These Sales objects are passed to the `StatelessKieSession` class, which runs the rules against them to verify and apply discounts.

The `Person`, `Sale`, `Customer` and `CustomerType` are defined in a BRMS Kmodule library that is added to the project with the following dependency: `org.jboss.quickstarts.brms:helloworld-brms-kmodule:1.0.0`.

The `SalesMan` class extendes the `Person` class. It is defined in another Kmodule that is added to the project with this dependency: `org.jboss.quickstarts.brms:my-store-brms-kmodule:1.0.0`. This KModule declares a KBase named `storeKBase` that includes the `helloworldKBase` and declares a KSession named `storeKSession`. It also includes:

* A DRL file named `customerAge` that is a free form rule that contains a rule that denies the sale to a customer that has less than 18 years old.
* A DRL file named `storeFunctions` that contains a function to calculate the age based on the customer birthdate.
* A DSL file named `storeDSL` that contains the Domain Specific Language used on the store.
* A global variable definition called `salesman` that defines a global variable of Salesman type.
* A guided rule called 'registerSale' that uses the DSL to define a rule that "register" all sales to the specified salesman on the global variable.

These predefined KModules are available in this git repository: <https://github.com/jboss-developer/jboss-brms-repository.git>

_NOTE:_ This project only includes the `org.jboss.quickstarts.brms:my-store-brms-kmodule:1.0.0` dependency. The `helloworld-brms-kmodule` is included because it is a Maven transitive dependency of `my-store-brms-kmodule`. These dependencies are located in the following Maven Repository: <http://localhost:8080/business-central/maven2/>

This quickstart does not contain a user interface layer. 

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or later, Maven 3.0 or later.

The application this project produces is designed to be run on BRMS 6

 
Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](../README.md#configure-maven) before testing the quickstart.

Start the JBoss Server
-----------

If you have not yet done so, you must [Configure BRMS](../README.md#configure-brms) before testing the quickstart.

1. Open a command line and navigate to the root of the BRMS directory.
2. The following shows the command line to start the server:

        For Linux:   EAP_HOME/bin/standalone.sh
        For Windows: EAP_HOME\bin\standalone.bat


Import the BRMS Repository
----------------------

If you have not yet done so, you must [Import the BRMS repository](../README.md#import-the-brms-repository) before testing the quickstart.


Deploy BRMS kmodule
-------------------

1. [Start the JBoss S erver](#start-the-jboss-server) as instructed above.

2. Open a browser and access the following URL: <http://localhost:8080/business-central> 

2. Log in with the following credentials:

        Username:  quickstartUser
        Password:  quickstartPwd1!

3. Choose menu option `Authoring` -> `Project Authoring`

4. Choose the following options under `Project Explorer`:

        Organizational Unit:  example
        Repository Name:      jboss-brms-repository
        BRMS Kmodule:         helloworld-brms-kmodule

5. Next, click on `Tools` and `Project Editor`

6. In the tab on the right, click on `Build & Deploy`. 
   * It will prompt you with a message: "Also save possible changes to project?". Click `Yes`. 
   * You are prompted for a comment. Add a comment and click on `Save` button.


7. Now, choose the following options under `Project Explorer`:

        Organizational Unit:  example
        Repository Name:      jboss-brms-repository
        BRMS Kmodule:         my-store-brms-kmodule

5. Next, click on `Tools` and `Project Editor`

6. In the tab on the right, click on `Build & Deploy`. 
   * It will prompt you with a message: "Also save possible changes to project?". Click `Yes`. 
   * You are prompted for a comment. Add a comment and click on `Save` button.

   This deploys the `org.jboss.quickstarts.brms:helloworld-brms-kmodule:1.0.0` and `org.jboss.quickstarts.brms:my-store-brms-kmodule:1.0.0` artifact to the BRMS Maven repository. You can verify the deployment choosing menu option `Deployment` --> `Artifact Repository`.


Run the Tests 
-------------

1. Open a command prompt and navigate to the root directory of this quickstart.
2. For these tests, you must add the `brms` dependency on the command line. First try the tests without this dependency. Type the following command to run the test goal with only the `enable-test` profile activated:

        mvn clean test -Penable-test

   You should see errors simliar to the following:
   
        [ERROR] COMPILATION ERROR : 
        [INFO] -------------------------------------------------------------
        [ERROR] store-brms/src/test/java/org/jboss/quickstarts/brms/StoreBRMSTest.java:[44,19] error: cannot find symbol
        [ERROR]  class StoreBRMSTest
        store-brms/src/test/java/org/jboss/quickstarts/brms/StoreBRMSTest.java:[46,19] error: cannot find symbol
        [ERROR]  class StoreBRMSTest
        store-brms/src/test/java/org/jboss/quickstarts/brms/StoreBRMSTest.java:[48,19] error: cannot find symbol
        
   The tests fail with compilation errors because the project was not built with the necessary dependencies.

3. Now run the test goal with both the `enable-test` and the `brms` profiles activated:

        mvn clean test -Penable-test,brms

   The `brms` profile enables the `http://localhost:8080/business-central/maven2/` repository and adds the `org.jboss.quickstarts.brms:my-store-brms-kmodule:1.0.0` as a project dependency. 

   Now the tests complete successfully.

Investigate the Console Output
----------------------------

When you run the tests, JUnit will present you test report summary:

    -------------------------------------------------------
     T E S T S
    -------------------------------------------------------
    Running org.jboss.quickstarts.brms.StoreBRMSTest
    17:37:21.256 [main] INFO  o.d.c.k.b.impl.ClasspathKieProject - Found kmodule: jar:file:/Users/rafaelbenevides/.m2/repository/org/jboss/quickstarts/brms/my-store-brms-kmodule/1.0.0/my-store-brms-kmodule-1.0.0.jar!/META-INF/kmodule.xml
    17:37:21.261 [main] DEBUG o.d.c.k.b.impl.ClasspathKieProject - KieModule URL type=jar url=/Users/rafaelbenevides/.m2/repository/org/jboss/quickstarts/brms/my-store-brms-kmodule/1.0.0/my-store-brms-kmodule-1.0.0.jar
    17:37:21.407 [main] DEBUG o.d.c.k.b.impl.ClasspathKieProject - Found and used pom.properties META-INF/maven/org.jboss.quickstarts.brms/my-store-brms-kmodule/pom.properties
    17:37:21.411 [main] DEBUG o.d.c.k.b.impl.ClasspathKieProject - Discovered classpath module org.jboss.quickstarts.brms:my-store-brms-kmodule:1.0.0
    17:37:21.415 [main] INFO  o.d.c.k.b.impl.KieRepositoryImpl - KieModule was added:ZipKieModule[ ReleaseId=org.jboss.quickstarts.brms:my-store-brms-kmodule:1.0.0file=/Users/rafaelbenevides/.m2/repository/org/jboss/quickstarts/brms/my-store-brms-kmodule/1.0.0/my-store-brms-kmodule-1.0.0.jar]
    17:37:21.415 [main] INFO  o.d.c.k.b.impl.ClasspathKieProject - Found kmodule: jar:file:/Users/rafaelbenevides/.m2/repository/org/jboss/quickstarts/brms/helloworld-brms-kmodule/1.0.0/helloworld-brms-kmodule-1.0.0.jar!/META-INF/kmodule.xml
    17:37:21.415 [main] DEBUG o.d.c.k.b.impl.ClasspathKieProject - KieModule URL type=jar url=/Users/rafaelbenevides/.m2/repository/org/jboss/quickstarts/brms/helloworld-brms-kmodule/1.0.0/helloworld-brms-kmodule-1.0.0.jar
    17:37:21.420 [main] DEBUG o.d.c.k.b.impl.ClasspathKieProject - Found and used pom.properties META-INF/maven/org.jboss.quickstarts.brms/helloworld-brms-kmodule/pom.properties
    17:37:21.421 [main] DEBUG o.d.c.k.b.impl.ClasspathKieProject - Discovered classpath module org.jboss.quickstarts.brms:helloworld-brms-kmodule:1.0.0
    17:37:21.421 [main] INFO  o.d.c.k.b.impl.KieRepositoryImpl - KieModule was added:ZipKieModule[ ReleaseId=org.jboss.quickstarts.brms:helloworld-brms-kmodule:1.0.0file=/Users/rafaelbenevides/.m2/repository/org/jboss/quickstarts/brms/helloworld-brms-kmodule/1.0.0/helloworld-brms-kmodule-1.0.0.jar]
    17:37:22.372 [main] DEBUG o.drools.core.reteoo.ReteooRuleBase - Starting Engine in PHREAK mode
    ** Testing VIP customer **
    VIP discount applied
    Sale approved
    sale registered to salesmen
    ** Testing regular customer **
    Sale approved
    sale registered to salesmen
    ** Testing Young customer **
    Sale approved
    sale registered to salesmen
    Sale denied for customer with less than 18 years old
    ** Testing BAD customer **
    Bad customer. Sale denied
    sale registered to salesmen
    ** Testing if all sales were registered to salesman **
    Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.846 sec
    
    Results :
    
    Tests run: 4, Failures: 0, Errors: 0, Skipped: 0


Test the Quickstart in JBoss Developer Studio or Eclipse
-------------------------------------

You can also start the server and deploy the quickstarts from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](../README.md#use-jboss-developer-studio-or-eclipse-to-run-the-quickstarts) 


Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc
