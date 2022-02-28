SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================================
-- Create view V_ACTIVE_FILE
-- =============================================================
IF EXISTS (SELECT name FROM sysobjects WHERE name = N'V_ACTIVE_FILE' AND type = 'V')
    DROP VIEW V_ACTIVE_FILE
GO
CREATE VIEW V_ACTIVE_FILE AS
    SELECT
        f.FILE_ID AS FILE_ID,
        fca.FCA_NO AS FCA_NO,
        b.NAME AS BUILDING_NAME,
        a.ADDR_LINE_1 + ', ' + a.SUBURB + ', ' + a.STATE AS BUILDING_ADDRESS,
        s.NAME AS SUPPLIER_NAME,
        o.LEGAL_NAME AS OWNER_NAME,
        -- used in where clause
        f.FILE_TYPE_ID AS FILE_TYPE_ID,
        f.FILE_STATUS_ID AS FILE_STATUS_ID,
        f.CREATED_DATE AS CREATED_DATE,
        b.BUILDING_ID AS BUILDING_ID,
        a.SUBURB AS BUILDING_SUBURB,
        c.SURNAME AS OWNER_CONTACT
    FROM FILES f LEFT OUTER JOIN
        ASE_SUPPLIER INNER JOIN
        SUPPLIER AS s ON ASE_SUPPLIER.SUPPLIER_ID = s.SUPPLIER_ID RIGHT OUTER JOIN
        ASE_FILE AS af ON ASE_SUPPLIER.ASE_FILE_ID = af.ASE_FILE_ID ON f.FILE_ID = af.FILE_ID LEFT OUTER JOIN
        OWNER AS o LEFT OUTER JOIN
        CONTACT AS c ON o.CONTACT_ID = c.CONTACT_ID ON f.FILE_ID = o.FILE_ID LEFT OUTER JOIN
        BUILDING AS b LEFT OUTER JOIN
        ADDRESS AS a ON b.ADDRESS_ID = a.ADDRESS_ID ON f.FILE_ID = b.FILE_ID LEFT OUTER JOIN
        FCA AS fca ON f.FILE_ID = fca.FILE_ID
    WHERE
        (o.OWNER_TYPE_ID IS NULL OR o.OWNER_TYPE_ID = 1) -- OwnerTypeEnum.OWNER
GO
-- SELECT * FROM V_ACTIVE_FILE;

-- =============================================================
-- Create view V_ACTIVE_JOB
-- =============================================================
IF EXISTS (SELECT name FROM sysobjects WHERE name = N'V_ACTIVE_JOB' AND type = 'V')
    DROP VIEW V_ACTIVE_JOB
GO
CREATE VIEW V_ACTIVE_JOB AS
    SELECT
        j.JOB_ID AS JOB_ID,
        f.FILE_ID AS FILE_ID,
        fca.FCA_NO AS FCA_NO,
        jt.NAME AS JOB_TYPE_NAME,
        j.CREATED_DATE AS JOB_START_DATE,
        ja.DUE_DATE AS NEXT_ACTION_DATE,
        ac.CODE AS NEXT_ACTION_CODE,
        u.LOGIN AS REQUESTED_BY,
        wg.NAME AS WORK_GROUP,
        -- used in where clause
        b.BUILDING_ID AS BUILDING_ID,
        b.NAME AS BUILDING_NAME,
        ja.ACTION_CODE_ID AS NEXT_ACTION_CODE_ID,
        jt.WORK_GROUP_ID AS WORK_GROUP_ID,
        j.JOB_TYPE_ID AS JOB_TYPE_ID
    FROM BUILDING AS b RIGHT OUTER JOIN
        FCA AS fca RIGHT OUTER JOIN
        FILES AS f ON fca.FILE_ID = f.FILE_ID ON b.FILE_ID = f.FILE_ID RIGHT OUTER JOIN
        WORK_GROUP AS wg RIGHT OUTER JOIN
        USERS AS u INNER JOIN
        JOBS AS j ON u.USER_ID = j.CREATED_BY LEFT OUTER JOIN
        JOB_ACTION AS ja INNER JOIN
        ACTION_CODE AS ac ON ja.ACTION_CODE_ID = ac.ACTION_CODE_ID ON j.JOB_ID = ja.JOB_ID AND (ja.LOGICALLY_DELETED IS NULL) AND (ja.COMPLETED_DATE IS NULL) LEFT OUTER JOIN
        JOB_TYPE AS jt ON j.JOB_TYPE_ID = jt.JOB_TYPE_ID ON wg.WORK_GROUP_ID = jt.WORK_GROUP_ID ON
        f.FILE_ID = j.FILE_ID
    WHERE (f.FILE_STATUS_ID = 1)
        AND (j.COMPLETED_DATE IS NULL)
GO
-- SELECT * FROM V_ACTIVE_JOB;

--
-- this should be the last statement in any update script
--
INSERT INTO DBVERSION (DBVERSION, PREV_DBVERSION) VALUES ('JMS.01.43', 'JMS.01.42');