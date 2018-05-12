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

<script src="jquery/jquery-3.1.1.min.js"></script>
<script src="dist/components/form.min.js"></script>
<script src="dist/components/transition.min.js"></script>
<script src="dist/semantic.min.js"></script>

<script>
	function check() {
		return true;
	}
	
	function addcompany() {
	  var aurl='companyedit.do';
	  var companynm = $('#companynm').val();
	    if(companynm != null && companynm.length >0 ){
	    	aurl = aurl + "?companynm=" + companynm;
	    }
		return $(location).prop('href',aurl);
	    
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
					<a class="section" href="personal.do">个人</a> <i
						class="right chevron icon divider"></i>
					<div class="active section">公司</div>
				</div>
			</div>
			
			<div class="ui teal inverted segment">
				<form action="" method="post" onsubmit="return check();">
				
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui action input">
								<input type="text" id="companynm" name="companynm" placeholder="搜索公司..." value=<%=(String) request.getAttribute("companynm")%>>
								<button class="ui icon button">
									<i class="search icon"></i>
								</button>
								<button type="button" class="ui icon button" style="margin-left:1px" onclick="addcompany();">
									<i class="add icon"></i>申请开通
								</button>
							</div>
							
						</div>
					</div>


                    <%=(String) request.getAttribute("scompnies")%>
					

					<div class="ui middle aligned divided list">
					<div class="item">
					<a class="ui red  label center aligned">热门</a>
					</div>
					<%=(String) request.getAttribute("hotcompnies")%>
						
					</div>

				</form>
			</div>

		</div>

	</div>

</body>