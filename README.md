jboss-brms-quickstarts: BRMS Quickstarts
========================================
Target Product: BRMS  
Source: <https://github.com/jboss-developer/jboss-brms-quickstarts/>  

Introduction
------------

These quickstarts run on Red Hat JBoss Enterprise Application Platform 6.1 or later. We recommend using the JBoss EAP ZIP file. This version uses the correct dependencies and ensures you test and compile against your runtime environment. 


Configure Maven
---------------

### Configure Maven to Build and Deploy the Quickstarts

The quickstarts use artifacts located in the JBoss GA and Early Access repositories. You must configure Maven to use these repositories before you build and deploy the quickstarts. 

_Note: These instructions assume you are working with a released version of the quickstarts. If you are working with the quickstarts located in the GitHub master branch, follow the instructions located in the [Contributing Guide](CONTRIBUTING.md#configure-maven)._

1. Locate the Maven install directory for your operating system. It is usually installed in `${user.home}/.m2/`. 

            For Linux or Mac:   ~/.m2/
            For Windows: "\Documents and Settings\USER_NAME\.m2\"  -or-  "\Users\USER_NAME\.m2\"

2. If you have an existing `settings.xml` file, rename it so you can restore it later.

            For Linux or Mac:  mv ~/.m2/settings.xml ~/.m2/settings-backup.xml
            For Windows: ren "\Documents and Settings\USER_NAME\.m2\settings.xml" settings-backup.xml
                    -or- ren "\Users\USER_NAME\.m2\settings.xml" settings-backup.xml
                   
3. If you have an existing `repository/` directory, rename it so you can restore it later. For example

            For Linux or Mac:  mv ~/.m2/repository/ ~/.m2/repository-backup/
            For Windows: ren "\Documents and Settings\USER_NAME\.m2\repository\" repository-backup
                    -or- ren "\Users\USER_NAME\.m2\repository\" repository-backup
4. Copy the `settings.xml` file from the root of the quickstarts directory to your Maven install directory.
 
            For Linux or Mac:  cp QUICKSTART_HOME/settings.xml  ~/.m2/settings.xml
            For Windows: copy QUICKSTART_HOME/settings.xml "\Documents and Settings\USER_NAME\.m2\settings.xml"
                    -or- copy QUICKSTART_HOME/settings.xml "\Users\USER_NAME\.m2\settings.xml"

_Note:_ If you do not wish to configure the Maven settings, you must pass the configuration setting on every Maven command as follows: ` -s QUICKSTART_HOME/settings.xml`

### Restore Your Maven Configuration When You Finish Testing the Quickstarts

1. Locate the Maven install directory for your operating system. It is usually installed in `${user.home}/.m2/`. 

            For Linux or Mac:   ~/.m2/
            For Windows: "\Documents and Settings\USER_NAME\.m2\"  -or-  "\Users\USER_NAME\.m2\"

2. Restore the `settings.xml` file/

            For Linux or Mac:  mv ~/.m2/settings-backup.xml ~/.m2/settings.xml
            For Windows: ren "\Documents and Settings\USER_NAME\.m2\settings-backup.xml" settings.xml
                    -or- ren "\Users\USER_NAME\.m2\settings-backup.xml" settings.xml
                   
3. Restore the `repository/` directory

            For Linux or Mac:  mv ~/.m2/repository-backup/ ~/.m2/repository/
            For Windows: ren "\Documents and Settings\USER_NAME\.m2\repository-backup\" repository
                    -or- ren "\Users\USER_NAME\.m2\repository-backup\" repository
            


