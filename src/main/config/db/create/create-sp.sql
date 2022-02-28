≈USE [JMS];

-- ================================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- Generate 10000 FCA's
-- e.g. 
-- Region 1 will have FCA 10000-00 to 19999-99
-- =============================================
IF EXISTS (SELECT name 
	   FROM   sysobjects 
	   WHERE  name = N'sp_init_fca' 
	   AND 	  type = 'P')
    DROP PROCEDURE sp_init_fca
GO

CREATE PROCEDURE sp_init_fca
	@fca_no int,
    @region_id int
AS
    DECLARE @count int
    SET @count = 0

	WHILE (@count < 10000)
	BEGIN
		DECLARE @subpanel int
		SET @subpanel = 0

		WHILE (@subpanel < 10)
		BEGIN
			DECLARE @fca_no_subpanel varchar(8)
			if (@subpanel < 10)
				SET @fca_no_subpanel = CAST(@fca_no AS varchar(5)) + '-0' + CAST(@subpanel AS varchar(1))
			else
				SET @fca_no_subpanel = CAST(@fca_no AS varchar(5)) + '-' + CAST(@subpanel AS varchar(2))

			if not exists (SELECT * FROM FCA WHERE FCA_NO = @fca_no_subpanel)
				INSERT INTO FCA (FCA_NO, FILE_ID, REGION_ID) VALUES (@fca_no_subpanel, NULL, @region_id)

			SET @subpanel = @subpanel + 1
		END

	    SET @count = @count + 1
	    SET @fca_no = @fca_no + 1
	END
GO

-- =============================================
-- 10000-00 to 79999-99
-- =============================================
-- DELETE FROM FCA WHERE FILE_ID IS NULL
EXECUTE sp_init_fca 10000, 1;
EXECUTE sp_init_fca 20000, 2;
EXECUTE sp_init_fca 30000, 3;
EXECUTE sp_init_fca 40000, 4;
EXECUTE sp_init_fca 50000, 5;
EXECUTE sp_init_fca 60000, 6;
EXECUTE sp_init_fca 70000, 7;



-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
IF EXISTS (SELECT name 
	   FROM   sysobjects 
	   WHERE  name = N'sp_delete_file' 
	   AND 	  type = 'P')
    DROP PROCEDURE sp_delete_file
GO

CREATE PROCEDURE sp_delete_file 
	-- Add the parameters for the stored procedure here
	@fileId int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
    DELETE FROM FILE_ACTION WHERE FILE_ID = @fileId
    DELETE FROM JOB_DOC WHERE JOB_ID IN (SELECT JOB_ID FROM JOBS WHERE FILE_ID = @fileId)
    DELETE FROM JOB_ACTION WHERE JOB_ID IN (SELECT JOB_ID FROM JOBS WHERE FILE_ID = @fileId)
    DELETE FROM JOBS WHERE FILE_ID = @fileId
    DELETE FROM FCA WHERE FILE_ID = @fileId
    DELETE FROM BUILDING_CONTACT WHERE FILE_ID = @fileId
    DELETE FROM BUILDING WHERE FILE_ID = @fileId
    DELETE FROM ALARM_PANEL WHERE FILE_ID = @fileId
    DELETE FROM SAP_HEADER WHERE FILE_ID = @fileId
    DELETE FROM OWNER WHERE FILE_ID = @fileId
    DELETE FROM KEY_RECEIPT WHERE FILE_ID = @fileId
	DELETE FROM FILES WHERE FILE_ID = @fileId

END
GO

-- EXECUTE sp_delete_file 10024;

-- =============================================================
-- Create procedure
-- =============================================================
IF EXISTS (SELECT name 
       FROM   sysobjects 
       WHERE  name = N'sp_create_building_alt' 
       AND    type = 'P')
    DROP PROCEDURE sp_create_building_alt
GO

CREATE PROCEDURE sp_create_building_alt
    @fca_no varchar(8),
    @building_name varchar(100),
    @address_line varchar(255) = 'TBD',
    @suburb varchar(255) = 'TBD',
    @state varchar(3) = 'QLD',
    @postcode varchar(10) = '4000',
    @type char(1) = 'E'
AS
    DECLARE @file_id int
    SELECT @file_id = FILE_ID FROM FCA WHERE FCA_NO = @fca_no

    IF (@file_id > 0)
    BEGIN
        DECLARE @building_id int
        SELECT @building_id = BUILDING_ID FROM BUILDING WHERE FILE_ID = @file_id

        IF (@building_id > 0)
        BEGIN
            DECLARE @building_alt_id int
            IF NOT EXISTS (SELECT * FROM BUILDING_ALT WHERE FILE_ID = @file_id AND TYPE = @type)
            BEGIN
                INSERT INTO ADDRESS (ADDR_LINE_1, SUBURB, POSTCODE, STATE) VALUES (@address_line, @suburb, @postcode, @state)
                DECLARE @address_id int
                SET @address_id = @@IDENTITY

                INSERT INTO BUILDING_ALT (FILE_ID, NAME, ADDRESS_ID, TYPE) VALUES (@file_id, @building_name, @address_id, @type)
                SET @building_alt_id = @@IDENTITY
            END
            ELSE
            BEGIN
                SELECT @building_alt_id = BUILDING_ALT_ID FROM BUILDING_ALT WHERE FILE_ID = @file_id AND TYPE = @type
            END
    
            IF @type = 'E'
                UPDATE BUILDING SET ESCAD_BUILDING_ID = @building_alt_id WHERE BUILDING_ID = @building_id
            ELSE
                UPDATE BUILDING SET MASTERMIND_BUILDING_ID = @building_alt_id WHERE BUILDING_ID = @building_id
        END
    END
GO
/*
EXECUTE sp_create_building_alt '61093-01','Escad Building','11E Hall Road', 'Hornsby', 'NSW', '2077', 'E';
EXECUTE sp_create_building_alt '61093-01','Mastermind Building','11M Hall Road', 'Hornsby Heights', 'NSW', '2077', 'M';
*/
