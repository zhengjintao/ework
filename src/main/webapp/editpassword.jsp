<!DOCTYPE html>
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

<style type="text/css">
body {
	background-color: #FFFFF0;
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
				password : {
					identifier : 'password',
					rules : [ {
						type : 'empty',
						prompt : '请输入密码'
					}, {
						type : 'length[5]',
						prompt : '密码必须是5位以上'
					} ]
				},
				newpassword : {
					identifier : 'newpassword',
					rules : [ {
						type : 'empty',
						prompt : '请输入新密码'
					}, {
						type : 'length[5]',
						prompt : '新密码必须是5位以上'
					} ]
				},
				repassword : {
					identifier : 'repassword',
					rules : [ {
						type : 'empty',
						prompt : '请输入新密码'
					}, {
						type : 'length[5]',
						prompt : '新密码必须是5位以上'
					} ]
				}
			}
		});
	});
</script>
</head>
<body>

	<div class="ui middle aligned center aligned grid">
		<div class="column">
			<div class="ui large center aligned ">
			</div>
			<form class="ui large form" action="editpassword.do" method="post">
				<div class="ui stacked segment">
					<div class="field">
						<div class="ui left icon input">
							<i class="lock icon"></i> <input type="password" name="password"
								placeholder="原密码">
						</div>
					</div>
					<div class="field">
						<div class="ui left icon input">
							<i class="lock icon"></i> <input type="password" name="newpassword"
								placeholder="新密码">
						</div>
					</div>
					<div class="field">
						<div class="ui left icon input">
							<i class="lock icon"></i> <input type="password" name="repassword"
								placeholder="重复输入新密码">
						</div>
					</div>
					<div class="ui fluid large yellow submit button">确认修改</div>
				</div>

				<div class="ui error message"></div>
				<input type="hidden" name="subKbn" value=<%=(String)request.getAttribute("subKbn") %>>
			</form>
		</div>
	</div>

</body>

</html>