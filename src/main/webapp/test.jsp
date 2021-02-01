<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/1/28
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script>
    $.ajax({
        url:"",
        data:{},
        type:"post",
        success:function (resp) {

        }
    });


    $(".time").datetimepicker({
        language:  "zh-CN",
        format: "yyyy-mm-ddhh:ii:ss",//显示格式
        minView: "hour",//设置只显示到月份
        initialDate: new Date(),//初始化当前日期
        autoclose: true,//选中自动关闭
        todayBtn: true, //显示今日按钮
        clearBtn : true,
        pickerPosition: "bottom-left"
    });

    $(".time").datetimepicker({
        minView: "month",
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });

   /* System.out.println("==========开始==========");
    System.out.println();
    System.out.println("==========结束==========");*/
</script>
</body>
</html>
