<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2017/12/18
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>TMS-售票站点管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <%@include file="../include/css.jsp"%>



    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- 顶部导航栏部分 -->
    <%@include file="../include/header.jsp"%>

    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <%@include file="../include/left.jsp"%>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">

                <div class="box-header with-border">
                    <h3 class="box-title">新增售票站点</h3>
                    <div class="box-tools pull-right">
                        <a href="/ticketStore/message"><i class="fa fa-arrow-left"></i> 返回列表</a>
                    </div>
                </div>

                <c:if test="${not empty message}">
                    <div class="form-group">${message}</div>
                </c:if>
                <div class="box-body">
                    <form method="post" id="addForm" enctype="multipart/form-data">
                        <div class="form-group">
                            <label>站点名称</label>
                            <input type="text" name="storeName" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>地址</label>
                            <input type="text" class="form-control" name="storeAddress">
                        </div>
                        <div class="form-group">
                            <label>负责人</label>
                            <input type="text" class="form-control" name="storeManager">
                        </div>
                        <div class="form-group">
                            <label>经度</label>
                            <input type="text" class="form-control" name="storeGeoLongitude">
                        </div>
                        <div class="form-group">
                            <label>纬度</label>
                            <input type="text" class="form-control" name="storeLatitude">
                        </div>
                        <div class="form-group">
                            <label>联系电话</label>
                            <input type="text" class="form-control" name="storeTel">
                        </div>
                        <div class="form-group">
                            <label>营业执照</label>
                            <input type="file" class="form-control" name="paperImg">
                        </div>
                        <div class="form-group">
                            <label>负责人证件</label>
                            <input type="file" class="form-control" name="IDcardImg">
                        </div>

                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn btn-primary" id="addNewCustomer">保存</button>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 1.0
        </div>
        <strong>Copyright &copy; 2010 -2017 <a href="http://almsaeedstudio.com">凯盛软件</a>.</strong> All rights
        reserved.
    </footer>

</div>
<!-- ./wrapper -->

<!-- jQuery 2.2.3 -->
<%@include file="../include/js.jsp"%>
<script src="/static/plugins/validate/jquery.validate.min.js"></script>
<script>

    $(function () {

        $("#addNewCustomer").click(function () {
            $("#addForm").submit();
        });


        $("#addForm").validate({

            errorClass : "text-danger",
            errorElement : "span",

            rules:{
                storeName:{
                    required:true
                },
                storeAddress:{
                    required:true
                },
                storeManager : {
                    required:true
                },
                storeGeoLongitude:{
                    required:true
                },
                storeLatitude:{
                    required:true
                },
                storeTel:{
                    required:true
                },
                paperImg:{
                    required:true
                },
                IDcardImg:{
                    required:true
                }
            },
            messages:{
                storeName:{
                    required:"请输入站点名称"
                },
                storeAddress:{
                    required:"请输入地址"
                },
                storeManager : {
                    required : "请输入负责人"
                },
                storeGeoLongitude : {
                    required : "请输入站点所在经度"
                },
                storeLatitude : {
                    required : "请输入所在纬度"
                },
                storeTel : {
                    required : "请输入联系方式"
                },
                paperImg : {
                    required : "请上传营业执照"
                },
                IDcardImg : {
                    required : "请上传负责人证件"
                }
            }

        });

    });



</script>

</body>
</html>
