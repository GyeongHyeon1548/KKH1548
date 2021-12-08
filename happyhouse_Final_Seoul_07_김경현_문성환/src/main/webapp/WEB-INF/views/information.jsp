<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <%@ include file="/WEB-INF/views/include/head.jsp" %>
        <script defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAbdZV0vY1kNPrgI63PR63Hj8sWF4IoT4E&callback=initMap"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />
        <link href="../css/information.css" rel="stylesheet" />
    </head>
    <body id="page-top">
    <script type="text/javascript">
	$(document).ready(function() {
		
		//공지 목록
		$.ajax({
			url:'${root}/admin/info',  
			type:'GET',
			contentType:'application/json;charset=utf-8',
			dataType:'json',
			success:function(users) {
				makeList(users);
			},
			error:function(xhr, status, error){
				console.log("상태값 : " + xhr.status + "\tHttp 에러메시지 : " + xhr.responseText);
			}	
		});
		
		//공지 등록 모달 실행
		$("#inforegiBtn").click(function() {
			$("#infoRegiModal").modal();
		});
		
		//공지 등록
		$("#registerBtn").click(function() {
			let registerinfo = JSON.stringify({
				"subject" : $("#regisubject").val(), 
				"content" : $("#regicontent").val()
			   });
			$.ajax({
				url:'${root}/admin/info',  
				type:'POST',
				contentType:'application/json;charset=utf-8',
				dataType:'json',
				data: registerinfo,
				success:function(info) {
					$("#regisubject").val(''), 
					$("#regicontent").val(''), 
					$("#infoRegiModal").modal("hide");
					makeList(info);
				},
				error:function(xhr,status,msg){
					console.log("상태값 : " + status + " Http에러메시지 : "+msg);
				}
			});
		});
		
		//공지 내용 보기
		$(document).on("dblclick", "div.view", function() {
			let vnum = $(this).attr("info-num");
			$.ajax({
				url:'${root}/admin/info/get/' + vnum,  
				type:'GET',
				contentType:'application/json;charset=utf-8',
				success:function(info) {
					$("#vnum").html(info.num);
					$("#vsubject").html(info.subject);
					$("#vcontent").html(info.content);
					$("#vregtime").html(info.regtime);
					$("#infodelBtn").val(info.num);
					$("#infomodiBtn").val(info.num);
					$("#infoViewModal").modal();
				},
				error:function(xhr,status,msg){
					console.log("상태값 : " + status + " Http에러메시지 : "+msg);
				}				
			});			
		});
		
		// 공지 수정 보기.
		$(document).on("click", "#modiBtn", function() {
			$("#modisubject").val($("#vsubject").html())
			$("#modicontent").val($("#vcontent").html())
			$("#infoViewModal").modal("hide");
			$("#infoModiModal").modal();
		});
		
		// 공지 수정 실행.
		$(document).on("click", "#infomodiBtn", function() {
			let num = $(this).val();
			let modifyinfo = JSON.stringify({
						"num" : num, 
						"subject" : $("#modisubject").val(), 
						"content" : $("#modicontent").val()
					   });
			$.ajax({
				url:'${root}/admin/info',  
				type:'PUT',
				contentType:'application/json;charset=utf-8',
				dataType:'json',
				data: modifyinfo,
				success:function(info) {
					$("#infoModiModal").modal("hide");
					makeList(info);
				},
				error:function(xhr,status,msg){
					console.log("상태값 : " + status + " Http에러메시지 : "+msg);
				}
			});
		});
		
		// 공지 수정 취소.
		$(document).on("click", "#modicloseBtn", function() {
			$("#modisubject").val('')
			$("#modicontent").val('')
			$("#infoModiModal").modal("hide");						
		});
		
		// 공지 삭제.
		$(document).on("click", "#infodelBtn", function() {
			if(confirm("삭제하시겠습니까?")) {
				let infonum = $(this).val();
				$.ajax({
					url:'${root}/admin/info/' + infonum,  
					type:'DELETE',
					contentType:'application/json;charset=utf-8',
					dataType:'json',
					success:function(info) {
						makeList(info);
						$("#infoViewModal").modal("hide");
					},
					error:function(xhr,status,msg){
						console.log("상태값 : " + status + " Http에러메시지 : "+msg);
					}
				});
			}
		});
		
		// 공지 검색 실행.
		$(document).on("click", "#infosearchBtn", function() {
			let sub=$("#searchsubject").val();
			$.ajax({
				url:'${root}/admin/info/'+sub,  
				type:'GET',
				contentType:'application/json;charset=utf-8',
				dataType:'json',
				success:function(info) {
					makeList(info);
				},
				error:function(xhr,status,msg){
					console.log("상태값 : " + status + " Http에러메시지 : "+msg);
				}
			});
		});
		
	});
	
	function makeList(infos) {
		$("#infobody").empty();
		$(infos).each(function(index, info) {
			/*
			let str = "<tr id=\"view_" + user.userId + "\" class=\"view\" data-id=\"" + user.userId + "\">"
			+ "	<td>" + user.userId + "</td>"
			+ "	<td>" + user.userPwd + "</td>"
			+ "	<td>" + user.userName + "</td>"
			+ "	<td>" + user.email + "</td>"
			+ "	<td>" + user.joinDate + "</td>"
			+ "	<td><button type=\"button\" class=\"modiBtn btn btn-outline-primary btn-sm\">수정</button> "
			+ "		<button type=\"button\" class=\"delBtn btn btn-outline-danger btn-sm\">삭제</button></td>"
			+ "</tr>"
			+ "<tr id=\"mview_" + user.userId + "\" data-id=\"" + user.userId + "\" style=\"display: none;\">"
			+ "	<td>" + user.userId + "</td>"
			+ "	<td><input type=\"text\" name=\"userpwd\" id=\"userpwd" + user.userId + "\" value=\"" + user.userPwd + "\"></td>"
			+ "	<td>" + user.userName + "</td>"
			+ "	<td><input type=\"text\" name=\"email\" id=\"email" + user.userId + "\" value=\"" + user.email + "\"></td>"
			+ "	<td>" + user.joinDate + "</td>"
			+ "	<td><button type=\"button\" class=\"modifyBtn btn btn-primary btn-sm\">수정</button> "
			+ "		<button type=\"button\" class=\"cancelBtn btn btn-danger btn-sm\">취소</button></td>"
			+ "</tr>";
			*/
			let str = `
			<tr>
				<td>${'${info.num}'}</td>
				<td>
					<div class="view" id="view_${'${info.num}'}" info-num="${'${info.num}'}">${'${info.subject}'}</div>
				</td>
			</tr>
			`;
			$("#infobody").append(str);
		});//each
	}
	</script>	     
	<%@ include file="/WEB-INF/views/include/header.jsp" %>
        <br />
        <br />
        <!-- Content section 1-->
        <div style="width: 100%; height: 800px; margin: auto; color: aliceblue" class="mb-1">
            <div class="container mt-5 text-center justify-content-center" style="width: 50%">
                <div class="mt-2 mb-3" style="color: black"><c:if test="${userinfo.grade eq '1'}"><button type="button" id="inforegiBtn" class="btn btn-dark" value="">공지 등록</button></c:if></div>
                <div class="mt-2 mb-3" style="color: black">[공지사항]   </div>
                <div class="mt-2 mb-3 form-inline " style="color: black">[검색]  <input type="text" class="form-control" placeholder="글 제목 입력" id="searchsubject"><button type="button" id="infosearchBtn" class="btn btn-sm btn-dark" value="">공지 검색</button></div>
                <table class="table">
                    <thead>
                        <tr>
                            <th width="100">글번호</th>
                            <th>제목</th>
                        </tr>
                    </thead>
                    <tbody id="infobody">
                        
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Footer-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
        <!-- Bootstrap core JS-->
        <!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script> -->
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
        <script src="js/initMap.js"></script>
        <script src="js/login.js"></script>
        <script src="js/memberfix.js"></script>
        
        <!-- 글 열람 모달 -->
		<div class="modal" id="infoViewModal">
		  <div class="modal-dialog modal-lg">
		    <div class="modal-content">
		    
		      <!-- Modal body -->
		      <div class="modal-body">
		       	<table class="table table-bordered" id="infotable">

		            <tbody>
		            <tr>
		                <td class="text-left" id="vnum" width="100px" height="50px"></td>
		                <td class="text-left" id="vsubject"></td>
		                <td class="text-left" id="vregtime" width="150px"></td>
		            </tr>
		            <tr>
		                <td colspan="3" class="text-left" id="vcontent"></td>
		            </tr>
		            </tbody>
		        </table>
		        <c:if test="${userinfo.grade eq '1'}"><button type="button" id="modiBtn" class="btn btn-dark" value="">공지 수정</button> <button type="button" id="infodelBtn" class="btn btn-danger" value="">공지 삭제</button></c:if>
		      </div>
		    </div>
		  </div>
		</div>
		<!-- 끝 -->
		
		<!-- 글 작성 모달 -->
		<div class="modal" id="infoRegiModal">
		  <div class="modal-dialog modal-lg">
		    <div class="modal-content">
		    
		      <!-- Modal body -->
		      <div class="modal-body">
		      <form id="registerform">
		       	<table class="table table-bordered" id="">
		            <tbody>
		            <tr>
		                <td class="text-left">
		                <div class="form-group">
						  <label for="usr">글 제목</label>
						  <input type="text" class="form-control" id="regisubject" name="subject">
						</div>
		                </td>
		                
		            </tr>
		            <tr>
		                <td colspan="3" class="text-left">
		                <div class="form-group">
		                  <label for="comment">글 내용</label>
						  <textarea class="form-control" rows="5" id="regicontent" name="content"></textarea>
						</div>
		                </td>
		            </tr>
		            </tbody>
		        </table>
		        <button type="button" id="registerBtn" class="btn btn-danger" value="">등록</button>
		        </form>
		      </div>
		    </div>
		  </div>
		</div>
		<!-- 끝 -->
		
				<!-- 글 수정 모달 -->
		<div class="modal" id="infoModiModal">
		  <div class="modal-dialog modal-lg">
		    <div class="modal-content">
		    
		      <!-- Modal body -->
		      <div class="modal-body">
		      <form id="modifyform">
		       	<table class="table table-bordered" id="">
		            <tbody>
		            <tr>
		                <td class="text-left">
		                <div class="form-group">
						  <label for="usr">글 제목</label>
						  <input type="text" class="form-control" id="modisubject" name="subject">
						</div>
		                </td>
		                
		            </tr>
		            <tr>
		                <td colspan="3" class="text-left">
		                <div class="form-group">
		                  <label for="comment">글 내용</label>
						  <textarea class="form-control" rows="5" id="modicontent" name="content"></textarea>
						</div>
		                </td>
		            </tr>
		            </tbody>
		        </table>
		        <button type="button" id="infomodiBtn" class="btn btn-dark" value="">수정</button> <button type="button" id="modicloseBtn" class="btn btn-danger" value="">취소</button>
		        </form>
		      </div>
		    </div>
		  </div>
		</div>
		<!-- 끝 -->
		
    </body>
</html>