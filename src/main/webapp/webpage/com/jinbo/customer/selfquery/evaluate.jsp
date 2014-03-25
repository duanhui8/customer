<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div style="margin-top: 14px;">

	<form id='pjtb' action='customerQueryController.do?doSaveEval'
		method='post'>
		客服评价:<input type='radio' name='kefupj' value='0' />好评&nbsp<input
			type='radio' name='kefupj' value='1' />一般<input type='radio'
			name='kefupj' value='2' />差评<br>
		<br> 结果评价:<input type='radio' name='deptpj' value='0' />好评&nbsp<input
			type='radio' name='deptpj' value='1' />一般<input type='radio'
			name='deptpj' value='2' />差评<br> <br>评论:<br>
		<textarea cols='40' rows='5' name='note'></textarea>

	</form>

</div>