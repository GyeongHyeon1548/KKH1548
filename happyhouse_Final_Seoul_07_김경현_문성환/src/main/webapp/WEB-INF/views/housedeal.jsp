<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    
    <meta charset="UTF-8">
       <%@ include file="/WEB-INF/views/include/head.jsp" %>
       
       
        <!-- <script defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAbdZV0vY1kNPrgI63PR63Hj8sWF4IoT4E&callback=initMap"></script> -->
        <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=9e6371ee576b8757a810cc7309c73a44&libraries=services"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />
        <script>
        <c:if test="${empty userinfo}"> location.href = "${root}/"</c:if>
       	$(document).ready(function(){
			$.get("${root}/map/sido"			
				,function(data, status){
					$.each(data, function(index, vo) {
						//console.log(vo);
						$("#city").append("<option value='"+vo.sidoCode+"'>"+vo.sidoName+"</option>");
					});//each
				}//function
				, "json"
			);//get
		});//ready
		$(document).ready(function () {
            $("#city").change(function () {
            	$.get("${root}/map/gugun"
						,{ sido:$("#city").val()}
						,function(data, status){
							$("#gugun").empty();
							$("#gugun").append('<option value="0">선택</option>');
							$.each(data, function(index, vo) {
								$("#gugun").append("<option value='"+vo.gugunCode+"'>"+vo.gugunName+"</option>");
							});
						}
						, "json"
				);
            });
        });
		
		$(document).ready(function () {
            $("#city").change(function () {
            	$.get("${root}/map/gugun"
						,{ sido:$("#city").val()}
						,function(data, status){
							$("#gugun").empty();
							$("#gugun").append('<option value="0">선택</option>');
							$.each(data, function(index, vo) {
								$("#gugun").append("<option value='"+vo.gugunCode+"'>"+vo.gugunName+"</option>");
							});
						}
						, "json"
				);
            });
        });
		
		$(document).ready(function(){
	        $(document).on("click","#interestBtn",function () {
	        	console.log("!");
	        	$.get("${root}/interest/register"
					,{aptCode:$(this).val()}
					,function(data, status){				
						$("#hospitalbody").empty();
						$.each(data, function(index, vo) {

						});
					}
					, "json"
				);
	        });
		});
		
		$(document).ready(function () {
            $("#gugun").change(function () {
            	$.get("${root}/map/dong"
						,{gugun:$("#gugun").val()}
						,function(data, status){
							$("#dong").empty();
							$("#dong").append('<option value="0">선택</option>');
							$.each(data, function(index, vo) {
								$("#dong").append("<option value='"+vo.dongCode+"'>"+vo.dongName+"</option>");
							});
						}
						, "json"
				);
            });
        });
		$(document).ready(function () {
            $("#dong").change(function () {
            	$.get("${root}/map/apt"
					,{dong:$("#dong").val()}
					,function(data, status){
						hideMarkers();
						$("#houses").empty();
						$.each(data, function(index, vo) {							
							$("#houses").append("<div class=\"media margin-clear\"><div class=\"media-body\">");
							$("#houses").append("<h4><a href='javascript:moveMap(\""+ vo.aptCode + "\",\"" + vo.dongName +vo.jibun + "\",\"" + vo.aptName + "\",\""+vo.area+"\",\""+vo.floor+"\",\"" + vo.sidoName +"\",\"" + vo.gugunName +"\");'>" + vo.aptName + "</a></h4>");
							$("#houses").append("<h6 class='media-heading' id=\"deal\">" + vo.recentPrice + "(만원)</h6>");
							$("#houses").append("<p class=\"small margin-clear\">" + vo.dongName + "</p></div></div>");
							addMarker(new kakao.maps.LatLng(vo.lat,vo.lng));
							map.setCenter(new kakao.maps.LatLng(vo.lat, vo.lng));
							console.log(vo);
						});
						//geocode(data);
					}
					, "json"
				);
            });
            
        });
		
        $(document).ready(function () {
            $("#searchCafeBtn").click(function () {
            	$.get("${root}/map/cafe"
					,{dong:$("#dong").val()}
					,function(data, status){
						hideMarkers();			
						$("#houses").empty();
						$.each(data, function(index, vo) {
							let str = vo.address;
							$("#houses").append("<div class=\"media margin-clear\"><div class=\"media-body\">");
							$("#houses").append("<h4><a href='javascript:moveMap2(\""+ vo.cafeName + "\",\"" + str +"\");'>" + vo.cafeName + "</a></h4>");
							$("#houses").append("<p class=\"small margin-clear\">" + vo.address + "</p></div></div>");
							addMarker(new kakao.maps.LatLng(vo.lat,vo.lng));
							map.setCenter(new kakao.maps.LatLng(vo.lat, vo.lng));
							console.log(vo);
						});
						//geocode(data);
					}
					, "json"
				);
        		map.relayout();
            });
            
        });
		
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
		/*
		kakao.maps.event.addListener(marker, 'click', function() {
		    overlay.setMap(map);
		});
*/

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
    </head>
    <body id="page-top">
	<%@ include file="/WEB-INF/views/include/header.jsp" %>
        <br />
        <br />
        <!-- Content section 1-->
        <section id="scroll" class="mt-5">
            <div class="container">


                <div class="dark-bg section">
                    <div class="container-fluid">
                        <!-- filters start -->
                        <div class="sorting-filters text-center mb-20 d-flex justify-content-center">
                                <!-- <input type="hidden" id="code"  name="code" value="code"/> -->
                                <div class="form-group md">
                                    <select class="form-control" name="city" id="city">
                                        <option value="0">선택</option>
                                    </select>
                                </div>
                                <div class="form-group md-1">
                                    <select class="form-control" name="gugun" id="gugun">
                                        <option value="0">선택</option>
                                    </select>
                                </div>
                                <div class="form-group md-1">
                                    <select class="form-control" name="dong" id="dong">
                                        <option value="0">선택</option>
                                    </select>
                                </div>
                                <div class="form-group md-1">
                                    <button type="button" id="searchCafeBtn" class="btn btn-primary">카페검색</button>
                                </div>
                        </div>
                    </div>
                </div>

                <!-- 거래 상세내역 -->
                <div class="mt-4 mb-5 mr-5">
                    <div class="align-items-center row">
                        <div class="sideBar col-lg-3" style="overflow: auto; max-height: 600px">
                            <div class="houses" name="houses" id="houses">
                                <!-- 가져온 매물 정보 뿌리는 곳 -->
                            </div>
                        </div>

                        <div id="map" style="height: 600px; width: 900px" class="mb-3 col-lg-9"></div>
                    </div>
                </div>
            </div>
        </section>

       

        <!-- Footer-->
        <footer class="py-5 bg-black w-100" style="position: fixed; bottom: 0">
            <div class="container px-5"><p class="m-0 text-center text-white small">Copyright &copy; ssafy</p></div>
        </footer>
        <!-- Bootstrap core JS-->
        <!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script> -->
        <!-- Core theme JS-->
        <div id="map" style="width: 100%; height: 500px; margin: auto;"></div>

		 <script>
			 var markers = []; //마커 위치
            var map, marker, infowindow;
            var mapContainer = document.getElementById('map'), // 지도를 표시할 div
                mapOption = {
                    center: new kakao.maps.LatLng(37.5012743, 127.039585), // 지도의 중심좌표
                    level: 3, // 지도의 확대 레벨
                };
            map = new kakao.maps.Map(mapContainer, mapOption);
            
            function moveMap(aptCode, address, apt, area, floor, sidoName, gugunName) {
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
                            content: '<div style="width:150px;text-align:center;padding:6px 0;">' + apt + '<br>'+area+' 제곱미터<br>'+floor+'층<br><button type="button" id="interestBtn" class="btn btn-danger" value="'+aptCode+'">관심등록</button></div>',
                        });
                        infowindow.open(map, marker);

                        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                        map.setCenter(coords);
                        new kakao.maps.LatLng(result[0].y, result[0].x)
                        markers.push(marker)
                    }
                });                
            }
            
            
            function moveMap2(cafeName, address) {
            	hideMarkers();
            	console.log(address);
                // 주소-좌표 변환 객체를 생성합니다
                var geocoder = new kakao.maps.services.Geocoder();

                // 주소로 좌표를 검색합니다
                geocoder.addressSearch(address, function (result, status) {
                    console.log(status);
                    console.log(result);
                    console.log(address);
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
                        
                        var content = '<div style="width:150px;text-align:center;padding:6px 0;">' + cafeName + '<br>'+address+'</div>'
                        
                        var customOverlay = new kakao.maps.CustomOverlay({
                            position: coords,
                            content: content,
                            xAnchor: 0.3,
                            yAnchor: 0.91
                        });

                        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                        map.setCenter(coords);
                        new kakao.maps.LatLng(result[0].y, result[0].x)
                        markers.push(marker)
                    }
                });
            }
            
        </script>

    </body>
</html>
