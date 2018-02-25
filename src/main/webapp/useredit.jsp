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
<style>
body {
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
			<div class="ui teal segment">
				<div class="ui  breadcrumb">
					<a class="section" href="personal.do">个人</a> <i
						class="right chevron icon divider"></i> <a class="section"
						href="personal.do">用户管理</a> <i class="right chevron icon divider"></i>
					<div class="active section">用户编辑</div>
				</div>
			</div>
		</div>
		<div class="column" style="margin-top: -20px;">

			<div class="ui teal inverted segment">
				<form action="useredit.do" method="post">
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">账号</div>
								<input type="text" placeholder="必须（5～30位）">
							</div>
						</div>
					</div>
					<div class="ui inverted divider"></div>


					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">姓名</div>
								<input type="text" placeholder="必须（请使用真名）">
							</div>
						</div>
					</div>
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">密码</div>
								<input type="password" placeholder="必须（5～30位）" value="111111"
									readonly="readonly">
								<div class="ui left floated  segment">
									<input type="checkbox">
								</div>
							</div>
						</div>
					</div>
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">性别</div>
								<select class="ui search dropdown">
									<option value="M">帅锅</option>
									<option value="F">镁铝</option>

								</select>
							</div>
						</div>
					</div>
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">权限</div>
								<select class="ui search dropdown">
									<option value="1">管理员</option>
									<option value="2">一般用户</option>

								</select>
							</div>
						</div>
					</div>

					<div class="ui inverted divider"></div>
					<button class="ui basic button">
						<i class="icon user"></i> 提交
					</button>
				</form>
			</div>

		</div>

	</div>

</body>