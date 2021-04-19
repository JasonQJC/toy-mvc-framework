<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="BASE" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>coffee management - create</title>
<script type="text/javascript"
	src="${BASE }/webjars/jquery/3.6.0/jquery.min.js" defer="defer"></script>
</head>
<body>

	<h1>create interface</h1>
	<button type="button" onclick="aaa()">create</button>
	<button type="button" onclick="update()">update</button>
	<button type="button" onclick="del()">delete</button>
	<%-- TODO --%>
	<script type="text/javascript">
		function aaa() {
			$.post("${BASE }/v2/coffee_create",{name:"sss",createBy:"dsadsa",price:18.23,remark:"dssda"},function(data){
				console.info(data);
			});
		}
		function update() {
			var settings = {
			  "url": "${BASE }/v2/coffee_edit",
			  "method": "PUT",
			  "timeout": 0,
			  "headers": {
			    "Content-Type": "text/plain"
			  },
			  "data": "name=sss&createBy=dsadsa&price=22.23&remark=dssda&id=1",
			};
	
			$.ajax(settings).done(function (response) {
			  console.log(response);
			});
		}
		function del() {
			var settings = {
			  "url": "${BASE }/v2/coffee_edit?id=1",
			  "method": "DELETE",
			  "timeout": 0,
			};

			$.ajax(settings).done(function (response) {
			  console.log(response);
			});
		}
	</script>
</body>
</html>