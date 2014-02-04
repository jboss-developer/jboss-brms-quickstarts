decision-table: Insurance company prices using Decision Tables
==============================================================
Author: Rafael Benevides  
Level: Intermediate  
Technologies: Drools, BRMS 
Summary: Shows how to use Decision Tables from a BRMS server
Target Product: BRMS  
Source: <https://github.com/jboss-developer/jboss-brms-quickstarts/>  

What is it?
-----------

This project demonstrates how to use BRMS to deploy modules that contain model classes and rules based on Decision Tables.

The `DriverProfile`, `CarProfile` and `PriceQuotation` classes are defined in a BRMS Kmodule that is added to the project with the following dependency: `org.jboss.quickstarts.brms:decision-table:6.1.0`. It is available in this git repository: <https://github.com/jboss-developer/jboss-brms-repository.git>. This module imports the Decision Table rules.

The rules for this BRMS quickstart are defined in the `rules.xsl` spreadsheet, which is located in the root folder of this quickstart. You import these rules into the BRMS server during the deployment process below.

The `DecisionTableTest` class tests each rule defined in the Decision Table.

This quickstart does not contain a user interface layer. 

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or later, Maven 3.0 or later.

The application this project produces is designed to be run on BRMS 6

 
Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CONFIGURE_MAVEN.md#configure-maven-to-build-and-deploy-the-quickstarts) before testing the quickstart.

Start the JBoss Server
-----------------------

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

1. [Start the JBoss Server](#start-the-jboss-server) as instructed above.

2. Open a browser and access the following URL: <http://localhost:8080/business-central> 

3. Log in with the following credentials:

        Username:  quickstartUser
        Password:  quickstartPwd1!

4. Choose menu option `Authoring` -> `Project Authoring`

5. Choose the following options under `Project Explorer`:

        Organizational Unit:  example
        Repository Name:      jboss-brms-repository
        BRMS Kmodule:         decision-table-kmodule

6. Choose menu option `New Item` -> `Decision Table (Spreadsheet)`

7. On `Create new Decision Table (Spreadsheet)` dialog, enter the following:
   * For `Resource Name`, type `insurance-rules`. 
   * Click on `Choose File` button, and select the `rules.xsl` file that is located root directory of this quickstart.
     _Note:_ The file path will display `C:\fakepath\rules.xls`, but you can ignore that. Click `OK`.

   * It will prompt you with a message: "Uploaded successfully". Click `OK`.

8. Next, click on `Tools` and `Project Editor`

9. In the tab on the right, click on `Build & Deploy`. 
   * It will prompt you with a message: "Also save possible changes to project?". Click `Yes`. 
   * You are prompted for a comment. Add a comment and click on `Save` button.
   This deploys the `org.jboss.quickstarts.brms:decision-table-kmodule:6.1.0` artifact to the BRMS Maven repository. You can verify the deployment choosing menu option `Deployment` --> `Artifact Repository`.


Run the Tests 
-------------

1. Open a command prompt and navigate to the root directory of this quickstart.
2. For these tests, you must add the`brms` dependency on the command line. First try the tests without this dependency. Type the following command to run the test goal with only the `enable-test` profile activated:

        mvn clean test -Penable-test

   You should see errors similar to the following:
   
        [ERROR] COMPILATION ERROR : 
        [INFO] -------------------------------------------------------------
        [ERROR] /home/sgilda/GitRepos/jboss-brms-quickstarts/decision-table/src/test/java/org/jboss/quickstarts/brms/DecisionTableTest.java:[53,8] error: cannot find symbol
        [ERROR]  class DecisionTableTest
        /home/sgilda/GitRepos/jboss-brms-quickstarts/decision-table/src/test/java/org/jboss/quickstarts/brms/DecisionTableTest.java:[53,44] error: cannot find symbol
        [ERROR]  class DecisionTableTest
        /home/sgilda/GitRepos/jboss-brms-quickstarts/decision-table/src/test/java/org/jboss/quickstarts/brms/DecisionTableTest.java:[59,8] error: cannot find symbol

   The tests fail with compilation errors because the project does not have the necessary dependencies.

4. Now run the test goal with the both the `enable-test` and `brms` profiles activated:

        mvn clean test -Penable-test,brms

  The `brms` profile enables the `http://localhost:8080/business-central/maven2/` repository and adds the `org.jboss.quickstarts.brms:my-store-brms-kmodule:6.1.0` as a project dependency. 

  Now the tests complete successfully.

Investigate the Console Output
----------------------------

When you run the tests, JUnit will present you test report summary:

    -------------------------------------------------------
     T E S T S
    -------------------------------------------------------
    Running org.jboss.quickstarts.brms.DecisionTableTest
    Jan 28, 2014 10:51:20 AM org.drools.compiler.kie.builder.impl.ClasspathKieProject discoverKieModules
    INFO: Found kmodule: jar:file:/Users/rafaelbenevides/.m2/repository/org/jboss/quickstarts/brms/decision-table-kmodule/6.1.0/decision-table-kmodule-6.1.0.jar!/META-INF/kmodule.xml
    Jan 28, 2014 10:51:21 AM org.drools.compiler.kie.builder.impl.KieRepositoryImpl addKieModule
    INFO: KieModule was added:ZipKieModule[ ReleaseId=org.jboss.quickstarts.brms:decision-table-kmodule:6.1.0file=/Users/rafaelbenevides/.m2/repository/org/jboss/quickstarts/brms/decision-table-kmodule/6.1.0/decision-table-kmodule-6.1.0.jar]
    Jan 28, 2014 10:51:21 AM org.drools.compiler.kie.builder.impl.ClasspathKieProject discoverKieModules
    INFO: Found kmodule: jar:file:/Users/rafaelbenevides/.m2/repository/org/jboss/quickstarts/brms/decision-table-kmodule/6.1.0/decision-table-kmodule-6.1.0.jar!/META-INF/kmodule.xml
    Jan 28, 2014 10:51:21 AM org.drools.compiler.kie.builder.impl.KieRepositoryImpl addKieModule
    INFO: KieModule was added:ZipKieModule[ ReleaseId=org.jboss.quickstarts.brms:decision-table-kmodule:6.1.0file=/Users/rafaelbenevides/.m2/repository/org/jboss/quickstarts/brms/decision-table-kmodule/6.1.0/decision-table-kmodule-6.1.0.jar]
    Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.219 sec
    
    Results :
    
    Tests run: 9, Failures: 0, Errors: 0, Skipped: 0


Test the Quickstart in JBoss Developer Studio or Eclipse
-------------------------------------

You can also start the server and deploy the quickstarts from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](../README.md#use-jboss-developer-studio-or-eclipse-to-run-the-quickstarts) 


Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc
