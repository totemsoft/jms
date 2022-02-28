SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================================
-- Create view V_ACTIVE_ARCHIVE
-- =============================================================
IF EXISTS (SELECT name FROM sysobjects WHERE name = N'V_ACTIVE_ARCHIVE' AND type = 'V')
    DROP VIEW V_ACTIVE_ARCHIVE
GO
CREATE VIEW V_ACTIVE_ARCHIVE AS
    SELECT
        DISTINCT a.ARCHIVE_ID AS ARCHIVE_ID,
        a.DATE_FROM AS DATE_FROM,
        -- used in where clause
        a.DATE_TO AS DATE_TO,
        fa.FILE_ID AS FILE_ID,
        fca.FCA_NO AS FCA_NO,
        sh.SAP_CUST_NO AS SAP_CUST_NO
    FROM
        FILES AS f INNER JOIN
        FILE_ARCHIVE AS fa ON f.FILE_ID = fa.FILE_ID LEFT OUTER JOIN
        FCA AS fca ON f.FILE_ID = fca.FILE_ID LEFT OUTER JOIN
        SAP_HEADER AS sh ON f.FILE_ID = sh.FILE_ID RIGHT OUTER JOIN
        ARCHIVE AS a ON fa.ARCHIVE_ID = a.ARCHIVE_ID
GO
-- SELECT top 10 * FROM V_ACTIVE_ARCHIVE;

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
-- Create view V_ACTIVE_ASE_FILE
-- =============================================================
IF EXISTS (SELECT name FROM sysobjects WHERE name = N'V_ACTIVE_ASE_FILE' AND type = 'V')
    DROP VIEW V_ACTIVE_ASE_FILE
GO
CREATE VIEW V_ACTIVE_ASE_FILE AS
    SELECT
        f.FILE_ID AS FILE_ID,
        fca.FCA_NO AS FCA_NO,
        b.BUILDING_ID AS BUILDING_ID,
        b.NAME AS BUILDING_NAME,
        a.ADDR_LINE_1 + ', ' + a.SUBURB + ', ' + a.STATE AS BUILDING_ADDRESS,
        s.NAME AS SUPPLIER_NAME,
        o.LEGAL_NAME AS OWNER_NAME,
        -- used in where clause
        f.FILE_TYPE_ID AS FILE_TYPE_ID,
        f.FILE_STATUS_ID AS FILE_STATUS_ID,
        f.CREATED_DATE AS CREATED_DATE,
        a.SUBURB AS BUILDING_SUBURB,
        c.SURNAME AS OWNER_CONTACT
    FROM ASE_SUPPLIER INNER JOIN
        SUPPLIER AS s ON ASE_SUPPLIER.SUPPLIER_ID = s.SUPPLIER_ID RIGHT OUTER JOIN
        ASE_CHANGE AS ac RIGHT OUTER JOIN
        ASE_FILE AS af ON ac.ASE_FILE_ID = af.ASE_FILE_ID ON ASE_SUPPLIER.ASE_FILE_ID = af.ASE_FILE_ID RIGHT OUTER JOIN
        FILES AS f ON af.FILE_ID = f.FILE_ID LEFT OUTER JOIN
        OWNER AS o LEFT OUTER JOIN
        CONTACT AS c ON o.CONTACT_ID = c.CONTACT_ID ON f.FILE_ID = o.FILE_ID LEFT OUTER JOIN
        BUILDING AS b LEFT OUTER JOIN
        ADDRESS AS a ON b.ADDRESS_ID = a.ADDRESS_ID ON f.FILE_ID = b.FILE_ID LEFT OUTER JOIN
        FCA AS fca ON f.FILE_ID = fca.FILE_ID
    WHERE   (ac.DATE_CHANGE IS NOT NULL)
GO
-- SELECT * FROM V_ACTIVE_ASE_FILE;

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

-- =============================================================
-- Create view V_ACTIVE_JOB_REQUEST
-- =============================================================
IF EXISTS (SELECT name FROM sysobjects WHERE name = N'V_ACTIVE_JOB_REQUEST' AND type = 'V')
    DROP VIEW V_ACTIVE_JOB_REQUEST
GO
CREATE VIEW V_ACTIVE_JOB_REQUEST AS
    SELECT
        jr.JOB_REQUEST_ID AS JOB_REQUEST_ID,
        jt.NAME AS JOB_TYPE,
        wg.NAME AS WORK_GROUP,
        u.LOGIN AS REQUESTED_BY,
        jr.REQUESTED_DATE AS REQUESTED_DATE, 
        jr.REQUESTED_EMAIL AS REQUESTED_EMAIL,
        jr.LINK AS LINK,
        -- used in where clause
        jt.JOB_TYPE_ID AS JOB_TYPE_ID,
        wg.WORK_GROUP_ID AS WORK_GROUP_ID
    FROM JOB_TYPE AS jt RIGHT OUTER JOIN
        USERS AS u RIGHT OUTER JOIN
        FCA AS fca RIGHT OUTER JOIN
        JOB_REQUEST AS jr ON fca.FCA_NO = jr.FCA_NO ON u.USER_ID = jr.REQUESTED_BY ON 
        jt.JOB_TYPE_ID = jr.JOB_TYPE_ID LEFT OUTER JOIN
        WORK_GROUP AS wg ON jt.WORK_GROUP_ID = wg.WORK_GROUP_ID
    WHERE (jr.ACCEPTED_DATE IS NULL)
GO
-- SELECT * FROM V_ACTIVE_JOB_REQUEST;

-- =============================================================
-- Create view V_ACTIVE_MAIL_REGISTER
-- =============================================================
IF EXISTS (SELECT name FROM sysobjects WHERE name = N'V_ACTIVE_MAIL_REGISTER' AND type = 'V')
    DROP VIEW V_ACTIVE_MAIL_REGISTER
GO
CREATE VIEW V_ACTIVE_MAIL_REGISTER AS
    SELECT
        mr.MAIL_REGISTER_ID AS MAIL_REGISTER_ID,
        -- used in where clause
        mr.MAIL_TYPE_ID AS MAIL_TYPE_ID,
        mr.MAIL_REGISTER_DATE AS MAIL_REGISTER_DATE,
        mr.MAIL_REGISTER_NO AS MAIL_REGISTER_NO,
        mr.MAIL_IN AS MAIL_IN,
        mr.RTS AS RTS,
        fca.FCA_NO AS FCA_NO,
        sh.SAP_CUST_NO AS SAP_CUST_NO,
        c.FIRST_NAME AS FIRST_NAME,
        c.SURNAME AS SURNAME
    FROM  CONTACT AS c RIGHT OUTER JOIN
        MAIL_REGISTER AS mr ON c.CONTACT_ID = mr.CONTACT_ID LEFT OUTER JOIN
        FCA AS fca INNER JOIN
        FILES AS f ON fca.FILE_ID = f.FILE_ID ON mr.FILE_ID = f.FILE_ID LEFT OUTER JOIN
        SAP_HEADER AS sh ON f.FILE_ID = sh.FILE_ID
GO
-- SELECT * FROM V_ACTIVE_MAIL_REGISTER;

-- =============================================================
-- Create view V_FILE_ACTION_TODO
-- =============================================================
IF EXISTS (SELECT name FROM sysobjects WHERE name = N'V_FILE_ACTION_TODO' AND type = 'V')
    DROP VIEW V_FILE_ACTION_TODO
GO
CREATE VIEW V_FILE_ACTION_TODO AS
    SELECT
        fa.FILE_ACTION_ID AS FILE_ACTION_ID,
        f.FILE_ID AS FILE_ID,
        fca.FCA_NO AS FCA_NO,
        fa.DUE_DATE AS NEXT_ACTION_DATE,
        ac.CODE AS NEXT_ACTION_CODE,
        wg.NAME AS WORK_GROUP,
        -- used in where clause
        fa.COMPLETED_DATE AS COMPLETED_DATE,
        ac.ACTION_TYPE_ID AS NEXT_ACTION_TYPE_ID,
        fa.ACTION_CODE_ID AS NEXT_ACTION_CODE_ID,
        ac.WORK_GROUP_ID AS WORK_GROUP_ID
    FROM
        ACTION_CODE ac INNER JOIN
        FILE_ACTION fa ON ac.ACTION_CODE_ID = fa.ACTION_CODE_ID LEFT OUTER JOIN
        WORK_GROUP wg ON ac.WORK_GROUP_ID = wg.WORK_GROUP_ID LEFT OUTER JOIN
        FILES f ON fa.FILE_ID = f.FILE_ID LEFT OUTER JOIN
        FCA fca ON f.FILE_ID = fca.FILE_ID
    WHERE (f.FILE_STATUS_ID = 1)
        AND (fa.LOGICALLY_DELETED IS NULL)
GO
-- SELECT * FROM V_FILE_ACTION_TODO;

-- =============================================================
-- Create view V_JOB_ACTION_TODO
-- =============================================================
IF EXISTS (SELECT name FROM sysobjects WHERE name = N'V_JOB_ACTION_TODO' AND type = 'V')
    DROP VIEW V_JOB_ACTION_TODO
GO
CREATE VIEW V_JOB_ACTION_TODO AS
    SELECT
        ja.JOB_ACTION_ID AS JOB_ACTION_ID,
        j.JOB_ID AS JOB_ID,
        f.FILE_ID AS FILE_ID,
        fca.FCA_NO AS FCA_NO,
        jt.NAME AS JOB_TYPE_NAME,
        j.CREATED_DATE AS JOB_START_DATE, 
        ja.DUE_DATE AS NEXT_ACTION_DATE,
        ac.CODE AS NEXT_ACTION_CODE,
        wg.NAME AS WORK_GROUP,
        -- used in where clause
        j.RESPONSIBLE_USER_ID AS RESPONSIBLE_USER_ID,
        uw.USER_ID AS WORK_GROUP_USER_ID,
        ja.COMPLETED_DATE AS COMPLETED_DATE,
        ac.ACTION_TYPE_ID AS NEXT_ACTION_TYPE_ID,
        ja.ACTION_CODE_ID AS NEXT_ACTION_CODE_ID,
        ac.WORK_GROUP_ID AS WORK_GROUP_ID,
        j.JOB_TYPE_ID AS JOB_TYPE_ID
    FROM
        USERS_WORKGROUP AS uw RIGHT OUTER JOIN
        FCA AS fca RIGHT OUTER JOIN
        FILES AS f ON fca.FILE_ID = f.FILE_ID RIGHT OUTER JOIN
        JOBS AS j RIGHT OUTER JOIN
        JOB_ACTION AS ja LEFT OUTER JOIN
        WORK_GROUP AS wg RIGHT OUTER JOIN
        ACTION_CODE AS ac ON wg.WORK_GROUP_ID = ac.WORK_GROUP_ID ON ja.ACTION_CODE_ID = ac.ACTION_CODE_ID ON j.JOB_ID = ja.JOB_ID ON 
        f.FILE_ID = j.FILE_ID ON uw.WORK_GROUP_ID = wg.WORK_GROUP_ID LEFT OUTER JOIN
        JOB_TYPE AS jt ON j.JOB_TYPE_ID = jt.JOB_TYPE_ID
    WHERE (f.FILE_STATUS_ID = 1)
        AND (j.COMPLETED_DATE IS NULL)
        AND (ja.LOGICALLY_DELETED IS NULL)
GO
-- SELECT * FROM V_JOB_ACTION_TODO;
