nginx代理的静态资源是f:/ftpfile/img目录下的静态资源（可以自行配置），具体的配置文件是/conf/vhost/image.shoppingmall.conf
只需修改配置文件中的root配置项即可
需要注意一下两点：
1.
代理静态资源的目录需要时ftp服务器上的目录，要不然springmvc上传的商品和二维码图片无法获取，比如本项目存储的商品图片和二维码都在
ftp服务器上的f:/ftpfile/img目录下，则nginx代理配置的目录也应该时此目录

2. 本地机器文件host的配置,
修改C:\Windows\System32\drivers\etc\hosts文件，加入一行127.0.0.1  img.cznshoppingmall.com，其中img.cznshoppingmall.com是nginx代理
图片资源的域名，当在网页上访问此域名时，会首先到hosts文件查找有没有此域名对应的ip。