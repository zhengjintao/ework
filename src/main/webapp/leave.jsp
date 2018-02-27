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
<script src="dist/components/form.js"></script>
<script src="dist/components/transition.js"></script>
<script type="text/javascript">
function getSettedtime()
{
	window.location.href = "leave.do?"+ $("form").serialize() + "&selectChg=1";
}

function deleteData()
{
	window.location.href = "leave.do?"+ $("form").serialize() + "&deleteFlg=1";
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
			<form action="./leave.do" method="post">
				<div class="ui yellow inverted segment">
					<div class="ui inverted form">
						<div class="inline field">
							<div class="field">
								<label>日期</label> <input type="date" name="wdate"
									value=<%=(String) request.getAttribute("sysDate")%> onchange="getSettedtime()">
							</div>
						</div>
						<div class="two fields">
							<div class="field">
								<label>理由</label> <input type="text" name="wcomment" value=<%=(String) request.getAttribute("wcomment")%>>
							</div>
						</div>
						<input type="hidden" name="subKbn" value="true">
						<Button class="ui active yellow button" type="submit">
							<i class="add to calendar icon"></i> 确定
						</Button>
						<div class="ui active yellow button" onclick = "deleteData()" >
							<i class="trash icon"></i> 删除
						</div>
					</div>

				</div>
			

			<div class="ui grey inverted segment">
			    <a class="ui orange ribbon label">当月请假</a>
				<input type="month" name="wdate2" value=<%=(String) request.getAttribute("sysDate2")%> onchange="getSettedtime()">

				<table class="ui celled table">
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
						out.print("<td>");
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
	<div style="height: 120px"></div>
	<footer>
		<div class="ui yellow four item menu">
			<a class="item" href="home.do"> <i class="home icon"></i> 首页
			</a> <a class="item" href="list.do"> <i class="calendar icon"></i>
				出勤
			</a> <a class="active item" href="leave.do"> <i class="browser icon"></i>
				请假
			</a> <a class="item" href="personal.do"> <i class="user icon"></i>
				个人
			</a>
		</div>
	</footer>
</body>
</html>
