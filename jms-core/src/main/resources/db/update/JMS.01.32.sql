-- DROP TABLE COMPANY;
CREATE TABLE COMPANY (
    COMPANY_ID numeric(11, 0) IDENTITY NOT NULL,
    COMPANY_TYPE_ID numeric(11, 0) NOT NULL,
    NAME varchar(255) NOT NULL,
    LEGAL_NAME varchar(255) NULL,
    ABN varchar(11) NULL,
	REGION_ID numeric(11, 0) NULL,
	CONTACT_ID numeric(11, 0) NULL,
	ADDRESS_ID numeric(11, 0) NULL,
	POST_ADDRESS_ID numeric(11, 0) NULL,
	ACTIVE char(1) NOT NULL,
    CREATED_BY numeric(11,0),
    CREATED_DATE datetime NOT NULL DEFAULT getDate(),
    UPDATED_BY numeric(11,0),
    UPDATED_DATE datetime,
    LOCK_VERSION numeric(11, 0) NOT NULL DEFAULT 0
);
ALTER TABLE COMPANY ADD CONSTRAINT COMPANY_PK PRIMARY KEY (COMPANY_ID);

-- ALTER TABLE BUILDING_CONTACT DROP CONSTRAINT BUILDING_CONTACT_FK5;
ALTER TABLE BUILDING_CONTACT ADD COMPANY_ID numeric(11,0) NULL;
ALTER TABLE BUILDING_CONTACT ADD CONSTRAINT BUILDING_CONTACT_FK5 FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY (COMPANY_ID);

ALTER TABLE CONTACT ALTER COLUMN SALUTATION varchar(50) NULL;
ALTER TABLE CONTACT ALTER COLUMN FIRST_NAME varchar(255) NULL;
ALTER TABLE CONTACT ALTER COLUMN SURNAME varchar(255) NULL;

ALTER TABLE ADDRESS ALTER COLUMN ADDR_LINE_1 varchar(255) NULL;
ALTER TABLE ADDRESS ALTER COLUMN SUBURB varchar(255) NULL;
ALTER TABLE ADDRESS ALTER COLUMN STATE varchar(3) NULL;

-- DROP TABLE HIBERNATE_SEQUENCES
CREATE TABLE HIBERNATE_SEQUENCES (
  SEQUENCE_NAME varchar(32) NOT NULL,
  NEXT_VAL numeric(11, 0) NOT NULL
);
ALTER TABLE HIBERNATE_SEQUENCES ADD CONSTRAINT HIBERNATE_SEQUENCES_PK PRIMARY KEY (SEQUENCE_NAME);
-- INSERT INTO HIBERNATE_SEQUENCES (SEQUENCE_NAME, NEXT_VAL) VALUES ('INVOICE', 1);

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('JMS.01.32', 'JMS.01.31');