-- UI access
UPDATE SYSTEM_FUNCTION SET NAME = '/uaaIncident', DESCRIPTION = 'Unwanted Alarm Activation (UAA) Incident Details'
 WHERE SYSTEM_FUNCTION_ID = 1147;

INSERT INTO SYSTEM_FUNCTION (SYSTEM_FUNCTION_ID, MODULE, NAME, DESCRIPTION)
 VALUES (1149, '/file', '/uaaInvoice', 'Unwanted Alarm Activation (UAA) Invoice Details');
INSERT INTO SYSTEM_ACCESS (SECURITY_GROUP_ID, SYSTEM_FUNCTION_ID) VALUES (2, 1149);

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('JMS.01.40', 'JMS.01.39');