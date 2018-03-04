
<%@ page import="java.io.File"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.util.Properties"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Standard Meta -->
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

<!-- Site Properties -->
<title></title>
</head>
<body>

	<script type="text/javascript">
		
	<%Properties prop = new Properties();
			FileInputStream fileInput = null;
			String path = Thread.currentThread().getContextClassLoader().getResource("jdbc.properties").getPath();
			try {
				fileInput = new FileInputStream(path);
				prop.load(fileInput);
				if (prop.getProperty("jdbc.deploy") !=null) {
					out.print("window.location.href = 'login.do';");
				}else{
					out.print("window.location.href = 'deploy.do';");
				}
			} catch (IOException io) {
				System.out.println("Read file[jdbc.properties] failed.");
			}%>
		
	</script>
</body>
</html>