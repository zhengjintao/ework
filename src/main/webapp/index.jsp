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

<style type="text/css">
body {
    margin-top: 10px;
	background-color: #FFFFFF;
}

body>.grid {
	height: 100%;
}

.segment>.lblheader {
	height: 20%;
}

.segment>.lblcontent {
	height: 53%;
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

			<div class="ui raised segment">
				<a class="ui red ribbon label">通知</a>
				<p class="lblheader">every one look here!</p>
				<a class="ui blue ribbon label">近期事件</a>
				<div class="lblcontent">Today is the Day of Accounting Test.</div>
			</div>

		</div>
	</div>

	<div style="height: 100px"></div>

	<footer>
		<div class="ui yellow four item menu">
			<a class="active item" href="index.do"> <i class="home icon"></i> 首页
			</a> <a class="item" href="list.do"> <i class="calendar icon"></i>
				出勤
			</a> <a class="item" href="leave.do"> <i class="browser icon"></i>
				请假
			</a> <a class="item" href="personal.do"> <i class="user icon"></i>
				个人
			</a>
		</div>
	</footer>
</body>
</html>
