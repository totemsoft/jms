USE [JMS]
GO
DROP USER [jms]
GO

USE [master]
GO
CREATE LOGIN [jms] WITH PASSWORD=N'Passw0rd', DEFAULT_DATABASE=[JMS], CHECK_EXPIRATION=OFF, CHECK_POLICY=ON
GO

USE [JMS]
GO
CREATE USER [jms] FOR LOGIN [jms]
GO
EXEC sp_addrolemember N'db_owner', N'jms'
GO
