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
    <title>TMS-景区详情</title>
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
                    <h3 class="box-title">景区信息</h3>
                    <c:if test="${not empty message}">
                        <div class="btn btn-primary btn-sm">${message}</div>
                    </c:if>

                    <div class="box-tools">
                        <a href="/scenic/message" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回景区列表</a>
                        <a href="/scenic/edit/${scenic.id}" class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 编辑</a>
                        <a href="/scenic/newAccount/${scenic.id}" class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 开通景区账号</a>
                        <%--<button id="btnDel" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>--%>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <tr>
                            <td class="td_title">景区名</td>
                            <td>${scenic.scenicName}</td>
                            <td class="td_title">景区星级</td>
                            <td>${scenic.scenicLevel}</td>
                            <td class="td_title">联系电话</td>
                            <td>${scenic.scenicTel}</td>
                        </tr>
                        <tr>
                            <td class="td_title">经度</td>
                            <td>${scenic.scenicGeoLongitude}</td>
                            <td class="td_title">纬度</td>
                            <td>${scenic.scenicGeoLatitude}</td>
                            <td class="td_title">负责人</td>
                            <td>${scenic.scenicManager}</td>
                        </tr>
                        <tr>
                            <td class="td_title">景区简介</td>
                            <td>${scenic.scenicIntro}</td>
                        </tr>
                    </table>
                </div>
                <div class="box-footer">
                    <span style="color: #ccc" class="pull-right">创建日期：<fmt:formatDate value="${scenic.createTime}" pattern="YYYY年MM月dd日"/> &nbsp;&nbsp;&nbsp;&nbsp;
                        最后修改日期：<fmt:formatDate value="${scenic.updateTime}" pattern="YYYY年MM月dd日"/> </span>
                </div>
            </div>




        </section>

        <div class="col-md-2">
            <img src="http://
ozp5bpxi8.bkt.clouddn.com/${scenic.scenicAttachment}?imageView2/1/w/200/h/200" alt="">
        </div>
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

</body>
</html>
