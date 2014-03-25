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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="customerSerController.do?doUpdate">
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
		     	 <input id="atitle" name="atitle" disabled="disabled" type="text" style="width: 150px" class="inputxt"  
					               
					                value='${customerSerPage.atitle}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">投诉标题</label>
			</td>
			<td align="right">
				<label class="Validform_label">投诉类型:</label>
			</td>
			<td class="value">
					<t:dictSelect field="atype" type="list"
						typeGroupCode="bugtype" defaultVal="${customerSerPage.atype}" hasLabel="false"  title="投诉类型"></t:dictSelect>     
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">投诉类型</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">投诉内容:</label>
			</td>
			<td class="value">
				 <textarea id="acontent" name="acontent" disabled="disabled">${customerSerPage.acontent}</textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">投诉内容</label>
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
				<label class="Validform_label">投诉部门:</label>
			</td>
			<td class="value">
					<%-- <t:dictSelect field="aadept" type="list"
						typeGroupCode="advicedept" defaultVal="${customerSerPage.aadept}" hasLabel="false"  title="投诉部门"></t:dictSelect>--%>
						<t:dictSelect field="aadept" type="list"
						 hasLabel="false" dictTable="t_s_depart" dictText="departname" dictField="id" title="投诉部门" zisql="source is null"></t:dictSelect>   
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">投诉部门</label>
			</td>
		</tr>
			</table>
			<div style="width: auto;height: 200px;">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:800px;height:1px;"></div>
				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="customerSerController.do?adviceReplyList&aorder=${customerSerPage.aorder}" icon="icon-search" title="下发投诉" id="adviceReply"></t:tab>
				</t:tabs>
			</div>
			</t:formvalid>
			<!-- 添加 附表明细 模版 -->
		<table style="display:none">
		<tbody id="add_adviceReply_table_template">
			<tr>
			 <td align="center"><div style="width: 25px;" name="xh"></div></td>
			 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
				  <td align="left">
					       	<input name="adviceReplyList[#index#].acontent" maxlength="500" 
						  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">处理结果</label>
				  </td>
			</tr>
		 </tbody>
		</table>
 </body>
 <script src = "webpage/com/jinbo/customer/customerservice/customerSer.js"></script>
  <script type="text/javascript">
	var tousu = $("select[name='atype']");
	tousu.attr("disabled","disabled");
	var tousu = $("select[name='aadept']");
	tousu.attr("disabled","disabled");
 </script>	