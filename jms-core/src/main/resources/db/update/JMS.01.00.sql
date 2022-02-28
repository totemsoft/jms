ALTER TABLE ASE_TYPE ADD ACTIVE char(1) NOT NULL DEFAULT 'Y';
ALTER TABLE ASE_CONN_TYPE ADD ACTIVE char(1) NOT NULL DEFAULT 'Y';
ALTER TABLE WORK_GROUP ADD ACTIVE char(1) NOT NULL DEFAULT 'Y';
ALTER TABLE AREA ADD ACTIVE char(1) NOT NULL DEFAULT 'Y';
ALTER TABLE REGION ADD ACTIVE char(1) NOT NULL DEFAULT 'Y';
ALTER TABLE STATION ADD ACTIVE char(1) NOT NULL DEFAULT 'Y';
ALTER TABLE STAKE_HOLDER ADD ACTIVE char(1) NOT NULL DEFAULT 'Y';

ALTER TABLE FILE_ACTION ALTER COLUMN NOTATION varchar(4098) NOT NULL;
ALTER TABLE JOB_ACTION ALTER COLUMN NOTATION varchar(4098) NOT NULL;

-- change FCA_NO size to 8 characters, eg 51001-02
ALTER TABLE JOBS DROP CONSTRAINT JOBS_FK4;
ALTER TABLE JOB_REQUEST DROP CONSTRAINT JOB_REQUEST_FK2;
ALTER TABLE FCA ALTER COLUMN FCA_NO varchar(8) NOT NULL;
ALTER TABLE JOB_REQUEST ALTER COLUMN FCA_NO varchar(8);
ALTER TABLE JOBS ALTER COLUMN FCA_NO varchar(8);
ALTER TABLE JOBS ADD CONSTRAINT JOBS_FK4 FOREIGN KEY (FCA_NO)
	REFERENCES FCA (FCA_NO);
ALTER TABLE JOB_REQUEST ADD CONSTRAINT JOB_REQUEST_FK2 FOREIGN KEY (FCA_NO)
	REFERENCES FCA (FCA_NO);

-- delete from fca where fca_no like '%-00' and file_id is null;
ALTER TABLE FCA ADD SUB_PANEL char(1) NOT NULL DEFAULT 'Y';
UPDATE FCA SET SUB_PANEL = 'N' WHERE FCA_NO LIKE '%-01';
CREATE INDEX FCA_IDX1 ON FCA (REGION_ID);

-- unique region, area, station

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('JMS.01.00', NULL);