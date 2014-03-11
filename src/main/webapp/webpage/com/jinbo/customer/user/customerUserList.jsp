<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript" charset="utf-8">
	function courseListExportXlsByT() {
		JeecgExcelExport("customerUserController.do?exportXlsByT","customerUserList");
	}
	function courseListImportXls() {
		openuploadwin("Excel导入", "customerUserController.do?upload", "customerUserList");
	}
		function courseListExportXls() {
		JeecgExcelExport("customerUserController.do?exportXls","customerUserList");
	}

</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="customerUserList" title="客户"  actionUrl="customerUserController.do?datagrid" idField="id" fit="true" >
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="编码" field="username"  query="true" queryMode="single"></t:dgCol>
   <t:dgCol title="公司名" field="realname"  query="true" queryMode="single" width="50"> </t:dgCol>
   <t:dgCol title="传真" field="phone"  query="true" queryMode="single" width="50"></t:dgCol>
   <t:dgCol title="电话" field="tel" width="50"></t:dgCol>
    <t:dgCol title="微信账号" field="weiname"  query="true" queryMode="single" width="100"> </t:dgCol>
   <t:dgCol title="地址" field="address" width="100"></t:dgCol>
   <t:dgCol title="QQ" field="qq" width="40"></t:dgCol>
   <t:dgCol title="邮箱" field="email" width="50"></t:dgCol>
   <t:dgCol title="状态" field="status"  replace="正常_0,禁用_1,异常_2"></t:dgCol>
   <t:dgCol title="等级" field="grade" replace="普通_0,中等_1,优先_2"></t:dgCol>
   <t:dgCol title="创建时间" field="createDatetime" formatter="yyyy-MM-dd hh:mm:ss" ></t:dgCol>
   <t:dgCol title="创建人" field="createName"  ></t:dgCol>
   <t:dgCol title="修改时间" field="updateDatetime" formatter="yyyy-MM-dd hh:mm:ss" ></t:dgCol>
   <t:dgCol title="修改人" field="updateName"  ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="customerUserController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="customerUserController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="customerUserController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="customerUserController.do?addorupdate" funname="detail"></t:dgToolBar>
   	<t:dgToolBar title="Excel模板下载" icon="icon-search" onclick="courseListExportXlsByT()"></t:dgToolBar>
	<t:dgToolBar title="导入Excel" icon="icon-search" onclick="courseListImportXls()"></t:dgToolBar>
   	<t:dgToolBar title="导出Excel" icon="icon-search" onclick="courseListExportXls();"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>