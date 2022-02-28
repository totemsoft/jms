How to use BTM as the transaction manager in Tomcat 5.5, 6.x
http://docs.codehaus.org/display/BTM/Tomcat2x

STEP 1: Copy the BTM jars
	Copy the following jars from the BTM distribution to the Tomcat common/lib/ directory:
	    * /org/codehaus/btm/btm/2.1.0/btm-2.1.0.jar
	    * /javax/transaction/jta/1.1/jta-1.1.jar
	    * /org/slf4j/slf4j-api/1.5.8/slf4j-api-1.5.8.jar
	    * /org/slf4j/slf4j-log4j12/1.5.8/slf4j-log4j12-1.5.8.jar
	    * /log4j/log4j/1.2.15/log4j-1.2.15.jar
	You will also need to copy your JDBC driver's JAR file in that same folder.

	Copy the following jars from the BTM distribution to the Tomcat server/lib/ directory:
	    * /org/codehaus/btm/btm-tomcat55-lifecycle/2.1.0/btm-tomcat55-lifecycle-2.1.0.jar (it works with both Tomcat 5.5 and Tomcat 6)

STEP 2: Configure BTM as the transaction manager
	Edit the file named server.xml under Tomcat's conf/ directory. Under this line:
	    <Listener className="org.apache.catalina.mbeans.GlobalResourcesLifecycleListener" />
	add this one:
	    <Listener className="bitronix.tm.integration.tomcat55.BTMLifecycleListener" />
	The <Listener> tag will make sure BTM is started when Tomcat starts up and shutdown when Tomcat shuts down.

	The next step is to edit the file named context.xml under Tomcat's conf/ directory. Under this line:
	    <WatchedResource>WEB-INF/web.xml</WatchedResource>
	add this one:
	    <Transaction factory="bitronix.tm.BitronixUserTransactionObjectFactory" />
	The <Transaction> tag will bind the transaction manager at the standard JNDI location java:comp/UserTransaction.


To set up Tomcat to use JAASRealm with your own JAAS login module, you will need to follow these steps:
http://tomcat.apache.org/tomcat-5.5-doc/realm-howto.html#JAASRealm

STEP 1: To configure a JAASRealm, you must create a <Realm> element and nest it in your $CATALINA_HOME/conf/server.xml
<Realm className="org.apache.catalina.realm.JAASRealm"
       appName="qfrsLogin"
       userClassNames="au.gov.qld.fire.jms.security.GroupPrincipal" />

-Xms256m -Xmx512m
-Djava.security.auth.login.config="C:\apache\apache-tomcat-5.5.27\conf\jaas.config"
-Djava.security.auth.login.config="C:\apache\apache-tomcat-6.0.32\conf\jaas.config"
