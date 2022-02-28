ALTER TABLE CONTACT ADD DATE_OF_BIRTH datetime;

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('JMS.01.05', 'JMS.01.04');