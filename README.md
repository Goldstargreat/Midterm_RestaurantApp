# 🍱 맛집 리스트 관리 매니저 (Restaurant Manager)
> **폴리텍대학교 인공지능소프트웨어학과 2학년 중간고사 과제**

사용자가 선호하는 맛집 목록을 **Spinner(콤보박스)**를 통해 효율적으로 관리하고, **Intent**를 활용하여 상세 정보를 전달하는 안드로이드 애플리케이션입니다.

---

## 📌 주요 기능 (Key Features)
* **동적 리스트 관리**: `ArrayList`와 `ArrayAdapter`를 활용하여 실시간으로 맛집 항목을 추가 및 삭제할 수 있습니다.
* **Spinner UI 적용**: 한정된 모바일 화면을 효율적으로 사용하기 위해 드롭다운 방식의 콤보박스를 구현하였습니다.
* **데이터 전달 (Intent)**: 메인 화면에서 선택된 맛집의 이름과 연락처 데이터를 상세 페이지(`DetailActivity`)로 안전하게 배달합니다.
* **UX 최적화**: 앱 실행 시 자동 선택 이벤트가 발생하여 초기 문구가 사라지는 현상을 `boolean` 플래그로 방어하였습니다.

---

## 🛠 기술 스택 (Tech Stack)
* **Language**: Java
* **IDE**: Android Studio
* **Design**: Figma (Android Compact 규격 사용)
* **Target SDK**: Android 12.0 (S) 이상 권장

---

## 📂 프로젝트 구조 (Project Structure)
* `MainActivity.java`: 맛집 리스트 관리 및 메인 UI 제어
* `DetailActivity.java`: 전달받은 맛집 상세 정보 출력
* `activity_main.xml`: 메인 화면 레이아웃 (Spinner, EditText, Buttons)
* `activity_detail.xml`: 상세 정보 확인 레이아웃
* `AndroidManifest.xml`: 앱 구성 요소 및 액티비티 등록

---

## 🎨 UI 설계 (Figma)
본 프로젝트는 **[Figma]**를 통해 사전에 UI를 설계하였으며, 교수님 계정(`kjminn20001@gmail.com`)에 협업 권한이 부여되어 있습니다.

---

## 👨‍💻 개발자 정보
* **소속**: 폴리텍대학교 인공지능소프트웨어학과
* **학년**: 2학년
* **성명**: 박경구 (Park Gyeong-gu)

---

### 💡 실행 방법
1. 이 리포지토리를 `Clone` 하거나 ZIP 파일로 다운로드합니다.
2. Android Studio에서 프로젝트를 엽니다.
3. Emulator 또는 안드로이드 기기를 연결하여 `Run`을 실행합니다.
