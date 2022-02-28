-- 5.3.23. Site Type
CREATE TABLE SITE_TYPE (
    SITE_TYPE_ID numeric(11, 0) IDENTITY NOT NULL,
    NAME varchar(50) NOT NULL,
    ACTIVE char(1) NOT NULL DEFAULT 'Y',
    CREATED_BY numeric(11,0),
    CREATED_DATE datetime NOT NULL DEFAULT getDate(),
    UPDATED_BY numeric(11,0),
    UPDATED_DATE datetime,
    LOCK_VERSION numeric(11, 0) NOT NULL DEFAULT 0
);
ALTER TABLE SITE_TYPE ADD CONSTRAINT SITE_TYPE_PK PRIMARY KEY (SITE_TYPE_ID);

-- SiteType
INSERT INTO SYSTEM_FUNCTION (SYSTEM_FUNCTION_ID, MODULE, NAME, DESCRIPTION) VALUES
 (330, '/setup', '/findSiteType', 'View all Site Type');
INSERT INTO SYSTEM_FUNCTION (SYSTEM_FUNCTION_ID, MODULE, NAME, DESCRIPTION) VALUES
 (331, '/setup', '/viewSiteType', 'View Site Type details');
INSERT INTO SYSTEM_FUNCTION (SYSTEM_FUNCTION_ID, MODULE, NAME, DESCRIPTION) VALUES
 (332, '/setup', '/editSiteType', 'Edit Site Type details');
INSERT INTO SYSTEM_ACCESS (SECURITY_GROUP_ID, SYSTEM_FUNCTION_ID) VALUES (1, 330);
INSERT INTO SYSTEM_ACCESS (SECURITY_GROUP_ID, SYSTEM_FUNCTION_ID) VALUES (1, 331);
INSERT INTO SYSTEM_ACCESS (SECURITY_GROUP_ID, SYSTEM_FUNCTION_ID) VALUES (1, 332);

-- FILES or FCA or BUILDING ???
ALTER TABLE FILES ADD SITE_TYPE_ID numeric(11, 0) NULL;
ALTER TABLE FILES ADD CONSTRAINT FILES_FK2 FOREIGN KEY (SITE_TYPE_ID) REFERENCES SITE_TYPE (SITE_TYPE_ID);

-- 5.3.27. Multi-site Identification and Linkage (see subPanel column)
ALTER TABLE FCA ADD PARENT_FCA_NO varchar(8) NULL;
ALTER TABLE FCA ADD CONSTRAINT FCA_FK5 FOREIGN KEY (PARENT_FCA_NO) REFERENCES FCA (FCA_NO);

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('JMS.01.10', 'JMS.01.09');