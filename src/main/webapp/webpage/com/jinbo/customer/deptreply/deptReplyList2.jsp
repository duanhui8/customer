<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<head>

</head>
<script type="text/javascript">
	$('#adddeptReplyBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#deldeptReplyBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#adddeptReplyBtn').bind('click', function(){   
 		 var tr =  $("#add_deptReply_table_template tr").clone();
	 	 $("#add_deptReply_table").append(tr);
	 	 resetTrNum('add_deptReply_table');
    });  
	$('#deldeptReplyBtn').bind('click', function(){   
      	$("#add_deptReply_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_deptReply_table'); 
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
			$(":input").each(function(){
				var $thisThing = $(this);
				$thisThing.attr("title",$thisThing.val());
			});
		}
    });
</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="adddeptReplyBtn" href="#">添加</a> <a id="deldeptReplyBtn" href="#">删除</a> 
</div>
<div style="width: auto;height: 300px;overflow-y:auto;overflow-x:scroll;">
<table border="0" cellpadding="2" cellspacing="0" id="deptReply_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE"><label class="Validform_label">序号</label></td>
		<td align="center" bgcolor="#EEEEEE"><label class="Validform_label">操作</label></td>
				  <td align="left" bgcolor="#EEEEEE">
				  <label class="Validform_label">
								处理结果
							</label></td>
	</tr>
	<tbody id="add_deptReply_table">	
	<c:if test="${fn:length(deptReplyList)  <= 0 }">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">1</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
					<input name="deptReplyList[0].id" type="hidden"/>
					<input name="deptReplyList[0].aorder" type="hidden"/>
					<input name="deptReplyList[0].createDatetime" type="hidden"/>
					<input name="deptReplyList[0].createName" type="hidden"/>
					<input name="deptReplyList[0].createDept" type="hidden"/>
			
				<!-- 	       	<input name="deptReplyList[0].acontent" maxlength="500" 
						  		type="text" class="inputxt"  style="width:120px;"
					               
					                > -->
					        
		 <td align="left">
					<!--        	<input name="adviceReplyList[0].acontent" maxlength="500" 
						  		type="text" class="inputxt"  style="width:120px;"
					               
					                > -->
					                <textarea name="adviceReplyList[0].acontent" rows="9" cols="60"  disabled="disabled"></textarea>
					  <label class="Validform_label" style="display: none;">处理结果</label>
					</td>
	
	
   			</tr>
	</c:if>
	<c:if test="${fn:length(deptReplyList)  > 0 }">
		<c:forEach items="${deptReplyList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
					<input name="deptReplyList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="deptReplyList[${stuts.index }].aorder" type="hidden" value="${poVal.aorder }"/>
					<input name="deptReplyList[${stuts.index }].createDatetime" type="hidden" value="${poVal.createDatetime }"/>
					<input name="deptReplyList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="deptReplyList[${stuts.index }].createDept" type="hidden" value="${poVal.createDept }"/>
				   <td align="left">
				<%-- 	       	<input name="deptReplyList[${stuts.index }].acontent" maxlength="500" 
						  		type="text" class="inputxt"  style="width:120px;"
					               
					                value="${poVal.acontent }"> --%>
					                
					                <textarea name="deptReplyList[0].acontent" rows="9" cols="60" >${poVal.acontent }</textarea>
					  <label class="Validform_label" style="display: none;">处理结果</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
</div>