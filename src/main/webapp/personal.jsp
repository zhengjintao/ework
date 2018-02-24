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
<script src="dist/semantic.min.js"></script>

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
			<div class="ui yellow inverted segment">
			    <a class="ui olive top attached label center aligned">个人信息</a>
				<div class="ui list">
					<div class="item">
						<img class="ui avatar image" src="<%=(String)request.getAttribute("sex") %>">
						<div class="content">
							<!-- <div class="description">00001</div> -->
							<div class="header"><%=(String)request.getAttribute("userid") %></div>
							<div class="description"><%=(String)request.getAttribute("username") %></div>
						</div>
					</div>
				</div>
				<button class="ui basic button" onclick="logout()">
					<i class="icon remove user"></i>注销
				</button>
				<button class="ui basic button" onclick="editpwd()">
					<i class="icon key"></i>修改密码
				</button>

				<div class="ui small test modal transition hidden">
					<div class="header">注销</div>
					<div class="content">
						<p><%=(String)request.getAttribute("nickname") %>，确认退出登陆吗?</p>
					</div>
					<div class="actions">
						<div class="ui negative button">取消</div>
						<div class="ui positive right labeled icon button">
							确认 <i class="checkmark icon"></i>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					function logout() {
						$('.ui.modal').modal({
							closable : false,
							onApprove : function() {
								window.location.href = "logout.do";
							}

						}).modal('show');
					}
					
					function editpwd() {
						window.location.href = "editpassword.do";
					}
				</script>
			</div>

			<form action="./personal.do" method="post">
				<div class="ui teal inverted segment">
				    <a class="ui orange right ribbon label">默认时间</a>
					<div class="ui inverted form">
						<div class="two fields">
						<div class="one fields">
							<div class="field">
								<label>出勤</label> <input type="time" name="wbegin"
									value=<%=(String)request.getAttribute("begintime") %>><br>
							</div>
							<div class="field">
								<label>退勤</label> <input type="time" name="wend" value=<%=(String)request.getAttribute("endtime") %>><br>
							</div>
						</div>
						</div>
						
						<input type="hidden" name="subKbn" value="setting">
						<Button class="ui active teal button" type="submit">
							<i class="add to calendar icon"></i> 设定
						</Button>
					</div>
				</div>
				<div class="ui grey inverted segment">
				<a class="ui orange right ribbon label">统计信息</a>
				<div style="height=10px"></div>
					<div class="ui labeled button" tabindex="0">
						<div class="ui red button">
							<i class="checked calendar icon"></i> 本月出勤时间
						</div>
						<a class="ui basic red left pointing label"> <%=(String)request.getAttribute("hours") %>小时 </a>
					</div>
					<div style="height: 10px"></div>
					<div class="ui labeled button" tabindex="0">
						<div class="ui blue button">
							<i class="delete calendar icon"></i> 本月请假天数
						</div>
						<a class="ui basic left pointing blue label"> <%=(String)request.getAttribute("days") %>天 </a>
					</div>
				</div>
				
				<div class="ui brown segment">
				<a class="ui orange right ribbon label">功能模块</a>
				<div style="height=10px"></div>
					<div class="ui labeled button" tabindex="0">
						<div class="ui black button">
							<i class="edit icon"></i> <a class="header" href="editnotice.do?type=1">通知发布</a>
						</div>
						<!-- <a class="ui basic left pointing label"> <%=(String)request.getAttribute("hours") %> </a>-->
					</div>
					<div style="height: 10px"></div>
					<div class="ui labeled button" tabindex="0">
						<div class="ui black button">
							<i class="edit icon"></i><a class="header" href="editnotice.do?type=2">活动发布</a>
						</div>
						<!-- <a class="ui basic left pointing label"> <%=(String)request.getAttribute("days") %> </a> -->
					</div>
					<div style="height: 10px"></div>
					<div class="ui labeled button" tabindex="0">
						<div class="ui black button">
							<i class="edit icon"></i><a class="header" href="location.jsp">定位签到</a>
						</div>
						<!-- <a class="ui basic left pointing label"> <%=(String)request.getAttribute("days") %> </a> -->
					</div>
				</div>

			</form>
		</div>
	</div>

	<div style="height: 200px"></div>

	<footer>
		<div class="ui yellow four item menu">
			<a class="item" href="index.do"> <i class="home icon"></i> 首页
			</a> <a class="item" href="list.do"> <i class="calendar icon"></i>
				出勤
			</a> <a class="item" href="leave.do"> <i class="browser icon"></i>
				请假
			</a> <a class="active item" href="personal.do"> <i class="user icon"></i>
				个人
			</a>
		</div>
	</footer>
</body>
</html>
