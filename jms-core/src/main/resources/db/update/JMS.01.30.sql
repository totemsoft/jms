-- UI access
INSERT INTO SYSTEM_FUNCTION (SYSTEM_FUNCTION_ID, MODULE, NAME, DESCRIPTION)
 VALUES (1147, '/file', '/uaaHistory', 'Unwanted Alarm Activation (UAA) History and Incident Details');
INSERT INTO SYSTEM_ACCESS (SECURITY_GROUP_ID, SYSTEM_FUNCTION_ID) VALUES (2, 1147);

-- 4 and 5
INSERT INTO BUILDING_CONTACT_TYPE (NAME) VALUES ('Security Company');
INSERT INTO BUILDING_CONTACT_TYPE (NAME) VALUES ('Fire Maintenence Company');

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('JMS.01.30', 'JMS.01.29');