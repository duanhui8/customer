function confirms(title,url,gname) {
	    var ids = [];
   var rows = $("#"+gname).datagrid('getSelections');
    if (rows.length > 0) {
    	$.dialog.confirm('确认选中的投诉单吗?'+"共"+rows.length+"条。", function(r) {
		   if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
			  $.ajax({
					url : url,
					type : 'post',
					data : {
						ids : ids.join(',')
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#"+gname).datagrid('unselectAll');
							ids='';
						}
					}
				});
				
			}
		});
	} else {
		tip("请选择需要确认的受理单");
	}
}