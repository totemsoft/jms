-- allow link other jms entity to action
ALTER TABLE FILE_ACTION ADD LINK_ID numeric(11,0) NULL;
ALTER TABLE JOB_ACTION ADD LINK_ID numeric(11,0) NULL;

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('JMS.01.27', 'JMS.01.26');