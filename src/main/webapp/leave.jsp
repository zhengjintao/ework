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
			<form action="./addleave" method="post">
				<div class="ui yellow inverted segment">
					<div class="ui inverted form">
						<div class="inline field">
							<div class="field">
								<label>日期</label> <input type="date" name="wdate"
									value="2018-02-01">
							</div>
						</div>
						<div class="two fields">
							<div class="field">
								<label>理由</label> <input type="text" name="wcomment">
							</div>
						</div>
						<Button class="ui active teal button" type="submit">
							<i class="add to calendar icon"></i> 确定
						</Button>
					</div>

				</div>
			</form>

			<div class="ui grey inverted segment">
			    <a class="ui orange right ribbon label">当月请假</a>
				<input type="date" name="wdate" value="2018-02-01">

				<table class="ui celled table">
					<tbody>
						<tr>
							<td>2018/1/2</td>
							<td>Test</td>
						</tr>
						<tr>
							<td>2018/1/2</td>
							<td>Test</td>
						</tr>
						<tr>
							<td>2018/1/2</td>
							<td>Test</td>
						</tr>
						<tr>
							<td>2018/1/2</td>
							<td>Test</td>
						</tr>
					</tbody>
				</table>

			</div>
		</div>
	</div>
	<div style="height: 120px"></div>
	<footer>
		<div class="ui yellow four item menu">
			<a class="item" href="index.do"> <i class="home icon"></i> 首页
			</a> <a class="item" href="list.do"> <i class="calendar icon"></i>
				出勤
			</a> <a class="active item" href="leave.do"> <i class="browser icon"></i>
				请假
			</a> <a class="item" href="personal.do"> <i class="user icon"></i>
				个人
			</a>
		</div>
	</footer>
</body>
</html>
