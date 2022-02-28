EXEC msdb.dbo.sp_delete_database_backuphistory @database_name = N'QFRS'
GO
USE [master]
GO
DROP DATABASE [QFRS]
GO
DROP LOGIN [qfrs]
GO
