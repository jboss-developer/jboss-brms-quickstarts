helloworld-brms: A Basic BRMS example
======================================
Author: Rafael Benevides  
Level: Beginner  
Technologies: Drools, BRMS 
Summary: Shows how to use a KModule from a BRMS server  
Target Product: BRMS  
Source: <https://github.com/jboss-developer/jboss-brms-quickstarts/>  

What is it?
-----------

This project demonstrates how to use BRMS to manage and deploy modules that contain model classes and rules.

Three Sales object instance (vipSale, regularSale, badSale) are created on HelloWorldBRMSTest class and they're placed on a StatelessKieSession to verify if the rules described was applied.

Note that the Sale, Customer and CustomerType was defined on the following dependency: org.jboss.quickstarts.brms:helloworld-brms-kmodule:6.1.0 . This dependency is a BRMS Kmodule that contains the model classes and the rules that were previously built and it's available on the the git repository: <https://github.com/jboss-developer/jboss-brms-repository.git>

The maven dependency becomes available through the following Maven Repository: `http://localhost:8080/business-central/maven2/` 

This quickstart does not contain a user interface layer. 

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or later, Maven 3.0 or later.

The application this project produces is designed to be run on BRMS 6

 
Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](../README.md#configure-maven) before testing the quickstart.

Start BRMS
-----------

If you have not yet done so, you must [Configure BRMS](../README.md#configure-brms) before testing the quickstart.

1. Open a command line and navigate to the root of the BRMS directory.
2. The following shows the command line to start the server:

        For Linux:   BRMS_HOME/bin/standalone.sh
        For Windows: BRMS_HOME\bin\standalone.bat


Import BRMS repository
----------------------

If you have not yet done so, you must [Import BRMS repository](../README.md#import-brms-repository) before testing the quickstart.


Deploy BRMS kmodule
-------------------

1. Navigate to http://localhost:8080/business-central in a web browser. 

2. Log in with the user 'quickstartsUser' and use 'quickstartPwd1!' as password.

3. Go to `Authoring` -> `Project Authoring`

4. On Project Explorer select: example -> jboss-brms-repository -> helloword-brms-kmodule

5. Now, click on `Tools` and `Project Editor`

6. On the opened tab on the right side, click on `Build & Deploy`, then click on `Save` button.

This will deploy the following artifact org.jboss.quickstarts.brms:helloworld-brms-kmodule:6.1.0 to the BRMS Maven repository. You can check BRMS Maven repository by clicking on `Deployment` ad `Artifact Repository`


Run the Tests 
-------------

1. Open a command prompt and navigate to the root directory of this quickstart.
2. Type the following command to run the test goal with the following profile activated:

        mvn clean test -Penable-test

The tests will fail because the project doesn't have the necessary dependencies

4. Now run the following command to run the test goal with the following profiles activated:

        mvn clean test -Penable-test,brms

The BRMS profile will enable repository `http://localhost:8080/business-central/maven2/` and add the `org.jboss.quickstarts.brms:helloworld-brms-kmodule:6.1.0` as the project dependency. 

Now the tests will complete.

Investigate the Console Output
----------------------------

When you run the tests, JUnit will present you test report summary:

    -------------------------------------------------------
     T E S T S
    -------------------------------------------------------
    Running org.jboss.quickstarts.brms.HelloWorldBRMSTest
    Jan 16, 2014 5:41:44 PM org.drools.compiler.kie.builder.impl.ClasspathKieProject discoverKieModules
    INFO: Found kmodule: jar:file:/Users/rafaelbenevides/.m2/repository/org/jboss/quickstarts/brms/helloworld-brms-kmodule/6.1.0/helloworld-brms-kmodule-6.1.0.jar!/META-INF/kmodule.xml
    Jan 16, 2014 5:41:45 PM org.drools.compiler.kie.builder.impl.KieRepositoryImpl addKieModule
    INFO: KieModule was added:ZipKieModule[ ReleaseId=org.jboss.quickstarts.brms:helloworld-brms-kmodule:6.1.0file=/Users/rafaelbenevides/.m2/repository/org/jboss/quickstarts/brms/helloworld-brms-kmodule/6.1.0/helloworld-brms-kmodule-6.1.0.jar]
    Jan 16, 2014 5:41:45 PM org.drools.compiler.kie.builder.impl.ClasspathKieProject discoverKieModules
    INFO: Found kmodule: jar:file:/Users/rafaelbenevides/.m2/repository/org/jboss/quickstarts/brms/helloworld-brms-kmodule/6.1.0/helloworld-brms-kmodule-6.1.0.jar!/META-INF/kmodule.xml
    Jan 16, 2014 5:41:45 PM org.drools.compiler.kie.builder.impl.KieRepositoryImpl addKieModule
    INFO: KieModule was added:ZipKieModule[ ReleaseId=org.jboss.quickstarts.brms:helloworld-brms-kmodule:6.1.0file=/Users/rafaelbenevides/.m2/repository/org/jboss/quickstarts/brms/helloworld-brms-kmodule/6.1.0/helloworld-brms-kmodule-6.1.0.jar]
    Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.487 sec
    
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

