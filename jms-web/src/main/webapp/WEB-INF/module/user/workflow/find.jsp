<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
{
"tabs": [
{"id":"tab4","config":{"label":"By Job Type", "active": true,
   "dataSrc":"user/workflowRegister.do?dispatch=findByJobType"}},
{"id":"tab3","config":{"label":"By Work Group",
   "dataSrc":"user/workflowRegister.do?dispatch=findByWorkGroup"}}
]
}
</jsp:root>