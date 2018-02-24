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
<style>
body {
  font: 12px sans-serif;
}

.map {
    width: 400px;
    height: 400px;
}
</style>
<script type="text/javascript" >
if (navigator.geolocation) {
    // Geolocationに関する処理を記述
    navigator.geolocation.getCurrentPosition(success, failure);
} else {
    window.alert("本ブラウザではGeolocationが使えません");
}

// 成功時コールバック
function success(pos) {
    // 緯度
    var lat = pos.coords.latitude;
    // 経度
    var lng = pos.coords.longitude;
    
    // htmlに描画
    document.getElementById('latitude').textContent = lat;
    document.getElementById('longitude').textContent = lng;
    var lurl = "https://maps.googleapis.com/maps/api/geocode/json?&key=AIzaSyAP42AskYza1DwaysKIXhoxKq3cvD8VS0Y&language=ja";
    lurl = lurl + "&latlng=" + lat + "," + lng;
    $.getJSON(lurl,function(result){
    	var inx=0;
        $.each(result, function(i, field){
        	if(inx == 0){
        		inx = 1;
        		document.getElementById('address').textContent = field[0].formatted_address;
        	}
        });
      });
    
    // 地図に埋め込み
    var latlng = new google.maps.LatLng(lat, lng);
    var myOptions={
        zoom: 14,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map=new google.maps.Map(document.getElementById('map_canvas'), myOptions);
    
    // 地図にピンをたてる
    var marker1= new google.maps.Marker({
        position: latlng,
        title: 'ここにいる'
	});
    marker1.setMap(map);
}

// 失敗時コールバック
function failure(error) {
    var message = "";
    
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
    window.alert(message);
}
</script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyAP42AskYza1DwaysKIXhoxKq3cvD8VS0Y"></script>
<body>
緯度：<span id='latitude'></span><br>
経度：<span id='longitude'></span><br>
地址：<span id='address'></span><br>
<div id="map_canvas" class="map"></div>
</body>