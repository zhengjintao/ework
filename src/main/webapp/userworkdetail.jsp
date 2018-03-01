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
<script type="text/javascript">
function tabclick(id){
	var addtab = "tab2"== id ? "#tab2" : "#tab1";
	var rmvtab = "tab2"!= id ? "#tab2" : "#tab1";
	$(addtab).attr("class", "active item");
	$(rmvtab).attr("class", "item");
	
	var showtab = "tab2"== id ? "#table2" : "#table1";
	var hidetab = "tab2"!= id ? "#table2" : "#table1";
	$(showtab).show();
	$(hidetab).hide();
}
</script>
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
				<a class="section" href="statisticsWork.do">出勤统计</a>
				<i class="right chevron icon divider"></i>
				<div class="active section">出勤详细</div>
			</div>
		</div>
	</div>
	<div class="column">
		<form action="./userworkdetail.do" method="post">
		<div class="ui teal inverted segment">
				<div class="ui inverted form">
					<div class="inline field">
						<div class="field">
							<label>出勤详细情况</label>
						</div>
					</div>
				</div>
			</div>
			
			<div class="ui teal inverted segment">
				<div class="ui inverted form">
					<div class="inline field">
						<div class="field">
							<label>月份</label> <input type="month" name="wdate" value=<%=(String) request.getAttribute("sysDate")%>>
						</div>
					</div>
					<input type="hidden" name="userid" value=<%=(String) request.getAttribute("userid")%>>
					<Button class="ui active teal button">
						<i class="search icon"></i>查询
					</Button>
				</div>
			</div>
			
			<div class="ui top attached tabular menu">
  <div id="tab1" class="active item"  onclick="tabclick('tab1')">出勤</div>
    <div id="tab2" class="item" onclick="tabclick('tab2')">休假</div>
</div>
<div class="ui bottom attached active tab segment">
  <table id="table1" class="ui unstackable celled table">
					<tr bgcolor="#48d1cc">
						<th width="40%" style="text-align:center;">日期</th>
						<th width="30%" style="text-align:center;">出勤</th>
						<th width="30%" style="text-align:center;">退勤</th>
					</tr>
					<tbody>
					<%
					List<String[]> workdata = (List<String[]>) request.getAttribute("workdata");
					if(workdata.size() == 0){
						out.print("<tr>");
						out.print("<td>");
						out.print("当月没有出勤");
						out.print("</td>");
						out.print("</tr>");
					}
					for(String[] each : workdata){
						out.print("<tr>");
						out.print("<td style='text-align:center;'>");
						out.print(each[0]);
						out.print("</td>");
						out.print("<td style='text-align:center;'>");
						out.print(each[1]);
						out.print("</td>");
						out.print("<td style='text-align:center;'>");
						out.print(each[2]);
						out.print("</td>");
						out.print("</tr>");
					}
					%>
					</tbody>				
				</table>
				 <table id="table2" class="ui unstackable celled table" style="display: none;">
					<tr bgcolor="#48d1cc">
						<th width="40%" style="text-align:center;">日期</th>
						<th width="60%" style="text-align:center;">理由</th>
					</tr>
					<tbody>
					<%
					List<String[]> monthData = (List<String[]>) request.getAttribute("monthdata");
					if(monthData.size() == 0){
						out.print("<tr>");
						out.print("<td>");
						out.print("当月没有请假");
						out.print("</td>");
						out.print("</tr>");
					}
					for(String[] each : monthData){
						out.print("<tr>");
						out.print("<td style='text-align:center;'>");
						out.print(each[0]);
						out.print("</td>");
						out.print("<td>");
						out.print(each[1]);
						out.print("</td>");
						out.print("</tr>");
					}
					%>
					</tbody>			
				</table>
</div>
				
				
			
		</form>
	</div>
	</div>
</body>
</html>
