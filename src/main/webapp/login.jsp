<%@ page import="java.util.List"%>
<!DOCTYPE html>
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

<style type="text/css">
body {
	background-color: #FCFCFC;
}

body>.grid {
	height: 100%;
}

.image {
	margin-top: -100px;
}

.column {
	max-width: 450px;
}
</style>
<script>
$(document).ready(function() {
		$('.ui.form').form({
			fields : {
				userid : {
					identifier : 'userid',
					rules : [ {
						type : 'empty',
						prompt : '请输入用户名'
					} ]
				},
				password : {
					identifier : 'password',
					rules : [ {
						type : 'empty',
						prompt : '请输入密码'
					}, {
						type : 'length[5]',
						prompt : '密码必须是5位以上'
					} ]
				}
			}
		});
	});
</script>
</head>
<body>
	<script type="text/javascript">
	$(document).ready(function() {
		var message = "<%=(String) request.getAttribute("errmsg")%>";
		if (message != "null" && message.length > 0) {
			$('#norm').modal({
				closable : false

			}).modal('show');
		}
		
		var multi = "<%=(String) request.getAttribute("multi")%>";
		if (multi != "null" && multi.length > 0) {
			$('#multi').modal({
				closable : false

			}).modal('show');
		}
	});
	
	function closemodal(){
		$('#multi').modal({
			closable : false

		}).modal('hide');
	}
	</script>
	<div class="ui small test modal transition hidden" id="norm">
	    <i class="close icon"></i>
		<div class="content">
			<p><%=(String) request.getAttribute("errmsg")%>
			</p>
		</div>
	</div>


	<div class="ui basic modal" id="multi">
		<div class="ui icon header">
			<i class="home icon"></i> 选择一个账号登录
		</div>
		<div class="content">
			<div class="ui middle aligned divided list">
				<%
							List<String[]> userinfo = (List<String[]>) request.getAttribute("userinfo");
							int inxnum =0;
							if(userinfo!=null){
								for (String[] each : userinfo) {
									out.print("<div class='item'>");
									out.print("<div class='content' style='text-align:center'>");
									out.print("<a class='header' href='login.do?rembpwd=1&userid=" + each[0] + "&password=" + each[1] + "'>" + each[2] +"</a>");
									out.print("</div>");
									out.print("</div>");
									inxnum++;
								}
							}
						%>
				<div class="item">

					<div class="content" style="text-align:center">
						<a class="header" href="javascript:void(0)" onclick="closemodal()">使用其他账号登录</a>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div class="ui middle aligned center aligned grid container">
		<div class="column">
			<div class="ui middle center aligned ">
				<div class="ui buttons">
					<button class="ui button">
						<a href="login.do"><i class="diamond icon"></i><span>e-work</span></a>
					</button>
					<div class="or" data-text="@" style="margin-top: 18px"></div>
					<button class="ui button">
						<a href="https://www.bridgeworld.co.jp"><img
							src="assets/images/logo.png"></a>
					</button>
				</div>
			</div>
			<div class="ui large center aligned ">

				<form class="ui large form" action="login.do" method="post">
					<div class="ui stacked segment" style="margin-top: 10px">
						<div class="field">
							<div class="ui left icon input">
								<i class="user icon"></i> <input type="text" name="userid"
									placeholder="用户名"
									style="ime-mode: disabled; -webkit-ime-mode: disabled"
									value=<%Object userid = request.getAttribute("userid");
			userid = userid != null ? (String) userid : "";
			out.print((String) userid);%>>
							</div>
						</div>
						<div class="field">
							<div class="ui left icon input">
								<i class="lock icon"></i> <input type="password" name="password"
									placeholder="密码">
							</div>
						</div>
						<div class="field">
							<div class="ui left icon input">
								<div class="ui checked checkbox">
									<input type="checkbox" name="rembpwd"> <label style="">记住密码(一周)</label>
								</div>
							</div>
						</div>
						<div class="ui fluid large teal submit button">登录</div>
					</div>

					<div class="ui error message"></div>

				</form>

				<div class="ui message" hidden>
					New to us? <a href="#">Sign Up</a>
				</div>
			</div>
		</div>
	</div>

</body>

</html>