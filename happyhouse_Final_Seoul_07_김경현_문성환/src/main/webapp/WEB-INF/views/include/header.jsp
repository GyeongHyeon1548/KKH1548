<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@ include file="/WEB-INF/views/include/head.jsp" %>
<!DOCTYPE html>
<script type="text/javascript">
        $(document).ready(function () {
            $("#loginBtn").click(function () {
                if (!$("#loginid").val()) {
                    alert("아이디 입력!!!");
                    return;
                } else if (!$("#loginpwd").val()) {
                    alert("비밀번호 입력!!!");
                    return;
                } else {
                    $("#loginform").attr("action", "${root}/users/login").submit();
                }
            });            
        });
        $(document).ready(function () {
            $("#signupBtn").click(function () {
                if (!$("#signupid").val()) {
                    alert("아이디 입력!!!");
                    return;
                } else if (!$("#signuppwd").val()) {
                    alert("비밀번호 입력!!!");
                    return;
                } else if (!$("#signupphone").val()) {
                    alert("전화번호 입력!!!");
                    return;
                } else if (!$("#signupaddress").val()) {
                    alert("주소 입력!!!");
                    return;
                } else if (!$("#signupname").val()) {
                    alert("이메일 입력!!!");
                    return;
                } else {
                    $("#signupform").attr("action", "${root}/users/register").submit();
                }
            });            
        });
        
        $(document).ready(function () {
            $("#updateBtn").click(function () {
               if (!$("#updatepwd").val()) {
                    alert("비밀번호 입력!!!");
                    return;
                } else if (!$("#updatephone").val()) {
                    alert("전화번호 입력!!!");
                    return;
                } else if (!$("#updateaddress").val()) {
                    alert("주소 입력!!!");
                    return;
                } else if (!$("#updatename").val()) {
                    alert("이메일 입력!!!");
                    return;
                } else {
                    $("#updateform").attr("action", "${root}/users/update").submit();
                }
            });            
        });
        
        $(document).ready(function () {
            $("#memberlist").click(function () {
            	$.ajax({
        			url:'${root}/users/list',  
        			type:'GET',
        			contentType:'application/json;charset=utf-8',
        			dataType:'json',
        			success:function(users) {
        				$("#memberbody").empty();
        				$.each(users, function(index, user) {
							console.log(user);
							let str = `
								<tr ><td width="100px">${'${user.userName}'}</td>
									<td width="100px">${'${user.userId}'}</td>
									<td width="100px">${'${user.userPwd}'}</td>
									<td width="300px">${'${user.address}'}</td>
									<td width="150px">${'${user.phone}'}</td>
									<td width="100px">${'${user.grade}'}</td>
									<td width="100px"></td>
								</tr>
							`;
							console.log(str);
							$("#memberbody").append(str);
							
						});
        			},
        			error:function(xhr, status, error){
        				console.log("상태값 : " + xhr.status + "\tHttp 에러메시지 : " + xhr.responseText);
        			}	
        		});
            });            
        });
        
        $(document).ready(function () {
            $("#membersearchBtn").click(function () {
            	let name = $('#searchName').val();
            	alert(name);
            	$.ajax({
        			url:'${root}/users/list/'+name,  
        			type:'GET',
        			contentType:'application/json;charset=utf-8',
        			dataType:'json',
        			success:function(users) {
        				$("#memberbody").empty();
        				$.each(users, function(index, user) {
							console.log(user);
							let str = `
								<tr >
									<td width="100px">${'${user.userName}'}</td>
									<td width="100px">${'${user.userId}'}</td>
									<td width="100px">${'${user.userPwd}'}</td>
									<td width="300px">${'${user.address}'}</td>
									<td width="150px">${'${user.phone}'}</td>
									<td width="100px">${'${user.grade}'}</td>
									<td width="100px"></td>
								</tr>
							`;
							console.log(str);
							$("#memberbody").append(str);
							
						});
        			},
        			error:function(xhr, status, error){
        				console.log("상태값 : " + xhr.status + "\tHttp 에러메시지 : " + xhr.responseText);
        			}	 
        		});
            });            
        });
        
        $(document).ready(function () {
            $("#deleteBtn").click(function () {
                if (confirm("정말로 탈퇴하시곘습니까?")){                
                    $("#updateform").attr("action", "${root}/users/delete").submit();
                }
            });            
        });
        
    </script>

<nav class="navbar navbar-expand-lg navbar-dark navbar-custom fixed-top">
    <div class="container px-5">
        <a class="navbar-brand" href="${root}/">Happy House</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ms-auto">
            <c:if test="${!empty userinfo}">
            	<c:if test="${userinfo.grade eq 1 }">
                <li class="nav-item"><a id="memberlist" class="nav-link"  href="#!" data-toggle="modal" data-target="#memberpage" data-backdrop="static">회원 목록</a></li>
                </c:if>
                <li class="nav-item"><a class="nav-link" href="${root}/housedeal">매물 거래 조회</a></li>
                <li class="nav-item"><a id="logout" class="nav-link" href="${root}/users/logout" data-backdrop="static">Log Out</a></li>
                <li class="nav-item"><a id="update" class="nav-link"  href="#!" data-toggle="modal" data-target="#updatepage" data-backdrop="static">회원 정보 수정</a></li>
                <li class="nav-item"><a id="location" class="nav-link" href="${root}/interest/list">관심지역</a></li>
            </c:if>
            <c:if test="${empty userinfo}">
            	<li class="nav-item"><a id="signup" class="nav-link" href="#!" data-toggle="modal" data-target="#signuppage" data-backdrop="static">Sign Up</a></li>
                <li class="nav-item"><a id="login" class="nav-link" href="#!" data-toggle="modal" data-target="#loginpage" data-backdrop="static">Log In</a></li>                
            </c:if>
                <li class="nav-item"><a class="nav-link" href="${root}/information">공지사항</a></li>
            </ul>
        </div>
    </div>
</nav>

 <!-- 로그인 모달 기능 -->
        <div class="modal" id="loginpage">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content">
                    <!-- Modal Header -->
                    <div class="modal-header">
                        <!-- <i class="fa fa-cloud mr-3"
                                style="font-size:30px;color:lightblue;text-shadow:2px 2px 4px #000000;"></i> -->
                        <h4 class="modal-title">로그인 화면</h4>
                        <!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
                    </div>

                    <!-- Modal body -->
                    <div class="modal-body">
                        <form id="loginform" class="text-left mb-3" method="post" action="">
                        	<input type="hidden" id="act" name="act" value="login">
                            <div class="form-group">
                                <label for="question"><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i> 아이디</label>
                                <input type="text" class="form-control col-lg-10" placeholder="아이디를 입력 하시오" id="loginid" name="loginid" />
                            </div>
                            <div class="form-group">
                                <label><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i>비밀번호</label>
                                <input type="text" class="form-control col-lg-10" placeholder="비밀번호를 입력 하시오" id ="loginpwd" name="loginpwd" />
                                <!-- <button type="button" id="btn-add" class="btn btn-outline-info btn-sm">항목추가</button> -->
                            </div>

                            <div class="form-group text-right col-lg-7">
                                <button type="button" id="loginBtn" class="btn btn-primary text-center col-lg-4">로그인</button>
                            </div>
                        </form>
                    </div>

                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 로그인 모달기능 종료 -->

        <!-- 회원가입 모달페이지 -->

        <div class="modal" id="signuppage">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content">
                    <!-- Modal Header -->
                    <div class="modal-header">
                        <!-- <i class="fa fa-cloud mr-3"
                    style="font-size:30px;color:lightblue;text-shadow:2px 2px 4px #000000;"></i> -->
                        <h4 class="modal-title">회원가입 화면</h4>
                        <!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
                    </div>

                    <!-- Modal body -->
                    <div class="modal-body">
                        <form id="signupform" class="text-left mb-3" method="post" action="">
                        <input type="hidden" id="act" name="act" value="signup">
                            <div class="form-group">
                                <label for="question"><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i> 아이디</label>
                                <input type="text" class="form-control col-lg-10" placeholder="아이디를 입력 하시오" id="signupid" name="signupid"/>
                            </div>
                            <div class="form-group">
                                <label><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i>비밀번호</label>
                                <input type="text" class="form-control col-lg-10" placeholder="비밀번호를 입력 하시오" id="signuppwd" name="signuppwd" />
                                <!-- <button type="button" id="btn-add" class="btn btn-outline-info btn-sm">항목추가</button> -->
                            </div>
                            <div class="form-group">
                                <label><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i>이름</label>
                                <input type="text" class="form-control col-lg-10" placeholder="이름을 입력 하시오" id="signupname" name="signupname" />
                                <!-- <button type="button" id="btn-add" class="btn btn-outline-info btn-sm">항목추가</button> -->
                            </div>
                            <div class="form-group">
                                <label><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i>주소</label>
                                <input type="text" class="form-control col-lg-10" placeholder="주소를 입력 하시오" id="signupaddress" name="signupaddress" />
                                <!-- <button type="button" id="btn-add" class="btn btn-outline-info btn-sm">항목추가</button> -->
                            </div>
                            <div class="form-group">
                                <label><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i>전화번호</label>
                                <input type="text" class="form-control col-lg-10" placeholder="전화번호를 입력 하시오" id="signupphone" name="signupphone" />
                                <!-- <button type="button" id="btn-add" class="btn btn-outline-info btn-sm">항목추가</button> -->
                            </div>

                            <div class="form-group text-right col-lg-7">
                                <button type="button" id="signupBtn" class="btn btn-primary text-center col-lg-4">회원가입</button>
                            </div>
                        </form>
                    </div>

                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 회원가입 모달페이지 종료 -->
        
		<!-- 정보수정 모달페이지  -->
        <div class="modal" id="updatepage">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content">
                    <!-- Modal Header -->
                    <div class="modal-header">
                        <!-- <i class="fa fa-cloud mr-3"
                    style="font-size:30px;color:lightblue;text-shadow:2px 2px 4px #000000;"></i> -->
                        <h4 class="modal-title">회원 정보 조회</h4>
                        <!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
                    </div>

                    <!-- Modal body -->
                    <div class="modal-body">
                        <form id="updateform" class="text-left mb-3" method="post" action="">
                            <div class="form-group">
                                <label for="question"><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i> 아이디</label>
                                <input type="hidden" id="updateid" name="updateid" value="${userinfo.userId }" >
                                <input type="text" class="form-control col-lg-10" value="${userinfo.userId }"  disabled />
                            </div>
                            <div class="form-group">
                                <label><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i>비밀번호</label>
                                <input type="text" class="form-control col-lg-10" id="updatepwd" name="updatepwd" value="${userinfo.userPwd}"/>
                                <!-- <button type="button" id="btn-add" class="btn btn-outline-info btn-sm">항목추가</button> -->
                            </div>
                            <div class="form-group">
                                <label><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i>이름</label>
                                <input type="text" class="form-control col-lg-10" id="updatename" name="updatename" value="${userinfo.userName}"/>
                                <!-- <button type="button" id="btn-add" class="btn btn-outline-info btn-sm">항목추가</button> -->
                            </div>
                            <div class="form-group">
                                <label><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i>주소</label>
                                <input type="text" class="form-control col-lg-10" id="updateaddress" name="updateaddress" value="${userinfo.address}"/>
                                <!-- <button type="button" id="btn-add" class="btn btn-outline-info btn-sm">항목추가</button> -->
                            </div>
                            <div class="form-group">
                                <label><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i>전화번호</label>
                                <input type="text" class="form-control col-lg-10" id="updatephone" name="updatephone" value="${userinfo.phone}"/>
                                <!-- <button type="button" id="btn-add" class="btn btn-outline-info btn-sm">항목추가</button> -->
                            </div>

                            <div class="form-group text-right col-lg-7"> 
                                <button type="button" id="updateBtn" class="btn btn-primary text-center col-lg-4">정보 수정</button>
                            </div>
                            <div class="form-group text-right col-lg-7"> 
                                <button type="button" id="deleteBtn" class="btn btn-danger text-center col-lg-4">회원 탈퇴</button>
                            </div>
                        </form>
                    </div>

                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 정보수정 모달페이지 종료 -->

        <!-- 내정보 보기 모달 페이지 -->

        <div class="modal" id="myinfopage">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content">
                    <!-- Modal Header -->
                    <div class="modal-header">
                        <!-- <i class="fa fa-cloud mr-3"
                    style="font-size:30px;color:lightblue;text-shadow:2px 2px 4px #000000;"></i> -->
                        <h4 class="modal-title">내정보 화면</h4>
                        <!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
                    </div>

                    <!-- Modal body -->
                    <div class="modal-body">
                        <form action="">
                            <div class="form-group">
                                <label for="question"
                                    ><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i>
                                    <div id="id1">현재 아이디 : ssafy</div></label
                                >
                                <input type="text" class="form-control col-lg-10" placeholder="새 아이디" id="id2" name="question" />
                                <button type="button" id="info-fix" class="btn btn-outline-danger btn-sm mt-1" onclick="input()">아이디 수정</button>
                            </div>
                            <div class="form-group">
                                <label id="id3"><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i>현재 비밀번호 : 1234</label>
                                <input id="id4" type="text" class="form-control col-lg-10" placeholder="새 비밀번호" name="answer" />
                                <button type="button" id="info-fix" class="btn btn-outline-danger btn-sm mt-1" onclick="input1()">비밀번호 수정</button>
                                <!-- <button type="button" id="btn-add" class="btn btn-outline-info btn-sm">항목추가</button> -->
                            </div>
                            <div class="form-group">
                                <label id="id5"><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i>현재 이름 : 윤희영</label>
                                <input id="id6" type="text" class="form-control col-lg-10" placeholder="새 이름" name="answer" />
                                <button type="button" id="info-fix" class="btn btn-outline-danger btn-sm mt-1" onclick="input2()">이름 수정</button>

                                <!-- <button type="button" id="btn-add" class="btn btn-outline-info btn-sm">항목추가</button> -->
                            </div>
                            <div class="form-group">
                                <label id="id7"><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i>현재 주소 : 경기도 성남시</label>
                                <input id="id8" type="text" class="form-control col-lg-10" placeholder="새 주소" name="answer" />
                                <button type="button" id="info-fix" class="btn btn-outline-danger btn-sm mt-1" onclick="input3()">주소 수정</button>

                                <!-- <button type="button" id="btn-add" class="btn btn-outline-info btn-sm">항목추가</button> -->
                            </div>
                            <div class="form-group">
                                <label id="id9"><i class="fa fa-user" aria-hidden="true" style="font-size: 19px; color: tomato"></i>현재 전화번호 : 010-0000-0000</label>
                                <input id="id10" type="text" class="form-control col-lg-10" placeholder="새 전화번호" name="answer" />
                                <button type="button" id="info-fix" class="btn btn-outline-danger btn-sm mt-1" onclick="input4()">전화번호 수정</button>

                                <!-- <button type="button" id="btn-add" class="btn btn-outline-info btn-sm">항목추가</button> -->
                            </div>

                            <div class="form-group text-right col-lg-7">
                                <button type="button" id="delete" class="btn btn-primary text-center col-lg-4" onclick="input()">회원 탈퇴</button>
                            </div>
                        </form>
                    </div>

                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 내정보 보기 모달 페이지 종료 -->
        
        <!-- 회원정보 목록 모달 페이지 -->

        <div class="modal" id="memberpage">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content">
                    <!-- Modal Header -->
                    <div class="modal-header">
                        <!-- <i class="fa fa-cloud mr-3"
                    style="font-size:30px;color:lightblue;text-shadow:2px 2px 4px #000000;"></i> -->
                        <h4 class="modal-title">회원정보</h4>
                        <div class="mt-2 mb-3 form-inline " style="color: black">[검색]  <input type="text" class="form-control" placeholder="이름 입력" id="searchName"><button type="button" id="membersearchBtn" class="btn btn-sm btn-dark" value="">회원 검색</button></div>
                        <!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
                    </div>

                    <!-- Modal body -->
                    <div class="modal-body">
                      <table>
                      	<thead>
                      		<th width="100px">이름</th>
                      		<th width="100px">아이디</th>
                      		<th width="100px">비밀번호</th>
                      		<th width="300px">주소</th>
                      		<th width="150px">전화번호</th>
                      		<th width="100px">회원등급</th>
                      	</thead>
                      	<tbody id="memberbody">
                      	</tbody>
                      </table>
                    </div>

                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 안심병원 보기 모달 페이지 종료 -->

        <!-- 회원정보 목록 모달 페이지 종료 -->