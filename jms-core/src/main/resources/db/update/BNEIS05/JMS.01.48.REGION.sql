IF EXISTS (SELECT name  FROM sysobjects WHERE  name = N'V_REGION' AND type = 'V')
    DROP VIEW V_REGION
GO
CREATE VIEW V_REGION
AS
SELECT
    SUBSTRING(EscadRegionPrefix, 1, 1) AS REGION_ID,
    DivisionId AS DIVISION_ID,
    LocationCode AS CODE,
    RegionName AS NAME,
    DescriptionExtended AS Description
FROM [BNEIS05].Fire.dbo.LocationHierarchy
WHERE
    (OrgUnitType = 'REGI') AND (IsLocationActive = 1) AND (AuthorityType = 'URBAN') AND (EscadRegionPrefix IS NOT NULL)
GO
-- SELECT * FROM V_REGION

/*
UPDATE FCA SET REGION_ID = NULL WHERE REGION_ID IS NOT NULL
UPDATE FCA SET STATION_CODE = NULL WHERE STATION_CODE IS NOT NULL
SELECT * FROM REGION
DELETE FROM STATION
DELETE FROM AREA
DELETE FROM REGION
*/
IF EXISTS (SELECT name  FROM sysobjects WHERE  name = N'sps_region' AND type = 'P')
    DROP PROCEDURE sps_region
GO
CREATE PROCEDURE sps_region
AS
BEGIN TRANSACTION;
BEGIN TRY
    DECLARE @regionId int, @code varchar(50), @name varchar(255);
    
    SELECT REGION_ID, CODE, NAME
    INTO #REGION
    FROM V_REGION
    WHERE DIVISION_ID = 1

    DECLARE cRegion CURSOR FOR
    SELECT REGION_ID, CODE, NAME
    FROM #REGION
    ORDER BY REGION_ID;
    
	OPEN cRegion
	FETCH NEXT FROM cRegion INTO @regionId, @code, @name
	
	WHILE @@FETCH_STATUS = 0 
	BEGIN
        IF EXISTS (SELECT REGION_ID FROM REGION WHERE REGION_ID = @regionId)
        BEGIN
            -- PRINT @name + '=' + convert(varchar(10), @regionId)
	        UPDATE REGION SET CODE = @code, NAME = @name WHERE REGION_ID = @regionId
        END 
        ELSE
        BEGIN
            SET IDENTITY_INSERT REGION ON
            INSERT INTO REGION (REGION_ID, CODE, NAME) VALUES (@regionId, @code, @name)
            SET IDENTITY_INSERT REGION OFF
        END
		FETCH NEXT FROM cRegion INTO @regionId, @code, @name
	END
	CLOSE cRegion
	DEALLOCATE cRegion

    DROP TABLE #REGION

END TRY
BEGIN CATCH
    SELECT 
        ERROR_NUMBER() AS ErrorNumber
        ,ERROR_SEVERITY() AS ErrorSeverity
        ,ERROR_STATE() AS ErrorState
        ,ERROR_PROCEDURE() AS ErrorProcedure
        ,ERROR_LINE() AS ErrorLine
        ,ERROR_MESSAGE() AS ErrorMessage;

    IF @@TRANCOUNT > 0
        ROLLBACK TRANSACTION;
END CATCH;

IF @@TRANCOUNT > 0
    COMMIT TRANSACTION;

GO

EXECUTE sps_region
GO
-- SELECT * FROM REGION
