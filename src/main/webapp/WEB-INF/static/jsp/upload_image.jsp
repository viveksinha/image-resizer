<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>OpenTable Image Resizer</title>
    <script
            src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        $(document).ready(function() {
                    //add more file components if Add is clicked
            $('#addFile').click(function() {
                var fileIndex = $('#fileTable tr').children().length - 1;
                $('#fileTable').append('<tr><td> <input type="file" name="files['+ fileIndex +']" /> </td></tr>');});
        });
    </script>
</head>
<body>
<br>
<br>
<div align="center">
    <h1>OpenTable Image Resizer</h1>

    <form:form method="post" action="/api/v1/transformer/upload"
               modelAttribute="uploadForm" enctype="multipart/form-data">

        <p>Select files to upload. Press Add button to add more file
            inputs.</p>

        <table id="fileTable">
            <tr>
                <td><input name="files[0]" type="file" /></td>
            </tr>
            <tr>
                <td><input name="files[1]" type="file" /></td>
            </tr>
        </table>
        <br />
        <input type="submit" value="Upload" />
        <input id="addFile" type="button" value="Add File" />
    </form:form>

    <br />
    <%@ include file="/WEB-INF/static/jsp/resize.jsp" %>
</div>
</body>
</html>
