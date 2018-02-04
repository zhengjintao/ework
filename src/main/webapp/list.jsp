<html>
<head>
<!-- Standard Meta -->
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

<!-- Site Properties -->
<title>BWC ework</title>
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
			<form action="./addwork" method="post">
				<div class="ui teal inverted segment">
					<div class="ui inverted form">
						<div class="inline field">
							<div class="field">
								<label>Date</label> <input type="date" name="wdate"
									value="2018-02-01">
							</div>
						</div>
						<div class="two fields">
							<div class="field">
								<label>Begin</label> <input type="time" name="wbegin"
									value="09:00"><br>
							</div>
							<div class="field">
								<label>End</label> <input type="time" name="wend" value="17:00"><br>
							</div>
						</div>

						<Button class="ui active teal button" type="submit">
							<i class="add to calendar icon"></i> Add
						</Button>
					</div>
				</div>
			</form>
			<div class="ui grey inverted segment">
				<input type="date" name="wdate" value="2018-02-01">
				<table class="ui celled table">
					<tbody>
						<tr>
							<td>2018/1/2 09:00 ~ 17:30</td>
						</tr>
						<tr>
							<td>2018/1/2 09:00 ~ 17:30</td>
						</tr>
						<tr>
							<td>2018/1/2 09:00 ~ 17:30</td>
						</tr>
						<tr>
							<td>2018/1/2 09:00 ~ 17:30</td>
						</tr>
					</tbody>
				</table>


			</div>
		</div>

	</div>
	<div style="height: 120px"></div>
	<footer>
		<div class="ui yellow four item menu">
			<a class="item" href="index.jsp"> <i class="home icon"></i> Home
			</a> <a class="active item" href="list.jsp"> <i class="calendar icon"></i>
				Work
			</a> <a class="item" href="leave.jsp"> <i class="browser icon"></i>
				Leave
			</a> <a class="item" href="person.jsp"> <i class="user icon"></i>
				Personal
			</a>
		</div>
	</footer>
</body>
</html>
