<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<link rel="shortcut icon" href="images/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>footer</title>
</head>
<body>
	<!-- right ends -->
	<div class="bottom" style="height: 40px; border: #000 solid 0px;">
		<p>
			&copy;<span class="copyYear">2014</span> <a
				href="http://www.Rainet.com.cn/" target="_blank"
				title="河南锐利特信息技术有限公司">Rainet.com.cn</a> All Rights Reserved
			&nbsp;&nbsp;备案号：<a href="http://www.miitbeian.gov.cn/"
				target="_blank" title="豫ICP备14002834号">豫ICP备14002834号</a>
				<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1000484149'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s11.cnzz.com/z_stat.php%3Fid%3D1000484149%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script>
		</p>
	</div>
</body>
</html>