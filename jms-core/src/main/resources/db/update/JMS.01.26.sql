-- UI access
INSERT INTO SYSTEM_FUNCTION (SYSTEM_FUNCTION_ID, MODULE, NAME, DESCRIPTION)
 VALUES (16, '/user', '/workflowRegister', 'Workflow/Action Item Register');
INSERT INTO SYSTEM_ACCESS (SECURITY_GROUP_ID, SYSTEM_FUNCTION_ID) VALUES (1, 16);

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('JMS.01.26', 'JMS.01.25');