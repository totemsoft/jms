<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
{
"menuItems": [
{"id": "actionFlow", "text": "Action Flow", "itemdata": [
{ "text": "Action Codes", "url": "setup/findActionCode.do?dispatch=find" },
{ "text": "Action Outcomes", "url": "setup/findActionOutcome.do?dispatch=find" },
{ "text": "Action Workflows", "url": "setup/findActionWorkflow.do?dispatch=find" }
]},
{"id": "user", "text": "Users", "itemdata": [
{ "text": "User Types", "url": "setup/findUserType.do?dispatch=find" },
{ "text": "Security Groups", "url": "setup/findSecurityGroup.do?dispatch=find" },
{ "text": "System Functions", "url": "setup/systemFunction.do?dispatch=find" },
{ "text": "Work Groups", "url": "setup/findWorkGroup.do?dispatch=find" }
]},
{"id": "documentTemplates", "text": "Document Templates", "itemdata": [
{ "text": "Add/Edit Templates", "url": "setup/findTemplate.do?dispatch=find" },
{ "text": "Document Types", "url": "setup/findDocType.do?dispatch=find" },
{ "text": "Document Checklist", "url": "setup/findDocChkList.do?dispatch=find" }
]},
{"id": "qfrsData", "text": "QFRS Data", "itemdata": [
{ "text": "Regions", "url": "setup/findRegion.do?dispatch=find" },
{ "text": "Areas", "url": "setup/findArea.do?dispatch=find" },
{ "text": "Stations", "url": "setup/findStation.do?dispatch=find" },
{ "text": "Building Classifications", "url": "setup/findBuildingClassification.do?dispatch=find" },
{ "text": "Site Types", "url": "setup/findSiteType.do?dispatch=find" },
{ "text": "Mail Types", "url": "setup/mailType.do?dispatch=find" },
{ "text": "QFRS Stakeholder Types", "url": "setup/findStakeholderType.do?dispatch=find" },
{ "text": "QFRS Stakeholder Data", "url": "setup/findStakeholder.do?dispatch=find" }
]},
{"id": "suppliers", "text": "Suppliers", "itemdata": [
{ "text": "Supplier Types", "url": "setup/findSupplierType.do?dispatch=find" },
{ "text": "ASE Types", "url": "setup/findAseType.do?dispatch=find" },
{ "text": "ASE Connection Types", "url": "setup/findAseConnType.do?dispatch=find" }
]},
{"id": "menuSelections", "text": "Menu Selections", "itemdata": [
{ "text": "Salutations", "url": "setup/findSalutation.do?dispatch=find" },
{ "text": "States", "url": "setup/findState.do?dispatch=find" }
]},
{"id": "fileStatus", "text": "File Status", "itemdata": [
{ "text": "File Status Types", "url": "setup/findFileStatus.do?dispatch=find" },
{ "text": "File Job Types", "url": "setup/findJobType.do?dispatch=find" }
]},
{"id": "maintenance", "text": "Maintenance", "itemdata": [
{ "id": "tasks", "text": "Tasks", "url": "setup/scheduledTask.do?dispatch=find" },
{ "id": "cache", "text": "Cache", "url": "setup/cache.do?dispatch=find" },
{ "id": "database", "text": "Database", "url": "setup/database.do?dispatch=find" }
]}
]}
</jsp:root>