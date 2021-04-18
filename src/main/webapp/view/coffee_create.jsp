<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="BASE" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>coffee management - create</title>
    <script type="text/javascript" src="${BASE }/webjars/jquery/3.6.0/jquery.min.js" defer="defer"></script>
</head>
<body>

<h1>create interface</h1>
	<button type="button" onclick="aaa()">create</button>
<%-- TODO --%>
	<script type="text/javascript">
		function aaa() {
			$.post("${BASE }/v2/coffee_create",{name:"sss",createBy:"dsadsa",price:18.23,remark:"dssda"},function(data){
				console.info(data);
			});
		}
	</script>
</body>
</html>