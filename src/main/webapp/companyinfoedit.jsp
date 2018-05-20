<html ng-app="listApp">
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
initdata=<%=request.getAttribute("initdata") %>;
</script>

<script src="jquery/jquery-3.1.1.min.js"></script>
<script src="dist/semantic.min.js"></script>
<script src="angularjs/angular.min.js"></script>
<script type="text/javascript">
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
  list.errmessage ="";
  list.companyid="000000001"
  list.name ="株式会社";
  list.shortname="BWC";
  list.label ="株式会社tesut";
  list.telnum="080-01";
  list.address ="新书";
  list.site="www.baidu.com";
  list.types =  [
      {id:'00001', text:'learn AngularJS'}
      ];
  list.onsalegoods =  [
      {id:'00001', text:'learn AngularJS'}
      ];
  (function(){
  	
  	$scope.url =  "companyinfoedit.do";
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
  
  list.submit = function() {
  	$scope.url =  "companyinfoedit.do";
  	var postdata = {'mode':'submit'};
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
<body ng-controller="ListController as list">
	<div id="cmodal" class="ui small test modal transition hidden">
		<i class="close icon"></i>
		<div class="content">
			<p id="errmsg">{{list.errmessage}}</p>
		</div>
	</div>
	<div class="ui one column grid container">
		<div class="column">
		   <div class="ui divider" style="margin:5px"></div>
		   <div class="ui basic buttons">
		    <a class="ui left attached button" href="companydetail.do">返回</a>
          </div>
			<div class="ui segment"  style="margin-top:5px">
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">公司编号</div>
								<input id="euserid" name="euserid" type="text" readonly="readonly" ng-model="list.companyid">
							</div>
						</div>	
					</div>

					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">公司名称</div>
								<input id="eusername" name="eusername" type="text"
									placeholder="必须（请使用真名）" ng-model="list.name">
							</div>
						</div>
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">公司略称</div>
								<input id="eusername" name="eusername" type="text"
									placeholder="必须（请使用真名）" ng-model="list.shortname">
							</div>
						</div>
					</div>
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">公司简介</div>
								<input id="eusername" name="eusername" type="text"
									placeholder="必须（请使用真名）" ng-model="list.label">
							</div>
						</div>
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">联系电话</div>
								<input id="eusername" name="eusername" type="text"
									placeholder="必须（请使用真名）" ng-model="list.telnum">
							</div>
						</div>
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">公司地址</div>
								<input id="eusername" name="eusername" type="text"
									placeholder="必须（请使用真名）" ng-model="list.address">
							</div>
						</div>
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">官网链接</div>
								<input id="eusername" name="eusername" type="text"
									placeholder="必须（请使用真名）" ng-model="list.site">
							</div>
						</div>
					</div>
					<div class="ui middle aligned divided list">
						<div class="item">
							<div class="ui labeled input">
								<div class="ui label">公司类别</div>
								<select name="esex" class="ui search dropdown">
								    <option value="M">信息传输、计算机服务和软件业</option>
								    <option value="M">农、林、牧、渔业</option>
									<option value="s">采矿业</option>
									<option value="M">制造业</option>
									<option value="M">电力、热力、燃气及水的生产和供应业</option>
									<option value="M">环境和公共设施管理业</option>
									<option value="M">建筑业</option>
									<option value="M">交通运输、仓储业和邮政业</option>
									<option value="M">批发和零售业</option>
									<option value="M">住宿、餐饮业</option>
									<option value="M">金融、保险业</option>
									<option value="M">房地产业</option>
									<option value="M">租赁和商务服务业</option>
									<option value="M">科学研究、技术服务和地质勘查业</option>
									<option value="M">水利、环境和公共设施管理业</option>
									<option value="M">居民服务和其他服务业</option>
									<option value="M">教育</option>
									<option value="M">卫生、社会保障和社会服务业</option>
									<option value="M">文化、体育、娱乐业</option>
									<option value="M">综合（含投资类、主业不明显）</option>
									<option value="M">其它</option>
								</select>
							</div>
						</div>
					</div>
					
					<button class="ui basic submit button" ng-click="list.submit()">
						<i class="icon user"></i> 提交
					</button>
			</div>

		</div>
		<div class="column"></div>
	</div>

</body>