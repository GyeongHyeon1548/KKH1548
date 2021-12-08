<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <%@ include file="/WEB-INF/views/include/head.jsp" %>
        <c:if test="${empty userinfo}"> location.href = "${root}/"</c:if>
        <script defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAbdZV0vY1kNPrgI63PR63Hj8sWF4IoT4E&callback=initMap"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />
        
        <script>
        $(document).ready(function(){
	        $(".hospitalBtn").click(function () {
	        	$.get("${root}/hospital/getlist"
					,{dongCode:$(this).val()}
					,function(data, status){				
						$("#hospitalbody").empty();
						$.each(data, function(index, vo) {
							console.log(vo);
							let str = `
								<tr ><td width="50px">${'${vo.sido}'}</td>
									<td width="70px">${'${vo.gugun}'}</td>
									<td width="160px">${'${vo.name}'}</td>
									<td width="300px">${'${vo.address}'}</td>
									<td width="150px">${'${vo.call_number}'}</td>
									<td width="70px"><button type="button" class="btn btn-danger hosmapBtn" value="${'${vo.address}'}">지도</button></td>
								</tr>
							`;
							console.log(str);
							$("#hospitalbody").append(str);
							$("#hospitalpage").modal();
							
						});
					}
					, "json"
				);
	        });
		});
        
        $(document).ready(function(){
	        $(document).on("click",".interestdelBtn",function () {

/* 	        	$.get("${root}/interest/delete"
					,{aptCode:$(this).val()}
					,function(data, status){				
						 location.reload();
					}
					, "json"
				); */
	        	$.ajax({
					url:'${root}/interest/delete',  
					type:'GET',
					contentType:'application/json;charset=utf-8',
					dataType:'text',
					data: {aptCode:$(this).val()},
					success:function(msg) {
						alert(msg);
						location.reload();
					},
					error:function(xhr,status,msg){
						console.log("상태값 : " + status + " Http에러메시지 : "+msg);
					}
				});
	        });
		});
        
        $(document).ready(function(){
	        $(document).on("click",".hosmapBtn",function () {
	        	$("#hospitalmap").modal();
        		moveMap($(this).val());
        		map.relayout();
	        });
		});
        

        </script>
    </head>
    <body id="page-top">
	<%@ include file="/WEB-INF/views/include/header.jsp" %>
        <br />
        <br />
        <!-- Content section 1-->
        <div style="width: 100%; height: 800px; margin: auto; color: aliceblue" class="mb-1">
            <div class="container mt-5 text-center justify-content-center" style="width: 50%">
                <div class="mt-2 mb-3" style="color: black">[내 관심지역]</div>
                <table class="table table-hover">
                    <tr>
		  	<td align=center bgcolor="E6ECDE"height="22">아파트코드</td>
		  	<td align=center bgcolor="E6ECDE"height="22">아파트이름</td>
			<td align=center bgcolor="E6ECDE"height="22">건축년도</td>
			<td align=center bgcolor="E6ECDE"height="22">동이름</td>
			<td align=center bgcolor="E6ECDE"height="22">국민안심병원</td>
			<td align=center bgcolor="E6ECDE"height="22"></td>
		  </tr>
		  <c:forEach var="interest" items="${interests}">
			  <tr>
			  	<td align=center height="22">${interest.aptCode}</td>
			  	<td align=center height="22">${interest.aptName}</td>
				<td align=center height="22">${interest.buildYear}</td>
				<td align=center height="22">${interest.dongName}</td>
				<td align=center height="22"><button type="button" class="btn btn-primary hospitalBtn" value="${interest.dongCode}">목록 보기</button></td>
				<td align=center height="22"><button type="button" class="btn btn-danger interestdelBtn" value="${interest.aptCode}">삭제</button></td>
			  </tr>
		  </c:forEach>
                </table>
                

<!--                 <div>
                    <ul class="pagination justify-content-center">
                        <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                        <li class="page-item active"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item"><a class="page-link" href="#">Next</a></li>
                    </ul>
                </div> -->
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
        
                        <!-- 안심병원 보기 모달 페이지 -->

        <div class="modal" id="hospitalpage">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content">
                    <!-- Modal Header -->
                    <div class="modal-header">
                        <!-- <i class="fa fa-cloud mr-3"
                    style="font-size:30px;color:lightblue;text-shadow:2px 2px 4px #000000;"></i> -->
                        <h4 class="modal-title">관심지역 국민 안심 병원</h4>
                        <!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
                    </div>

                    <!-- Modal body -->
                    <div class="modal-body">
                      <table>
                      	<thead>
                      		<th>시도</th>
                      		<th>구군</th>
                      		<th>병원명</th>
                      		<th>주소</th>
                      		<th>전화번호</th>
                      		<th>지도</th>
                      	</thead>
                      	<tbody id="hospitalbody">
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
        
        <!-- 병원 위치 보기 모달 페이지 -->
        <div class="modal" id="hospitalmap">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content">
                    <!-- Modal Header -->

                    <!-- Modal body -->
                    <div class="modal-body">
                      <div id="map" style="height: 600px; width: 900px" class="mb-3 col-lg-9"></div>
                    </div>

                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- 병원 위치 보기 모달 페이지 종료 -->
        
        <script>
			 var markers = []; //마커 위치
            var map, marker, infowindow;
            var mapContainer = document.getElementById('map'), // 지도를 표시할 div
                mapOption = {
                    center: new kakao.maps.LatLng(37.5012743, 127.039585), // 지도의 중심좌표
                    level: 3, // 지도의 확대 레벨
                };
            map = new kakao.maps.Map(mapContainer, mapOption);
            
            function moveMap(address) {
            	hideMarkers();
                 console.log(address);
                // 주소-좌표 변환 객체를 생성합니다
                var geocoder = new kakao.maps.services.Geocoder();

                // 주소로 좌표를 검색합니다
                geocoder.addressSearch(address, function (result, status) {
                    console.log(status);
                    console.log(result);
                    // 정상적으로 검색이 완료됐으면
                    if (status === kakao.maps.services.Status.OK) {
                        console.log('검색 성공!!!');
                        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                        if (marker) marker.setMap(null); // 기존 marker 제거
                        if (infowindow) infowindow.close();
                        // 결과값으로 받은 위치를 마커로 표시합니다
                        marker = new kakao.maps.Marker({
                            map: map,
                            position: coords,
                        });

                        // 인포윈도우로 장소에 대한 설명을 표시합니다
                        infowindow = new kakao.maps.InfoWindow({
                            content: '<div style="width:150px;text-align:center; color:black ;padding:6px 0;">' + address+ '</div>',
                        });
                        infowindow.open(map, marker);

                        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                        map.setCenter(coords);
                        new kakao.maps.LatLng(result[0].y, result[0].x)
                        markers.push(marker)
                    }
                });
            }
            
            function addMarker(position) {
    		    
    		    // 마커를 생성합니다
    		    var marker = new kakao.maps.Marker({
    		        position: position
    		    });

    		    // 마커가 지도 위에 표시되도록 설정합니다
    		    marker.setMap(map);
    		    
    		    // 생성된 마커를 배열에 추가합니다
    		    markers.push(marker);
    		}
    		
    		function setMarkers(map) {
    		    for (var i = 0; i < markers.length; i++) {
    		        markers[i].setMap(map);
    		    }            
    		}

    		// "마커 보이기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에 표시하는 함수입니다
    		function showMarkers() {
    		    setMarkers(map)    
    		}

    		// "마커 감추기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에서 삭제하는 함수입니다
    		function hideMarkers() {
    		    setMarkers(null);    
    		}
    		
    		function geocode(jsonData) {
    			let idx = 0;
    			$.each(jsonData, function(index, vo) {
    				/* let tmpLat;
    				let tmpLng;
    				$.get("https://maps.googleapis.com/maps/api/geocode/json"
    						,{	key:'AIzaSyAhGXConSPtluvtsBT7LoOxJQncaeqFJFU'
    							, address:vo.dong_name+"+"+vo.apt_name+"+"+vo.jibun
    						}
    						, function(data, status) {
    							tmpLat = data.results[0].geometry.location.lat;
    							tmpLng = data.results[0].geometry.location.lng;
    							$("#lat_"+index).text(tmpLat);
    							$("#lng_"+index).text(tmpLng);
    							addMarker(tmpLat, tmpLng, vo.apt_name);
    						}
    						, "json"
    				);//get */
    				
    			});//each
    		}
        </script>
    </body>
</html>
