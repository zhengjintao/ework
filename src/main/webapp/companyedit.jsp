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
		var userid = $("#companynm").val();

		if (userid == null || userid == "undefine" || userid.length == 0) {
			errmsg = errmsg + "名称必须输入。";
		}

		var username = $("#companyexp").val();
		if (username == null || username == "undefine" || username.length == 0) {
			if(errmsg.length > 0){
				errmsg = errmsg + "<br>";
			}
			errmsg = errmsg + "简介必须输入。";
		}
		
		var reason = $("#reason").val();
		if (reason == null || reason == undefined || reason.length == 0) {
			if(errmsg.length > 0){
				errmsg = errmsg + "<br>";
			}
			errmsg = errmsg + "备注必须输入。";
		}
		
		if(errmsg.length ==0){
			if (userid.length > 30 || userid.length < 5) {
				if(errmsg.length > 0){
					errmsg = errmsg + "<br>";
				}
				errmsg = errmsg + "名称长度不正确（必须5-30位）";
			}
			
			if (username.length > 20) {
				if(errmsg.length > 0){
					errmsg = errmsg + "<br>";
				}
				errmsg = errmsg + "姓名长度不正确（20位以内）";
			}
		}

		if (errmsg.length > 0) {
			$("#errmsg").html(errmsg);
			$('#cmodal').modal({
				closable : false

			}).modal('show');
			return false;
		}
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
   <script type="text/javascript">
	$(document).ready(function() {
		var message = "<%=(String) request.getAttribute("errmsg")%>";
		if (message != "null" && message.length > 0) {
			$('#cmodal').modal({
				closable : false

			}).modal('show');
		}});
	</script>
	<div id="cmodal" class="ui small test modal transition hidden">
		<i class="close icon"></i>
		<div class="content">
			<p id="errmsg"><%=(String) request.getAttribute("errmsg")%></p>
		</div>
	</div>
	<div class="ui one column grid container">
		<div class="column">
			<!-- <div class="ui teal segment">
				<div class="ui  breadcrumb">
					<a class="section" href="personal.do">个人</a> <i
						class="right chevron icon divider"></i> <a class="section"
						href="companysetting.do">公司</a> <i
						class="right chevron icon divider"></i>
					<div class="active section">创建公司</div>
				</div>
			</div> -->
			<div class="ui divider" style="margin:5px"></div>
		   <div class="ui basic buttons">
		    <a class="ui left attached button" href="companysetting.do">返回</a>
           <!-- <a class="right attached ui button" href="service.do?mode=detail">新規追加</a> -->
           </div>
			
			<div class="ui segment" style="margin-top:5px">
				<form action="companyedit.do" method="post" onsubmit="return check();">
				   <div class="ui middle aligned divided list">
						<div class="item" style="display:none">
							<div class="ui labeled input">
								<div class="ui label">编号</div>
								<input id="companyid" name="companyid" type="text" readonly="readonly"
									placeholder="系统自动生成" value=<%=(String) request.getAttribute("companyid")%>>
							</div>
						</div>
					</div>
					
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">名称</div>
								<input id="companynm" name="companynm" type="text"
									placeholder="必须（5～30位）" value=<%=(String) request.getAttribute("companynm")%>>
							</div>
						</div>
					</div>

					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">简介</div>
								<input id="companyexp" name="companyexp" type="text"
									placeholder="必须" value=<%=(String) request.getAttribute("companyexp")%>
									>
							</div>
						</div>
					</div>
					
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">备注</div>
								<input id="reason" name="reason" type="text"
									placeholder="填写开通理由（必须）" value=<%=(String) request.getAttribute("reason")%>
									>
							</div>
						</div>
					</div>
					
					<div class="ui inverted divider"></div>
					<input type="hidden" name="mode"
						value=<%=(String) request.getAttribute("mode")%>>
					<button class="ui basic submit button">
						<i class="icon add"></i> <%=(String) request.getAttribute("btnname")%>
					</button>
				</form>
			</div>

		</div>
		<div class="column"></div>
	</div>

</body>