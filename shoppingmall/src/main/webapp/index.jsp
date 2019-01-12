<%@ page language="java"  contentType="text/html; charset=UTF-8" %>

<html>
<body>
<h2>welcome shoppingmall</h2>
springmvc上传文件
<form name="form1" action="/seller/product/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="springmvc上传文件" />
</form>


富文本图片上传文件
<form name="form2" action="/seller/product/rich_text_upload" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="富文本图片上传文件" />
</form>

</body>
</html>
