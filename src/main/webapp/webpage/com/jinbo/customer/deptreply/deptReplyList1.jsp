<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>

<body>

		<t:ckeditor name="remark" isfinder="true" value="${jeecgDemoCkfinderPage.remark}" type="width:500"></t:ckeditor>


</body>