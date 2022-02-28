USE [qfrsfiru]
GO

IF NOT EXISTS (SELECT name FROM sys.database_principals WHERE name = 'jms')
    CREATE USER [jms] FOR LOGIN [jms] WITH DEFAULT_SCHEMA=[db_datareader]
GO
-- TODO: why [db_datareader] is not enough? need to add [db_owner]
EXEC sp_addrolemember N'db_owner', N'jms'
GO


USE [JMS];

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================================
-- Create view V_FIRU_INJURIES
-- =============================================================
IF EXISTS (SELECT name FROM sysobjects WHERE name = N'V_FIRU_INJURIES' AND type = 'V')
    DROP VIEW V_FIRU_INJURIES
GO

CREATE VIEW V_FIRU_INJURIES AS
SELECT
[Fire Call No] AS FIRE_CALL_NO,
Inj_no AS INJURY_NO,
Region AS REGION,
Year AS INCIDENT_YEAR,
Incident_date AS INCIDENT_DATE,
Surname AS SURNAME, 
[Given Names] AS FIRST_NAME,
Gender AS GENDER,
[Date of Birth] AS DATE_OF_BIRTH,
[Address No] AS ADDR_LINE_1,
Street AS ADDR_LINE_2,
Town AS SUBURB,
Postcode AS POSTCODE,
[Type of Injuries] AS INJURY_TYPE,
[Injuries to] AS INJURY_TO, 
[Multiple Injury Types] AS MULTIPLE_INJURY_TYPE,
[Multiple Injuries to] AS MULTIPLE_INJURY_TO,
[Alcohol Level] AS ALCOHOL_LEVEL,
Drugs AS DRUGS,
[Treated at] AS TREATED_AT
FROM qfrsfiru.dbo.FIRU_Injuries
GO

-- SELECT * FROM V_FIRU_INJURIES ORDER BY FIRE_CALL_NO;

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('UAA.01.01', 'UAA.01.00');