========================= DETAILED INSTRUCTIONS =====================
https://community.jboss.org/message/724488

JBoss 5
http://docs.jboss.org/jbossas/admindevel326/html/ch8.chapter.html
http://www.javaworld.com/javaforums/showthreaded.php?Cat=2&Number=2500&page=0
Migrating a WebLogic EJB Application to JBoss
http://www.onjava.com/lpt/a/5662

Edit
vi $JBOSS_HOME/server/default/conf/login-config.xml
add the following lines:
  <application-policy name="qfrsLogin">
    <authentication>
      <login-module code="au.gov.qld.fire.security.auth.JdbcLoginModule" flag="required">
      </login-module>
    </authentication>
  </application-policy>

Copy jtds-1.2.4.jar to server/default/lib/
cp /home/shibaevv/dev/.m2/repository/net/sourceforge/jtds/jtds/1.2.4/jtds-1.2.4.jar $JBOSS_HOME/server/default/lib/

Copy server/default/deploy/mssql-ds.xml

I you want to change default ports, eg 8080 to 18080:
Update "server/default/conf/bootstrap/bindings.xml"
Update "server/default/deploy/jbossweb.sar/server.xml"


http://www.jboss.org/community/wiki/UsingModjk12withjboss
http://www.jboss.org/community/wiki/UsingModjk12WithJBossAndIIS

jbossweb.sar/server.xml

      <!-- A AJP 1.3 Connector on port 8009 -->
      <Connector port="8009" address="${jboss.bind.address}"
         emptySessionPath="true" enableLookups="false" redirectPort="8443" 
         protocol="AJP/1.3" connectionTimeout="600000" maxThreads="200"/>

      <Engine name="jboss.web" defaultHost="localhost" jvmRoute="jboss01">

In IIS Manager/Web Service Extensions/All Unknown ISAPI Extensions=Allowed

Which says "allow access by every host".
JAVA_OPTS: -Xrs -Dprogram.name=run.bat -server -Xms256m -Xmx512m -XX:MaxPermSize=256m -Djboss.bind.address=0.0.0.0 -Dorg.jboss.resolver.warning=true -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000
By default it's set to: ./bin/run.sh -b 127.0.0.0


APACHE httpd.conf
vi /usr/apache/apache2/conf/httpd.conf
service apachectl stop
service apachectl start
======================
<IfModule proxy_module>
  ProxyRequests On
  ProxyVia On

  <Proxy *>
    Order Deny,Allow
#    Deny from all
    Allow from 192.168.1
    Allow from 127.0.0.1
  </Proxy>

#  ProxyPass / ajp://127.0.0.1:8009/
#  ProxyPassReverse / ajp://127.0.0.1:8009/

  <Location /elixir/>
    ProxyPass ajp://localhost:8009/elixir/
    ProxyPassReverse ajp://localhost:8009/elixir/
  </Location>
  <Location /elixir-uat/>
    ProxyPass ajp://localhost:8009/elixir-uat/
    ProxyPassReverse ajp://localhost:8009/elixir-uat/
  </Location>
  <Location /elixir-dev/>
    ProxyPass ajp://localhost:8009/elixir-dev/
    ProxyPassReverse ajp://localhost:8009/elixir-dev/
  </Location>

  <Location /jms-dev/>
    ProxyPass ajp://localhost:18009/jms-dev/
    ProxyPassReverse ajp://localhost:18009/jms-dev/
  </Location>

</IfModule>


groupadd -g 220 jboss
useradd -u 220 -g jboss -c "JBoss" -r -d "/usr/jboss/jboss-5.1.0.GA" -s "/sbin/nologin" jboss
chown -R jboss:jboss /usr/jboss/jboss-5.1.0.GA
chown -R jboss:jboss /tmp/jms-dev/logs

ln -s -f $JBOSS_HOME/bin/jboss_init_redhat.sh /etc/init.d/jboss
ln -s -f /etc/init.d/jboss /etc/rc3.d/S85jboss
ln -s -f /etc/init.d/jboss /etc/rc5.d/S85jboss 
ln -s -f /etc/init.d/jboss /etc/rc4.d/S85jboss
ln -s -f /etc/init.d/jboss /etc/rc6.d/K15jboss 
ln -s -f /etc/init.d/jboss /etc/rc0.d/K15jboss
ln -s -f /etc/init.d/jboss /etc/rc1.d/K15jboss
ln -s -f /etc/init.d/jboss /etc/rc2.d/K15jboss

####################################################

more $JBOSS_HOME/server/default/log/boot.log
more $JBOSS_HOME/server/default/log/server.log
more /tmp/jms-dev/logs/jms.log
more /tmp/jms-dev/logs/jms-stat.log
