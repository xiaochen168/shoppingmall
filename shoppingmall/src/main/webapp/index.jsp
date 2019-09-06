<%@ page language="java"  contentType="text/html; charset=UTF-8" %>

<html>
<body>
<h2>welcome shoppingmall</h2>
springmvc上传文件
<form name="form1" action="/seller/product/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="springmvc上传文件" />
</form>


<body>
<h2>springmvc上传商品图片文件</h2>
<form name="form1" action="/seller/product/product-image-upload" method="post" enctype="multipart/form-data">
    <h6>商品Id</h6>
    <input type="number" name="productId" />
    <h6>主图</h6>
    <input type="file" name="mainImage" />
    <h6>子图</h6>
    <input type="file" name="subImage" />
    <input type="file" name="subImage" />
    <input type="file" name="subImage" />
    <input type="file" name="subImage" />
    <input type="submit" value="springmvc上传文件" />
</form>


富文本图片上传文件
<form name="form2" action="/seller/product/rich_text_upload" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="富文本图片上传文件" />
</form>

</body>
</html>
