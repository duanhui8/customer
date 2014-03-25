<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addAdviceReplyBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delAdviceReplyBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addAdviceReplyBtn').bind('click', function(){   
 		 var tr =  $("#add_adviceReply_table_template tr").clone();
	 	 $("#add_adviceReply_table").append(tr);
	 	 resetTrNum('add_adviceReply_table');
    });  
	$('#delAdviceReplyBtn').bind('click', function(){   
      	$("#add_adviceReply_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_adviceReply_table'); 
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
<!-- <div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addAdviceReplyBtn" href="#">添加2</a> <a id="delAdviceReplyBtn" href="#">删除</a> 
</div> -->
<div style="width: auto;height: 300px;overflow-y:auto;overflow-x:scroll;">
<table border="0" cellpadding="2" cellspacing="0" id="adviceReply_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE"><label class="Validform_label">序号</label></td>
		<td align="center" bgcolor="#EEEEEE"><label class="Validform_label">部门</label></td>
				  <td align="left" bgcolor="#EEEEEE">
				  <label class="Validform_label">
								备注
							</label></td>
	</tr>
	<tbody id="add_adviceReply_table">	

			<tr>
				<td align="center"><div style="width: 25px;" name="xh">1</div></td>
				<td align="center">
				
			<%-- 	<t:dictSelect field="dept1" type="list"
						typeGroupCode="advicedept" defaultVal="${customerAdvicePage.aadept}" hasLabel="false"  title="投诉部门">
						</t:dictSelect>     --%>   
							<t:dictSelect field="aadept" type="list"
						 hasLabel="false" dictTable="t_s_depart" dictText="departname" dictField="id" title="投诉部门" zisql="source is null"></t:dictSelect> 
						
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">投诉部门</label></td>
				
				  <td align="left">
					       	<input name="note" maxlength="500" 
						  		type="text" class="inputxt"  style="width:250px;"
					               
					                >
					  <label class="Validform_label" style="display: none;">备注</label>
					</td>
   			</tr>

	
	</tbody>
</table>
</div>