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
            <!-- 基本信息管理 -->
            <li class="treeview ${fn:startsWith(param.menu,'customer_') ? 'active' : ''}">
                <a href="/ticket/store/transaction">
                    <i class="fa fa-address-book-o"></i> <span>年票办理</span>
                    <span class="pull-right-container">
                     </span>
                </a>
            </li>

            <li class="treeview ${fn:startsWith(param.menu,'customer_') ? 'active' : ''}">
                <a href="/ticket/store/renew">
                    <i class="fa fa-address-book-o"></i> <span>年票续费</span>
                    <span class="pull-right-container">
                    </span>
                </a>
            </li>
            <!-- 工作记录 -->
            <li class="treeview ${fn:startsWith(param.menu, 'saleChance_') ? 'active' : ''}">
                <a href="/ticket/store/sayLoss">
                    <i class="fa fa-bars"></i> <span>年票挂失</span>
                    <span class="pull-right-container">
                    </span>
                </a>
            </li>
            <!-- 待办事项 -->
            <li class="treeview">
                <a href="/ticket/store/sayFind">
                    <i class="fa fa-calendar"></i> <span>年票解挂</span>
                    <span class="pull-right-container">
                    </span>
                </a>
            </li>

            <li class="treeview">
                <a href="/ticket/store/reapply">
                    <i class="fa fa-calendar"></i> <span>年票补办</span>
                    <span class="pull-right-container">
                    </span>
                </a>
            </li>
            <li class="treeview">
                <a href="/ticket/store/replace">
                    <i class="fa fa-calendar"></i> <span>年票更换</span>
                    <span class="pull-right-container">
                    </span>
                </a>
            </li>
            <li class="treeview">
                <a href="/ticket/store/server">
                    <i class="fa fa-calendar"></i> <span>办理统计</span>
                    <span class="pull-right-container">
                    </span>
                </a>
            </li>

        </ul>
    </section>
    <!-- /.sidebar -->
</aside>

<!-- =============================================== -->
