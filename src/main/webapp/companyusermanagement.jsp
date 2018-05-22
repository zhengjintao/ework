
<%@ page import="java.util.List"%>
<html>
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

	<div class="ui one column grid container">
		<div class="column">
		   <!-- <div class="ui divider" style="margin:5px"></div>
		<div class="ui basic buttons">
		<a class="ui left attached button" href="companydetail.do">返回</a>
<a class="right attached ui button" href="service.do?mode=detail">新規追加</a>
</div>

		
		<div class="ui divider" style="margin:5px"></div> -->
		<div style="margin:5px"></div>
			<form action="./useredit.do" method="post">
				<div class="ui base segment">

					<div class="ui middle aligned divided list">
						<%
						List<String[]> userinfo = (List<String[]>) request.getAttribute("userinfo");
						for (String[] each : userinfo) {
							out.print("<div class='item'>");
							out.print("<div class='right floated content'>");
							out.print("<div class='ui basic button'>");
							out.print("<a href='" + "companyusermanagement.do?mode=auth&userid="+ each[0] + "&rolekbn="+ each[4] + "' >" +each[2]+"</a></div>");
							out.print("</div>");
							out.print("<img class='ui avatar image' src='" + each[5] + "'>");
							out.print("<div class='content'>");
							out.print(each[1]);  // 姓名
							out.print("</div>");
							out.print("</div>");
						}
					%>
					</div>

					<div class="column"></div>

				</div>
			</form>
		</div>

	</div>
</body>