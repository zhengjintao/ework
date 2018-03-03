
<%@ page import="java.util.List"%>
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

<style>
body {
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

	<div class="ui one column grid container">
		
		<div class="column">
		<div class="ui teal segment">
				<div class="ui  breadcrumb">
					<a class="section" href="home.do">首页</a> <i
						class="right chevron icon divider"></i>
					<div class="active section"><%=(String)request.getAttribute("name") %>一览</div>
				</div>
			</div>
			<form action="./useredit.do" method="post">
				<div class="ui teal inverted segment">

					<div class="ui middle aligned divided list">
						<%
							List<String[]> userinfo = (List<String[]>) request.getAttribute("userinfo");
							int inxnum =0;
							for (String[] each : userinfo) {
								out.print("<div class='item'>");
								out.print("<div class='right floated content'>");
								out.print("<div class='ui basic button' onclick='deleteuser(" + inxnum +")'><div style='color:white'>详情</div></div>");
								out.print("</div>");
								out.print("<small style='color:gray'>");
								out.print(each[5]);
								out.print("</small>");
								out.print("<div class='content'>");
								out.print(each[1]); // 姓名
								out.print("</div>");
								out.print("</div>");
								
								out.print("<p style='diplay:none'" + "id='id"+  inxnum +"'>");
								out.print(each[2]);
								out.print("</p>");
								inxnum++;
							}
						%>
					</div>
					<script type="text/javascript">
					<%
					
				    inxnum =0;
					for (String[] each : userinfo) {
						out.print("$('#id" + inxnum + "').hide();");
						inxnum++;
					}%>
					
					</script>

					<div class="ui small test modal transition hidden">
						<i class="close icon"></i>
						<div class="header">详细</div>
						<div class="content">
							<div class="ui form">
								<div class="field">
									<textarea id="content"
										style="margin-top: 0px; margin-bottom: 0px; height: 500px;"
										readonly="readonly"></textarea>
								</div>
							</div>
						</div>
					</div>
					<script type="text/javascript">
						function deleteuser(contentid) {
							
							<%
							out.print("var arr = new Array(" +userinfo.size() + ");");
						    inxnum =0;
							for (String[] each : userinfo) {
								out.print("arr[" + inxnum + "] = '#id" + inxnum + "';");
								inxnum++;
							}%>
							
							arr[1] = "#id0";
							arr[2] = "#id0";
							var content = $(arr[contentid]).html();
							

						$('#content').val(content);
							$('.ui.modal').modal({
								closable : false

							}).modal('show');
							
						}
					</script>
					</div>
			</form>
		</div>
	</div>
</body>