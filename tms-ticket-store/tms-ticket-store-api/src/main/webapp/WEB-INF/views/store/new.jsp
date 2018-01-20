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
    <title>TMS-办理年票</title>
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
                    <h3 class="box-title">顾客身份信息录入</h3>
                    <div class="box-tools pull-right">
                        <a class="btn btn-primary btn-sm" href="/ticket/store/showList">查看本站办理列表</a>
                    </div>
                </div>

                <c:if test="${not empty message}">
                    <div class="form-group">${message}</div>
                </c:if>
                <div class="box-body">
                    <form method="post" id="addForm" enctype="multipart/form-data">
                        <div class="form-group">
                            <label>姓名</label>
                            <input type="text" name="customerName" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>身份证号码</label>
                            <input type="text" class="form-control" name="customerIdCard">
                        </div>
                        <div class="form-group">
                            <label>身份证正面照</label>
                            <input type="file" class="form-control" name="truePhoto">
                        </div>
                        <div class="form-group">
                            <label>身份证背面照</label>
                            <input type="file" class="form-control" name="falsePhoto">
                        </div>
                        <div class="form-group">
                            <label>顾客照片</label>
                            <input type="file" class="form-control" name="peoplePhoto">
                        </div>
                        <div class="form-group">
                            <label>手机号码</label>
                            <input type="text" class="form-control" name="customerTel">
                        </div>
                        <div class="form-group">
                            <label>住址</label>
                            <input type="text" class="form-control" name="customerAddress">
                        </div>
                        <div class="form-group">
                            <label>年龄</label>
                            <input type="text" class="form-control" name="customerAge">
                        </div>
                        <div class="form-group">
                            <label>性别</label>
                            <input type="text" class="form-control" name="customerSex">
                        </div>
                        <div class="form-group">
                            <label>年票卡号</label>
                            <select class="form-control" name="ticketId">
                                <option value="">请选择年票卡号</option>
                                <c:forEach var="ticket" items="${ticketList}">
                                    <option value="${ticket.id}">${ticket.ticketNum}</option>
                                </c:forEach>
                            </select>
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
                customerName:{
                    required:true
                },
                customerIdCard:{
                    required:true
                },
                customerIdCardPhoto : {
                    required:true
                },
                customerIdCardPhotoBack:{
                    required:true
                },
                customerPhoto:{
                    required:true
                },
                customerTel : {
                    required:true
                },
                customerAddress:{
                    required:true
                },
                customerAge:{
                    required:true
                },
                customerSex : {
                    required:true
                }
            },
            messages:{
                customerName:{
                    required:"请输入姓名"
                },
                customerIdCard:{
                    required:"请输入身份证号码"
                },
                customerIdCardPhoto : {
                    required : "请上传身份证正面照"
                },
                customerIdCardPhotoBack:{
                    required:"请上传身份证背面照"
                },
                customerPhoto : {
                    required : "请选择顾客照片"
                },
                customerTel:{
                    required:"请输入联系方式"
                },
                customerAddress : {
                    required : "请输入住址"
                },
                customerAge:{
                    required:"请输入年龄"
                },
                customerSex : {
                    required : "请输入性别"
                }
            }

        });

    });



</script>

</body>
</html>
