Red Hat JBoss BRMS & BPM Suite Quickstarts
========================================
Target Product: JBoss BRMS & BPM Suite 
Source: <https://github.com/jboss-developer/jboss-brms-quickstarts/>  
Summary:These quickstarts demonstrate how to use BRMS and BPM to manage and deploy business process and rules.

*Red Hat JBoss BRMS* is an open source decision management platform that combines Business Rules Management and Complex Event Processing. It automates business decisions and makes that logic available to the entire business. 

*Red Hat JBoss BPM Suite* is an open source business process management suite that combines Business Process Management and Business Rules Management and enables business and IT users to create, manage, validate, and deploy Business Processes and Rules.

Both BRMS and BPM Suite use a centralized repository where all resources are stored. This ensures consistency, transparency, and the ability to audit across the business. Business users can modify business logic without requiring assistance from IT personnel.

The root folder of each individual quickstart contains a README file with specific details on how to configure the environment and how build and run the example. In some most cases you need to configure either BRMS or BPM and import the BRMS repository.

* [Configure EAP] (#configure-eap): Download and configure Red Hat JBoss EAP.

* [Configure BRMS](#configure-brms): Download and configure Red Hat JBoss BRMS.

* [Configure BPM Suite](#configure-bpm-suite): Download and configure Red Hat JBoss BPM Suite.

* [Start the JBoss BRMS or BPM Suite Server](#start-the-jboss-eap-server): Start the JBoss BRMS or BPM Suite Server.

* [Import the BRMS Repository](#import-the-brms-repository): Import the BRMS repository containing the rules and resources used by the quickstarts.

Download and configure EAP
--------------------------
JBoss BRMS and BPM Suite need installation of JBoss EAP (or other supported container). Following steps will guide you
through the process of downloading and installing EAP. If you already have JBoss EAP installed, you can skip this section.

1. Download EAP installer
    * Login to the Customer Portal at <https://access.redhat.com/jbossnetwork/restricted/listSoftware.html>.
    * Select `Enterprise Application Platform` from the product list.
    * Make sure the selected version is 6.4.
    * Find `Red Hat JBoss Enterprise Application Platform 6.4.0 Installer` in the file list and click `Download`.
    * This downloads the JBoss EAP 6.4 Installer to a directory of your choice.
    
    * Visit the JBoss EAP page at <https://www.jboss.org/products/eap.html>.
    * Select `Download JBoss EAP`, login or create an account, and agree to the download terms and conditions.
    * Click on `Installer` for version 6.4.0.GA.
    * This downloads the JBoss EAP 6.4 Installer to a directory of your choice.
    
2. Run the installer with: `java -jar jboss-eap-6.4.0-installer.jar`. Click through the UI and install the EAP into
directory of your choice.

_Note:_: These instructions use `EAP_HOME` to refer to the `ROOT_DIR/jboss-eap-6.4/` directory where the EAP was installed.
    
Please refer to [JBoss EAP Installation Guide](https://access.redhat.com/documentation/en-US/JBoss_Enterprise_Application_Platform/6.4/html/Installation_Guide/index.html)
for more details about the installation process and other installation options (for example installing from zip file).

Configure BRMS
--------------
1. [Optional] Download and install JBoss EAP 6.4 (Enterprise Application Platform) from the customer portal or from JBoss.org.
See [Download and configure EAP](#configure-eap) for details on how to install EAP.
If you already have JBoss EAP installed, you can go directly to step 2.

2. Download JBoss BRMS 6.2.0 from the Customer Portal or from JBoss.org
    * Login to the Customer Portal at <https://access.redhat.com/jbossnetwork/restricted/listSoftware.html>
    * Select `BRMS` from the product list.
    * Find `Red Hat JBoss BRMS 6.2.0 Installer` in the file list and click `Download`.
    * This downloads the BRMS 6.2.0 Installer to a directory of your choice.

    * Visit the JBoss BRMS page at <https://www.jboss.org/products/brms.html>
    * Select `Download JBoss BRMS`, login or create an account, and agree to the download terms and conditions.
    * Click on `Installer` for version 6.2.0.GA.
    * This downloads the JBoss BRMS 6.2.0 Installer to a directory of your choice.

3. Run the installer with: `java -jar jboss-brms-6.2.0.GA-installer.jar`. Click through the UI and point the installer
to your local EAP installation.
 
   The JBoss BRMS installation is also now located under the `ROOT_DIR/jboss-eap-6.4/` directory.
   
   _Note:_ These instructions use `EAP_HOME` to refer to the `ROOT_DIR/jboss-eap-6.4/` directory.

4. Add an application user

        For Linux:   EAP_HOME/bin/add-user.sh -a -u 'quickstartUser' -p 'quickstartPwd1!' -ro 'admin,analyst'
        For Windows: EAP_HOME\bin\add-user.bat  -a -u 'quickstartUser' -p 'quickstartPwd1!' -ro 'admin,analyst'
    
Configure BPM Suite
-------------------
1. [Optional] Download and install JBoss EAP 6.4 (Enterprise Application Platform) from the customer portal or from JBoss.org.
See [Download and configure EAP](#configure-eap) for details on how to install EAP.
If you already have JBoss EAP installation available, you can go directly to step 2.

2. Download BPM Suite 6.2.0 from the Customer Portal or from JBoss.org
    * Login to the Customer Portal at <https://access.redhat.com/jbossnetwork/restricted/listSoftware.html>/
    * Select `BPM Suite` from the Business Automation Platforms product list.
    * Find `Red Hat JBoss BPM Suite 6.2.0 Installer` in the file list and click `Download`.
    * This downloads the BPM Suite 6.2.0 Installer to a directory of your choice.

    * Visit the BPM Suite page at <https://www.jboss.org/products/bpmsuite.html>/
    * Select `Download JBoss BPM Suite`, login or create an account, and agree to the download terms and conditions.
    * Click on `Installer` for version 6.2.0.GA.
    * This downloads the BPM Suite 6.2.0 Installer to a directory of your choice.

3. Run the installer with: `java -jar jboss-bpmsuite-6.2.0.GA-installer.jar`. Click through the UI and point the installer
to your local EAP installation.
 
   The BPM Suite installation is also now located the `ROOT_DIR/jboss-eap-6.4/` directory.
   
   _Note:_ These instructions use `EAP_HOME` to refer to the `ROOT_DIR/jboss-eap-6.4/` directory.

4. Add an application user

         For Linux:   EAP_HOME/bin/add-user.sh -a -u 'quickstartUser' -p 'quickstartPwd1!' -ro 'admin,analyst'
         For Windows: EAP_HOME\bin\add-user.bat  -a -u 'quickstartUser' -p 'quickstartPwd1!' -ro 'admin,analyst'


Start the JBoss BRMS or BPM Suite Server
----------------------------------------

1. Open a command prompt and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server:

        For Linux:   EAP_HOME/bin/standalone.sh
        For Windows: EAP_HOME\bin\standalone.bat

Import the BRMS Repository
--------------------------

1. Make sure you have started the JBoss EAP Server as described above.
 
2. Navigate to <http://localhost:8080/business-central> in a web browser. 

3. Log in with the following credentials:

         Username: quickstartUser
         Password: quickstartPwd1!

4. Choose menu option `Authoring` -> `Administration`

5. Choose sub-menu option `Repositories` -> `Clone repository`

6. Complete the form as follows:

         Repository Name:      jboss-brms-repository
         Organizational Unit:  example
            either
         Git URL:              file:///path/to/jboss-brms-repository
            or
         Git URL:              https://github.com/jboss-developer/jboss-brms-repository.git
         User Name:            <leave blank>
         Password:             <leave blank>

7. Click the `Clone` button to create the repository. You see the message "The repository is cloned successfully".
