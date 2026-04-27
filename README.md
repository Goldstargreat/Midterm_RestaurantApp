# 🍴 Restaurant List Manager (식당 관리 매니저)
> **2026학년도 1학기 안드로이드 프로그래밍 중간고사 대체 과제**

사용자가 가고 싶은 식당이나 단골집 목록을 동적으로 관리할 수 있는 안드로이드 애플리케이션입니다. `ListView`와 `ArrayAdapter`를 활용하여 데이터의 추가, 삭제 및 상태 표시 기능을 구현함으로써 안드로이드의 핵심 데이터 바인딩 원리를 적용하였습니다.

---

## 👤 작성자 정보 (Developer Information)

* **이름**: 박경구 (Park Gyeong-gu)
* **학번**: 2501110205
* **소속**: 폴리텍대학교 인공지능소프트웨어학과 2학년
* **기술 스택**: Java, Android SDK, Figma

---

## 📌 주요 기능 (Key Features)

* **실시간 식당 추가**: 상단 입력창(`EditText`)에 식당명을 입력하고 '추가' 버튼을 누르면 목록에 즉시 반영됩니다.
* **항목 선택 및 상태 출력**: 리스트의 식당 항목을 터치하면 상단 텍스트뷰를 통해 현재 선택된 모임 장소를 실시간으로 확인할 수 있습니다.
* **데이터 삭제 기능**: 목록에서 특정 식당을 선택한 후 '삭제' 버튼을 누르면 데이터 리스트에서 해당 항목이 제거됩니다.
* **사용자 피드백**: 빈 값 입력 시 경고 토스트(Toast) 알림 및 작업 성공 시 상태 메시지를 제공합니다.

---

## 🛠 기술 스택 및 환경 (Tech Stack & Environment)

* **Language**: Java 17
* **IDE**: Android Studio Jellyfish (2023.3.1)
* **UI Design**: Figma (Material Design 기반)
* **Components**: `ListView`, `ArrayAdapter`, `ArrayList`, `LinearLayout`

---

## 📐 설계 및 구조 (Architecture)

### 1. UI/UX Design (Figma)
사용자의 시각적 흐름을 고려하여 **상단(상태 확인) - 중단(데이터 제어) - 하단(데이터 목록)**의 3단 레이아웃으로 설계하였습니다. 
* **색상 전략**: 추가(Blue / #2196F3), 삭제(Red / #F44336), 상태창(Green / #E8F5E9)

### 2. 프로그램 로직
* **데이터 모델**: `ArrayList<String>`을 사용하여 동적인 식당 리스트 데이터를 관리합니다.
* **어댑터 패턴**: `ArrayAdapter`가 Java 객체 데이터와 UI 리스트뷰 사이의 통신을 담당합니다.
* **상태 동기화**: 데이터 수정 시 `notifyDataSetChanged()`를 호출하여 실시간으로 UI를 갱신합니다.

---

## 🚀 실행 화면 (Screenshots)

| 초기 화면 및 리스트 | 식당 추가 및 선택 | 식당 항목 삭제 |
| :---: | :---: | :---: |
| ![Main UI](https://via.placeholder.com/200x400?text=Main+UI) | ![Add Item](https://via.placeholder.com/200x400?text=Add+Item) | ![Delete Item](https://via.placeholder.com/200x400?text=Delete+Item) |
*(※ 실제 에뮬레이터 실행 화면이나 피그마 설계 화면으로 교체할 것!)*

---

## 📂 프로젝트 구조 (Project Structure)

```text
app/
 └── src/
      └── main/
           ├── java/com/polytech/ai/restaurantmanager/
           │    └── MainActivity.java (핵심 비즈니스 로직)
           └── res/
                └── layout/
                     └── activity_main.xml (UI 레이아웃 설계)
