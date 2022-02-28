ALTER TABLE FILES ADD NO_MAIL_OUT char(1) NOT NULL DEFAULT 'N';

CREATE TABLE MAIL_METHOD (
    MAIL_METHOD_ID numeric(11, 0) NOT NULL,
    NAME varchar(50) NOT NULL,
    CREATED_BY numeric(11,0),
    CREATED_DATE datetime NOT NULL DEFAULT getDate(),
    UPDATED_BY numeric(11,0),
    UPDATED_DATE datetime,
    LOCK_VERSION numeric(11, 0) NOT NULL DEFAULT 0,
    CONSTRAINT MAIL_METHOD_PK PRIMARY KEY CLUSTERED (MAIL_METHOD_ID)
);
INSERT INTO MAIL_METHOD (MAIL_METHOD_ID, NAME) VALUES (1, 'Email');
INSERT INTO MAIL_METHOD (MAIL_METHOD_ID, NAME) VALUES (2, 'Post');

ALTER TABLE FILES ADD MAIL_METHOD_ID numeric(11, 0);
ALTER TABLE FILES ADD CONSTRAINT FILES_FK3 FOREIGN KEY (MAIL_METHOD_ID) REFERENCES MAIL_METHOD (MAIL_METHOD_ID);

update dbo.MAIL_BATCH_FILE set STATUS_ID = 1 where SENT_DATE is not null;
update dbo.MAIL_BATCH_FILE set STATUS_ID = 2 where RECEIVED_DATE is not null;
update dbo.MAIL_BATCH_FILE set STATUS_ID = 3 where REJECTED_DATE is not null;

INSERT INTO SYSTEM_FUNCTION (SYSTEM_FUNCTION_ID, MODULE, NAME, DESCRIPTION, LOGICAL)
 VALUES (1153, '/file', '/editFcaDoc', 'Edit File FCA Documents', 'Y');

ALTER TABLE BUILDING ADD LOT_PLAN_NUMBER varchar(30);

ALTER TABLE BUILDING ADD ESCAD_BUILDING_ID numeric(11, 0);
ALTER TABLE BUILDING ADD MASTERMIND_BUILDING_ID numeric(11, 0);

-- ALTER TABLE BUILDING DROP CONSTRAINT BUILDING_FK5
-- ALTER TABLE BUILDING DROP CONSTRAINT BUILDING_FK6
-- DROP TABLE BUILDING_ALT
CREATE TABLE BUILDING_ALT (
    BUILDING_ALT_ID numeric(11, 0) IDENTITY(10001,1) NOT NULL,
    NAME varchar(100) NOT NULL,
    FILE_ID numeric(11, 0) NOT NULL,
    ADDRESS_ID numeric(11, 0) NULL,
    TYPE char(1) NOT NULL,
    CREATED_BY numeric(11,0),
    CREATED_DATE datetime NOT NULL DEFAULT getDate(),
    UPDATED_BY numeric(11,0),
    UPDATED_DATE datetime,
    LOCK_VERSION numeric(11, 0) NOT NULL DEFAULT 0,
    CONSTRAINT BUILDING_ALT_PK PRIMARY KEY CLUSTERED (BUILDING_ALT_ID)
);

ALTER TABLE BUILDING_ALT ADD CONSTRAINT BUILDING_ALT_FK1 FOREIGN KEY (FILE_ID) REFERENCES FILES (FILE_ID);
ALTER TABLE BUILDING_ALT ADD CONSTRAINT BUILDING_ALT_FK2 FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESS (ADDRESS_ID);

ALTER TABLE BUILDING ADD CONSTRAINT BUILDING_FK5 FOREIGN KEY (ESCAD_BUILDING_ID) REFERENCES BUILDING_ALT (BUILDING_ALT_ID);
ALTER TABLE BUILDING ADD CONSTRAINT BUILDING_FK6 FOREIGN KEY (MASTERMIND_BUILDING_ID) REFERENCES BUILDING_ALT (BUILDING_ALT_ID);

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

ALTER TABLE ACTION_CODE ADD BULK_MAIL char(1) NOT NULL DEFAULT 'N';
ALTER TABLE ACTION_CODE ALTER COLUMN [CODE] varchar(64) NOT NULL;
ALTER TABLE ACTION_CODE ADD LOGICALLY_DELETED char(1) NULL;

ALTER TABLE ACTION_WORKFLOW ADD ACTIVE char(1) NOT NULL DEFAULT 'Y';

INSERT INTO SYSTEM_FUNCTION (SYSTEM_FUNCTION_ID, MODULE, NAME, DESCRIPTION)
 VALUES (340, '/setup', '/cache', 'Cache Management');

ALTER TABLE TEMPLATE ADD LOGICALLY_DELETED char(1) NULL;

ALTER TABLE REGION ALTER COLUMN CODE varchar(3) NULL;

ALTER TABLE AREA DROP CONSTRAINT AREA_UK1;
ALTER TABLE AREA ADD CONSTRAINT AREA_UK1 UNIQUE (NAME, REGION_ID, ACTIVE);

-- sp_rename 'AREA.AREA_ID', 'AREA_CODE';

ALTER TABLE USERS ALTER COLUMN [PASSWORD] varchar(100) NOT NULL;

UPDATE FILE_STATUS SET NAME = 'Connected' WHERE FILE_STATUS_ID = 1;
UPDATE FILE_STATUS SET NAME = 'Disconnected' WHERE FILE_STATUS_ID = 2;
INSERT INTO FILE_STATUS (FILE_STATUS_ID, NAME) VALUES (4, 'Pending');

ALTER TABLE ADDRESS ALTER COLUMN STATE varchar(3) NOT NULL;

INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('JMS.01.48', 'JMS.01.47');