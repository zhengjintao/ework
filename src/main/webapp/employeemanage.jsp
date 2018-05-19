<html ng-app="listApp">
<head>
<!-- Standard Meta -->
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">

<!-- Site Properties -->
<title>考勤系统</title>
<link rel="shortcut icon" type="image/png" href="favicon.ico">
<link rel="stylesheet" type="text/css" href="dist/semantic.min.css">

<style type="text/css">
body {
	-webkit-text-size-adjust: 100%;
	background-color: #FFFFFF;
}
</style>
<script>
initdata=<%=request.getAttribute("initdata") %>;
</script>

<script src="jquery/jquery-3.1.1.min.js"></script>
<script src="dist/semantic.min.js"></script>
<script src="angularjs/angular.min.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	  $('.menu .item')
	  .tab()
	;
  });
  
  var app = angular.module('listApp',[]);
  
  app.config(function($provide){
          
      $provide.factory("transFormFactory",function(){
          return {
              transForm : function(obj){
                  var str = [];  
                  for(var p in obj){  
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
                  } 
                  return str.join("&");  
              }
          };
      });
  });
  
app.controller('ListController', function($scope,$http,transFormFactory) {
  var list = this;
  list.companyid=initdata.companyid;
  list.currentPage = 1;
  list.totalPages=3;
  list.unsalegoods =  [
      {id:'00001', text:'learn AngularJS', img:'assets/images/companypic.jpg', reason:'199'}
      ];
  list.onsalegoods =  [
      {id:'00001', text:'learn AngularJS', img:'assets/images/companypic.jpg', reason:'199'}
      ];
  
  (function(){
  	
  	$scope.url =  "employeemanage.do";
  	var postdata = {'mode':'list', 'companyid':list.companyid};
      $http(
  		{
  			method:"POST",
  			url:$scope.url,
  			data:postdata,
  			transformRequest:transFormFactory.transForm,
  			headers:{'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
  		}).then(function (result) {
  			list.onsalegoods = result.data.onsalegoods;
  			list.unsalegoods = result.data.unsalegoods;
          }).catch(function (result) {
          	list.message = "SORRY!エラーが発生しました。";
          	$('.ui.basic.modal') .modal('show');
          });
      
  })();
  
  list.unsale = function(id, userid) {
  	$scope.url =  "employeemanage.do";
  	var postdata = {'mode':'apply', 'companyid': id, 'userid': userid};
      $http(
  		{
  			method:"POST",
  			url:$scope.url,
  			data:postdata,
  			transformRequest:transFormFactory.transForm,
  			headers:{'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
  		}).then(function (result) {
  			list.onsalegoods = result.data.onsalegoods;
  			list.unsalegoods = result.data.unsalegoods;
          }).catch(function (result) {
          	orderList.message = "SORRY!エラーが発生しました。";
          	$('.ui.basic.modal') .modal('show');
          });
  }
  
  list.onsale = function(id, userid) {
  	$scope.url =  "employeemanage.do";
  	var postdata = {'mode':'refuse', 'companyid': id, 'userid': userid};
      $http(
  		{
  			method:"POST",
  			url:$scope.url,
  			data:postdata,
  			transformRequest:transFormFactory.transForm,
  			headers:{'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
  		}).then(function (result) {
  			list.onsalegoods = result.data.onsalegoods;
  			list.unsalegoods = result.data.unsalegoods;
          }).catch(function (result) {
          	orderList.message = "SORRY!エラーが発生しました。";
          	$('.ui.basic.modal') .modal('show');
          });
  }
});
</script>

</head>
<body ng-controller="ListController as list" >
	<div class="ui container">
		<div class="row"></div>
		<div class="ui divider"></div>
		<div class="ui basic buttons">
		<a class="ui left attached button" href="companydetail.do">返回</a>
<!-- <a class="right attached ui button" href="service.do?mode=detail">新規追加</a> -->
</div>
		
		<div class="ui divider"></div>
		<div class="row">
			<div class="ui top attached tabular menu">
				<a class="item active" data-tab="first">待审核</a> <a class="item"
					data-tab="second" style="display:none">已批准</a>
			</div>
			<div class="ui bottom attached tab segment active" data-tab="first">
				<div class="ui relaxed divided list">
					<div class="item"  ng-repeat="good in list.onsalegoods">
						<img class="ui avatar image" src={{good.img}}>
						<div class="content">
							<div class="header">申请人：{{good.username}}</div>
						</div>
						<div class="right floated content">
						    <div class="ui button" ng-click="list.onsale(good.id, good.userid)">拒绝</div>
							<div class="ui button" ng-click="list.unsale(good.id, good.userid)">同意</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ui bottom attached tab segment" data-tab="second">
				<div class="ui relaxed divided list">
					<div class="item"  ng-repeat="unsalegood in list.unsalegoods">
						<img class="ui avatar image" src={{unsalegood.img}}>
						<div class="content">
							<div class="header">申请人：{{unsalegood.text}} 名称：{{unsalegood.text}}</div>
							<div class="header">理由：{{unsalegood.reason}}</div>
						</div>
						<!-- <div class="right floated content">
						   <div class="ui button" ng-click="list.onsale(unsalegood.id)">上架</div>
							<a class="ui button" href="service.do?mode=detail&id={{unsalegood.id}}">编辑</a>
						</div>
						 -->
					</div>
				</div>
			</div>

		</div>
	</div>
</body>
</html>
