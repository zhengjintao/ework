<%@ page import="java.util.List" %>
<html>
<head>
<!-- Standard Meta -->
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

<!-- Site Properties -->
<title>BWC考勤系统</title>
<link rel="shortcut icon" type="image/png" href="favicon.ico">
<link rel="stylesheet" type="text/css" href="dist/semantic.min.css">

<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="dist/components/form.min.js"></script>
<script src="dist/components/transition.min.js"></script>

<style type="text/css">
body {
    margin-top: 10px;
	background-color: #FFFFFF;
}

body>.grid {
	height: 100%;
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
	<div class="ui one column grid container">
	<div class="column">
		<div class="ui teal segment">
			<div class="ui  breadcrumb">
				<a class="section"  href="personal.do">个人</a> <i class="right chevron icon divider"></i>
				<div class="active section">出勤统计</div>
			</div>
		</div>
	</div>
	<div class="column" style="margin-top: -200px;">
		<form action="./statisticsWork.do" method="post">
			<div class="ui teal inverted segment">
				<div class="ui inverted form">
					<div class="inline field">
						<div class="field">
							<label>月份</label> <input type="month" name="wdate" value=<%=(String) request.getAttribute("sysDate")%>>
						</div>
					</div>
					<Button class="ui active teal button">
						<i class="search icon"></i>查询
					</Button>
				</div>
			</div>
			
			<div class="ui teal inverted segment">
				<div class="ui inverted form">
					<div class="inline field">
						<div class="field">
							<label>出勤情况一览</label>
						</div>
					</div>
				</div>
			</div>
				<table class="ui unstackable celled table">
					<tr bgcolor="#48d1cc">
						<th width="50%" style="text-align:center;">姓名</th>
						<th width="25%" style="text-align:center;">出勤(小时)</th>
						<th width="25%" style="text-align:center;">休假(小时)</th>
					</tr>
					<%
					List<String[]> dataList = (List<String[]>) request.getAttribute("dataList");
					for(String[] each : dataList){
						out.print("<tr>");
						out.print("<td>");
						out.print("<a href='userworkdetail.do?userid=" + each[3]+"'>");
						out.print(each[0]);
						out.print("</a>");
						out.print("</td>");
						out.print("<td style='text-align:right;'>");
						out.print(each[1]);
						out.print("</td>");
						out.print("<td style='text-align:right;'>");
						out.print(each[2]);
						out.print("</td>");
						out.print("</tr>");
					}
					%>				
				</table>
		</form>
	</div>
	</div>
</body>
</html>
