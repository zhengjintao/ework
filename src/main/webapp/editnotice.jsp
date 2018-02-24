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

			<form action="./editnotice.do" method="post">
				<div class="ui teal inverted segment">
				    <a class="ui orange ribbon label">发布<%=(String)request.getAttribute("title") %></a>
					<div class="ui inverted form">
						<textarea name="content" style="height:90%"><%=(String)request.getAttribute("content") %></textarea>
						
						<input type="hidden" name="subKbn" value=<%=(String)request.getAttribute("subKbn") %>>
						<div style="height:10px"></div>
						<Button class="ui active teal button" type="submit">
							<i class="edit icon"></i> 发布
						</Button>
					</div>
				</div>
			</form>

		</div>
	</div>
</body>
</html>
