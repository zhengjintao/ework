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
<script src="dist/semantic.min.js"></script>

<script>
	function check() {
		var errmsg = "";
		var userid = $("#euserid").val();

		if (userid == null || userid == "undefine" || userid.length == 0) {
			errmsg = errmsg + "账号必须输入。";
		}

		var username = $("#eusername").val();
		if (username == null || username == "undefine" || username.length == 0) {
			if(errmsg.length > 0){
				errmsg = errmsg + "<br>";
			}
			errmsg = errmsg + "姓名必须输入。";
		}
		
		var userid = $("#euserid").val();
		if (userid.length > 30 || userid.length < 5) {
			if(errmsg.length > 0){
				errmsg = errmsg + "<br>";
			}
			errmsg = errmsg + "账号长度不正确（必须5-30位）";
		}
		
		var username = $("#eusername").val();
		if (username.length > 20) {
			if(errmsg.length > 0){
				errmsg = errmsg + "<br>";
			}
			errmsg = errmsg + "姓名长度不正确（20位以内）";
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
   <script type="text/javascript">
	$(document).ready(function() {
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
			<p id="errmsg"><%=(String) request.getAttribute("errmsg")%></p>
		</div>
	</div>
	<div class="ui one column grid container">
		<div class="column">
			<div class="ui teal segment">
				<div class="ui  breadcrumb">
					<a class="section" href="personal.do">个人</a> <i
						class="right chevron icon divider"></i> <a class="section"
						href="usermanagement.do">用户管理</a> <i
						class="right chevron icon divider"></i>
					<div class="active section">用户编辑</div>
				</div>
			</div>
			<div class="ui teal inverted segment">
				<form action="useredit.do" method="post" onsubmit="return check();">
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">账号</div>
								<input id="euserid" name="euserid" type="text"
									placeholder="必须（5～30位）"
									<%if ((Boolean) request.getAttribute("hasuserid")) {
				out.print("value='" + (String) request.getAttribute("userid") + "' " + "readonly='readonly'");
			} else if (request.getAttribute("userid") != null) {
				out.print("value='" + request.getAttribute("userid").toString() + "'");
			}%>>
							</div>
						</div>
					</div>



					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">姓名</div>
								<input id="eusername" name="eusername" type="text"
									placeholder="必须（请使用真名）"
									<%if ((Boolean) request.getAttribute("hasuserid")) {
				out.print("value='" + (String) request.getAttribute("username") + "' ");
			} else if (request.getAttribute("username") != null) {
				out.print("value='" + request.getAttribute("username").toString() + "'");
			}%>>
							</div>
						</div>
					</div>
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">性别</div>
								<select name="esex" class="ui search dropdown">
									<option value="M"
										<%if (!(Boolean) request.getAttribute("isfemale")) {
				out.print("selected='selected'");
			}%>>帅锅</option>
									<option value="F"
										<%if ((Boolean) request.getAttribute("isfemale")) {
				out.print("selected='selected'");
			}%>>镁铝</option>

								</select>
							</div>
						</div>
					</div>
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">权限</div>
								<select name="eauthflg" class="ui search dropdown">
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
					<div class="ui middle aligned divided list"
						<%if (!(Boolean) request.getAttribute("hasuserid")) {
				out.print("style='display: none'");
			}%>>
						<div class="item">
							<div class="ui toggle checkbox">
								<input name="initpwd" type="checkbox" name="public"> <label>初始化密码</label>
							</div>
						</div>
					</div>
					<div class="ui inverted divider"></div>
					<input type="hidden" name="edit"
						value=<%=(Boolean) request.getAttribute("hasuserid")%>>
					<button class="ui basic submit button">
						<i class="icon user"></i> 提交
					</button>
					<button type="button" class="ui basic button"
						onclick="deleteuser();"
						<%if (!(Boolean) request.getAttribute("hasuserid")) {
				out.print("style='display: none'");
			}%>>
						<i class="icon user"></i> 删除
					</button>

					<div id="almodal" class="ui small test modal transition hidden">
						<div class="header">删除用户</div>
						<div class="content">
							<p>确认删除该用户吗?</p>
						</div>
						<div class="actions">
							<div class="ui negative button">取消</div>
							<div class="ui positive right labeled icon button">
								确认 <i class="checkmark icon"></i>
							</div>
						</div>
					</div>
					<script type="text/javascript">
					function deleteuser() {
						$('#almodal').modal({
							closable : false,
							onApprove : function() {
								window.location.href = "useredit.do?delflg=true&deluserid=<%=(String) request.getAttribute("userid")%>";
												}

											}).modal('show');
						}
					</script>
				</form>
			</div>

		</div>
		<div class="column"></div>
	</div>

</body>