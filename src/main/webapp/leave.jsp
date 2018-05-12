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

<script src="jquery/jquery-3.1.1.min.js"></script>
<script src="dist/components/form.min.js"></script>
<script src="dist/components/transition.min.js"></script>
<script src="dist/semantic.min.js"></script>

<link id="bsdp-css" href="dist/datepicker/css/bootstrap-datepicker3.standalone.min.css" rel="stylesheet">
<script src="dist/datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="dist/datepicker/locales/bootstrap-datepicker.ja.min.js"></script>

<script type="text/javascript">
function checkdata(){
	var data = $('.datepicker');
	$('#pp').popup('hide all');
	var wdate = $('#wdate').val();
	if(wdate.length == 0){
    	$("#errmsg").html("未选择请假日期");
		$('#cmodal').modal({
			closable : false

		}).modal('show');
        return false;
    }
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
    return true;
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

function cancel(wdate)
{
	$('#pp').popup('hide all');
}
function ondelete(wdate)
{
	var swdate = wdate.toString();
	var fwdate = swdate.substring(0, 4) + "-"+ swdate.substring(4,6) + "-" + swdate.substring(6 ,8);
	$.ajax({ 
	    type: "post", 
	    url: "leave.do?"+ "wdate=" + fwdate + "&deleteFlg=1&subKbn=1", 
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
		function formatDate(d) {
		        month = '' + (d.getMonth() + 1),
		        day = '' + d.getDate(),
		        year = d.getFullYear();

		    if (month.length < 2) month = '0' + month;
		    if (day.length < 2) day = '0' + day;

		    return [year, month, day].join('-');
		}
		
		$('#sandbox-container div').datepicker({
			format: "yyyy-mm-dd",
		    language: "ja",
		    multidate: true,
		    daysOfWeekHighlighted: "0,6",
		    todayHighlight: true
		}).on("changeDate",function(e) {
	        // `e` here contains the extra attributes
	        var str ='';
	        var strlist ='';
	        var obj = e.dates;
	        var arr = new Array(e.dates.length);
	        for(i=0 ; i< e.dates.length; i++){
	        	var datestr = formatDate(obj[i]);
	        	arr[i] = datestr;
	        }
	        arr.sort();
	        for (k in arr)
	        {
	        	
	        	if(str!=''){
	        		str = str + ',' + arr[k];
	        		strlist = strlist + '\r\n' + arr[k];
	        	}else{
	        		str = arr[k];
	        		strlist = arr[k];
	        	}
	        	
	        }
			$('#wdate').val(str);
			$('#llist').val(strlist);
			
	    });
		
		$('#pp')
		  .popup({
		    popup : $('.custom.popup'),
		    on    : 'click'
		  })
		;
		
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
					<div id='sandbox-container' style='background-color:white;' align="center"><div> </div> </div>
			             <input type="hidden" id="wdate" name="wdate">
					</div>
					
					<!-- 	<div class="two fields">
							<div class="field">
								<input type="text" style="width:96%" id="wcomment" name="wcomment" placeholder="理由(必填)">
							</div>
						</div>
						
						-->
						<input type="hidden" name="subKbn" value="true">
						
						<div id= "pp"  class="some-wrapping-div ui active yellow button" >
							<i class="add to calendar icon"></i><span>确定</span>
						</div>
						<div class="ui custom popup top right transition hidden">
                            <div class="ui yellow inverted segment">
							<div class="ui inverted form">
							    <div class="field">
								 <textarea id='llist' rows=4 placeholder="选择请假日期" readonly="readonly"></textarea>
								</div>
								<div class="field">
								<input type="text" id="wcomment" name="wcomment" placeholder="输入理由(必填)"
										value=<%=(String)request.getAttribute("wcomment") %>>
								</div>
								<div class="inline field" >
									<button class="ui active yellow button" type="submit">
										OK
									</button>
									<button class="ui active yellow button" type="button" onclick='cancel();'>
										Cancel
									</button>
								</div>
							</div>
					    	</div>
                        </div>
					<!-- 
						<div class="ui active yellow button" onclick = "deleteData()" >
							<i class="trash icon"></i> Clear
						</div>
					 -->
					</div>
				</div>
			

			<div class="ui grey inverted segment">
			    <a class="ui orange ribbon label">当月请假</a>
				<input type="month"  id="wdate2" name="wdate2" value=<%=(String) request.getAttribute("sysDate2")%> onchange="getleaveinfomonth()">

				<table class="ui unstackable celled table">
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
