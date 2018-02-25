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
						class="right chevron icon divider"></i>
					<div class="active section">用户管理</div>
				</div>
			</div>
		</div>
		<div class="column" style="margin-top: -20px;">

			<div class="ui teal inverted segment">
				<form action="./location.jsp" method="post">
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="right floated content">
								<div class="ui button">编辑</div>
							</div>
							<img class="ui avatar image" src="assets/images/rachel.png">
							<div class="content">Lena</div>
						</div>
						<div class="item">
							<div class="right floated content">
								<div class="ui button">编辑</div>
							</div>
							<img class="ui avatar image"
								src="assets/images/rachel.png">
							<div class="content">Lindsay</div>
						</div>
						<div class="item">
							<div class="right floated content">
								<div class="ui button">编辑</div>
							</div>
							<img class="ui avatar image" src="assets/images/rachel.png">
							<div class="content">Mark</div>
						</div>
						<div class="item">
							<div class="right floated content">
								<div class="ui button">编辑</div>
							</div>
							<img class="ui avatar image"
								src="assets/images/rachel.png">
							<div class="content">Molly</div>
						</div>
					</div>
				</form>


			</div>

		</div>

	</div>
</body>