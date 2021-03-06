﻿<html>
<head>
<!-- Standard Meta -->
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

<!-- Site Properties -->
<title>考勤系统</title>
<link rel="shortcut icon" type="image/png" href="favicon.ico">
<link rel="stylesheet" type="text/css" href="dist/semantic.min.css">

<script src="jquery/jquery-3.1.1.min.js"></script>
<script src="dist/components/form.min.js"></script>
<script src="dist/components/transition.min.js"></script>
<script src="dist/semantic.min.js"></script>

<script>
	function check() {
		return true;
	}
</script>
<style>
body {
    margin-top: 10px;
	font: 12px sans-serif;
}

.map {
	height: 400px;
}

footer {
	display: block;
	position: fixed;
	bottom: 0;
	width: 100%;
}
</style>

</head>
<body>
  <script type="text/javascript">
	$(document).ready(function() {
		$('.ui.accordion').accordion();
		
		var message = "<%=(String) request.getAttribute("errmsg")%>";
		if (message != "null" && message.length > 0) {
			$('#cmodal').modal({
				closable : false

			}).modal('show');
		}});
	</script>
	<div id="cmodal" class="ui small test modal transition hidden">
	    <i class="close icon"></i>
		<div class="content">
			<p id="errmsg"><%=(String) request.getAttribute("errmsg")%>
			</p>
		</div>
	</div>
	<div class="ui one column grid container">
		<div class="column" style="margin-top: 10px;">
		
		<div class="ui teal segment">
				<div class="ui  breadcrumb">
					<a class="section" href="personal.do">个人</a>
					 <i class="right chevron icon divider"></i>
					<div class="active section">公司信息</div>
				</div>
			</div>
    
       <div class="ui yellow inverted segment">
			    <a class="ui olive top attached label center aligned">公司信息</a>
				<div class="ui list">
					<div class="item">
						<img class="ui avatar image" src="<%=(String)request.getAttribute("imgurl") %>">
						<div class="content">
							<!-- <div class="description">00001</div> -->
							<div class="header" style='color:gray;margin-left:2px'><%=(String)request.getAttribute("companyid") %></div>
							<div class="description"><%=(String)request.getAttribute("companynm") %></div>
						</div>
					</div>
					<div class="item">
						 <%=(String) request.getAttribute("companyexp")%>
					</div>
				</div>
			
				<a class="ui basic button" href="companydetail.do?mode=add&companyid=<%=(String)request.getAttribute("companyid") %>"  style="<%=(String)request.getAttribute("displaybtn") %>">
					<i class="icon add user"></i><%=(String)request.getAttribute("btnname") %>
				</a>
				</div>
				
				<div class="ui brown segment" style="<%=(String)request.getAttribute("display") %>">
				<a class="ui orange right ribbon label">管理模块</a>
				 <div style="height: 10px"></div>
					<div class="ui basic buttons" tabindex="0">
						<div class="ui button">
							<i class="Add User icon"></i> <a href="employeemanage.do?companyid=<%=(String)request.getAttribute("companyid") %>">入社审批</a>
						</div>
						<!-- <a class="ui basic left pointing label"> <%=(String)request.getAttribute("hours") %> </a>-->
					</div>
					
					<div style="height: 10px"></div>
					<div class="ui basic buttons" tabindex="1">
						<div class="ui button">
							<i class="edit icon"></i> <a href="companyusermanagement.do">员工管理</a>
						</div>
						<!-- <a class="ui basic left pointing label"> <%=(String)request.getAttribute("hours") %> </a>-->
					</div>
				  <div style="height: 10px"></div>
					<div class="ui basic buttons" tabindex="1">
						<div class="ui button">
							<i class="edit icon"></i> <a href="statisticsWork.do">出勤统计</a>
						</div>
						<!-- <a class="ui basic left pointing label"> <%=(String)request.getAttribute("hours") %> </a>-->
					</div>
				    <div style="height: 10px"></div>
					<div class="ui basic buttons" tabindex="1">
						<div class="ui button">
							<i class="edit icon"></i> <a href="editnotice.do?type=1">通知发布</a>
						</div>
						<!-- <a class="ui basic left pointing label"> <%=(String)request.getAttribute("hours") %> </a>-->
					</div>
					<div style="height: 10px"></div>
					<div class="ui basic buttons" tabindex="1">
						<div class="ui button">
							<i class="edit icon"></i><a href="editnotice.do?type=2">活动发布</a>
						</div>
						<!-- <a class="ui basic left pointing label"> <%=(String)request.getAttribute("days") %> </a> -->
					</div>
				</div>
  </div>


	</div>

</body>