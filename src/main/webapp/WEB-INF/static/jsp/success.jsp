<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Image Resizer</title>
</head>
<body>
<br>
<br>

<div align="center">

    <h1>Image Resizer</h1>

    <p>Awesome.. Following files are uploaded successfully.</p>
    <ol>
        <c:forEach items="${files}" var="file">
            - ${file} <br>
        </c:forEach>
    </ol>
    <a href="/api/v1/transformer"><input type="button" value="Go Back"/></a> <br/>
    <a href="/api/v1/transformer/image-collection"><input type="button" value="Image Collection"/></a>
    <br/>
    <br/>
</div>
</body>
</html>
