IF EXISTS (SELECT name  FROM sysobjects WHERE  name = N'V_AREA' AND type = 'V')
    DROP VIEW V_AREA
GO
CREATE VIEW V_AREA
AS
SELECT
    AreaId AS AREA_ID,
    LocationCode AS AREA_CODE,
    SUBSTRING(EscadRegionPrefix, 1, 1) AS REGION_ID,
    AreaName AS NAME,
    CASE IsLocationActive WHEN 1 THEN 'Y' ELSE 'N' END AS ACTIVE,
    'TBD' AS ADDR_LINE_1,
    'TBD' AS SUBURB,
    'TBD' AS POSTCODE,
    Telephone AS PHONE,
    Facsimile AS FAX
FROM [BNEIS05].Fire.dbo.LocationHierarchy
WHERE (OrgUnitType = 'AREA') AND (AuthorityType = 'URBAN') -- AND (IsLocationActive = 1)
GO
-- SELECT * FROM [BNEIS05].Fire.dbo.LocationHierarchy WHERE (OrgUnitType = 'AREA') AND (AuthorityType = 'URBAN') AND LocationCode IN ('SE9','NROSC','NC5')
-- SELECT * FROM V_AREA WHERE AREA_CODE IN ('SE9','NROSC','NC5')

IF EXISTS (SELECT name  FROM sysobjects WHERE  name = N'sps_area' AND type = 'P')
    DROP PROCEDURE sps_area
GO
CREATE PROCEDURE sps_area
AS
BEGIN TRANSACTION;
BEGIN TRY
    DECLARE @areaId int, @areaCode varchar(10), @regionId int, @name varchar(255), @active char(1),
            @addrLine varchar(255), @suburb varchar(255), @postcode varchar(10),
            @phone varchar(50), @fax varchar(50),
            @contactId int, @addressId int
    
    SELECT AREA_ID, AREA_CODE, REGION_ID, NAME, ACTIVE, ADDR_LINE_1, SUBURB, POSTCODE, PHONE, FAX
    INTO #AREA
    FROM V_AREA

    DECLARE cArea CURSOR FOR
    SELECT AREA_ID, AREA_CODE, REGION_ID, NAME, ACTIVE, ADDR_LINE_1, SUBURB, POSTCODE, PHONE, FAX
    FROM #AREA
    ORDER BY REGION_ID, AREA_CODE
    
	OPEN cArea
	FETCH NEXT FROM cArea INTO @areaId, @areaCode, @regionId, @name, @active, @addrLine, @suburb, @postcode, @phone, @fax
	
	WHILE @@FETCH_STATUS = 0 
	BEGIN
        IF EXISTS (SELECT REGION_ID FROM REGION WHERE REGION_ID = @regionId)
        BEGIN
	        IF EXISTS (SELECT AREA_ID FROM AREA WHERE AREA_ID = @areaCode)
	        BEGIN
	            SELECT @contactId = CONTACT_ID, @addressId = ADDRESS_ID FROM AREA WHERE AREA_ID = @areaCode
	            UPDATE CONTACT SET PHONE = @phone, FAX = @fax WHERE CONTACT_ID = @contactId
	            UPDATE ADDRESS SET ADDR_LINE_1 = @addrLine, SUBURB = @suburb, POSTCODE = @postcode WHERE ADDRESS_ID = @addressId
	            UPDATE AREA SET NAME = @name, ACTIVE = @active, REGION_ID = @regionId WHERE AREA_ID = @areaCode
	        END 
	        ELSE
	        BEGIN
	            INSERT INTO CONTACT (SALUTATION, FIRST_NAME, SURNAME, PHONE, FAX) VALUES ('-', 'TBD', 'TBD', @phone, @fax)
	            SET @contactId = @@IDENTITY
	            INSERT INTO ADDRESS (ADDR_LINE_1, SUBURB, POSTCODE, STATE) VALUES (@addrLine, @suburb, @postcode, 'QLD')
	            SET @addressId = @@IDENTITY
	            INSERT INTO AREA (AREA_ID, NAME, ACTIVE, REGION_ID, CONTACT_ID, ADDRESS_ID) VALUES (@areaCode, @name, @active, @regionId, @contactId, @addressId);
	        END
        END
        ELSE
        BEGIN
            PRINT 'No regionId found for areaCode: ' + @areaCode
        END
		FETCH NEXT FROM cArea INTO @areaId, @areaCode, @regionId, @name, @active, @addrLine, @suburb, @postcode, @phone, @fax
	END
	CLOSE cArea
	DEALLOCATE cArea

    DROP TABLE #AREA

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

EXECUTE sps_area
GO
-- SELECT * FROM AREA
