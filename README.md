# FineDustEscape
먼지 탈출

 이 프로젝트는 미세먼지의 수치를 직접 확인해보기 위하여 개발한 미세먼지 정보 수신 앱입니다.
현재 이 앱 또한 안드로이드 구글 마켓에 등록 완료하였고 버그 수정 업데이트까지 모든 과정을 혼자 진행하였습니다.

 GPS 위치 정보를 사용하여 현재 위치 기준으로 미세먼지 정보를 보여주는 기능을 탑재하였고,
직접 미세먼지 정보를 알고 싶은 지역을 검색하고 알 수 있으며, 매일 아침마다 미세먼지 정보를 알려주는 알림 기능이 있습니다. 
그리고 개발자에게 메일을 보내는 기능도 추가하였습니다.

 이 앱 역시 공공데이터 포털에서 제공하는 미세먼지 정보 API를 연동하기 위해  HTTP 라이브러리 loopj를 사용하였습니다.
그리고 알림 기능을 위해 Notification을 사용하였습니다.

 이 프로젝트를 통해 안드로이드의 reciever의 개념을 이해할 수 있었습니다.
 공공데이터 포털의 API의 결과가 원하는 결과가 나오지 않아 며칠 동안 고생하였지만, 
그것의 원인이 서비스키 encoding 문제라는 것을 알게 되었고 utf-8 인코딩 처리를 함으로서 원하는 결과를 얻을 수 있었습니다.
 GPS 정보를 얻는 부분은 오픈 소스를 사용하였습니다.
 
 향후에는 서비스를 사용하여 미세먼지 정보가 안 좋았을 때에 알림을 나중에 추가해보고 싶습니다.
그리고 지역명 검색 기능에서 자동완성이 되었으면 했는데 이것은 지역명 db가 필요한 부분이라서 적용하지 못한 것이 아쉽습니다.
