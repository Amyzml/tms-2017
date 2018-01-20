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
                <div class="form-group">
                    <label>当前库存:${canUseNum}</label>
                    <label>当前有效年票的起始卡号:${minTicketNum}</label>

                </div>
                <div class="box-body">
                    <form method="post" id="addForm">
                        <div>
                            <label>请输入下发年票数量:</label>
                            <input type="text" id="input" name="ticketNumber">
                            <input type="text" name="minCanUseCardNum" value="${minTicketNum}">
                        </div>

                    </form>

                        <div class="box-body no-padding">
                            <c:forEach items="${ticketStoreList}" var="ticketStorage">
                            <table class="table table-hover">
                                <tr class="dataRow" rel="${ticketStorage.id}">
                                    <td>站点名称:</td>
                                    <td>${ticketStorage.storeName}</td>
                                    <td>站点地址:</td>
                                    <td>${ticketStorage.storeAddress}</td>
                                    <td>联系电话:</td>
                                    <td>${ticketStorage.storeTel}</td>
                                    <td class="td_title">负责人:</td>
                                    <td>${ticketStorage.storeManager}</td>
                                </tr>
                            </table>
                            </c:forEach>
                        </div>

                </div>



                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <tbody>
                        <tr>
                            <th width="80"></th>
                            <th>已经作废的卡号</th>
                        </tr>
                        <c:forEach items="${ticketList}" var="ticket">
                            <tr class="dataRow">
                                <td width="80"></td>
                                <td>${ticket.ticketNum}</td>
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

    $(function() {


        $(".dataRow").click(function () {
            var id = $(this).attr("rel");

            var ticketNum = $("#input").val;
            //window.location.href="/scenic/detail/" + id;
            layer.confirm("确定下发吗?",function (index) {
                $.post("/ticket/confirmIssue/" + id, $("#addForm").serialize()).done(function (json) {
                    if(json.state == "success"){
                        layer.msg("年票下发成功")
                    }else{
                        layer.msg("年票下发失败");
                    }
                }).error(function () {
                    layer.msg("服务器异常");
                });

            })
        });

    });

   /* $("#addNewCustomer").click(function () {
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
