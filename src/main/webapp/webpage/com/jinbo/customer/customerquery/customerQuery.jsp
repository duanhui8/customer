<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid fitColumns="false" checkbox="true" name="customerQueryController" title="客服查询" actionUrl="customerSerQueryController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="受理单号"  field="aorder"  hidden="true" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="投诉标题"  field="atitle"  hidden="true" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="投诉类型"  field="atype"  hidden="true" query="true" queryMode="single" dictionary="bugtype" width="80"></t:dgCol>
   <t:dgCol title="投诉内容"  field="acontent"  hidden="true"  queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="投诉来源"  field="ainfo"  hidden="false"  queryMode="single" dictionary="adsource" width="120"></t:dgCol>
   <t:dgCol title="客服状态"  field="aktatus"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="客服备注"  field="anotes"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="进度"  field="astatus"  hidden="true" query="true" queryMode="single" dictionary="a_status" width="70"></t:dgCol>
   <t:dgCol title="投诉时间"  field="createDatetime" formatter="yyyy-MM-dd hh:mm:ss" hidden="true" query="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="投诉人"  field="createName"  hidden="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="客户账号"  field="azhtype"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="公司名"  field="arealname"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="受理时间"  field="slDatetime"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="受理人"  field="slName"  hidden="false"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="部门处理时间"  field="deDatetime" formatter="yyyy-MM-dd hh:mm:ss" hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="处理人"  field="deName"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="反馈时间"  field="comDatetime"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="反馈人"  field="comName"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="附件"  field="afile"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="电话"  field="atel"  hidden="false"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="投诉部门"  field="aadept"  hidden="false"  queryMode="single" dictionary="advicedept" width="120"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="customerQueryControllerController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="customerQueryControllerController.do?goAdd" funname="add" height="450"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="customerQueryControllerController.do?goUpdate" funname="update" height="450"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="customerQueryControllerController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="customerSerQueryController.do?goUpdate" funname="detail" height="450"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/jinbo/customer/customerquery/customerQueryList.js"></script>		
 <script type="text/javascript">
  $(document).ready(function(){
 		//给时间控件加上样式
 			$("#customerQueryControllerListtb").find("input[name='createDatetime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#customerQueryControllerListtb").find("input[name='createDatetime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#customerQueryControllerListtb").find("input[name='deDatetime']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 </script>