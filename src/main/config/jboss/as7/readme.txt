new modules to add:
#
# jdbc
net.sourceforge.jtds
#
# jaxrs - optional
javax.ws.rs.api (replace main/resteasy with jsr311-api)
# cxf - optional
org.apache.cxf
<resource-root path="cxf-rt-frontend-jaxrs-2.4.6.jar"/>
<module name="javax.ws.rs.api" optional="true"/>
#
# application
# cglib.cglib-nodep (http://www.springsource.org/node/3654)
org.apache.commons.digester
org.apache.commons.fileupload
org.apache.commons.httpclient
org.apache.poi
org.apache.tiles
org.aopalliance
org.aspectj
org.springframework.spring
org.springframework.ldap
org.springframework.security
# edit the modules\sun\jdk\main\module.xml by adding: <path name="com/sun/image/codec/jpeg"/>

# JAAS
# Edit
# JBOSS7_HOME=/usr/jboss/jboss-as-7.1.1.Final
# vi $JBOSS7_HOME/standalone/configuration/standalone.xml
# add the following lines:
                <security-domain name="qfrsLogin" cache-type="default">
                    <authentication>
                        <login-module code="au.gov.qld.fire.security.auth.JdbcLoginModule" flag="required"/>
                    </authentication>
                </security-domain>
