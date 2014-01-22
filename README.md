jboss-brms-quickstarts: BRMS Quickstarts
========================================
Target Product: BRMS
Source: <https://github.com/jboss-developer/jboss-brms-quickstarts/>  

Configure BRMS
--------------

1. Download BRMS 6.0 0 Beta from the Customer Portal
    * Login to the Customer Portal at <https://access.redhat.com/jbossnetwork/restricted/listSoftware.html>/
    * Select `BRMS Platform` from the Product list.
    * Find `Red Hat JBoss BRMS 6.0.0 Beta Deployable for EAP 6.1.1` in the file list and click `Download`.
    * This downoads the `jboss-brms-6.0.0.Beta-redhat-5-deployable-eap6.x.zip` file to a directory of your choice.
 
2. Download JBoss EAP 6.1.1 from the Customer Portal
    * Login to the Customer Portal at <https://access.redhat.com/jbossnetwork/restricted/listSoftware.html>/
    * Select `Application Platform` from the Product list.
    * Select Version `6.1.1`.
    * Find `Red Hat JBoss Enterprise Application Platform 6.1.1` in the file list and click `Download`.
    * This downoads the `jboss-eap-6.1.1.zip` file to a directory of your choice.

3. Unzip BRMS and EAP into the same directory.

    * Move the downloaded zip archive to the Red Hat JBoss Enterprise Application Platform home directory (EAP_HOME; the jboss-eap-6.1.1 directory).
    * Unzip the downloaded zip archive: make sure it is merged into the EAP_HOME directory (jboss-eap-6.1.1). It is necessary to overwrite the files that already exist in the EAP_HOME directory with their versions from the downloaded zip archive. When prompted to do so, accept overwriting the original files.
   
    unzip jboss-eap-6.1.1.zip 
    unzip -o jboss-brms-6.0.0.Beta-redhat-5-deployable-eap6.x.zip -d EAP_HOME/
    

3. Add an application user

        For Linux:   EAP_HOME/bin/add-user.sh -a -u 'quickstartUser' -p 'quickstartPwd1!' -ro 'admin,analyst'
        For Windows: EAP_HOME\bin\add-user.bat  -a -u 'quickstartUser' -p 'quickstartPwd1!' -ro 'admin,analyst'
    
Start the JBoss Server
-------------------------

1. Open a command prompt and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server:

        For Linux:   EAP_HOME/bin/standalone.sh
        For Windows: EAP_HOME\bin\standalone.bat

Import BRMS repository
----------------------

1. Navigate to http://localhost:8080/business-central in a web browser. 

2. Log in with the user 'quickstartUser' and use 'quickstartPwd1!' as password.

3. Choose menu option `Authoring` -> `Administration`

4. Choose on the sub menu option `Repositories` -> `Clone repository`

5. Complete the form as follows:

       Repository Name: jboss-brms-repository
       Organizational Unit: example
       Git URL: https://github.com/jboss-developer/jboss-brms-repository.git
       User Name: 
       Password:

6. Click the `Clone` button to create the repository. You see a message "The repository is cloned successfully".


