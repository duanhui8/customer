<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport"
		content="width=device-width; initial-scale=1.0;  minimum-scale=1.0; maximum-scale=1.0" />
		<meta name="MobileOptimized" content="320" />
		<title>订单明细</title>
		<link href="cs/css.css" rel="stylesheet" type="text/css" />
	</head>
	<body class="bog">
		<!-- 顶部logo -->
		<div class="con font05"></div>
		<div class="title font02">
			<strong style="margin-left: 90px; font-size: 20px;">订单明细</strong>
			<div class="con font05"></div>
			<div class="con font05"></div>
		</div>
  		<c:forEach var="detail" items="${requestScope.list}">
			<div class="con font05">
				<div class="con font05"></div>
			
					<div class="con font05"></div>
					<p>
					<span class="fontbla">品名：${detail.pm}</span>
				</p>
			<div class="con font05"></div>
				<p>
					<span class="fontbla">规格：${detail.gg}</span>
					<span id="pwdinfo"></span>
				</p>
					<div class="con font05"></div>
					<p>
					<span class="fontbla">材质：${detail.cz}</span>
					<span id="pwdinfo"></span>
				</p>
					<div class="con font05"></div>
					<p>
					<span class="fontbla">重量：${detail.zl}</span>
					<span id="pwdinfo"></span>
				</p>
					<div class="con font05"></div>
						<p>
					<span class="fontbla">仓库：${detail.ck}</span>
					<span id="pwdinfo"></span>
				</p>
			————————————
			</div>
			</c:forEach>
			<br>
	<div class="con font05">
	
			<span style="font-size: 13px"> <span class="fontbla">说明：</span>
				<br />
				<div style="margin-left: 36px;">

					<span> 1.此数据仅供参考。<br /> 2.详细信息请登录<a href="#" style="color: red">金博第二方电商平台。</a><br />
	</span>

				</div> </span>


	</body>
</html>