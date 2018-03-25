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
   $(document).ready(function() {
	var message = "<%=(String) request.getAttribute("errmsg")%>";
		if (message != "null" && message.length > 0) {
			$('#cmodal').modal({
				closable : false
			}).modal('show');
		}
	});

	function check() {
		var errmsg = "";
		var subkbn=document.getElementById("subKbnselect").selectedIndex;
		var stationf = $("#stationf").val();
		var stationt = $("#stationt").val();
		if (subkbn== "0" && ((stationf == null || stationf == "undefine" || stationf.length == 0) 
				|| (stationt == null || stationt == "undefine" || stationt.length == 0))) {
			if (errmsg.length > 0) {
				errmsg = errmsg + "<br>";
			}
			errmsg = errmsg + "起始车站必须输入。";
		}
		var money = $("#money").val();
		if (money == null || money == "undefine" || money.length == 0) {
			if (errmsg.length > 0) {
				errmsg = errmsg + "<br>";
			}
			errmsg = errmsg + "费用必须输入。";
		}

		if (errmsg.length > 0) {
			$("#errmsg").html(errmsg);
			$('#cmodal').modal({
				closable : false

			}).modal('show');
			return false;
		}
	}

function selectChanged(obj){
	var selectId = obj.selectedIndex;
	if(selectId ==0){
		$("#dstation").show();
	}else if(selectId == 1){
		$("#dstation").hide();
		}
}

function getexpinfomonth()
{	
	$.ajax({ 
	    type: "post", 
	    url: "./moneysubmit.do?"+ $("form").serialize() + "&expinfo=1", 
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

function ondelete(dtlno)
{
	$.ajax({ 
	    type: "post", 
	    url: "./moneysubmit.do?"+ $("form").serialize() + "&dtlno=" + dtlno + "&delflg=1", 
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
	<div id="cmodal" class="ui small test modal transition hidden">
		<i class="close icon"></i>
		<div class="content">
			<p id="errmsg"><%=(String) request.getAttribute("errmsg")%></p>
		</div>
	</div>
	<div class="ui one column grid container">
		<div class="column">
			<div class="ui teal segment">
				<div class="ui  breadcrumb">
					<a class="section" href="personal.do">个人</a> <i
						class="right chevron icon divider"></i>
					<div class="active section">经费报销</div>
				</div>
			</div>

			<form action="./moneysubmit.do" method="post" onsubmit="return check();">
				<div class="ui teal inverted segment">
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">类别</div>
								<select  id="subKbnselect" onchange="selectChanged(this)" name="subKbn"
									class="ui search dropdown" style='width: 185px'>
									<option value="交通费">交通费</option>
									<option value="其他">其他</option>
								</select>
							</div>
						</div>
					</div>

					<!-- 面试和其他选择的情况 -->
					<div id="dsubdate" class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input" style='width: 235px'>
								<div class="ui label">日期</div>
								<input type="date" name="subdate"
									value=<%=(String) request.getAttribute("nowdate")%>>
							</div>
						</div>
					</div>

					<!-- 交通费选择的情况 -->
                    <div class="ui form" id= dstation>
                      <div class="inline fields">
                        <div class="field">
							<div class="ui labeled input">
								<div class="ui label">区间</div>
								<input style="width:85px" id="stationf" name="stationf" type="text" value="" placeholder="起始站">
							</div>
                        </div>
                        <div class="field">
                         <input style="width:86px" id="stationt" name="stationt" type="text" value="" placeholder="终点站">
                        </div>
                      </div>
                    </div>
					
					<div id="dsubmoney" class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">费用</div>
								<input id="money" name="money" type="text" value="" placeholder="必填项目	">
							</div>
						</div>
					</div>
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">备注</div>
								<input id="notes" name="notes" type="text" value="">
							</div>
						</div>
					</div>
					<button class="ui basic submit button">
						<i class="icon user"></i> 提交
					</button>
				</div>
				<div class="ui grey inverted segment">
					<a class="ui orange ribbon label">当月报销</a> <input type="month"
						id="wdate2" name="wdate2"
						value=<%=(String) request.getAttribute("sysDate2")%>
						onchange="getexpinfomonth()">

					<table class="ui unstackable celled table">
						<tbody id="infobody">
							<%=(String) request.getAttribute("info")%>
						</tbody>
					</table>

				</div>
				<input type="hidden" name="userid" value=<%=(String) request.getAttribute("userid")%>>
				<input type="hidden" name="saveFlg" value="1">
			</form>
		</div>
		<div class="column"></div>
	</div>
</body>