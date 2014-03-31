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
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="customerSerController.do?doReturnResult">
	<table  cellpadding="0" cellspacing="1" class="formtable">
             <input type="hidden" name="id" value="${customerSerPage.id}"/>
		<tr>
			<td align="right"><label class="Validform_label">回复: </label></td>
			<td class="value">	<t:dictSelect field="returnS" type="list" divClass="backgroud:red"
						typeGroupCode="returns" defaultVal="${customerSerPage.ainfo}" hasLabel="false"  title="投诉部门">
						</t:dictSelect>   </td>
		</tr>
			<tr>
		<!-- 	<td align="right"><label class="Validform_label">客服备注: </label></td>
			<td class="value">	
		         <input/>
			   </td> -->
		</tr>
	</table>
</t:formvalid>

</body>