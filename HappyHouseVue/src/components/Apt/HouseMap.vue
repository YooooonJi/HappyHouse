<template>
  <div id="makemap">
    <div id="map"></div>
    <div v-if="apt">
      {{ changeMap() }}
    </div>
  </div>
</template>
<script>
let infowindow;
let marker;

let map;

export default {
  data: function() {
    return {};
  },
  props: {
    apt: [Object, String]
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=1d9b2d844dbc392ba0e7c4aa846de280&libraries=services";
      document.head.appendChild(script);
    }
  },
  // updated() {
  //   this.initMap();
  // },
  methods: {
    initMap() {
      var container = document.getElementById("map");

      var options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3
      };

      map = new kakao.maps.Map(container, options);

      // var markerPosition = new kakao.maps.LatLng(33.450701, 126.570667);
      // var marker = new kakao.maps.Marker({
      //   position: markerPosition
      // });
      // marker.setMap(map2);

      var geocoder = new daum.maps.services.Geocoder();
      geocoder.addressSearch("서울특별시 강남구 역삼동 테헤란로 212", function(
        result,
        status
      ) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

          // 결과값으로 받은 위치를 마커로 표시합니다
          marker = new kakao.maps.Marker({
            map: map,
            position: coords
          });

          // 인포윈도우로 장소에 대한 설명을 표시합니다
          infowindow = new kakao.maps.InfoWindow({
            content:
              '<div style="width:150px;text-align:center;padding:6px 0;">' +
              "멀티캠퍼스" +
              "</div>"
          });
          infowindow.open(map, marker);

          // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
          map.setCenter(coords);
        }
      });
    },
    changeMap() {
      if (marker != null) marker.setMap(null);
      if (infowindow != null) infowindow.close();

      // var mapContainer = document.getElementById("map"), // 지도를 표시할 div
      //   mapOption = {
      //     center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
      //     level: 3 // 지도의 확대 레벨
      //   };

      // 지도를 생성합니다
      // map = new kakao.maps.Map(mapContainer, mapOption);

      var Name = this.apt.아파트;
      var Dong = this.apt.법정동;
      var Jibun = this.apt.지번;
      if (this.apt.연립다세대 != undefined) Name = this.apt.연립다세대;
      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();

      // 주소로 좌표를 검색합니다
      geocoder.addressSearch(Dong + " " + Jibun, function(result, status) {
        // 정상적으로 검색이 완료됐으면
        console.log(status);
        if (status === kakao.maps.services.Status.OK) {
          var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

          // 결과값으로 받은 위치를 마커로 표시합니다
          marker = new kakao.maps.Marker({
            // map: map,
            position: coords
          });
          marker.setMap(map);

          // 인포윈도우로 장소에 대한 설명을 표시합니다
          infowindow = new kakao.maps.InfoWindow({
            position: coords,
            content:
              '<div style="width:150px;text-align:center;padding:6px 0;">' +
              Name +
              "</div>"
          });
          infowindow.open(map, marker);

          // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
          map.setCenter(coords);
        } else {
          var geocoder2 = new kakao.maps.services.Geocoder();
          geocoder2.addressSearch(Dong, function(result, status) {
            // 정상적으로 검색이 완료됐으면
            console.log(status);
            if (status === kakao.maps.services.Status.OK) {
              var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

              // 결과값으로 받은 위치를 마커로 표시합니다
              marker = new kakao.maps.Marker({
                // map: map,
                position: coords
              });
              marker.setMap(map);

              // 인포윈도우로 장소에 대한 설명을 표시합니다
              infowindow = new kakao.maps.InfoWindow({
                position: coords,
                content:
                  '<div style="width:150px;text-align:center;padding:6px 0;">' +
                  aptName +
                  "</div>"
              });
              infowindow.open(map, marker);

              // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
              map.setCenter(coords);
            }
          });
        }
      });
    }
  }
};
</script>

<style>
#map {
  width: 99%;
  height: 300px;
}
#makemap {
  margin-top: 4%;
  margin-bottom: 2%;
  margin-bottom: 15%;
}
</style>
