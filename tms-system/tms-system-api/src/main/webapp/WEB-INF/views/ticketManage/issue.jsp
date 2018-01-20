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
    <title>TMS-年票管理</title>
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
                    <h3 class="box-title">年票下发</h3>
                </div>

                <c:if test="${not empty message}">
                    <div class="form-group btn-info">${message}</div>
                </c:if>

                <div class="box-body">
                    <form method="get" action="/ticket/issue/search" id="addForm">

                        <div class="form-group">
                            <label>请输入下发站点</label>
                            <input type="text"  name="storeName" class="form-control">
                        </div>
                        <div class="box-footer">
                            <button class="btn btn-primary" id="addNewCustomer">搜索</button>
                        </div>
                    </form>
                </div>



                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <tbody>
                        </tbody>
                    </table>
                </div>

                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <tbody>
                        <tr>
                            <th width="80"></th>
                            <th>售票站点一览表</th>
                        </tr>
                        <c:forEach items="${ticketStoreList}" var="ticketStore">
                            <tr class="dataRow">
                                <td width="80"></td>
                                <td>${ticketStore.storeName}</td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
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
<script src="/static/plugins/layer/layer.js"></script>
<script>

  /*  $("#addNewCustomer").click(function () {
        $("#addForm").submit();
    });*/

  /*  $(function () {

        $("#addNewCustomer").click(function () {

            layer.confirm("确定要作废该年票吗?",function (index) {
                $.post("/ticket/cancellation",$("#addForm").serialize()).done(function (json) {
                    if(json.state == "success"){
                        layer.msg("作废成功");
                    }else {
                        layer.msg(json.message)
                    }
                }).error(function () {
                    layer.msg("服务器异常");
                });
            })
        });

    });*/



</script>

</body>
</html>
