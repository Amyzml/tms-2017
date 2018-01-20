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
    <title>TMS-售票站点详情</title>
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
                    <h3 class="box-title">售票点信息</h3>
                    <c:if test="${not empty message}">
                        <div class="btn btn-primary btn-sm">${message}</div>
                    </c:if>

                    <div class="box-tools">
                        <a href="/ticketStore/message" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回售票点列表</a>
                        <%--<a href="/ticketStore/edit/${ticketStore.id}" class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 编辑</a>--%>
                        <a href="/ticketStore/newAccount/${ticketStore.id}" class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 开通售票点账号</a>


                        <%--<button id="btnDel" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>--%>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <tr>
                            <td class="td_title">站点名称</td>
                            <td>${ticketStore.storeName}</td>
                            <td class="td_title">站点地址</td>
                            <td>${ticketStore.storeAddress}</td>
                            <td class="td_title">联系电话</td>
                            <td>${ticketStore.storeTel}</td>
                        </tr>
                        <tr>
                            <td class="td_title">经度</td>
                            <td>${ticketStore.storeGeoLongitude}</td>
                            <td class="td_title">纬度</td>
                            <td>${ticketStore.storeLatitude}</td>
                            <td class="td_title">负责人</td>
                            <td>${ticketStore.storeManager}</td>
                        </tr>
                    </table>
                    <div class="img">
                        <div class="col-md-2">
                            <img src="http://
ozp5bpxi8.bkt.clouddn.com/${ticketStore.storeAttachment}?imageView2/1/w/200/h/200" alt="">
                        </div>
                        <div class="col-md-2">
                            <img src="http://
ozp5bpxi8.bkt.clouddn.com/${ticketStore.storeManagerAttachment}?imageView2/1/w/200/h/200" alt="">
                        </div>
                    </div>
                </div>
                <div class="box-footer">
                    <span style="color: #ccc" class="pull-right">创建日期：<fmt:formatDate value="${ticketStore.createTime}" pattern="YYYY年MM月dd日"/> &nbsp;&nbsp;&nbsp;&nbsp;
                        最后修改日期：<fmt:formatDate value="${ticketStore.updateTime}" pattern="YYYY年MM月dd日"/> </span>
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
