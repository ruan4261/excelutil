# excelutil

这不是一个多强力的工具\
他目前只在一个场景下能展现其高效

## 场景
>现在有一大堆Excel表格，表格中保存的是上千人的信息，及头像。但是公司系统基础设施比较差，图片不能和文本一起导入系统，现在要把所有图片导出到文件夹内，并且以表中某一列各行的信息标记各张图片，才能使用公司的图片批量导入。
>
>于是在这一环节出现了问题：效率过低。如果使用WPS的导出所有图片功能（当时并不知道WPS有这功能，而且还tm要收费），并不能一次性赋予图片各自的文件名。
>
>于是这个服务可以很好地解决图片导出的效率问题，一张表格中图片越多效率越高。

## 使用方法
>下载最新release版本中的excelutil.jar，使用jdk启动，你可以自定义其监听端口，如不设置，端口默认为42602
>```
>java -jar excelutil.jar --server.port=[?]
>```
>启动后即可使用浏览器访问本地端口

## 注意
服务部署机器与实际使用机器请使用同一机器。\
因为本服务没有开启上传功能，源文件指定路径和图片导出的默认路径都是对于服务器而言的，所以你不能将本服务部署在服务器却使用不同主机使用本服务。

## 未来
工作上出现奇葩任务，临时开发的工具，就写一个操作，打了个web服务。\
如果你需要拓展其功能，可以联系我，如果有需求，我非常乐意继续开发。
***
SDK -> Apache poi\
Author -> ruan4261\
Contact -> QQ2598055586 or [i@4261.ink](mailto:i@4261.ink)