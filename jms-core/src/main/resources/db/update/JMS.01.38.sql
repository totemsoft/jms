ALTER TABLE BUILDING_CONTACT ADD NOTES varchar(1024) NULL;

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('JMS.01.38', 'JMS.01.37');