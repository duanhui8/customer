<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport"
		content="width=device-width; initial-scale=1.0;  minimum-scale=1.0; maximum-scale=1.0" />
		<meta name="MobileOptimized" content="320" />
		<title>微信账号绑定</title>
		<link href="cs/css.css" rel="stylesheet" type="text/css" />
	
	</head>
	<body class="bog">
		<!-- 顶部logo -->
		<div class="con font05"></div>
		<div class="title font02">
			<strong style="margin-left: 40px; font-size: 20px;">${info}</strong>
		</div>
  
			<div class="con font05">
				<div class="con font05"></div>
				<p>
					<span class="fontbla">账号：</span>
					<span id="nameinfo">${yhm}</span>
				</p>
				<div class="con font05"></div>
				<div class="con font05"></div>
				<p>
					<span class="fontbla">微信账号：${openid}</span>
					<span id="pwdinfo"></span>
				</p>
			</div>
			<br>
	


	</body>
</html>