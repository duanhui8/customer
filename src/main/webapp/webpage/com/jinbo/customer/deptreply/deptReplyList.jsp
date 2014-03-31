<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>回复</title>
<t:base type="ckfinder,ckeditor,jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" type="text/css" href="plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="deptSerController.do?doUpdate">
	<table style="width: 870px; height: 300px;" cellpadding="0" cellspacing="1" class="formtable">
<input id="id" name="id" type="hidden" value="${deptSerPage.id }">
		<tr>
			<td align="right"><label class="Validform_label">回复: </label></td>
			<td class="value"><t:ckeditor name="acontent" isfinder="true" value="${deptreply!=null?deptreply.acontent:'' }" type="width:600"></t:ckeditor></td>
		</tr>
	</table>
</t:formvalid>

</body>