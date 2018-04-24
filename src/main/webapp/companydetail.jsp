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
<script src="dist/components/form.min.js"></script>
<script src="dist/components/transition.min.js"></script>
<script src="dist/semantic.min.js"></script>

<script>
	function check() {
		return true;
	}
</script>
<style>
body {
    margin-top: 10px;
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
		
		<div class="ui teal segment">
				<div class="ui  breadcrumb">
					<a class="section" href="personal.do">个人</a>
					 <i class="right chevron icon divider"></i>
					<a class="section" href="companysetting.do">公司</a>
					 <i class="right chevron icon divider"></i>
					<div class="active section">公司信息</div>
				</div>
			</div>
			
			
    
       <div class="ui yellow inverted segment">
			    <a class="ui olive top attached label center aligned">公司信息</a>
				<div class="ui list">
					<div class="item">
						<img class="ui avatar image" src="<%=(String)request.getAttribute("imgurl") %>">
						<div class="content">
							<!-- <div class="description">00001</div> -->
							<div class="header" style='color:gray;margin-left:2px'><%=(String)request.getAttribute("companyid") %></div>
							<div class="description"><%=(String)request.getAttribute("companynm") %></div>
						</div>
					</div>
					<div class="item">
						 <%=(String) request.getAttribute("companyexp")%>
					</div>
				</div>
			
				<button class="ui basic button" onclick="logout()">
					<i class="icon remove user"></i><%=(String)request.getAttribute("btnname") %>
				</button>
				</div>
  </div>


		</div>

	</div>

</body>