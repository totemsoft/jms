1.       V_REGION now selects CodeNumeric as REGION_ID. This is the code that is actually used to identify the Region, not RegionId. (Logical yes?)
DONE!

2.       I have created a view called Y_REGION that gives us the results that we actually need. V_REGION returns too many rows which do not reflect the actual regional structure.
DONE!

3.       I have also created views Y_AREA and Y_STATION to more accurately reflect the information needed. Apparently table bneis05.[Fire].[dbo].[LocationHierarchy] has replaced all of the other tables that we have been using so far (REGION, AREA & STATION. It is now the only table to be used for this reference information. This may also change in the near future.
DONE!
EscadRegionPrefix IS NULL for:
No regionId found for areaCode: 3BS
No regionId found for areaCode: 4FN
No regionId found for areaCode: 4NC
No regionId found for areaCode: 4NR
No regionId found for areaCode: 5NC
No regionId found for areaCode: NC5
No regionId found for areaCode: NROSC
No regionId found for areaCode: SE9

Need address part of AREA and STATION

4.       View V_LOCATION returns AssetLocation as FCA_NO which is incorrect because it is not an FCA number. I’m not sure of what it is but we don’t use it. I don’t believe that this view will be useable.

Need link FCA to Station

5.       I have managed to run script  Full_FCA_Import2.sql with a reasonable level of success. There are foreign key constraints to overcome but the majority of the information managed to load successfully. I ran a backup before I started and had the database restored after I finished so all is well.

DONE! see V_FCA_BUILDING and sps_fca_building

No regionId found for fcaNo: 90000-01
No regionId found for fcaNo: 90001-01
No regionId found for fcaNo: 90002-01
No regionId found for fcaNo: 90003-01
No regionId found for fcaNo: 90006-01
No regionId found for fcaNo: 90006-02
No regionId found for fcaNo: 90006-03
No regionId found for fcaNo: 90090-01
No regionId found for fcaNo: 90095-01
No regionId found for fcaNo: 90096-01
No regionId found for fcaNo: 90097-01
No regionId found for fcaNo: 90098-01
No regionId found for fcaNo: 90099-01

6. Danielle Feltham
When doing a search for the Bulk Mail Out I am not finding any FCA’s where they have a Last Received Date. I have tried several search variations and only FCA’s without a Last Received date are listed. Below is an example of a Region search.
I tried searching for FCA 67608-01 (an om89 was last received on 11/06/2014) and no records were found.

SELECT MAX(fa.FILE_ACTION_ID) AS fileActionId, f.FILE_ID AS fileId
 FROM OWNER AS o RIGHT OUTER JOIN
 FILES AS f INNER JOIN
 FCA AS fca ON f.FILE_ID = fca.FILE_ID ON o.FILE_ID = f.FILE_ID LEFT OUTER JOIN
 FILE_ACTION AS fa INNER JOIN
 MAIL_BATCH_FILE AS mbf ON fa.FILE_ACTION_ID = mbf.FILE_ACTION_ID ON f.FILE_ID = mbf.FILE_ID
 WHERE (f.FILE_STATUS_ID = 1)
 AND (:fileNo IS NULL OR CAST(f.FILE_ID AS VARCHAR) LIKE :fileNo)
 AND (:fcaNo IS NULL OR fca.FCA_NO LIKE :fcaNo)
 AND (:regionId IS NULL OR fca.REGION_ID = :regionId)
 AND ((:noStatus = 'Y' AND mbf.STATUS_ID IS NULL) OR (mbf.STATUS_ID IN (:status)))
 AND (f.NO_MAIL_OUT = :doNotMail)
 AND (:actionCodeId IS NULL OR fa.FILE_ACTION_ID IS NULL OR (fa.ACTION_CODE_ID = :actionCodeId AND fa.LOGICALLY_DELETED IS NULL))
 AND (mbf.FILE_ACTION_ID IS NULL OR
 (:sentDateOption IS NULL) OR
 ((:sentDateOption = 1) AND (:sentDateFrom IS NULL OR mbf.SENT_DATE <= :sentDateFrom)) OR
 ((:sentDateOption = 2) AND (:sentDateFrom IS NULL OR mbf.SENT_DATE >= :sentDateFrom)) OR
 (((:sentDateOption = 3) AND (:sentDateFrom IS NULL OR mbf.SENT_DATE >= :sentDateFrom) AND (:sentDateTo IS NULL OR mbf.SENT_DATE <= :sentDateTo))))
 AND (mbf.FILE_ACTION_ID IS NULL OR
 (:receivedDateOption IS NULL) OR
 ((:receivedDateOption = 1) AND (:receivedDateFrom IS NULL OR mbf.RECEIVED_DATE <= :receivedDateFrom)) OR
 ((:receivedDateOption = 2) AND (:receivedDateFrom IS NULL OR mbf.RECEIVED_DATE >= :receivedDateFrom)) OR
 (((:receivedDateOption = 3) AND (:receivedDateFrom IS NULL OR mbf.RECEIVED_DATE >= :receivedDateFrom) AND (:receivedDateTo IS NULL OR mbf.RECEIVED_DATE <= :receivedDateTo))))
 AND (:ownerTypeId IS NULL OR o.OWNER_TYPE_ID = :ownerTypeId)
 AND (:ownerId IS NULL OR o.OWNER_ID = :ownerId)
 AND (:ownerIdContact IS NULL OR o.OWNER_ID = :ownerIdContact)
 GROUP BY f.FILE_ID
 ORDER BY fileActionId DESC
