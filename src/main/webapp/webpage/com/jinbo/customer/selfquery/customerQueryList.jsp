<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid fitColumns="false" checkbox="true"
			name="customerQueryList" title="查询"
			actionUrl="customerQueryController.do?datagrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="主键" field="id" hidden="false" queryMode="single"
				width="120"></t:dgCol>
			<t:dgCol title="受理单号" field="aorder" hidden="true" query="true"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="投诉标题" field="atitle" hidden="true" query="true"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="投诉类型" field="atype" hidden="true" query="true"
				queryMode="single" dictionary="bugtype" width="80"></t:dgCol>
			<t:dgCol title="投诉内容" field="acontent" hidden="true"
				queryMode="single" width="200"></t:dgCol>
			<t:dgCol title="投诉来源" field="ainfo" hidden="false" queryMode="single"
				dictionary="adsource" width="120"></t:dgCol>
			<t:dgCol title="客服状态" field="aktatus" hidden="false"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="客服备注" field="anotes" hidden="false"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="进度" field="astatus" hidden="true" query="false"
				queryMode="single" dictionary="a_status" width="70"></t:dgCol>
			<t:dgCol title="投诉时间" field="createDatetime"
				formatter="yyyy-MM-dd hh:mm:ss" hidden="true" query="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="投诉人" field="createName" hidden="false"
				queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="客户账号" field="azhtype" hidden="false"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="公司名" field="arealname" hidden="false"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="受理时间" field="slDatetime" hidden="false"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="受理人" field="slName" hidden="false" queryMode="single"
				width="80"></t:dgCol>
			<t:dgCol title="部门处理时间" field="deDatetime"
				formatter="yyyy-MM-dd hh:mm:ss" hidden="false" queryMode="single"
				width="120"></t:dgCol>
			<t:dgCol title="处理人" field="deName" hidden="false" queryMode="single"
				width="120"></t:dgCol>
			<t:dgCol title="反馈时间" field="comDatetime" hidden="true"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="反馈人" field="comName" hidden="false"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="附件" field="afile" hidden="false" queryMode="single"
				width="120"></t:dgCol>
			<t:dgCol title="电话" field="atel" hidden="false" queryMode="single"
				width="80"></t:dgCol>
			<t:dgCol title="投诉部门" field="aadept" hidden="false"
				queryMode="single" dictionary="advicedept" width="120"></t:dgCol>
			<t:dgToolBar title="评价与查看" icon="icon-search"
				url="customerQueryController.do?goUpdate" funname="detailst"
				height="450"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>

<script src="webpage/com/jinbo/customer/selfquery/customerQueryList.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				//给时间控件加上样式
				$("#customerQueryListtb").find(
						"input[name='createDatetime_begin']").attr("class",
						"Wdate").attr("style", "height:20px;width:90px;")
						.click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd'
							});
						});
				$("#customerQueryListtb").find(
						"input[name='createDatetime_end']").attr("class",
						"Wdate").attr("style", "height:20px;width:90px;")
						.click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd'
							});
						});
				$("#customerQueryListtb").find("input[name='deDatetime']")
						.attr("class", "Wdate").attr("style",
								"height:20px;width:90px;").click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd'
							});
						});
			});

	function detailst(title, url, id, width, height) {
		var rowsData = $('#' + id).datagrid('getSelections');
		//	if (rowData.id == '') {
		//		tip('请选择查看项目');
		//		return;
		//	}

		if (!rowsData || rowsData.length == 0) {
			tip('请选择查看项目');
			return;
		}
		if (rowsData.length > 1) {
			tip('请选择一条记录再查看');
			return;
		}
		url += '&load=detail&id=' + rowsData[0].id;
		createdetailwindowtty(title, url, width, height, rowsData[0].id);
	}

	function createdetailwindowtty(title, addurl, width, height, id) {
		width = width ? width : 700;
		height = height ? height : 400;
		if (width == "100%" || height == "100%") {
			width = document.body.offsetWidth;
			height = document.body.offsetHeight - 100;
		}
		if (typeof (windowapi) == 'undefined') {
			$.dialog({
				content : 'url:' + addurl,
				button : [ {
					name : '确定',
					callback : function() {
						queryConfirmDD(id);

					}
				} ],
				lock : true,
				width : width,
				height : height,
				title : title,
				opacity : 0.3,
				cache : false
			});
		} else {
			W.$.dialog({
				content : 'url:' + addurl,
				lock : true,
				width : width,
				height : height,
				parent : windowapi,
				title : title,
				opacity : 0.3,
				cache : false,
				cancelVal : '关闭',
				cancel : true
			/*为true等价于function(){}*/
			});
		}

	}

	function queryConfirmDD(id) {
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : "customerQueryController.do?doQueryPing&id=" + id,// 请求的action路径
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var msg = d.msg;
					if (msg == "1") {
						tip("你还未评价，请先进行评价！");
						evaluatewwf(id);
					}
					/* tip(msg);
					reloadTable(); */
				}
			}
		});
	}
	function evaluatewwf(id) {
		$
				.dialog({
					title : "评价",
					content: "<form id='pjtb' action='customerQueryController.do?doSaveEval' method='post'> 客服评价:<input type='radio' name='kefupj' value='0' />非常满意&nbsp<input type='radio' name='kefupj' value='1' />满意&nbsp<input type='radio' name='kefupj' value='1' />一般<input type='radio' name='kefupj' value='3' />较差<input type='radio' name='kefupj' value='4' />很差<br> <br> 结果评价:<input type='radio' name='deptpj' value='0' />非常满意&nbsp<input type='radio' name='deptpj' value='1' />满意&nbsp<input type='radio' name='deptpj' value='2' />一般<input type='radio' name='deptpj' value='3' />差评<input type='radio' name='deptpj' value='4' />很差<br> <br>评论:<br> <textarea cols='40' rows='5' name='note'></textarea><input type='hidden' name='advice_id' value='"+id+"'/></form>",
					ok : function() {			
					console.log($('#pjtb'));	
						$('#pjtb').form('submit', {
							url : "customerQueryController.do?doSaveEval",
							onSubmit : function() {
				
							},
							success : function(r) {
								tip('操作成功');
							}
						});
					}

				});
	}
</script>