<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>客服</title>
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="customerSerController.do?doAdd">
					<input id="id" name="id" type="hidden" value="${customerSerPage.id }">
					<input id="aorder" name="aorder" type="hidden" value="${customerSerPage.aorder }">
					<input id="ainfo" name="ainfo" type="hidden" value="${customerSerPage.ainfo }">
					<input id="aktatus" name="aktatus" type="hidden" value="${customerSerPage.aktatus }">
					<input id="anotes" name="anotes" type="hidden" value="${customerSerPage.anotes }">
					<input id="astatus" name="astatus" type="hidden" value="${customerSerPage.astatus }">
					<input id="createDatetime" name="createDatetime" type="hidden" value="${customerSerPage.createDatetime }">
					<input id="createName" name="createName" type="hidden" value="${customerSerPage.createName }">
					<input id="azhtype" name="azhtype" type="hidden" value="${customerSerPage.azhtype }">
					<input id="arealname" name="arealname" type="hidden" value="${customerSerPage.arealname }">
					<input id="slDatetime" name="slDatetime" type="hidden" value="${customerSerPage.slDatetime }">
					<input id="slName" name="slName" type="hidden" value="${customerSerPage.slName }">
					<input id="deDatetime" name="deDatetime" type="hidden" value="${customerSerPage.deDatetime }">
					<input id="deName" name="deName" type="hidden" value="${customerSerPage.deName }">
					<input id="comDatetime" name="comDatetime" type="hidden" value="${customerSerPage.comDatetime }">
					<input id="comName" name="comName" type="hidden" value="${customerSerPage.comName }">
					<input id="atel" name="atel" type="hidden" value="${customerSerPage.atel }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">投诉标题:</label>
			</td>
			<td class="value">
		     	 <input id="atitle" name="atitle" type="text" style="width: 150px" class="inputxt"  
					                value='${customerSerPage.atitle}'
					               >
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">投诉标题</label>
			</td>
			<td align="right">
				<label class="Validform_label">投诉类型:</label>
			</td>
			<td class="value">
					<t:dictSelect field="atype" type="list"
						typeGroupCode="bugtype"  hasLabel="false"  defaultVal="${customerSerPage.atype}"  title="投诉类型"></t:dictSelect>     
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">投诉类型</label>
			</td>
		</tr>
		<tr>
	
				<td align="right">
				<label class="Validform_label">投诉来源:</label>
			</td>
			<td class="value">
					<t:dictSelect field="ainfo" type="list" divClass="backgroud:red"
						typeGroupCode="adsource" defaultVal="3" hasLabel="false"  title="投诉来源" >
						</t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">投诉部门</label>
			</td>
			<td align="right">
				<label class="Validform_label">附件:</label>
			</td>
        	<td class="value">
						<input type="hidden" id="afile" name="afile" />
						<c:if test="${customerSerPage.afile==''}">
							<a   target="_blank" id="afile_href">暂时未上传文件</a>
						</c:if>
						<c:if test="${customerSerPage.afile!=''}">
							<a href="${customerSerPage.afile}"  target="_blank" id="afile_href">下载</a>
						</c:if>
					   <input class="ui-button" type="button" value="上传附件"
								disabled="disabled"	onclick="browseFiles('afile','afile_href')"/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">附件</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">投诉公司:</label>
			</td>
			<td class="value">
					<%-- <%-- <t:dictSelect field="aadept" type="list"
						typeGroupCode="advicedept"  hasLabel="false"  title="投诉部门"></t:dictSelect>      --%> 
							<t:dictSelect field="deptid" type="list"
						 hasLabel="false" dictTable="t_s_depart" dictText="departname" dictField="id" title="投诉部门" zisql="source is not null"></t:dictSelect> 
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">投诉</label>
			</td>
			<td align="right">
				<label class="Validform_label">投诉部门:</label>
			</td>
			<td class="value">
					<t:dictSelect field="aadept" type="list"
						 hasLabel="false" dictTable="t_s_depart" dictText="departname" dictField="id" title="投诉部门" zisql="source is null"></t:dictSelect>   
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">投诉部门</label>
			</td>
		</tr>
		<tr>
				<td align="right">
				<label class="Validform_label">投诉内容:</label>
			</td>
			<td class="value" colspan="3">
				 <textarea id="acontent" name="acontent" style="width:500px;height:150px">${customerSerPage.acontent}</textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">投诉内容</label>
			</td>
		</tr>
	</table>
			</t:formvalid>
			<!-- 添加 附表明细 模版 -->
	
 </body>
 <script src = "webpage/com/jinbo/customer/customerservice/customerSer.js"></script>	
 <script  type="text/javascript">
 $(function(){
  $("select[name='ainfo']").attr("disabled","disabled");
 })
 </script>