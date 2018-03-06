 <%@ page import="java.util.List" %> 
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

<script type="text/javascript">
function checkdata(){
	var comment = $("#wcomment").val();
	if(comment.length == 0){
    	$("#errmsg").html("请假理由必须输入");
		$('#cmodal').modal({
			closable : false

		}).modal('show');
        return false;
    }
    if(comment.length > 50){
    	$("#errmsg").html("请假理由超长（50字以内）");
		$('#cmodal').modal({
			closable : false

		}).modal('show');
        return false;
    }
}
function getSettedtime()
{	
	$.ajax({ 
	    type: "post", 
	    url: "leave.do?"+ $("form").serialize() + "&selectChg=1", 
	    dataType: "json", 
	    success: function (data) {
	    	$("#wcomment").val(data.comment);
	    	$("#oklabel").html(data.oklabel);
	    }, 
	    error: function() {
	            alert("网络异常，请稍后重试");
	    } 
	});
}

function getleaveinfomonth()
{	
	$.ajax({ 
	    type: "post", 
	    url: "leave.do?"+ $("form").serialize() + "&leaveinfo=1", 
	    dataType: "json", 
	    success: function (data) {
	    	$('#infobody').empty();
	    	$('#infobody').append(data.info);
	    }, 
	    error: function() {
	            alert("网络异常，请稍后重试");
	    } 
	});
}

function deleteData()
{
	window.location.href = "leave.do?"+ $("form").serialize() + "&deleteFlg=1";
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
   <script type="text/javascript">
	$(document).ready(function() {
		var message = "<%=(String) request.getAttribute("errmsg")%>";
		if (message != "null" && message.length > 0) {
			$('.ui.modal').modal({
				closable : false

			}).modal('show');
		}});
	</script>
	<div id="cmodal" class="ui small test modal transition hidden">
	    <i class="close icon"></i>
		<div class="content">
			<p id="errmsg"><%=(String) request.getAttribute("errmsg")%>
			</p>
		</div>
	</div>
	<div class="ui one column grid container">
		<div class="column">
			<form action="./leave.do" method="post" onsubmit="return checkdata();">
				<div class="ui yellow inverted segment">
					<div class="ui inverted form">
						<div class="inline field">
							<div class="field">
								<label>日期</label> <input type="date" id="wdate" name="wdate"
									value=<%=(String) request.getAttribute("sysDate")%> onchange="getSettedtime()">
							</div>
						</div>
						<div class="two fields">
							<div class="field">
								<label>理由</label> <input type="text" id="wcomment" name="wcomment" placeholder="必填项目" value=<%=(String) request.getAttribute("wcomment")%>>
							</div>
						</div>
						<input type="hidden" name="subKbn" value="true">
						<Button class="ui active yellow button" type="submit">
							<i class="add to calendar icon"></i><span id="oklabel"><%=(String) request.getAttribute("oklabel")%></span>
						</Button>
						<div class="ui active yellow button" onclick = "deleteData()" >
							<i class="trash icon"></i> 删除
						</div>
					</div>

				</div>
			

			<div class="ui grey inverted segment">
			    <a class="ui orange ribbon label">当月请假</a>
				<input type="month"  id="wdate2" name="wdate2" value=<%=(String) request.getAttribute("sysDate2")%> onchange="getleaveinfomonth()">

				<table class="ui celled table">
					<tbody id="infobody">
					<%=(String) request.getAttribute("info")%>
					</tbody>
				</table>

			</div>
			</form>
		</div>
		<div class="column">
		</div>
	</div>
	<footer>
		<div class="ui yellow four item menu">
			<a class="item" href="home.do"> <i class="home icon"></i> 首页
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
