ALTER TABLE REGION ADD CODE char(2) NULL;

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('JMS.01.42', 'JMS.01.41');