<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <form method="post" action="oauth/file/fcaDoc/uploadFile/${fileId}?dir=${dir}"
          enctype="multipart/form-data">
        <jsp:include page="/WEB-INF/entity/upload.jsp" />
    </form>

</jsp:root>