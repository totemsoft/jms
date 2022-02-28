-- 5.3.27. Multi-site Identification and Linkage (also see subPanel column)
INSERT INTO SYSTEM_FUNCTION (SYSTEM_FUNCTION_ID, MODULE, NAME, DESCRIPTION, LOGICAL)
 VALUES (1135, '/file', '/viewMultiSite', 'View Multi-site Identification and Linkage details', 'Y');
INSERT INTO SYSTEM_FUNCTION (SYSTEM_FUNCTION_ID, MODULE, NAME, DESCRIPTION)
 VALUES (1136, '/file', '/editMultiSite', 'Edit Multi-site Identification and Linkage details');

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('JMS.01.11', 'JMS.01.10');