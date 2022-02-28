IF EXISTS (SELECT name FROM sysobjects WHERE  name = N'sps_fca_station' AND type = 'P')
    DROP PROCEDURE sps_fca_station
GO
CREATE PROCEDURE sps_fca_station
AS
BEGIN TRANSACTION;
BEGIN TRY
    DECLARE @areaId int, @stationCode varchar(10), @fcaNo varchar(8);
    DECLARE @areaCode varchar(10), @regionId int;
    
    SELECT AREA_ID, STATION_CODE, FCA_NO
    INTO #LOCATION
    FROM V_LOCATION

    DECLARE c CURSOR FOR
    SELECT AREA_ID, STATION_CODE, FCA_NO
    FROM #LOCATION
    ORDER BY AREA_ID, STATION_CODE;
    
    OPEN c
    FETCH NEXT FROM c INTO @areaId, @stationCode, @fcaNo
    
    WHILE @@FETCH_STATUS = 0 
    BEGIN
        IF EXISTS (SELECT AREA_CODE FROM V_AREA WHERE AREA_ID = @areaId)
        BEGIN
            SELECT @areaCode = AREA_CODE FROM V_AREA WHERE AREA_ID = @areaId
            IF EXISTS (SELECT STATION_CODE FROM STATION WHERE STATION_CODE = @stationCode)
            BEGIN
                SELECT @regionId = REGION_ID FROM AREA WHERE AREA_ID = @areaCode
                UPDATE FCA SET REGION_ID = @regionId, AREA_ID = @areaCode, STATION_CODE = @stationCode WHERE FCA_NO = @fcaNo
            END 
        END
        FETCH NEXT FROM c INTO @areaId, @stationCode, @fcaNo
    END
    CLOSE c;
    DEALLOCATE c;

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

EXECUTE sps_fca_station
GO