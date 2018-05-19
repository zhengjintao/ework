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
    margin-top: 10px;
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
   <script type="text/javascript">
	$(document).ready(function() {
		var message = "<%=(String) request.getAttribute("errmsg")%>";
		if (message != "null" && message.length > 0) {
			$('.ui.modal').modal({
				closable : false

			}).modal('show');
		}});
	</script>
	<div class="ui small test modal transition hidden">
	    <i class="close icon"></i>
		<div class="content">
			<p><%=(String) request.getAttribute("errmsg")%>
			</p>
		</div>
	</div>
	<div class="ui center aligned grid container">
		<div class="column">
		   <div class="ui left aligned teal segment">
				<div class="ui  breadcrumb">
					<a class="section"  href="personal.do">个人</a> <i class="right chevron icon divider"></i>
					<div class="active section">密码修改</div>
				</div>
			</div>
			<div class="ui large center aligned ">
			</div>
			<form class="ui large form" action="editpassword.do" method="post">
				<div class="ui stacked segment">
					<div class="field">
						<div class="ui left icon input">
							<i class="lock icon"></i> <input type="password" name="password"
								placeholder="原密码(初始密码为6个数字1)">
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