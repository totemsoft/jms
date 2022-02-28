========================= DETAILED INSTRUCTIONS =====================
WebLogic 9.2.3
Copy file "jaas.config" to ${java.home}/jre/lib/security directory
Edit "java.security" file in the same directory, add this line
#
# Default login configuration file
#
#login.config.url.1=file:${user.home}/.java.login.config
login.config.url.1=file:${java.home}/lib/security/jaas.config
