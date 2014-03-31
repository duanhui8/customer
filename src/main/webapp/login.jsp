<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport"
			content="width=device-width; initial-scale=1.0;  minimum-scale=1.0; maximum-scale=1.0" />
		<meta name="MobileOptimized" content="320" />
			<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<title>微信账号绑定</title>
		<link href="cs/css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
	window.onload = function() {
		var sub = document.getElementById("sub");
		sub.onclick = function() {
			var name = document.getElementById("uname");
			var pwd = document.getElementById("pwd");
			var nameinfo = document.getElementById("nameinfo");
			var pwdinfo = document.getElementById("pwdinfo");
			nameinfo.innerHTML = "";
			pwdinfo.innerHTML = "";
			if (name.value == null || name.value.length <= 0) {
				nameinfo.innerHTML = "<p style='color:red'>账号不能为空<p>";
				return;
			}
			if (pwd.value == null || pwd.value.length <= 0) {
				pwdinfo.innerHTML = "<p style='color:red'>密码不能为空<p>";
				return;
			}
			var tab = document.getElementById("dp");
			tab.submit();
		}
	}
</script>
	</head>
	<body class="bog">
		<!-- 顶部logo -->
		<div class="con font05"></div>
		<div class="title font02">
			<strong
				style="margin-left: 40px; font-size: 20px; font-size: 18px;  font-weight: bold; padding-bottom: 14px">金博微信账号绑定</strong>
		</div>
		<div class="con font05"></div>
		<div class="con font05"></div>
		<form action="/LoginServlet" method="post" id="dp">
			<div class="con font05">
				<div class="con font05"></div>
				<p>
					<span class="fontbla">账号：</span>
					<input class="sinput80" id="uname" style="width: 180px"
						name="username" size="10" type="text" value="" />
					<span id="nameinfo"></span>
				</p>
				<div class="con font05"></div>
				<div class="con font05"></div>
				<p>
					<span class="fontbla">密码：</span>
					<input type="hidden" name="openid" value="${param.openid}" />
					<input type="hidden" name="goal" value="${param.goal}" />
					<input class="sinput80" id="pwd" style="width: 180px"
						name="password" size="10" type="password" value="" />
					<span id="pwdinfo"></span>
				</p>
			</div>
		</form>
		<!--二手房end-->
		<!--租房start-->
		<div class="con font05"></div>
		<div class="con font05">
			<div class="con font05">
				<input name="" class="btn5" id="sub" style="margin-left: 75px;"
					type="submit" value="绑定" />
			</div>
			<br />
			<span style="font-size: 13px"> <span class="fontbla">说明：</span>
				<br />
				<div style="margin-left: 36px;">

					<span> 1.该账号为金博订单系统的用户名。<br /> 2.绑定成功后，即可根据订单号查询订单信息。<br />
						3.点击链接<a href="#" style="color: red">订单系统</a> </span>

				</div> </span>
	</body>
</html>