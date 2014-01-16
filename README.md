jboss-brms-quickstarts: BRMS Quickstarts
========================================
Target Product: BRMS
Source: <https://github.com/jboss-developer/jboss-brms-quickstarts/>  

Configure BRMS
--------------

1. Obtain BRMS 6.0 Beta (Red Hat JBoss BRMS 6.0.0 Beta 1 Deployable for EAP 6.1.1) and EAP 6.1.1 server distribution on Red Hat's Customer Portal at <https://access.redhat.com/jbossnetwork/restricted/listSoftware.html>

2. Unzip BRMS and EAP on the same folder
   
    unzip jboss-eap-6.1.1.zip 
    unzip -o unzip -o jboss-brms-6.0.0.Beta-redhat-5-deployable-eap6.x.zip 
    

3. Add an application user

        For Linux:   BRMS_HOME/bin/add-user.sh -a -u 'quickstartUser' -p 'quickstartPwd1!' -ro 'admin,analyst'
        For Windows: BRMS_HOME\bin\add-user.bat  -a -u 'quickstartUser' -p 'quickstartPwd1!' -ro 'admin,analyst'
        

Import BRMS repository
----------------------

1. Navigate to http://localhost:8080/business-central in a web browser. 

2. Log in with the user 'quickstartsUser' and use 'quickstartPwd1!' as password.

3. Go to `Authoring` -> `Administration`

4. Click on `Repositories` and `Clone repository`

5. On repository name type: jboss-brms-repository

6. Select `example` on Organization Unit

7. On Git URL type: https://github.com/jboss-developer/jboss-brms-repository.git

8. Click on `Clone` button.


