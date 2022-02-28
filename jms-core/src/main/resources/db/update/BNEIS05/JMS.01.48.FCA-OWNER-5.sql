--	OWNER_TYPE_ID	NAME
--	1				Owner
--	2				Body Corporate
--	3				Property Manager
--	4				Property Tenant
--	5				Alternate
IF EXISTS (SELECT name  FROM sysobjects WHERE  name = N'V_FCA_OWNER_5' AND type = 'V')
    DROP VIEW V_FCA_OWNER_5
GO
CREATE VIEW V_FCA_OWNER_5
AS
SELECT fca AS FCA_NO,
      'N/A' AS LEGAL_NAME,
      NULL AS ABN,
      streetname AS ADDR_LINE_1,
      suburb AS SUBURB,
      postcode AS POSTCODE,
      UPPER(state) AS STATE,
      contact AS CONTACT_NAME,
      office AS PHONE,
      NULL AS MOBILE,
      email AS EMAIL,
      NULL AS FAX
FROM [BB1911\SAMC].[AIMS].[dbo].[tbl_ot]
WHERE [FCA] < '90000-01'
AND LEN([FCA]) = 8
AND [contact] IS NOT NULL
AND [office] IS NOT NULL
AND [email] IS NOT NULL
AND [streetname] IS NOT NULL
AND [suburb] IS NOT NULL
AND [postcode] IS NOT NULL
AND [state] IS NOT NULL
GO
-- SELECT * FROM V_FCA_OWNER_5 ORDER BY FCA_NO

IF EXISTS (SELECT name  FROM sysobjects WHERE  name = N'sps_fca_owner_5' AND type = 'P')
    DROP PROCEDURE sps_fca_owner_5
GO
CREATE PROCEDURE sps_fca_owner_5
AS
BEGIN TRANSACTION;
BEGIN TRY
    DECLARE @fcaNo varchar(8),
            @legalName varchar(255), @abn varchar(11),
            @addrLine varchar(255), @suburb varchar(255), @postcode varchar(10), @state varchar(3),
            @contactName varchar(255), @firstName varchar(255), @surname varchar(255), @phone varchar(50), @mobile varchar(50), @email varchar(50), @fax varchar(50),
            @fileId int, @contactId int, @addressId int, @ownerId int, @regionId int, @idx int, @ownerTypeId int;

    SELECT FCA_NO, LEGAL_NAME, ABN, ADDR_LINE_1, SUBURB, POSTCODE, STATE, CONTACT_NAME, PHONE, MOBILE, EMAIL, FAX
    INTO #FCA_OWNER_5
    FROM V_FCA_OWNER_5

    DECLARE c CURSOR FOR
    SELECT FCA_NO, LEGAL_NAME, ABN, ADDR_LINE_1, SUBURB, POSTCODE, STATE, CONTACT_NAME, PHONE, MOBILE, EMAIL, FAX
    FROM #FCA_OWNER_5
    --    WHERE FCA_NO = '61232-01'
        ORDER BY FCA_NO;
    
    OPEN c
    FETCH NEXT FROM c INTO @fcaNo, @legalName, @abn, @addrLine, @suburb, @postcode, @state, @contactName, @phone, @mobile, @email, @fax

    SET @ownerTypeId = 5

    WHILE @@FETCH_STATUS = 0 
    BEGIN
        PRINT 'fcaNo ' + @fcaNo

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

            IF (@legalName IS NULL)
            BEGIN
                SET @legalName = 'TBD'
            END
            ELSE
            BEGIN
                SET @legalName = REPLACE(REPLACE(@legalName, CHAR(13), ', '), CHAR(10), '')
                -- PRINT @legalName
            END

            SET @idx = CHARINDEX(' ', @contactName, 1)
            IF (@idx > 1)
            BEGIN
                SET @firstName = SUBSTRING(@contactName, 1, @idx - 1)
                SET @surname = SUBSTRING(@contactName, @idx + 1, LEN(@contactName))
                -- PRINT @contactName + '=[' + @firstName + '] [' + @surname + ']'
            END

            IF NOT EXISTS (SELECT * FROM STATE WHERE STATE = @state)
            BEGIN
                SET @state = NULL -- 'QLD'
            END

            SELECT @fileId = FILE_ID FROM FCA WHERE FCA_NO = @fcaNo
            IF (@fileId IS NULL) OR NOT EXISTS (SELECT FILE_ID FROM FCA WHERE FCA_NO = @fcaNo)
            BEGIN
                INSERT INTO FILES (FILE_STATUS_ID) VALUES (1)
                SET @fileId = @@IDENTITY
                UPDATE FCA SET FILE_ID = @fileId WHERE FCA_NO = @fcaNo
            END
            -- PRINT 'fileId ' + CAST(@fileId AS VARCHAR(11))

            IF NOT EXISTS (SELECT FILE_ID FROM OWNER WHERE FILE_ID = @fileId AND OWNER_TYPE_ID = @ownerTypeId)
            BEGIN
                -- PRINT 'owner will be added for fca ' + @fcaNo
                INSERT INTO CONTACT (SALUTATION, FIRST_NAME, SURNAME, PHONE, MOBILE, FAX, EMAIL) VALUES ('-', @firstName, @surname, @phone, @mobile, @fax, @email)
                SET @contactId = @@IDENTITY
                INSERT INTO ADDRESS (ADDR_LINE_1, SUBURB, POSTCODE, STATE) VALUES (@addrLine, @suburb, @postcode, @state)
                SET @addressId = @@IDENTITY
                INSERT INTO OWNER (OWNER_TYPE_ID, LEGAL_NAME, ABN, FILE_ID, CONTACT_ID, ADDRESS_ID) VALUES (@ownerTypeId, @legalName, @abn, @fileId, @contactId, @addressId)
            END 
            ELSE
            BEGIN
                -- PRINT 'owner will be updated for fca ' + @fcaNo
                SELECT @ownerId = OWNER_ID, @contactId = CONTACT_ID, @addressId = ADDRESS_ID FROM OWNER WHERE FILE_ID = @fileId AND OWNER_TYPE_ID = @ownerTypeId
                UPDATE OWNER SET LEGAL_NAME = @legalName, ABN = @abn WHERE OWNER_ID = @ownerId
                UPDATE CONTACT SET FIRST_NAME = @firstName, SURNAME = @surname, PHONE = @phone, MOBILE = @mobile, FAX = @fax, EMAIL = @email WHERE CONTACT_ID = @contactId
                UPDATE ADDRESS SET ADDR_LINE_1 = @addrLine, SUBURB = @suburb, POSTCODE = @postcode, STATE = @state WHERE ADDRESS_ID = @addressId
            END 
        END
        ELSE
        BEGIN
            PRINT 'No regionId found for fcaNo: ' + @fcaNo
        END
        FETCH NEXT FROM c INTO @fcaNo, @legalName, @abn, @addrLine, @suburb, @postcode, @state, @contactName, @phone, @mobile, @email, @fax
    END
    CLOSE c;
    DEALLOCATE c;

    DROP TABLE #FCA_OWNER_5

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

-- TRUNCATE table OWNER
EXECUTE sps_fca_owner_5
GO
