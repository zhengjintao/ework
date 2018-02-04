<!DOCTYPE html>
<html>
<head>
<!-- Standard Meta -->
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

<!-- Site Properties -->
<title>BWC ework</title>
<link rel="stylesheet" type="text/css" href="dist/components/reset.css">
<link rel="stylesheet" type="text/css" href="dist/components/site.css">

<link rel="stylesheet" type="text/css"
	href="dist/components/container.css">
<link rel="stylesheet" type="text/css" href="dist/components/grid.css">
<link rel="stylesheet" type="text/css" href="dist/components/header.css">
<link rel="stylesheet" type="text/css" href="dist/components/image.css">
<link rel="stylesheet" type="text/css" href="dist/components/menu.css">

<link rel="stylesheet" type="text/css"
	href="dist/components/divider.css">
<link rel="stylesheet" type="text/css"
	href="dist/components/segment.css">
<link rel="stylesheet" type="text/css" href="dist/components/form.css">
<link rel="stylesheet" type="text/css" href="dist/components/input.css">
<link rel="stylesheet" type="text/css" href="dist/components/button.css">
<link rel="stylesheet" type="text/css" href="dist/components/list.css">
<link rel="stylesheet" type="text/css"
	href="dist/components/message.css">
<link rel="stylesheet" type="text/css" href="dist/components/icon.css">

<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="dist/components/form.js"></script>
<script src="dist/components/transition.js"></script>

<style type="text/css">
body {
	background-color: #DADADA;
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
						prompt : 'Please enter your userid'
					} ]
				},
				password : {
					identifier : 'password',
					rules : [ {
						type : 'empty',
						prompt : 'Please enter your password'
					}, {
						type : 'length[5]',
						prompt : 'Your password must be at least 5 characters'
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
			<div class="ui middle center aligned ">
				<img class="ui large image" src="assets/images/logo.png">

			</div>
			<form class="ui large form" action="login.do">
				<div class="ui stacked segment">
					<div class="field">
						<div class="ui left icon input">
							<i class="user icon"></i> <input type="text" name="userid"
								placeholder="User">
						</div>
					</div>
					<div class="field">
						<div class="ui left icon input">
							<i class="lock icon"></i> <input type="password" name="password"
								placeholder="Password">
						</div>
					</div>
					<div class="ui fluid large teal submit button">Login</div>
				</div>

				<div class="ui error message"></div>

			</form>

			<div class="ui message" hidden>
				New to us? <a href="#">Sign Up</a>
			</div>
		</div>
	</div>

</body>

</html>