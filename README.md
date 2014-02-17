jboss-brms-quickstarts: BRMS Quickstarts
========================================
Target Product: BRMS  
Source: <https://github.com/jboss-developer/jboss-brms-quickstarts/>  

Configure BRMS
--------------

1. Download BRMS 6.0 0 from the Customer Portal
    * Login to the Customer Portal at <https://access.redhat.com/jbossnetwork/restricted/listSoftware.html>/
    * Select `BRMS Platform` from the Product list.
    * Find `Red Hat JBoss BRMS 6.0.0 Deployable for EAP 6.1.1` in the file list and click `Download`.
    * This downoads the `jboss-brms-6.0.0.GA-redhat-2-deployable-eap6.x.zip` file to a directory of your choice.
 
2. Download JBoss EAP 6.1.1 from the Customer Portal
    * Login to the Customer Portal at <https://access.redhat.com/jbossnetwork/restricted/listSoftware.html>/
    * Select `Application Platform` from the Product list.
    * Select Version `6.1.1`.
    * Find `Red Hat JBoss Enterprise Application Platform 6.1.1` in the file list and click `Download`.
    * This downoads the `jboss-eap-6.1.1.zip` file to a directory of your choice.

3. Unzip EAP into the directory of your choice:

        For example: unzip jboss-eap-6.1.1.zip -d ROOT_DIR/

   The JBoss EAP installation is now located the `ROOT_DIR/jboss-eap-6.1.1/` directory,

4. Unzip BRMS into the same ROOT_DIR directory. This will merge the BRMS installation with the EAP installation. 
 
        For example: unzip -o jboss-brms-6.0.0.GA-redhat-2-deployable-eap6.x.zip -d ROOT_DIR/

   The BRMS installation is also now located the `ROOT_DIR/jboss-eap-6.1.1/` directory. 
   
   _Note:_ These instructions use `EAP_HOME` to refer to the `ROOT_DIR/jboss-eap-6.1.1/` directory.

5. Add an application user

        For Linux:   EAP_HOME/bin/add-user.sh -a -u 'quickstartUser' -p 'quickstartPwd1!' -ro 'admin,analyst'
        For Windows: EAP_HOME\bin\add-user.bat  -a -u 'quickstartUser' -p 'quickstartPwd1!' -ro 'admin,analyst'
    
Start the JBoss Server
-------------------------

1. Open a command prompt and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server:

        For Linux:   EAP_HOME/bin/standalone.sh
        For Windows: EAP_HOME\bin\standalone.bat

Import the BRMS Repository
----------------------

1. Navigate to <http://localhost:8080/business-central> in a web browser. 

2. Log in with the following credentials:

        Username: quickstartUser
        Password: quickstartPwd1!

3. Choose menu option `Authoring` -> `Administration`

4. Choose sub-menu option `Repositories` -> `Clone repository`

5. Complete the form as follows:

        Repository Name:      jboss-brms-repository
        Organizational Unit:  example
        Git URL:              https://github.com/jboss-developer/jboss-brms-repository.git
        User Name:            <leave blank>
        Password:             <leave blank>

6. Click the `Clone` button to create the repository. You see the message "The repository is cloned successfully".


