<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    <head>

	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<c:if test="${!empty msg}">
	<script>
	alert("${msg}");
	</script>
	</c:if>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />
    </head>
    <body id="page-top">
 	<%@ include file="/WEB-INF/views/include/header.jsp" %>
        <!-- Header-->
        <header class="masthead text-center text-white">
            <div>
                <div class="container px-5">
                    <h1 class="animate__animated animate__bounce masthead-heading mb-0">Happy House</h1>
                    <h2 class="masthead-subheading mb-0 mt-4">당신 주변의 매물을 쉽게 찾아드려요</h2>
                    <c:if test="${!empty userinfo}">
                    <a class="btn btn-primary btn-xl rounded-pill mt-5" href="${root}/housedeal">매물 찾기</a>
                    </c:if>
                </div>
            </div>
        </header>
        <!-- Content section 1-->

        <!-- Footer-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
        <!-- Bootstrap core JS-->
        <!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script> -->
        <!-- Core theme JS-->

    </body>
</html>
