<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>客户</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="customerUserController.do?save">
			<input id="id" name="id" type="hidden" value="${customerUserPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							* 公司编码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="username" name="username" 
							   value="${customerUserPage.username}" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							* 公司名:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="realname" name="realname" 
							   value="${customerUserPage.realname}" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			
				<tr>
					<td align="right">
						<label class="Validform_label">
							状态:
						</label>
					</td>
					<td class="value">
				<%-- 		<input class="inputxt" id="status" name="status"  dictTable=""
							   value="${customerUserPage.status}" datatype="n"/> --%>
							   <t:dictSelect field="status" type="list"
								typeGroupCode="userStatus" defaultVal="${customerUserPage.status}" hasLabel="false"  title="状态"></t:dictSelect>     
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							传真:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="phone" name="phone" ignore="ignore"
							   value="${customerUserPage.phone}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							微信账号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="weiname" name="weiname"  ignore="ignore"
							   value="${customerUserPage.weiname}" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							电话:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="tel" name="tel" ignore="ignore"
							   value="${customerUserPage.tel}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							地址:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="address" name="address" ignore="ignore"
							   value="${customerUserPage.address}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							QQ:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="qq" name="qq" ignore="ignore"
							   value="${customerUserPage.qq}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							邮箱:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="email" name="email" ignore="ignore"
							   value="${customerUserPage.email}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							等级:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="grade" type="list"
								typeGroupCode="grade" defaultVal="${customerUserPage.grade}" hasLabel="false"  title="等级"></t:dictSelect>     
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				
			</table>
		</t:formvalid>
 </body>