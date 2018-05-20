<%@ page import="java.util.List" %>
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
<script src="dist/semantic.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	var message = "<%=(String) request.getAttribute("errmsg")%>";
	if (message != "null" && message.length > 0) {
		$('#cmodal').modal({
			closable : false
		}).modal('show');
	}
	
	$('#envelope').popup({
		popup : $('.custom.popup'),
		on : 'click'
	});
});

function sendmail() {
	$('#envelope').popup('hide all');
	var mail = $("#mail").val();
	var errmsg="";
	if (mail.length == 0) {
		errmsg ="收件地址必须输入";
	}
	
	if (mail.length > 0){
		var Regex = /^(?:\w+\.?)*\w+@(?:\w+\.).*\w+$/;
		if (!Regex.test(mail)){
			if (errmsg.length > 0) {
				errmsg = errmsg + "<br>";
			}
			errmsg = errmsg + "邮箱格式不正确";
		}
	}
	
	if (errmsg.length > 0) {
		$("#errmsg").html(errmsg);
		$('#cmodal').modal({
			closable : false

		}).modal('show');
		return false;
	}

	$.ajax({
		type : "post",
		url : "./sendmilealluser.do?" + $("form").serialize(),
		dataType : "json",
		success : function(data) {
			$("#errmsg").html(data.message);
			$('#cmodal').modal({
				closable : false

			}).modal('show');
		},
		error : function() {
			alert("网络异常，请稍后重试");
		}
	});

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
	<div id="cmodal" class="ui small test modal transition hidden">
		<i class="close icon"></i>
		<div class="content">
			<p id="errmsg"><%=(String) request.getAttribute("errmsg")%></p>
		</div>
	</div>
	<div class="ui one column grid container">
	<div class="column">
	<div class="ui teal segment">
			<div class="ui  breadcrumb">
				<a class="section"  href="personal.do">个人</a> <i class="right chevron icon divider"></i>
				<div class="active section">出勤统计</div>
			</div>
		</div>
		<form action="./statisticsWork.do" method="post">
        <div class="ui teal inverted segment" style="height: 45px;margin-bottom:-10px">
					<div class="ui inverted form" style="float: left;">
						<div class="inline field">
							<div class="field">
								<label>全员出勤一览</label>
							</div>
						</div>
					</div>
					<div class="some-wrapping-div" style="float: right;">
						<i id='envelope' class="envelope outline  icon"></i>
					</div>
					<div class="ui custom popup top left transition hidden">
						<div class="ui yellow inverted segment">
							<div class="ui inverted form">
								

								<div class="inline fields">
									<div class="field">
										<input type="text" id="mailname" name="mailname"
											placeholder="邮件名" value="当月全员出勤统计">
									</div>
								</div>
								<div class="inline fields">
									<div class="field">
								<input type="text" id="mail" name="mail" placeholder="收件邮箱地址(必须)"
										value="<%=request.getAttribute("email") %>">
								</div>
								</div>
								
								<div class="inline field" >
									<div class="ui custom button" onclick="sendmail()">
										<i class="envelope icon"></i>送信
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			<div class="ui teal inverted segment"  style="margin-bottom:-10px">
				<div class="ui inverted form">
					<div class="inline field">
						<div class="field">
							<input type="month" name="wdate" value=<%=(String) request.getAttribute("sysDate")%>>
							<Button class="ui active teal button">
						<i class="search icon"></i>查询
					</Button>
						</div>
					</div>
					
				</div>
			</div>
				<table class="ui unstackable celled table">
					<tr bgcolor="#00B5AB">
						<th width="30%" style="text-align:center; color:white">姓名</th>
						<th width="35%" style="text-align:center; color:white">出勤</th>
						<th width="35%" style="text-align:center; color:white">休假</th>
					</tr>
					<%
					List<String[]> dataList = (List<String[]>) request.getAttribute("dataList");
					for(String[] each : dataList){
						out.print("<tr>");
						out.print("<td>");
						out.print("<a href='userworkdetail.do?userid=" + each[3]+"&wdate=" + each[4]+ "&username=" + each[0] + "'>");
						out.print(each[0]);
						out.print("</a>");
						out.print("</td>");
						out.print("<td style='text-align:right;'>");
						out.print(each[1] + "(H) /" + each[5]+"(天)");
						out.print("</td>");
						out.print("<td style='text-align:right;'>");
						out.print(each[2] + "(H) /" + each[6]+"(天)");
						out.print("</td>");
						out.print("</tr>");
					}
					%>				
				</table>
		</form>
	</div>
	<div class="column"></div>
	</div>
</body>
</html>
