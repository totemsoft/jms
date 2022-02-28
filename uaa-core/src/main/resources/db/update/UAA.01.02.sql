USE [JMS];

IF EXISTS (SELECT name FROM sys.objects WHERE name = N'UAA_INJURY' AND type = N'U')
    DROP TABLE UAA_INJURY
GO
IF EXISTS (SELECT name FROM sys.objects WHERE name = N'UAA_INJURY_TYPE' AND type = N'U')
    DROP TABLE UAA_INJURY_TYPE
GO

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('UAA.01.02', 'UAA.01.01');