<html>
<head>
<!-- Standard Meta -->
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

<!-- Site Properties -->
<title>考勤系统</title>
<link rel="shortcut icon" type="image/png" href="favicon.ico">
<link rel="stylesheet" type="text/css" href="dist/semantic.min.css">

<script src="jquery/jquery-3.1.1.min.js"></script>
<script src="dist/components/form.min.js"></script>
<script src="dist/components/transition.min.js"></script>
<script src="dist/semantic.min.js"></script>

<script>
	function check() {
		var errmsg = "";
		var wbegin = $("#wbegin").val();

		if (wbegin == null || wbegin == "undefine" || wbegin.length == 0) {
			errmsg = errmsg + "默认出勤时间必须输入";

		}

		var wend = $("#wend").val();
		if (wend == null || wend == "undefine" || wend.length == 0) {
			if(errmsg.length > 0){
				errmsg = errmsg + "<br>";
			}
			errmsg = errmsg + "默认退勤时间必须输入";
		}

		var wrest = $("#wrest").val();
		if (wrest == null || wrest == "undefine" || wrest.length == 0) {
			if (errmsg.length > 0) {
				errmsg = errmsg + "<br>";
			}
			errmsg = errmsg + "默认休息时间必须输入";
		}

		if (errmsg.length == 0) {
			var arr = wrest.split(".");
			var re = /^[0-9]+(.[0-9]{1})?$/;
			if (!re.test(wrest) || (arr.length > 1 && "5" != arr[1])) {
				if (errmsg.length > 0) {
					errmsg = errmsg + "<br>";
				}
				errmsg = errmsg + "默认休息时间必须为0.5的倍数的数值";
			}
		}
		if (errmsg.length > 0) {
			$("#errmsg").html(errmsg);
			$('#cmodal').modal({
				closable : false

			}).modal('show');
			return false;
		}

		var date1 = "1999-01-01 " + $("#wbegin").val();
		var date2 = "1999-01-01 " + $("#wend").val();
		var oDate1 = new Date(date1);
		var oDate2 = new Date(date2);
		if (oDate1.getTime() > oDate2.getTime()) {
			$("#errmsg").html("默认退勤时间必须大于默认出勤时间！");
			$('#cmodal').modal({
				closable : false

			}).modal('show');
			return false;
		}

		if (date1 > date2) {
			$("#errmsg").html("退勤时间必须大于出勤时间！");
			$('#cmodal').modal({
				closable : false

			}).modal('show');
			return false;
		}
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
	<div id="cmodal" class="ui small test modal transition hidden">
		<i class="close icon"></i>
		<div class="content">
			<p id="errmsg"></p>
		</div>
	</div>
	<div class="ui one column grid container">
		<div class="column">
			<div class="ui yellow inverted segment">
			    <a class="ui olive top attached label center aligned">个人信息</a>
				<div class="ui list">
					<div class="item">
						<img class="ui avatar image" src="<%=(String)request.getAttribute("sex") %>">
						<div class="content">
							<!-- <div class="description">00001</div> -->
							<div class="header" style='color:gray;margin-left:2px'><%=(String)request.getAttribute("userid") %></div>
							<div class="description"><a style='color:gray' href="userInfoEdit.do?userid=<%=(String)request.getAttribute("userid") %>"><%=(String)request.getAttribute("username") %></a></div>
						</div>
					</div>
				</div>
				<button class="ui basic button" onclick="logout()">
					<i class="icon remove user"></i>注销
				</button>
				<button class="ui basic button" onclick="editpwd()">
					<i class="icon key"></i>修改密码
				</button>

				<div id="almodal" class="ui small test modal transition hidden">
					<div class="header">注销</div>
					<div class="content">
						<p>确认退出登陆吗?</p>
					</div>
					<div class="actions">
						<div class="ui negative button">取消</div>
						<div class="ui positive right labeled icon button">
							确认 <i class="checkmark icon"></i>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					function logout() {
						$('#almodal').modal({
							closable : false,
							onApprove : function() {
								window.location.href = "logout.do";
							}

						}).modal('show');
					}
					
					function editpwd() {
						window.location.href = "editpassword.do";
					}
					function userinfoedit() {
						window.location.href = "userInfoEdit.do";
					}
				</script>
				<div class="ui inverted divider"></div>
				 
				<a href=<%=(String)request.getAttribute("companyurl") %>><i class="home icon"></i><%=(String)request.getAttribute("companyname") %></a>
					
			</div>
			<form action="./personal.do" method="post" onsubmit="return check();">
			<div class="ui yellow inverted segment" style="margin-top:-10px;<%=(String)request.getAttribute("display") %>">
				<a class="ui orange right ribbon label">系统管理</a>
				<div style="height: 10px"></div>
					<div class="ui labeled button" tabindex="0">
						<div class="ui basic button">
							<i class="edit icon"></i> <a class="header" style="color:gray" href="companymanagement.do">公司审批</a>
						</div>
						<!-- <a class="ui basic left pointing label"> <%=(String)request.getAttribute("hours") %> </a>-->
					</div>
					<div style="height: 10px"></div>
					<div class="ui labeled button" tabindex="0">
						<div class="ui basic button">
							<i class="user icon"></i> <a class="header" style="color:gray" href="usermanagement.do">用户管理</a>
						</div>
						<!-- <a class="ui basic left pointing label"> <%=(String)request.getAttribute("hours") %> </a>-->
					</div>
					
					
					<!-- 
					<div style="height:10px"></div>
					<div class="ui labeled button" tabindex="0">
						<div class="ui green button">
							<i class="marker alternate icon"></i><a class="header" style="color:white" href="recordworktime.do">出退定位</a>
						</div>
						<a class="ui basic left pointing label"> <%=(String)request.getAttribute("days") %> </a>
					</div>
					 -->
				</div>
				<div class="ui yellow inverted segment" style="margin-top:-10px;<%=(String)request.getAttribute("superdiplay") %>">
				<a class="ui orange right ribbon label">超级管理</a>
				    <div style="height: 10px"></div>
					<div class="ui labeled button" tabindex="0">
						<div class="ui basic button">
							<i class="user icon"></i> <a class="header" style="color:gray" href="editnotice.do?type=1">系统通知</a>
						</div>
						<!-- <a class="ui basic left pointing label"> <%=(String)request.getAttribute("hours") %> </a>-->
					</div>
					<div style="height: 10px"></div>
					<div class="ui labeled button" tabindex="0">
						<div class="ui basic button">
							<i class="user icon"></i> <a class="header" style="color:gray" href="editnotice.do?type=2">系统活动</a>
						</div>
						<!-- <a class="ui basic left pointing label"> <%=(String)request.getAttribute("hours") %> </a>-->
					</div>
				</div>
				<div class="ui teal inverted segment" style="<%=(String)request.getAttribute("userdiplay") %>">
				    <a class="ui orange right ribbon label">默认时间</a>
					<div class="ui inverted form">
						<div class="two fields">
						<div class="one fields">
							<div class="field">
								<label>出勤时间</label> <input id="wbegin" type="time" name="wbegin"
									value=<%=(String)request.getAttribute("begintime") %>><br>
							</div>
							<div class="field">
								<label>退勤时间</label> <input id="wend" type="time" name="wend" value=<%=(String)request.getAttribute("endtime") %>><br>
							</div>
							<div class="field">
								<label>休息时间</label> 
								<input id="wrest" type="text" style="width:60px" name="wrest" value=<%=(String)request.getAttribute("wrest") %>><br>
							</div>
						</div>
						</div>
						
						<input type="hidden" name="subKbn" value="setting">
						<Button class="ui active teal button" type="submit">
							<i class="add to calendar icon"></i> 设定
						</Button>
					</div>
				</div>
				<div class="ui olive inverted segment" style="<%=(String)request.getAttribute("userdiplay") %>">
				<a class="ui orange right ribbon label">统计信息</a>
				   <div style="height:10px"></div>
					<div class="ui labeled button" tabindex="0">
						<a class="ui red button" href="userworkdetail.do?userid=<%=(String)request.getAttribute("userid") %>&username=<%=java.net.URLEncoder.encode((String)request.getAttribute("username"), "UTF-8") %>">
							<i class="checked calendar icon"></i> 本月出勤时间
						</a>
						<a class="ui basic red left pointing label"> <%=(String)request.getAttribute("hours") %>小时(<%=(String)request.getAttribute("days") %>天) </a>
					</div>
					<div style="height: 10px"></div>
					<div class="ui labeled button" tabindex="0">
						<a class="ui blue button" href="userworkdetail.do?userid=<%=(String)request.getAttribute("userid") %>&username=<%=java.net.URLEncoder.encode((String)request.getAttribute("username"), "UTF-8") %>">
							<i class="delete calendar icon"></i> 本月请假天数
						</a>
						<a class="ui basic left pointing blue label"> <%=(String)request.getAttribute("leavedays") %>天 </a>
					</div>
				</div>
				
				<div class="ui red segment" style="<%=(String)request.getAttribute("userdiplay") %>">
				<a class="ui orange right ribbon label">功能模块</a>
				   <div style="height: 10px"></div>
					<div class="ui labeled button" tabindex="0">
						<div class="ui violet button">
							<i class="edit icon"></i> <a class="header" style="color:white" href="statisticsWork.do">出勤统计</a>
						</div>
						<!-- <a class="ui basic left pointing label"> <%=(String)request.getAttribute("hours") %> </a>-->
					</div>
					<!--
				<div style="height:10px"></div>
					<div class="ui labeled button" tabindex="0">
						<div class="ui violet button">
							<i class="marker alternate icon"></i><a class="header" style="color:white" href="jsdemo.html">定位签到</a>
						</div>
						 <a class="ui basic left pointing label"> <%=(String)request.getAttribute("days") %> </a>
					</div>
				 	 -->
				   <div style="height:10px"></div>
					<div class="ui labeled button" tabindex="0">
						<div class="ui violet button">
							<i class="yen sign icon"></i><a class="header" style="color:white" href="moneysubmit.do?userid=<%=(String)request.getAttribute("userid") %>">经费报销</a>
						</div>
					</div>
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
			</a> <a class="item" href="leave.do"> <i class="browser icon"></i>
				请假
			</a> <a class="active item" href="personal.do"> <i class="user icon"></i>
				个人
			</a>
		</div>
	</footer>
</body>
</html>
