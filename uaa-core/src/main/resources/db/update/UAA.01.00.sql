DELETE FROM DBVERSION WHERE DBVERSION LIKE 'UAA.%';

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('UAA.01.00', NULL);