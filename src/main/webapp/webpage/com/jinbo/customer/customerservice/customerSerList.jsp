<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <script type="text/javascript">
	function previews(id) {
	            id = id.replace(/%/g,"|");
				var url = 'customerSerController.do?preview&afile=' + id;
				
				createwindow('预览', url, 830, 400);
			 
			}
	function downFile(path){
	window.open(path);
	}		
</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid fitColumns="false" checkbox="true" name="customerSerList" title="客服" actionUrl="customerSerController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="受理单号"  field="aorder"  hidden="true" query="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="投诉标题"  field="atitle"  hidden="true" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="投诉类型"  field="atype"  hidden="true" query="true" queryMode="single" dictionary="bugtype" width="80"></t:dgCol>
   <t:dgCol title="投诉内容"  field="acontent"  hidden="true"  queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="投诉部门"  field="aadept"  dictionary="t_s_depart,ID,departname"  hidden="true"  queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="进度"  field="astatus"  hidden="true" query="true" queryMode="single" dictionary="a_status" width="80"></t:dgCol>
   <t:dgCol title="投诉人"  field="createName"   queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="电话"  field="atel"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="附件"   field="afile" url="{id}" queryMode="single"   width="120"></t:dgCol>
  <t:dgCol title="客户账号"  field="azhtype"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="投诉公司"  field="arealname"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="投诉来源"  field="ainfo"   queryMode="single" dictionary="adsource" width="80"></t:dgCol>
   <t:dgCol title="客服状态"  field="aktatus"   queryMode="single" dictionary="s_status"   width="60"></t:dgCol>
   <t:dgCol title="客服备注"  field="anotes"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="受理时间"  field="slDatetime"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="投诉时间"  field="createDatetime" formatter="yyyy-MM-dd hh:mm:ss" hidden="true" query="true" queryMode="group"  width="130"></t:dgCol>
   <t:dgCol title="部门处理时间"  field="deDatetime" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="受理人"  field="slName"  hidden="false"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="处理人"  field="deName"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="反馈时间"  field="comDatetime"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="反馈人"  field="comName"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> 
   <t:dgDelOpt title="删除"  url="customerSerController.do?doDel&id={id}&ainfo={ainfo}" /> 
   <t:dgToolBar title="受理单确认"  operationCode="doconcfirms" icon="icon-edit" url="customerSerController.do?doBatchConfirm" funname="confirms" height="450"></t:dgToolBar>
   <t:dgToolBar title="取消确认" icon="icon-edit" url="customerSerController.do?doBatchCancel" funname="confirms" height="450"></t:dgToolBar>
   <t:dgToolBar title="受理单下发"  icon="icon-remove" url="customerSerController.do?goUpdate" funname="update" height="450"></t:dgToolBar>
   <t:dgToolBar title="取消下发"  icon="icon-remove" url="customerSerController.do?doCancel" funname="confirmsdoCancel" height="450"></t:dgToolBar>
   <t:dgToolBar title="查看处理" icon="icon-search" url="customerSerController.do?goQuery" funname="detail" height="450"></t:dgToolBar>
   <t:dgToolBar title="受理单反馈" icon="icon-search" url="customerSerController.do?goReturnResult" funname="details" height="450"></t:dgToolBar>
   <t:dgToolBar title="客户电话投诉受理" icon="icon-add" url="customerSerController.do?goAdd" funname="add" height="450"></t:dgToolBar>
      <t:dgToolBar title="修改电话投诉单" icon="icon-add" url="customerSerController.do?goUpdateK" funname="update" height="450"></t:dgToolBar>
  </t:datagrid>
  
  </div>
 </div>
 <script src = "webpage/com/jinbo/customer/customerservice/customerSerList.js"></script>		
 <script type="text/javascript">
  $(document).ready(function(){
 		//给时间控件加上样式
 			$("#customerSerListtb").find("input[name='createDatetime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#customerSerListtb").find("input[name='createDatetime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#customerSerListtb").find("input[name='deDatetime']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 
	function details(title, url, id, width, height) {
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
		createdetailwindowse(title, url, width, height, rowsData[0].id);
	}

	function createdetailwindowse(title, addurl, width, height, id) {
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
					name : '反馈客户结果',
					callback : function() {
					evaluate(id);
					}
				},{
				  name:'关闭'
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

	
	function evaluate(id) {
		$
				.dialog({
					title : "处理反馈",
				content : 'url:' + 'customerSerController.do?goReturnResult2&id='+id,
				 ok: function(){
		    	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    }

				});
	}
	

		

 </script>