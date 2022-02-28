-- [futuremailouts_indx]
--                    Type    Email    Post
--Owner                1        1        2
--Body Corporate       2        3        4
--Property Manager     3        5        6
--Tenant               4        7        8
--Other                5        9        10
IF EXISTS (SELECT name  FROM sysobjects WHERE  name = N'V_FCA_OM89' AND type = 'V')
    DROP VIEW V_FCA_OM89
GO
CREATE VIEW V_FCA_OM89
AS
SELECT AD.[fca] AS FCA_NO,
       AD.[om89recieved] AS RECEIVED_DATE,
       AD.[om89sent] AS SENT_DATE,
       AD.[sendto] AS DESTINATION,
       CASE WHEN AD.[futuremailouts_indx] IN ('1','3','5','7','9') THEN 1 ELSE 2 END AS MAIL_METHOD_ID,
       CASE WHEN AD.[futuremailouts_indx] IN ('1', '2') THEN 1
            WHEN AD.[futuremailouts_indx] IN ('3', '4') THEN 2
            WHEN AD.[futuremailouts_indx] IN ('5', '6') THEN 3
            WHEN AD.[futuremailouts_indx] IN ('7', '8') THEN 4
            WHEN AD.[futuremailouts_indx] IN ('9','10') THEN 5
            ELSE NULL END AS DEFAULT_CONTACT_TYPE,
       CASE WHEN AD.[recievemailoutviaemail] = 0 THEN 2 ELSE 1 END AS RECEIVED_MAIL_METHOD_ID,
       CASE WHEN AD.[returnedtosender] = 0 THEN 'N' ELSE 'Y' END AS RTS,
       AD.[returnedtosenderdate] AS REJECTED_DATE,
       CASE WHEN AD.[mailsendoutnotrequired] = 0 THEN 'N' ELSE 'Y' END AS NO_MAIL_OUT,
       AD.[reasonformailout] AS NOTATION,
       CASE WHEN PD.disconnected = 0 THEN 1
            WHEN PD.disconnected = 1 AND PD.connpending = 1 THEN 4
            ELSE 2 END AS FILE_STATUS_ID
FROM [BB1911\SAMC].[AIMS].[dbo].[tbl_ad] AD
LEFT OUTER JOIN [BB1911\SAMC].[AIMS].[dbo].[tbl_pd] PD ON PD.fca = AD.fca
GO
-- SELECT * FROM V_FCA_OM89 ORDER BY FCA_NO

/*
FCA_NO                      10054-01
RECEIVED_DATE               2014-01-23 14:57:42.000
SENT_DATE                   2014-01-08 10:42:52.000
DESTINATION                 rhonda.vetter@tafe.qld.edu.au
MAIL_METHOD_ID              EMAIL
DEFAULT_CONTACT_TYPE        OWNER
RECEIVED_MAIL_METHOD_ID     1
RTS                         0
REJECTED_DATE               2013-11-12 00:00:00.000
NO_MAIL_OUT                 0
NOTATION                    MAIL-OUT
FILE_STATUS_ID              CONNECTED
*/

IF EXISTS (SELECT name  FROM sysobjects WHERE  name = N'sps_fca_om89' AND type = 'P')
    DROP PROCEDURE sps_fca_om89
GO
CREATE PROCEDURE sps_fca_om89
AS
BEGIN TRANSACTION;
BEGIN TRY
    DECLARE @fcaNo varchar(8),
            @receivedDate date, @sentDate date, @destination varchar(250),
            @mailMethodId int, @defContactType int, @receivedMailMethodId int,
            @rts char(1), @rejectedDate date, @noMailOut char(1), @notation varchar(4096), @fileStatusId int,
            @fileId int, @mailBatchId int, @mailBatchFileId int, @statusId int,
            @fileActionId int, @actionCodeId int, @emailActionCodeId int, @postActionCodeId int, @dueDate date, @createdDate date, @completedBy int, @completedDate date,
            @now date;

    SET @now = GETDATE();

    SELECT @emailActionCodeId = ACTION_CODE_ID FROM ACTION_CODE WHERE CODE = 'OM89FormEmail'
    IF (@emailActionCodeId IS NULL)
    BEGIN
        RAISERROR('No action code [OM89FormEmail] found', 16, 1);
        return;
    END
    SELECT @postActionCodeId = ACTION_CODE_ID FROM ACTION_CODE WHERE CODE = 'OM89FormLetter'
    IF (@postActionCodeId IS NULL)
    BEGIN
        RAISERROR('No action code [OM89FormLetter] found', 16, 1);
        return;
    END

    SELECT FCA_NO, RECEIVED_DATE, SENT_DATE, DESTINATION, MAIL_METHOD_ID, DEFAULT_CONTACT_TYPE, RECEIVED_MAIL_METHOD_ID, RTS, REJECTED_DATE, NO_MAIL_OUT, NOTATION, FILE_STATUS_ID
    INTO #FCA_OM89
    FROM V_FCA_OM89

    DECLARE c CURSOR FOR
    SELECT FCA_NO, RECEIVED_DATE, SENT_DATE, DESTINATION, MAIL_METHOD_ID, DEFAULT_CONTACT_TYPE, RECEIVED_MAIL_METHOD_ID, RTS, REJECTED_DATE, NO_MAIL_OUT, NOTATION, FILE_STATUS_ID
    FROM #FCA_OM89
    --    WHERE FCA_NO = '10813-01'
        ORDER BY FCA_NO;
    
    OPEN c
    FETCH NEXT FROM c INTO @fcaNo, @receivedDate, @sentDate, @destination, @mailMethodId, @defContactType, @receivedMailMethodId, @rts, @rejectedDate, @noMailOut, @notation, @fileStatusId

    SELECT @mailBatchId = MAIL_BATCH_ID FROM MAIL_BATCH WHERE NAME = 'sps_fca_om89'
    IF (@mailBatchId IS NULL)
    BEGIN
        INSERT INTO MAIL_BATCH (NAME) VALUES ('sps_fca_om89')
        SET @mailBatchId = @@IDENTITY
    END
    -- PRINT 'mailBatchId ' + CAST(@mailBatchId AS VARCHAR(11))
    -- DELETE FROM MAIL_BATCH_FILE WHERE MAIL_BATCH_ID = @mailBatchId

    WHILE @@FETCH_STATUS = 0 
    BEGIN
        -- PRINT 'fcaNo=' + @fcaNo
        SELECT @fileId = FILE_ID FROM FCA WHERE FCA_NO = @fcaNo
        IF (@fileId IS NOT NULL)
        BEGIN
            -- PRINT 'fileId=' + CAST(@fileId AS VARCHAR(11))
            SELECT @mailBatchFileId = MAIL_BATCH_FILE_ID FROM MAIL_BATCH_FILE WHERE MAIL_BATCH_ID = @mailBatchId AND FILE_ID = @fileId
            IF (@mailBatchFileId IS NULL)
            BEGIN
                -- OM89FormEmail/OM89FormLetter
                IF (@mailMethodId = 1)
                BEGIN
                    SET @actionCodeId = @emailActionCodeId
                END
                ELSE
                BEGIN
                    SET @actionCodeId = @postActionCodeId
                END
                -- 
                IF (@rts = 'Y')
                BEGIN
                    SET @statusId = 3
                    SET @completedDate = @rejectedDate
                END
                ELSE
                BEGIN
                    IF (@receivedDate IS NULL)
                    BEGIN
                        SET @statusId = 1
                        SET @completedDate = @sentDate
                    END
                    ELSE
                    BEGIN
                        SET @statusId = 2
                        SET @completedDate = @receivedDate
                    END
                END
                -- PRINT 'mailMethodId=' + CAST(@mailMethodId AS VARCHAR(11)) + ', statusId=' + CAST(@statusId AS VARCHAR(11))
                --
                IF (@notation IS NULL)
                BEGIN
                    SET @notation = 'TBD'
                END
                --
                IF (@completedDate IS NULL)
                BEGIN
                    SET @dueDate = @now
                    SET @createdDate = @now
                END
                ELSE
                BEGIN
                    SET @dueDate = @completedDate
                    SET @createdDate = @completedDate
                END
                -- admin user
                SET @completedBy = 10001
                --
                INSERT INTO FILE_ACTION
                    (FILE_ID, DUE_DATE, ACTION_CODE_ID, NOTATION, DESTINATION, CREATED_DATE, COMPLETED_BY, COMPLETED_DATE)
                    VALUES
                    (@fileId, @dueDate, @actionCodeId, @notation, @destination, @createdDate, @completedBy, @completedDate)
                SET @fileActionId = @@IDENTITY

                INSERT INTO MAIL_BATCH_FILE
                    (MAIL_BATCH_ID, FILE_ID, FILE_ACTION_ID, STATUS_ID, SENT_DATE, RECEIVED_DATE, REJECTED_DATE)
                    VALUES
                    (@mailBatchId, @fileId, @fileActionId, @statusId, @sentDate, @receivedDate, @rejectedDate)

                -- @defContactType
                IF (@defContactType IS NOT NULL)
                BEGIN
                    UPDATE OWNER SET DEFAULT_CONTACT = 'N' WHERE FILE_ID = @fileId
                    UPDATE OWNER SET DEFAULT_CONTACT = 'Y' WHERE FILE_ID = @fileId AND OWNER_TYPE_ID = @defContactType
                END
                -- @fileStatusId, @noMailOut
                UPDATE FILES SET FILE_STATUS_ID = @fileStatusId, NO_MAIL_OUT = @noMailOut WHERE FILE_ID = @fileId
            END
            ELSE
            BEGIN
                PRINT 'already loaded mailBatchFileId=' + CAST(@mailBatchFileId AS VARCHAR(11))
            END
        END
        ELSE
        BEGIN
            PRINT 'No fileId found for fcaNo: ' + @fcaNo
        END
        FETCH NEXT FROM c INTO @fcaNo, @receivedDate, @sentDate, @destination, @mailMethodId, @defContactType, @receivedMailMethodId, @rts, @rejectedDate, @noMailOut, @notation, @fileStatusId
    END
    CLOSE c;
    DEALLOCATE c;

    DROP TABLE #FCA_OM89

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

EXECUTE sps_fca_om89
GO
