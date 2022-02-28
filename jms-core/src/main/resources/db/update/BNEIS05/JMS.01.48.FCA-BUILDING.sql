IF EXISTS (SELECT name  FROM sysobjects WHERE  name = N'V_FCA_BUILDING' AND type = 'V')
    DROP VIEW V_FCA_BUILDING
GO
CREATE VIEW V_FCA_BUILDING
AS
SELECT
    PD.fca AS FCA_NO,
    CASE WHEN PD.[premisename] IS NULL THEN 'TBD' WHEN PD.[premisename] = '' THEN 'TBD' ELSE PD.[premisename] END AS BUILDING_NAME, 
    PD.streetnumber + ' ' + PD.streetname AS ADDR_LINE_1,
    PD.suburb AS SUBURB,
    PD.postcode AS POSTCODE,
    ES.Station AS STATION_NAME
FROM         [BB1911\SAMC].AIMS.dbo.tbl_pd AS PD LEFT OUTER JOIN
                      [BB1911\SAMC].ESCAD.dbo.ESCAD_DATA AS ES ON ES.FCA = PD.fca
WHERE     (PD.fca < '90000-01') AND (LEN(PD.fca) = 8) OR
                      (PD.fca < '90000-01') AND (LEN(PD.fca) = 8)
GO
-- SELECT * FROM V_FCA_BUILDING ORDER BY FCA_NO

IF EXISTS (SELECT name  FROM sysobjects WHERE  name = N'V_LOCATION_STATION' AND type = 'V')
    DROP VIEW V_LOCATION_STATION
GO
CREATE VIEW V_LOCATION_STATION
AS
SELECT
    LocationCode AS STATION_CODE,
    EscadStationName AS STATION_NAME
FROM [BNEIS05].Fire.dbo.LocationHierarchy
WHERE (AuthorityType = 'URBAN' AND EscadStationName LIKE '%FS') OR (AuthorityType = 'RURAL' AND EscadStationName LIKE '%RFB')
GO
-- SELECT * FROM V_LOCATION_STATION ORDER BY STATION_NAME

IF EXISTS (SELECT name  FROM sysobjects WHERE  name = N'sps_fca_building' AND type = 'P')
    DROP PROCEDURE sps_fca_building
GO
CREATE PROCEDURE sps_fca_building
AS
BEGIN TRANSACTION;
BEGIN TRY
    DECLARE @fcaNo varchar(8), @buildingName varchar(255),
            @addrLine varchar(255), @suburb varchar(255), @postcode varchar(10), @state varchar(3),
            @stationName varchar(255), @stationCode varchar(10),
            @fileId int, @contactId int, @addressId int, @regionId int, @buildingId int;
    SET @state = 'QLD'

    SELECT FCA_NO, BUILDING_NAME, ADDR_LINE_1, SUBURB, POSTCODE, STATION_NAME
    INTO #FCA_BUILDING
    FROM V_FCA_BUILDING;

    DECLARE c CURSOR FOR
    SELECT FCA_NO, BUILDING_NAME, ADDR_LINE_1, SUBURB, POSTCODE, STATION_NAME
    FROM #FCA_BUILDING
    ORDER BY FCA_NO;
    
    OPEN c
    FETCH NEXT FROM c INTO @fcaNo, @buildingName, @addrLine, @suburb, @postcode, @stationName
    
    WHILE @@FETCH_STATUS = 0 
    BEGIN
        -- TODO: check this logic, see [sp_init_fca] (or derive from @stationCode)
        SET @regionId = SUBSTRING(@fcaNo, 1, 1)
        IF EXISTS (SELECT REGION_ID FROM REGION WHERE REGION_ID = @regionId)
        BEGIN
            -- create FCA record (if does not exists)
            IF NOT EXISTS (SELECT * FROM FCA WHERE FCA_NO = @fcaNo)
            BEGIN
                INSERT INTO FCA (FCA_NO, REGION_ID) VALUES (@fcaNo, @regionId)
            END
            ELSE
            BEGIN
                UPDATE FCA SET REGION_ID = @regionId WHERE FCA_NO = @fcaNo
            END

            SELECT @fileId = FILE_ID FROM FCA WHERE FCA_NO = @fcaNo
            IF (@fileId IS NULL) OR NOT EXISTS (SELECT FILE_ID FROM FCA WHERE FCA_NO = @fcaNo)
            BEGIN
                INSERT INTO FILES (FILE_STATUS_ID) VALUES (1)
                SET @fileId = @@IDENTITY
                UPDATE FCA SET FILE_ID = @fileId WHERE FCA_NO = @fcaNo

                INSERT INTO ADDRESS (ADDR_LINE_1, SUBURB, POSTCODE, STATE) VALUES (@addrLine, @suburb, @postcode, @state)
                SET @addressId = @@IDENTITY
                INSERT INTO BUILDING (NAME, FILE_ID, ADDRESS_ID) VALUES (@buildingName, @fileId, @addressId)
            END
            ELSE
            BEGIN
                SELECT @buildingId = BUILDING_ID, @addressId = ADDRESS_ID FROM BUILDING WHERE FILE_ID = @fileId
                UPDATE BUILDING SET NAME = @buildingName WHERE BUILDING_ID = @buildingId
                UPDATE ADDRESS SET ADDR_LINE_1 = @addrLine, SUBURB = @suburb, POSTCODE = @postcode, STATE = @state WHERE ADDRESS_ID = @addressId
            END 
        END
        ELSE
        BEGIN
            PRINT 'No regionId found for fcaNo: ' + @fcaNo
        END
        FETCH NEXT FROM c INTO @fcaNo, @buildingName, @addrLine, @suburb, @postcode, @stationName
    END
    CLOSE c;
    DEALLOCATE c;

    DROP TABLE #FCA_BUILDING

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

EXECUTE sps_fca_building
GO
