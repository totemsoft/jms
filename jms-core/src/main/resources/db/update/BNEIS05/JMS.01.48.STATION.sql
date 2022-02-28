IF EXISTS (SELECT name  FROM sysobjects WHERE name = N'V_STATION' AND type = 'V')
    DROP VIEW V_STATION
GO
CREATE VIEW dbo.V_STATION
AS
SELECT
    lh.LocationId AS STATION_ID,
    lh.LocationCode AS STATION_CODE,
    lh.EscadStationName AS NAME,
    'Y' AS ACTIVE,
    lh.AreaId AS AREA_ID,
    s.Address AS ADDR_LINE_1,
    s.City AS SUBURB,
    UPPER(s.State) AS [STATE],
    s.Zip AS POSTCODE,
    lh.Telephone AS PHONE,
    NULL AS MOBILE,
    lh.Facsimile AS FAX
FROM [BNEIS05].Fire.dbo.LocationHierarchy AS lh
    LEFT OUTER JOIN [BNEIS05].ESCADWH.dbo.Stations AS s ON lh.LocationId = s.ID
WHERE (lh.AuthorityType = 'URBAN') AND (lh.AreaName IS NOT NULL) AND (lh.EscadStationName LIKE '%FS')
/*
SELECT
    ST.ID AS STATION_ID,
    ST.Code AS STATION_CODE,
    ST.Name AS NAME,
    'Y' AS ACTIVE,
    SUBSTRING(DV.DivCode, 2, 5) AS AREA_ID,
    ST.Address AS ADDR_LINE_1,
    ST.City AS SUBURB,
    UPPER(ST.State) AS [STATE], 
    ST.Zip AS POSTCODE,
    ST.Phone1 AS PHONE,
    NULL AS MOBILE,
    ST.Fax AS FAX
FROM [SQL2008\SQL2008].ESCAD_DW_System.dbo.vw_Stations AS ST LEFT OUTER JOIN
     [SQL2008\SQL2008].ESCAD_DW_System.dbo.vw_StationBattalionList AS SB ON SB.StationID = ST.ID AND SB.RecordFlag = 1 LEFT OUTER JOIN
     [SQL2008\SQL2008].ESCAD_DW_System.dbo.vw_Battalion AS BT ON BT.ID = SB.BattalionID LEFT OUTER JOIN
     [SQL2008\SQL2008].ESCAD_DW_System.dbo.vw_Division AS DV ON DV.ID = BT.DivisionID
WHERE  (DV.DivCode IS NOT NULL)
   AND (ST.Name LIKE '% FS'
    OR ST.Name IN ('Amby Town RFB', 'Cramsie No 1 RFB', 'Doomadgee RFB', 'Hamilton Island', 'Normanton RFB', 'NPA Cape York  RFB', 'Rio Tinto', 'Roper Creek RFB', 'Thargomindah Town RFB', 'The Caves RFB'))
*/
/*
SELECT     ST.ID AS STATION_ID, ST.Code AS STATION_CODE, ST.Name AS NAME, 'Y' AS ACTIVE, SUBSTRING(DV.DivCode, 2, 5) AS AREA_ID, ST.Address AS ADDR_LINE_1, ST.City AS SUBURB, 
                      ST.State AS STATE, ST.Zip AS POSTCODE, ST.Phone1 AS PHONE, NULL AS MOBILE, ST.Fax AS FAX
FROM         [SQL2008\SQL2008].ESCAD_DW_System.dbo.vw_Stations AS ST LEFT OUTER JOIN
                      [SQL2008\SQL2008].ESCAD_DW_System.dbo.vw_StationBattalionList AS SB ON SB.StationID = ST.ID AND SB.RecordFlag = 1 LEFT OUTER JOIN
                      [SQL2008\SQL2008].ESCAD_DW_System.dbo.vw_Battalion AS BT ON BT.ID = SB.BattalionID LEFT OUTER JOIN
                      [SQL2008\SQL2008].ESCAD_DW_System.dbo.vw_Division AS DV ON DV.ID = BT.DivisionID
WHERE     (DV.DivCode IS NOT NULL) AND (ST.Name COLLATE DATABASE_DEFAULT LIKE '% FS' OR
                      ST.Name COLLATE DATABASE_DEFAULT IN ('Amby Town RFB', 'Cramsie No 1 RFB', 'Doomadgee RFB', 'Hamilton Island', 'Normanton RFB', 'NPA Cape York  RFB', 'Rio Tinto', 'Roper Creek RFB', 
                      'Thargomindah Town RFB', 'The Caves RFB')) AND (DV.DivCode IN
                          (SELECT     DivCode
                            FROM          [SQL2008\SQL2008].ESCAD_DW_System.dbo.vw_Division AS DV
                            WHERE      (DivName LIKE '%Area') OR
                                                   (DivName LIKE '%Rural') OR
                                                   (DivName LIKE '%Command') OR
                                                   (DivCode = '7R01')))

*/
GO
-- SELECT * FROM V_STATION ORDER BY STATION_CODE

IF EXISTS (SELECT name FROM sysobjects WHERE  name = N'sps_station' AND type = 'P')
    DROP PROCEDURE sps_station
GO
CREATE PROCEDURE sps_station
AS
BEGIN TRANSACTION;
BEGIN TRY
    DECLARE @stationId int, @stationCode varchar(10), @name varchar(255), @active char(1),
            @addrLine varchar(255), @suburb varchar(255), @state varchar(10), @postcode varchar(10),
            @phone varchar(50), @mobile varchar(50), @fax varchar(50),
            @areaId int, @areaCode varchar(10), @contactId int, @addressId int

    SELECT STATION_ID, STATION_CODE, NAME, ACTIVE, AREA_ID, ADDR_LINE_1, SUBURB, [STATE], POSTCODE, PHONE, MOBILE, FAX
    INTO #STATION
    FROM V_STATION

    DECLARE cStation CURSOR FOR
    SELECT STATION_ID, STATION_CODE, NAME, ACTIVE, AREA_ID, ADDR_LINE_1, SUBURB, [STATE], POSTCODE, PHONE, MOBILE, FAX
    FROM #STATION
    ORDER BY STATION_CODE

    OPEN cStation
    FETCH NEXT FROM cStation INTO @stationId, @stationCode, @name, @active, @areaId, @addrLine, @suburb, @state, @postcode, @phone, @mobile, @fax

    WHILE @@FETCH_STATUS = 0 
    BEGIN
        IF EXISTS (SELECT AREA_CODE FROM V_AREA WHERE AREA_ID = @areaId)
        BEGIN
            SELECT @areaCode = AREA_CODE FROM V_AREA WHERE AREA_ID = @areaId
            -- PRINT '@areaId=' + convert(varchar(10), @areaId) + ', @areaCode=' + @areaCode
            IF EXISTS (SELECT AREA_ID FROM AREA WHERE AREA_ID = @areaCode)
            BEGIN
                IF NOT EXISTS (SELECT * FROM STATE WHERE STATE = @state)
                BEGIN
                    SET @state = NULL -- 'QLD'
                END

                IF EXISTS (SELECT STATION_CODE FROM STATION WHERE STATION_CODE = @stationCode)
                BEGIN
                    SELECT @contactId = CONTACT_ID, @addressId = ADDRESS_ID FROM STATION WHERE STATION_CODE = @stationCode
                    UPDATE CONTACT SET PHONE = @phone, MOBILE = @mobile, FAX = @fax WHERE CONTACT_ID = @contactId
                    UPDATE ADDRESS SET ADDR_LINE_1 = @addrLine, SUBURB = @suburb, POSTCODE = @postcode, STATE = @state WHERE ADDRESS_ID = @addressId
                    UPDATE STATION SET NAME = @name, ACTIVE = @active, AREA_ID = @areaCode WHERE STATION_CODE = @stationCode
                END 
                ELSE
                BEGIN
                    INSERT INTO CONTACT (SALUTATION, FIRST_NAME, SURNAME, PHONE, MOBILE, FAX) VALUES ('-', 'TBD', 'TBD', @phone, @mobile, @fax)
                    SET @contactId = @@IDENTITY
                    INSERT INTO ADDRESS (ADDR_LINE_1, SUBURB, POSTCODE, STATE) VALUES (@addrLine, @suburb, @postcode, @state)
                    SET @addressId = @@IDENTITY
                    INSERT INTO STATION (STATION_CODE, NAME, ACTIVE, AREA_ID, CONTACT_ID, ADDRESS_ID) VALUES (@stationCode, @name, @active, @areaCode, @contactId, @addressId)
                END
            END
            ELSE
            BEGIN
                PRINT 'No areaCode found: ' + @areaCode
            END
        END
        FETCH NEXT FROM cStation INTO @stationId, @stationCode, @name, @active, @areaId, @addrLine, @suburb, @state, @postcode, @phone, @mobile, @fax
    END
    CLOSE cStation
    DEALLOCATE cStation

    DROP TABLE #STATION

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

EXECUTE sps_station
GO
-- SELECT * FROM STATION
