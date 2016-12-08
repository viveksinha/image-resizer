<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>OpenTable Transformer</title>
</head>
<body>

<form:form method="POST" action="/api/v1/transformer/image">
    <table>
        <tr>
            <td><form:label path="scaledHeight">Height</form:label></td>
            <td><form:input path="scaledHeight"/></td>
        </tr>
        <tr>
            <td><form:label path="scaledWidth">Width</form:label></td>
            <td><form:input path="scaledWidth"/></td>
        </tr>
        <tr>
            <td><form:label path="outputFormat">Output Format</form:label></td>
            <td><form:input path="outputFormat"/></td>
        </tr>
        <tr>
            <td><form:label
                    path="maintainAspectRatio">Fixed or maintain aspect ratio</form:label></td>
            <td><form:input path="maintainAspectRatio"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Submit"/>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
