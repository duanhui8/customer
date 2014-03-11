<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>客户投诉</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');
  });
 </script>
 </head>
 <body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="customerAdviceController.do?doAdd">
					<input id="id" name="id" type="hidden" value="${customerAdvicePage.id }">
					<input id="aorder" name="aorder" type="hidden" value="${customerAdvicePage.aorder }">
					<input id="ainfo" name="ainfo" type="hidden" value="${customerAdvicePage.ainfo }">
					<input id="aktatus" name="aktatus" type="hidden" value="${customerAdvicePage.aktatus }">
					<input id="anotes" name="anotes" type="hidden" value="${customerAdvicePage.anotes }">
					<input id="astatus" name="astatus" type="hidden" value="${customerAdvicePage.astatus }">
					<input id="createDatetime" name="createDatetime" type="hidden" value="${customerAdvicePage.createDatetime }">
					<input id="createName" name="createName" type="hidden" value="${customerAdvicePage.createName }">
					<input id="azhtype" name="azhtype" type="hidden" value="${customerAdvicePage.azhtype }">
					<input id="arealname" name="arealname" type="hidden" value="${customerAdvicePage.arealname }">
					<input id="slDatetime" name="slDatetime" type="hidden" value="${customerAdvicePage.slDatetime }">
					<input id="slName" name="slName" type="hidden" value="${customerAdvicePage.slName }">
					<input id="deDatetime" name="deDatetime" type="hidden" value="${customerAdvicePage.deDatetime }">
					<input id="deName" name="deName" type="hidden" value="${customerAdvicePage.deName }">
					<input id="comDatetime" name="comDatetime" type="hidden" value="${customerAdvicePage.comDatetime }">
					<input id="comName" name="comName" type="hidden" value="${customerAdvicePage.comName }">
					<input id="atel" name="atel" type="hidden" value="${customerAdvicePage.atel }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">投诉标题:</label>
			</td>
			<td class="value">
		     	 <input id="atitle" name="atitle" type="text" style="width: 150px" class="inputxt"  
					               
					               >
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">投诉标题</label>
			</td>
			<td align="right">
				<label class="Validform_label">投诉类型:</label>
			</td>
			<td class="value">
					<t:dictSelect field="atype" type="list"
						typeGroupCode="bugtype"  hasLabel="false"  title="投诉类型"></t:dictSelect>     
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">投诉类型</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">投诉部门:</label>
			</td>
			<td class="value">
					<t:dictSelect field="aadept" type="list"
						typeGroupCode="advicedept"  hasLabel="false"  title="投诉部门"></t:dictSelect>     
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">投诉部门</label>
			</td>
			<td align="right">
				<label class="Validform_label">附件:</label>
			</td>
			<td class="value">
						<input type="hidden" id="afile" name="afile" />
						<a  target="_blank" id="afile_href">暂时未上传文件</a>
					   <input class="ui-button" type="button" value="上传附件"
									onclick="browseFiles('afile','afile_href')"/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">附件</label>
			</td>
		</tr>
		<tr >
			<td align="right" >
				<label class="Validform_label">投诉内容:</label>
			</td>
			<td class="value" colspan="3">
				 <textarea id="acontent" name="acontent" style="width:500px;height:150px"></textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">投诉内容</label>
			</td>
		</tr>
	</table>
			</t:formvalid>
 </body>
 <script src = "webpage/com/jinbo/customer/advice/customerAdvice.js"></script>	