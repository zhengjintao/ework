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
<script type="text/javascript">
function getSettedtime()
{
	$.ajax({ 
	    type: "post", 
	    url: "./list.do?" + $("form").serialize() + "&selectChg=1", 
	    dataType: "json", 
	    success: function (data) {
	    	$("#wbegin").val(data.defaultBeginTime)
	    	$("#wend").val(data.defaultEndTime)
	    	$("#actionname").html("1" == data.settedFlg ? "已签" : "签到")
	    	$("#week0").html(data.week0)
	    	$("#week1").html(data.week1)
	    	$("#week2").html(data.week2)
	    	$("#week3").html(data.week3)
	    	$("#week4").html(data.week4)
	    	$("#week5").html(data.week5)
	    	$("#week6").html(data.week6)
	    }, 
	    error: function() {
	            alert("网络异常，请稍后重试");
	    } 
	});
}

function deleteData()
{
	$.ajax({ 
	    type: "post", 
	    url: "./list.do?" + $("form").serialize() + "&deleteFlg=1", 
	    dataType: "json", 
	    success: function (data) {
	    	$("#wbegin").val(data.defaultBeginTime)
	    	$("#wend").val(data.defaultEndTime)
	    	$("#actionname").html("1" == data.settedFlg ? "已签" : "签到")
	    	$("#week0").html(data.week0)
	    	$("#week1").html(data.week1)
	    	$("#week2").html(data.week2)
	    	$("#week3").html(data.week3)
	    	$("#week4").html(data.week4)
	    	$("#week5").html(data.week5)
	    	$("#week6").html(data.week6)
	    }, 
	    error: function() {
            alert("网络异常，请稍后重试");
	    } 
	});
}
</script>

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
			<form action="./list.do" method="post">
				<div class="ui teal inverted segment">
					<div class="ui inverted form">
						<div class="inline field">
							<div class="field">
								<label>日期</label> <input type="date" name="wdate" onchange="getSettedtime()"
									value=<%=(String) request.getAttribute("sysDate")%>>
							</div>
						</div>
						<div class="two fields">
							<div class="one fields">
								<div class="field">
									<label>出勤时间</label> <input type="time" name="wbegin" id="wbegin"
										value=<%=(String) request.getAttribute("defaultBeginTime")%>><br>
								</div>

								<div class="field">
									<div style="width: 20px; vertical-align: middle;"></div>
								</div>

								<div class="field">
									<label>退勤时间</label> <input type="time" name="wend" id="wend"
										value=<%=(String) request.getAttribute("defaultEndTime")%>><br>
								</div>
							</div>
						</div>
						<input type="hidden" name="subKbn" value="true">
						<Button class="ui active teal button">
							<i class="add to calendar icon"></i> <span id="actionname"><%=(String) request.getAttribute("qiandao")%></span>
						</Button>
						<div class="ui active teal button" onclick = "deleteData()" >
							<i class="trash icon"></i> 删除
						</div>
					</div>
				</div>
			<div class="ui grey inverted segment">
				<a class="ui orange ribbon label">当周出勤</a> <input type="date"
					name="wdate2" value=<%=(String) request.getAttribute("sysDate")%> onchange="getSettedtime()">
				<table class="ui celled table">
					<tbody>
						<tr>
							<td id= "week0"><%=(String) request.getAttribute("week0")%></td>
						</tr>
						<tr>
							<td id= "week1"><%=(String) request.getAttribute("week1")%></td>
						</tr>
						<tr>
							<td id= "week2"><%=(String) request.getAttribute("week2")%></td>
						</tr>
						<tr>
							<td id= "week3"><%=(String) request.getAttribute("week3")%></td>
						</tr>
						<tr>
							<td id= "week4"><%=(String) request.getAttribute("week4")%></td>
						</tr>
						<tr>
							<td id= "week5"><%=(String) request.getAttribute("week5")%></td>
						</tr>
						<tr>
							<td id= "week6"><%=(String) request.getAttribute("week6")%></td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		</div>

	</div>
	<div style="height: 120px"></div>
	<footer>
		<div class="ui yellow four item menu">
			<a class="item" href="home.do"> <i class="home icon"></i> 首页
			</a> <a class="active item" href="list.do"> <i class="calendar icon"></i>
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
