<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2017/11/7
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 左侧菜单栏 -->
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- 菜单 -->
        <ul class="sidebar-menu">
            <li class="header">系统功能</li>
            <!-- 客户管理 -->
            <li class="treeview ${fn:startsWith(param.menu,'customer_') ? 'active' : ''}">
                <a href="#">
                    <i class="fa fa-address-book-o"></i> <span>景区工作</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class=""><a href="/checkTicket/check"><i class="fa fa-circle-o"></i> 刷卡信息校验</a></li>
                    <li class=""><a href="/checkTicket/config"><i class="fa fa-circle-o"></i> 参数配置</a></li>
                </ul>
            </li>

        </ul>
    </section>
    <!-- /.sidebar -->
</aside>

<!-- =============================================== -->
