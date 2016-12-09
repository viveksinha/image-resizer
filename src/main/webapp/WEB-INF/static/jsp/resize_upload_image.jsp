<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Image Resizer</title>
</head>
<body>
<br>
<br>

<div align="center">
    <h1>Image Resizer</h1>
    <a href="/api/v1/transformer/image-collection"><input type="button" value="Image Collection"/></a>
    <br/>
    <form:form commandName="image" method="post" action="/api/v1/transformer/upload"
               enctype="multipart/form-data">
        <fieldset>
            <p>Currently we only support JPG image</p>

            <p>Select files to resize.</p>

            <p>
                <label for="image">Images: </label>
                <input type="file" name="multipartFiles" multiple="multiple"/>
            </p>

            <p>
                <label for="scaledHeight">Height: </label>
                <form:input id="scaledHeight" path="scaledHeight"/>
            </p>

            <p>
                <label for="scaledWidth">Width: </label>
                <form:input id="scaledWidth" path="scaledWidth"/>
            </p>

            <p>
                <label for="outputFormat">Output Format: </label>
                <form:select path="outputFormat">
                    <form:option value="jpg" label="jpg"/>
                </form:select>
            </p>

            <p>
                <label for="maintainAspectRatio">Maintain aspect ratio: </label>
                <form:select path="maintainAspectRatio">
                    <form:option value="True" label="Yes"/>
                    <form:option value="False" label="No"/>
                </form:select>
            </p>

            <p id="buttons">
                <input id="reset" type="reset" tabindex="4">
                <input id="submit" type="submit" tabindex="5" value="Resize">
            </p>
        </fieldset>
    </form:form>

    <br/>
</div>
</body>
</html>
