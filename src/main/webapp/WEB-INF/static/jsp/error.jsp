<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>OpenTable Image Resizer</title>
</head>
<body>
<br>
<br>

<div align="center">

    <h1>OpenTable Image Resizer</h1>
    <p>OOPS Files were not uploaded successfully.</p>
    <ol>
        Error: ${error_message} <br>
    </ol>
    <a href="/api/v1/transformer"><input type="button" value="Go Back"/></a> <br/>
    <a href="/api/v1/transformer/image-collection"><input type="button" value="Image Collection"/></a>
    <br/>
    <br/>
</div>
</body>
</html>
