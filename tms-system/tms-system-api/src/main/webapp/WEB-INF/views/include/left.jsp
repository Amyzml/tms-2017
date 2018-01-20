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

        <!-- 搜索表单，不需要删除即可 -->
        <!--<form action="#" method="get" class="sidebar-form">
          <div class="input-group">
            <input type="text" name="q" class="form-control" placeholder="Search...">
                <span class="input-group-btn">
                  <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                  </button>
                </span>
          </div>
        </form>-->
        <!-- /.search form -->
        <!-- 菜单 -->
        <ul class="sidebar-menu">
            <li class="header">系统功能</li>
            <!-- 基本信息管理 -->
            <li class="treeview ${fn:startsWith(param.menu,'customer_') ? 'active' : ''}">
                <a href="#">
                    <i class="fa fa-address-book-o"></i> <span>基本信息管理</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class=""><a href="/scenic/message"><i class="fa fa-circle-o"></i> 景区信息</a></li>
                    <li class=""><a href="/ticketStore/message"><i class="fa fa-circle-o"></i> 售票点信息</a></li>
                    <li class=""><a href="#"><i class="fa fa-circle-o"></i> 特约消费点信息</a></li>
                    <li class=""><a href="#"><i class="fa fa-circle-o"></i> 分类统计信息</a></li>
                </ul>
            </li>

            <li class="treeview ${fn:startsWith(param.menu,'customer_') ? 'active' : ''}">
                <a href="#">
                    <i class="fa fa-address-book-o"></i> <span>年票库存管理</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class=""><a href="/ticket/putToStorage"><i class="fa fa-circle-o"></i> 年票入库</a></li>
                    <li class=""><a href="/ticket/issue"><i class="fa fa-circle-o"></i>年票下发</a></li>
                    <li class=""><a href="/ticket/cancellation"><i class="fa fa-circle-o"></i> 年票作废</a></li>
                    <li class=""><a href="#"><i class="fa fa-circle-o"></i> 盘点统计</a></li>
                </ul>
            </li>
            <!-- 工作记录 -->
            <li class="treeview ${fn:startsWith(param.menu, 'saleChance_') ? 'active' : ''}">
                <a href="#">
                    <i class="fa fa-bars"></i> <span>综合办公</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class=""><a href="/saleChance/my"  ><i class="fa fa-circle-o"></i>电子公告</a></li>
                    <li><a href="/saleChance/public"><i class="fa fa-circle-o"></i> 消息中心</a></li>
                    <li class=""><a href="/saleChance/my"  ><i class="fa fa-circle-o"></i>规章制度</a></li>
                    <li><a href="/saleChance/public"><i class="fa fa-circle-o"></i> 待办工作</a></li>
                </ul>
            </li>
            <!-- 待办事项 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-calendar"></i> <span>领导管理驾驶舱</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="/leader/ticket/message"><i class="fa fa-circle-o"></i> 年票信息查询</a></li>
                    <li><a href="/leader/store/message"><i class="fa fa-circle-o"></i> 售票点业务查询</a></li>
                    <li><a href="/leader/scenic/message"><i class="fa fa-circle-o"></i> 景区验票查询</a></li>
                    <li><a href="/leader/consumer/message"><i class="fa fa-circle-o"></i> 特约消费点查询</a></li>
                    <li><a href="/leader/total/select"><i class="fa fa-circle-o"></i> 分类统计查询</a></li>
                </ul>
            </li>

            <li class="header">系统管理</li>

            <li class="${param.menu == 'user' ? 'active' : ''}"><a href="/user/list"><i class="fa fa-users"></i> <span>系统管理</span></a>
                <ul class="treeview-menu">
                    <li><a href="/account/manager"><i class="fa fa-circle-o"></i> 用户管理</a></li>
                    <li><a href="../../index2.html"><i class="fa fa-circle-o"></i> 权限设置</a></li>
                    <li><a href="../../index2.html"><i class="fa fa-circle-o"></i> 日志管理</a></li>
                    <li><a href="../../index2.html"><i class="fa fa-circle-o"></i> 系统备份</a></li>
                </ul>
            </li>

        </ul>
    </section>
    <!-- /.sidebar -->
</aside>

<!-- =============================================== -->
