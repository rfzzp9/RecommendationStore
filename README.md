# 돈 버는 점포



상권분석을 기반으로 예비 소상공인을 위한 맞춤형 점포를 추천하는 서비스입니다.

실제 존재하는 어플리케이션인 "호갱노노"의 UI를 벤치마킹했습니다.


## 📌 주요 기능


- 사용자에게 적합한 점포와 부가 정보(청소 및 시공업체, 소상공인 관련 정책) 제공
- 사용자 커뮤니티 및 스크랩 기능


## 📌 기술 스택


|구분|Skill|
|------|---|
|Language|Java|
|Networking|Firebase SDK|
|Design|Figma|
|ETC|SharedPreferences|


## 📌 개발 내용


- 사용자 인증 및 계정 관리
    - Firebase Authentication을 사용해 이메일 기반 회원가입/로그인 구현
- 메인 지도 화면
    - Kakao Maps API를 활용하여 지도 화면 생성
    - 지도 이동시 새로운 상권 데이터를 Firebase에서 비동기로 불러와 해당 영역의 점포 마커 표시
- 점포 상세 정보 화면
    - 마커 클릭 시 점포의 상세 정보를 Firebase에서 로드해 표시
- 필터 및 검색 기능
    - 카테고리 선택에 따라 마커가 실시간으로 변경되도록 구현
- 데이터 관리
    - Firebase Realtime Database를 사용하여 상권 및 점포 데이터 관리
- 커뮤니티 기능
    - Firebase를 활용해 글쓰기, 댓글 달기, 좋아요 기능 구현
- UI/UX 개선
    - Figma로 UI 프로토타입 설계 및 Android XML로 구현
