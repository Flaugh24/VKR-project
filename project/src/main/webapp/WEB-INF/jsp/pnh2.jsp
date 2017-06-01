<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>File Upload</title>
</head>
<body>
<h3>@MultipartConfig Annotation Example</h3>
<FORM action="FileUpload" enctype="multipart/form-data" method="POST">
    File Name: &nbsp;&nbsp;&nbsp;<INPUT type="text" name="filename"><br/>
    Upload File: &nbsp;<INPUT type="file" name="content"><br/>
    <INPUT type="submit" value="Submit">
</FORM>
</body>
</html>