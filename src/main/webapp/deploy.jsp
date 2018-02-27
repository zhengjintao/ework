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

<script>
	function check() {
		return true;
	}
</script>
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
		<div class="column" style="margin-top: 10px;">

			<div class="ui teal inverted segment">
				<form action="deploy.html" method="post" onsubmit="return check();">
				<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">配置数据库信息</div>
							</div>
						</div>
					</div>
				
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">地址</div>
								<input id="address" name="address" type="text"
									placeholder="必须"
									value=<%=(String) request.getAttribute("address") %>>
							</div>
						</div>
					</div>

                    <div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">端口</div>
								<input id="port" name="port" type="text"
									placeholder="必须"
									value=<%=(String) request.getAttribute("port") %>>
							</div>
						</div>
					</div>
					
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">名称</div>
								<input id="dbname" name="dbname" type="text"
									placeholder="必须"
									value=<%=(String) request.getAttribute("dbname") %>>
							</div>
						</div>
					</div>
					
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">用户</div>
								<input id="userid" name="userid" type="text"
									placeholder="必须"
									value=<%=(String) request.getAttribute("userid") %>>
							</div>
						</div>
					</div>
					
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">密码</div>
								<input id="password" name="password" type="text"
									placeholder="必须"
									value=<%=(String) request.getAttribute("password") %>>
							</div>
						</div>
					</div>
					<div class="ui inverted divider"></div>
					<input type="hidden" name="edit"
						value=true>
					<button class="ui basic submit button">
						<i class="icon user"></i> 提交
					</button>
					
				</form>
			</div>

		</div>

	</div>

</body>