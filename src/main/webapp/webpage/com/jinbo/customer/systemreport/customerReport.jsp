<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<t:base type="jquery,tools"></t:base>
<div style="margin-top: 14px;">
	<div id="container98" style="width:50%;height:400px;float: left;"></div>
	<div id="container97" style="width:50%;height:400px;float: right;"></div>
</div>

<!--  <script src = "webpage/com/jinbo/customer/customerquery/high.js"></script>	 -->
<script type="text/javascript">
	$(document).ready(
					function() {
						$('#container98')
								.highcharts(
										{
											chart : {
												plotBackgroundColor : null,
												plotBorderWidth : null,
												plotShadow : false
											},
											title : {
												text : '客户对客服评价'
											},
											tooltip : {
												pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
											},
											plotOptions : {
												pie : {
													allowPointSelect : true,
													cursor : 'pointer',
													dataLabels : {
														enabled : true,
														color : '#000000',
														connectorColor : '#000000',
														format : '<b>{point.name}</b>: {point.percentage:.1f} %'
													}
												}
											},
											series : [ {
												type : 'pie',
												name : '客户满意度',
												data : [ 
														[ '满意', ${ping.manyi} ], {
															name : '非常满意',
															y : ${ping.feichang},
															sliced : true,
															selected : true
														}, [ '一般', ${ping.yiban} ],
														[ '较差', ${ping.jiaocha} ],
														[ '很差', ${ping.hencha} ] ]
											} ]
										});
					
					//----------------
						$('#container97')
								.highcharts(
										{
											chart : {
												plotBackgroundColor : null,
												plotBorderWidth : null,
												plotShadow : false
											},
											title : {
												text : '客户对部门评价'
											},
											tooltip : {
												pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
											},
											plotOptions : {
												pie : {
													allowPointSelect : true,
													cursor : 'pointer',
													dataLabels : {
														enabled : true,
														color : '#000000',
														connectorColor : '#000000',
														format : '<b>{point.name}</b>: {point.percentage:.1f} %'
													}
												}
											},
											series : [ {
												type : 'pie',
												name : '客户满意度',
												data : [ 
														[ '满意', ${ping1.feichang} ], {
															name : '非常满意',
															y : ${ping1.manyi},
															sliced : true,
															selected : true
														}, [ '一般', ${ping1.yiban} ],
														[ '较差', ${ping1.jiaocha} ],
														[ '很差', ${ping1.hencha} ] ]
											} ]
										});
					})
					
					
					
					
</script>