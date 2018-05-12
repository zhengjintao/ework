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

<script type="text/javascript">
function checkdate(){
	var wdate = $("#wdate").val();
    var now = new Date(); 
    var dB = new Date(wdate);//获取当前选择日期  
     
    if (dB > now) {//时间戳对比 
    	$("#errmsg").html("不允许提前签到");
		$('#cmodal').modal({
			closable : false

		}).modal('show');
        return false;  
    }
    
    var wrest = $("#wrest").val();
	if (wrest == null || wrest == "undefine" || wrest.length == 0) {
    	$("#errmsg").html("休息时间必须输入");
		$('#cmodal').modal({
			closable : false

		}).modal('show');
        return false;  
	}

	var arr = wrest.split(".");
	var re = /^[0-9]+(.[0-9]{1})?$/;
	if (!re.test(wrest) || (arr.length > 1 && "5" != arr[1])) {
	    $("#errmsg").html("默认休息时间必须为0.5的倍数的数值");
		$('#cmodal').modal({
			closable : false
		}).modal('show');
	    return false;  
	}

    var t1 = now.getFullYear() * 12 + now.getMonth();
    var t2 = dB.getFullYear() * 12 + dB.getMonth();
    
    if (t1 - t2 > 1) {//时间戳对比 
    	$("#errmsg").html("只允许修改当月和前一月的数据");
		$('#cmodal').modal({
			closable : false

		}).modal('show');
        return false;  
    }
    
	var date1="1999-01-01 " + $("#wbegin").val();
	var date2="1999-01-01 " + $("#wend").val();
	var oDate1 = new Date(date1);
    var oDate2 = new Date(date2);
    if(oDate1.getTime() > oDate2.getTime()){
    	$("#errmsg").html("退勤时间必须大于出勤时间！");
		$('#cmodal').modal({
			closable : false

		}).modal('show');
        return false;
    }
    
    if(date1 > date2){
    	$("#errmsg").html("退勤时间必须大于出勤时间！");
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
	    url: "./list.do?" + $("form").serialize() + "&selectChg=1", 
	    dataType: "json", 
	    success: function (data) {
	    	$("#wbegin").val(data.defaultBeginTime)
	    	$("#wend").val(data.defaultEndTime)
	    	$("#wcomment").val(data.comment)
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
	    	$("#wcomment").val(data.comment)
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
   <script type="text/javascript">
	$(document).ready(function() {
		$('.ui.accordion').accordion();
		
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
			<p id="errmsg"><%=(String) request.getAttribute("errmsg")%>
			</p>
		</div>
	</div>
	<div class="ui one column grid container">
		<div class="column">
			<form action="./list.do" method="post" onsubmit="return checkdate();">
				<div class="ui teal inverted segment">
					<div class="ui inverted form">
						<div class="inline field">
							<div class="field">
								<label>日期</label> <input type="date" id="wdate" name="wdate" onchange="getSettedtime()"
									value=<%=(String) request.getAttribute("sysDate")%>>
							</div>
						</div>
						<div class="two field">
							<div class="one fields">
								<div class="field">
									<label>出勤时间</label> <input type="time" name="wbegin" id="wbegin"
										value=<%=(String) request.getAttribute("defaultBeginTime")%>><br>
								</div>

								<div class="field">
									<label>退勤时间</label> <input type="time" name="wend" id="wend"
										value=<%=(String) request.getAttribute("defaultEndTime")%>><br>
								</div>

								<div class="field">
									<label>休息时间</label> <input type="text" name="wrest" id="wrest" style="width:60px"
										value=<%=(String) request.getAttribute("defaultRestTime")%>><br>
								</div>
							</div>
						</div>
						<div class="field">
						   <div class="ui accordion" id="postionacc" style="display:none">
								<div class="active title" style="color:white;padding-top:0px;padding-bottom:0px">
									<i class="dropdown icon"></i> 地点
								</div>
								<div class="active content">
									<p class="transition visible"
										style="display: block !important;">
										<input type="text" id='latitude' name='latitude'></input>
			                            <input type="text" id='longitude' name='longitude'></input>
										<textarea id="dtladdress" readonly="readonly" name="dtladdress" rows="2" cols="0" placeholder="未获取到位置情報" onClick="openmap();"></textarea>
								</div>
						   </div>
							<div class="ui accordion" >
								<div class="title" style="color:white">
									<i class="dropdown icon"></i> 备注
								</div>
								<div class="content">
									<p class="transition visible"
										style="display: block !important;">
										<textarea id="wcomment" name="wcomment" rows="3" cols="0" placeholder="填写作业内容等（选填）"><%=(String) request.getAttribute("comment")%></textarea>
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
		<div class="column">
		</div>

	</div>
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
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	configWx();
	
	var a = navigator.userAgent.toLowerCase().match(/micromessenger\/(\d+\.\d+\.\d+)/) || navigator.userAgent.toLowerCase().match(/micromessenger\/(\d+\.\d+)/);

	if(!a && document.getElementById('dtladdress').textContent.length==0){
		getlocationUseNav();
	}

});
   function configWx() {
		var thisPageUrl = location.href.split('#')[0];
		$.ajax({ 
		    type: "post", 
		    url: "./getJsTicket.do?url="+ thisPageUrl, 
		    dataType: "json", 
		    success: function (data) {
		    	if (data != null) {
					configWeiXin(data.appId, data.timestamp, data.nonceStr,
							data.signature);
					
					wx.error(function (res) {
		                 $.scojs_message(res.errMsg, $.scojs_message.TYPE_ERROR); 
		             });
		              wx.ready(function(){
		            	  getLocationWx();
		            });
					
				} else {
					getlocationUseNav();
				}
		    }, 
		    error: function() {
		    	getlocationUseNav();
		    } 
		});
	}

	function configWeiXin(appId, timestamp, nonceStr, signature) {
		wx.config({
			debug : false,// 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			appId : appId,
			timestamp : timestamp,
			nonceStr : nonceStr,
			signature : signature,
			jsApiList: [
		        'checkJsApi',
		        'onMenuShareTimeline',
		        'onMenuShareAppMessage',
		        'onMenuShareQQ',
		        'onMenuShareWeibo',
		        'onMenuShareQZone',
		        'hideMenuItems',
		        'showMenuItems',
		        'hideAllNonBaseMenuItem',
		        'showAllNonBaseMenuItem',
		        'translateVoice',
		        'startRecord',
		        'stopRecord',
		        'onVoiceRecordEnd',
		        'playVoice',
		        'onVoicePlayEnd',
		        'pauseVoice',
		        'stopVoice',
		        'uploadVoice',
		        'downloadVoice',
		        'chooseImage',
		        'previewImage',
		        'uploadImage',
		        'downloadImage',
		        'getNetworkType',
		        'openLocation',
		        'getLocation',
		        'hideOptionMenu',
		        'showOptionMenu',
		        'closeWindow',
		        'scanQRCode',
		        'chooseWXPay',
		        'openProductSpecificView',
		        'addCard',
		        'chooseCard',
		        'openCard'
		      ],
		      success: function (res) {
		    	  getLocationWx();
		      },
		      fail: function(res) {
		    	  getlocationUseNav();
		        }
		});
	}
	
	function getLocationWx() {
	    wx.getLocation({
	      success: function (res) {
	    	  window.locationcache = res;
	    	  $('#latitude').val(res.latitude);
			$('#longitude').val(res.longitude);
	    	  var lurl = "https://maps.googleapis.com/maps/api/geocode/json?&key=AIzaSyAP42AskYza1DwaysKIXhoxKq3cvD8VS0Y&language=ja";
	  		lurl = lurl + "&latlng=" + res.latitude + "," + res.longitude;
	  		$.getJSON(lurl,function(result) {
	  							var inx = 0;
	  							$.each(
	  											result,
	  											function(i, field) {
	  												if (inx == 0) {
	  													inx = 1;
	  													document
														.getElementById('dtladdress').textContent = field[0].formatted_address;

	  												}
	  											});
	  						});

	      },
	      cancel: function (res) {
	        alert('用户拒绝授权获取地理位置');
	      },
	      fail: function(res) {
	    	  getlocationUseNav();
	        }
	    });
	  };

	  function getlocationUseNav(){
		  if (navigator.geolocation) {
				// Geolocationに関する処理を記述
				navigator.geolocation.getCurrentPosition(success, failure);
			} else {
				window.alert("本ブラウザではGeolocationが使えません");
			}
	  }
		// 成功時コールバック
		function success(pos) {
			// 緯度
			var lat = pos.coords.latitude;
			// 経度
			var lng = pos.coords.longitude;

			// htmlに描画
			$('#latitude').val(lat);
			$('#longitude').val(lng);
			var lurl = "https://maps.googleapis.com/maps/api/geocode/json?&key=AIzaSyAP42AskYza1DwaysKIXhoxKq3cvD8VS0Y&language=ja";
			lurl = lurl + "&latlng=" + lat + "," + lng;
			$.getJSON(
					lurl,
					function(result) {
						var inx = 0;
						$.each(
								result,
								function(i, field) {
									if (inx == 0) {
										inx = 1;
										document
												.getElementById('dtladdress').textContent = field[0].formatted_address;
									}
								});
								
					});
					
		}

		// 失敗時コールバック
		function failure(error) {
			var message = "位置情報取失敗";

			switch (error.code) {
			// 位置情報が取得できない場合
			case error.POSITION_UNAVAILABLE:
				message = "位置情報の取得ができませんでした。";
				break;
			// Geolocationの使用が許可されない場合
			case error.PERMISSION_DENIED:
				message = "位置情報取得の使用許可がされませんでした。";
				break;
			// タイムアウトした場合
			case error.PERMISSION_DENIED_TIMEOUT:
				message = "位置情報取得中にタイムアウトしました。";
				break;
			default:
			}
			//alert(message);
		}
		
		function openmap(){
			if(window.locationcache){
				wx.openLocation(window.locationcache); 
			}
		}
</script>
</html>
