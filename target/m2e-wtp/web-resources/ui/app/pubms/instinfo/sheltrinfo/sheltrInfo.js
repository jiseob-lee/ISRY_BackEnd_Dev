/************************************************
 * @파일명 : sheltrInfo.js
 * @프로그램명 :  
 * @작성자 : Lee.Myeong.Sang
 * @작성일자: 2023. 3. 7. 
 * @수정로그 : 
 * [일자, 수정자] 내용 
 ************************************************/

//=================================================================================
// [Object Setting]
//=================================================================================


/**
 * 주소 데이터 지도에 표시
 * @param psAddrs - 해당 지역 주소들
 */

function displayMap(psAddrs, profile) {

    if (profile != 'real2') {  //인터넷 환경만 실행
        return true;
    }
    
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = {
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 11 // 지도의 확대 레벨
        };  
    
    // 지도를 생성합니다    
    var map = new kakao.maps.Map(mapContainer, mapOption); 
    
    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();

    for (var i = 0; i < psAddrs.length; i++) {
        
        // 주소로 좌표를 검색합니다
        geocoder.addressSearch(psAddrs[i], function(result, status) {

            // 정상적으로 검색이 완료됐으면 
            if (status === kakao.maps.services.Status.OK) {
                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/2012/img/marker_p.png';
                var imageSize = new kakao.maps.Size(20, 21);
                var imageOption = {offset: new kakao.maps.Point(27, 69)};
                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

                // 결과값으로 받은 위치를 마커로 표시합니다
                var marker = new kakao.maps.Marker({
                    map: map,
                    position: coords,
                    opacity : 1,
                    image: markerImage
                });

                // 인포윈도우로 장소에 대한 설명을 표시합니다
      //          var infowindow = new kakao.maps.InfoWindow({
        //            content: '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>'
        //            content: loc1
        //        });
    
        //        infowindow.open(map, marker);
        
                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                map.setCenter(coords);
            } else {
                console.log("지도 검색을 못했습니다.")
            }    
        });    
    }
    
}