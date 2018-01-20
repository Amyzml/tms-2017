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
    <title>TMS-用户管理</title>
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
                    <h3 class="box-title">新增账户</h3>
                    <div class="box-tools pull-right">
                        <button class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</button>
                    </div>
                </div>

                <c:if test="${not empty message}">
                    <div class="form-group">${message}</div>
                </c:if>
                <div class="box-body">
                    <form method="post" id="addForm">
                        <div class="form-group">
                            <label>姓名</label>
                            <input type="text" name="accountName" class="form-control">
                        </div>

                        <div class="form-group">
                            <label>手机号码</label>
                            <input type="text" class="form-control" name="accountMobile">
                        </div>
                        <div class="form-group">
                            <label>账户密码</label>
                            <input type="text" class="form-control" name="accountPassword">
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
                accountName:{
                    required:true
                },
                accountMobile:{
                    required:true
                },
                accountPassword : {
                    required:true
                }
            },
            messages:{
                accountName:{
                    required:"请输入姓名"
                },
                accountMobile:{
                    required:"请输入联系方式"
                },
                accountPassword : {
                    required : "请输入密码"
                }
            }

        });

    });



</script>

</body>
</html>
