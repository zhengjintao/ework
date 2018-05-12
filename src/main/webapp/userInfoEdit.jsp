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

<script>
   $(document).ready(function() {
	var message = "<%=(String) request.getAttribute("errmsg")%>";
		if (message != "null" && message.length > 0) {
			$('#cmodal').modal({
				closable : false
				}).modal('show');
		}
	});
   
   function save()
   {
   	$.ajax({ 
   	    type: "post", 
   	    url: "./userInfoEdit.do?" + $("form").serialize() + "&saveFlg =1&userid" +$("#euserid").val(), 
   	    dataType: "json", 
   	    success: function (data) {
   	        alert("成功");
   	    }, 
   	    error: function() {
   	        alert("网络异常，请稍后重试");
   	    } 
   	});
   }
   
	function check() {
		var errmsg = "";
		var username = $("#eusername").val();
		if (username == null || username == "undefine" || username.length == 0) {
			if (errmsg.length > 0) {
				errmsg = errmsg + "<br>";
			}
			errmsg = errmsg + "姓名必须输入。";
		}

		var username = $("#eusername").val();
		if (username.length > 20) {
			if (errmsg.length > 0) {
				errmsg = errmsg + "<br>";
			}
			errmsg = errmsg + "姓名长度不正确（20位以内）";
		}
		
		var textmail = $('#email').val();
		if (textmail.length > 50) {
			if (errmsg.length > 0) {
				errmsg = errmsg + "<br>";
			}
			errmsg = errmsg + "邮箱长度不正确（50位以内）";
		}
		if (textmail.length > 0){
			var Regex = /^(?:\w+\.?)*\w+@(?:\w+\.).*\w+$/;
			if (!Regex.test(textmail)){
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
					<a class="section" href="personal.do">个人</a> <i
						class="right chevron icon divider"></i>
					<div class="active section">个人资料</div>
				</div>
			</div>
			<div class="ui teal inverted segment">
				<form  action="./userInfoEdit.do" method="post" onsubmit="return check();">
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">账号</div>
								<input id="euserid" name="euserid" type="text" readonly="readonly" value=<%=(String) request.getAttribute("userid")%>>
							</div>
						</div>	
					</div>

					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">姓名</div>
								<input id="eusername" name="eusername" type="text" readonly="readonly" value=<%=(String) request.getAttribute("username")%>
									placeholder="必须（请使用真名）">
							</div>
						</div>
					</div>
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">性别</div>
								<select name="esex" class="ui search dropdown" disabled>
									<option value="M"
										<%if (!(Boolean) request.getAttribute("isfemale")) {
											out.print("selected='selected'");
											}%>>男</option>
									<option value="F"
										<%if ((Boolean) request.getAttribute("isfemale")) {
											out.print("selected='selected'");
											}%>>女</option>

								</select>
							</div>
						</div>
					</div>

					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">邮箱</div>
								<input id='email' name="email" type="text" value='<%=(String) request.getAttribute("mail")%>'
									placeholder="50字符以内">
							</div>
						</div>
					</div>
					
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">生日</div>
								<input type="date" name="ebirthday" onchange="getSettedtime()" value=<%=(String) request.getAttribute("birthday")%>>
							</div>
						</div>
					</div>
					
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">权限</div>
								<select name="eauthflg" class="ui search dropdown" disabled>
									<option value="2"
										<%if (!(Boolean) request.getAttribute("isadmin")) {
											out.print("selected='selected'");
											}%>>一般用户</option>
									<option value="1"
										<%if ((Boolean) request.getAttribute("isadmin")) {
											out.print("selected='selected'");
											}%>>管理员</option>
								</select>
							</div>
						</div>
					</div>
					<button class="ui basic submit button">
						<i class="icon user"></i> 提交
					</button>
					<input type="hidden" name="saveFlg" value="1">
					<input type="hidden" name="userid" value=<%=(String) request.getAttribute("userid")%>>
				</form>
			</div>

		</div>
		<div class="column"></div>
	</div>

</body>