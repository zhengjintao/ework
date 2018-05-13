<%@ page import="java.util.List"%>
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

<script src="jquery/jquery-3.1.1.min.js"></script>
<script src="dist/components/form.min.js"></script>
<script src="dist/components/transition.min.js"></script>
<script src="dist/semantic.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('.ui.accordion').accordion();
		$('#envelope').popup({
			popup : $('.custom.popup'),
			on : 'click'
		});
	});

	function tabclick(id) {
		var addtab = "tab2" == id ? "#tab2" : "#tab1";
		var rmvtab = "tab2" != id ? "#tab2" : "#tab1";
		$(addtab).attr("class", "active item");
		$(rmvtab).attr("class", "item");

		var showtab = "tab2" == id ? "#table2" : "#table1";
		var hidetab = "tab2" != id ? "#table2" : "#table1";
		$(showtab).show().css("margin", "0");
		$(hidetab).hide().css("margin", "0");
	}

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
			url : "./sendmail.do?" + $("form").serialize(),
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
			<p id="errmsg"><%=(String) request.getAttribute("errmsg")%>
			</p>
		</div>
	</div>

	<div class="ui one column grid container">
		<div class="column" style="padding-top: 14px">
			<div class="ui teal segment">
				<div class="ui  breadcrumb">
					<a class="section" href="personal.do">个人</a> <i
						class="right chevron icon divider"></i> <a class="section"
						href="statisticsWork.do">出勤统计</a> <i
						class="right chevron icon divider"></i>
					<div class="active section">出勤详细</div>
				</div>
			</div>
			<form action="./userworkdetail.do" method="post">
				<div class="ui teal inverted segment"  style="margin-bottom:-10px">
					<div class="ui inverted form">
						<div class="inline field">
							<div class="field">
								<label>月份</label> <input type="month" name="wdate"
									value=<%=(String) request.getAttribute("sysDate")%>>
							</div>
						</div>
						<input type="hidden" name="userid"
							value='<%=(String) request.getAttribute("userid")%>'> <input
							type="hidden" name="username"
							value='<%=(String) request.getAttribute("username")%>'>
						<Button class="ui active teal button">
							<i class="search icon"></i>查询
						</Button>
					</div>
				</div>

				<div class="ui teal inverted segment" style="height: 45px;margin-bottom:-10px">
					<div class="ui inverted form" style="float: left;">
						<div class="inline field">
							<div class="field">
								<label>出勤详细情况 - <%=(String) request.getAttribute("username")%></label>
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
											placeholder="邮件名" value="当月出勤统计">
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
				<div class="ui top attached tabular menu">
					<div id="tab1" class="active item" onclick="tabclick('tab1')">出勤</div>
					<div id="tab2" class="item" onclick="tabclick('tab2')">休假</div>
				</div>
				<div class="ui bottom attached active tab segment">
					<table id="table1" class="ui unstackable celled table" border="1">
						<tr bgcolor="#00B5AB">
							<th width="40%" style="text-align: center; color: white">日期</th>
							<th width="30%" style="text-align: center; color: white">出勤</th>
							<th width="30%" style="text-align: center; color: white">退勤</th>
						</tr>
						<tbody>
							<%
								List<String[]> workdata = (List<String[]>) request.getAttribute("workdata");
								if (workdata.size() == 0) {
									out.print("<tr>");
									out.print("<td>");
									out.print("当月没有出勤");
									out.print("</td>");
									out.print("</tr>");
								}
								for (String[] each : workdata) {
									out.print("<tr>");
									out.print("<td style='text-align:center;'  rowspan='2'>");
									out.print(each[0]);
									out.print("</td>");
									out.print("<td style='text-align:center;'>");
									out.print(each[1]);
									out.print("</td>");
									out.print("<td style='text-align:center;'>");
									out.print(each[2]);
									out.print("</td>");
									out.print("</tr>");
									out.print("<tr>");
									out.print("<td style='text-align:left;' colspan='2'>");
									out.print("<div class='ui accordion'  height='10px'>");
									out.print("<div class='title' style='color:black'>");
									out.print("<i class='dropdown icon'></i> 备注");
									out.print("</div>");
									out.print("<div class='content'>");
									out.print("<span class='transition visible' style='display: block !important;'>");
									//out.print("<div class='ui tall stacked segment'>");
									out.print(each[3]);
									//out.print("</div>");
									out.print("</span>");
									out.print("</div>");
									out.print("</div>");
									out.print("</div>");
									out.print("</td>");
									out.print("</tr>");
								}
							%>
						</tbody>
					</table>
					<table id="table2" class="ui unstackable celled table"
						style="display: none;">
						<tr bgcolor="#00B5AB">
							<th width="40%" style="text-align: center; color: white">日期</th>
							<th width="60%" style="text-align: center; color: white">理由</th>
						</tr>
						<tbody>
							<%
								List<String[]> monthData = (List<String[]>) request.getAttribute("monthdata");
								if (monthData.size() == 0) {
									out.print("<tr>");
									out.print("<td>");
									out.print("当月没有请假");
									out.print("</td>");
									out.print("</tr>");
								}
								for (String[] each : monthData) {
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

				<div class="column"></div>

			</form>
		</div>
	</div>
</body>
</html>
