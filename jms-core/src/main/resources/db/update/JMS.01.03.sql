INSERT INTO SYSTEM_FUNCTION (SYSTEM_FUNCTION_ID, MODULE, NAME, DESCRIPTION)
 VALUES (1119, '/file', '/downloadFileDoc', 'Download File FileDoc');
INSERT INTO SYSTEM_ACCESS (SECURITY_GROUP_ID, SYSTEM_FUNCTION_ID) VALUES (2, 1119);

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('JMS.01.03', 'JMS.01.02');