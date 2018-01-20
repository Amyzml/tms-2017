<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2017/12/18
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>TMS-账户详情</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@include file="../include/css.jsp"%>
    <style>
        .td_title {
            font-weight: bold;
        }
    </style>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
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

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">账户资料</h3>
                    <c:if test="${not empty message}">
                        <div class="btn btn-primary btn-sm">${message}</div>
                    </c:if>

                    <div class="box-tools">
                        <a href="/account/manager" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                        <a href="/account/edit/${account.id}" class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 编辑</a>

                        <%--<button id="publicBtn" class="btn bg-maroon btn-sm"><i class="fa fa-recycle"></i> 放入公海</button>--%>
                        <button id="btnDel" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table">
                        <tr>
                            <td class="td_title">账户名</td>
                            <td>${account.accountName}</td>
                            <td class="td_title">联系电话</td>
                            <td>${account.accountMobile}</td>
                        </tr>
                        <tr>
                            <td class="td_title">权限列表</td>
                            <c:forEach items="${roleList}" var="role">
                                <td>${role.roleName}</td>
                            </c:forEach>
                        </tr>

                    </table>
                </div>
                <div class="box-footer">
                    <span style="color: #ccc" class="pull-right">创建日期：<fmt:formatDate value="${account.createTime}" pattern="YYYY年MM月dd日"/> &nbsp;&nbsp;&nbsp;&nbsp;
                        最后修改日期：<fmt:formatDate value="${account.updateTime}" pattern="YYYY年MM月dd日"/> </span>
                </div>
            </div>


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
<script src="/static/plugins/layer/layer.js"></script>
<script>

    $(function () {

        var customerId = ${customer.id};
        $("#publicBtn").click(function () {
            layer.confirm("确定要放入公海吗?",function (index) {
                layer.close(index);
                window.location.href="/customer/my/" + customerId + "/public";
            })
        });

        $("#btnDel").click(function () {
            layer.confirm("确定要删除吗?",function (index) {
                layer.close(index);
                $.get("/customer/delete/"+customerId).done(function (res) {
                    if(res.state == 'success'){
                        layer.msg("删除成功");
                        window.location.href="/mycustomer";
                    }else{
                        layer.msg(res.message);
                    }
                }).error(function () {
                    layer.msg("服务器异常,请稍后再试");
                });
            })
        });

        $("#changeToOther").click(function () {
            layer.confirm("确定要转交他人么?",function (index) {
                layer.close(index);
                $.get().done(function () {

                }).error(function () {

                });
            })
        });
    });

</script>
</body>
</html>
