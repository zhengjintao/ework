<html>
<head>
<!-- Standard Meta -->
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

<!-- Site Properties -->
<title>BWC考勤系统</title>
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
				<div class="ui list">
					<div class="item">
						<img class="ui avatar image" src="assets/images/christian.jpg">
						<div class="content">
							<!-- <div class="description">00001</div> -->
							<div class="header">teikintou</div>
							<div class="description">zhengjintao</div>
						</div>
					</div>
				</div>
				<button class="ui basic button" onclick="logout()">
					<i class="icon remove user"></i>注销
				</button>

				<div class="ui small test modal transition hidden">
					<div class="header">注销</div>
					<div class="content">
						<p>骚年，确认退出登陆吗?</p>
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
								window.location.href = "logout";
							}

						}).modal('show');
					}
				</script>
			</div>

			<form action="./addwork" method="post">
				<div class="ui teal inverted segment">
					<div class="ui inverted form">
						<div class="two fields">
							<div class="field">
								<label>默认出勤时间</label> <input type="time" name="wbegin"
									value="09:00"><br>
							</div>
							<div class="field">
								<label>默认退勤时间</label> <input type="time" name="wend" value="17:00"><br>
							</div>
						</div>

						<Button class="ui active teal button" type="submit">
							<i class="add to calendar icon"></i> 设定
						</Button>
					</div>
				</div>
				<div class="ui grey inverted segment">
					<div class="ui labeled button" tabindex="0">
						<div class="ui red button">
							<i class="checked calendar icon"></i> 本月出勤时间
						</div>
						<a class="ui basic red left pointing label"> 148h </a>
					</div>
					<div style="height: 10px"></div>
					<div class="ui labeled button" tabindex="0">
						<div class="ui blue button">
							<i class="delete calendar icon"></i> 本月请假天数
						</div>
						<a class="ui basic left pointing blue label"> 2day </a>
					</div>
				</div>

			</form>
		</div>
	</div>

	<div style="height: 100px"></div>

	<footer>
		<div class="ui yellow four item menu">
			<a class="item" href="index.jsp"> <i class="home icon"></i> 首页
			</a> <a class="item" href="list.jsp"> <i class="calendar icon"></i>
				出勤
			</a> <a class="item" href="leave.jsp"> <i class="browser icon"></i>
				请假
			</a> <a class="active item" href="personal.jsp"> <i class="user icon"></i>
				个人
			</a>
		</div>
	</footer>
</body>
</html>
