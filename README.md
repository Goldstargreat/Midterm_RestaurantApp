# 🍴 Restaurant List Manager (식당 관리 매니저)
> **2026학년도 1학기 안드로이드 프로그래밍 중간고사 대체 과제**

사용자가 가고 싶은 식당이나 단골집 목록을 동적으로 관리할 수 있는 안드로이드 애플리케이션입니다. `ListView`와 `ArrayAdapter`를 활용하여 데이터의 추가, 삭제 및 상태 표시 기능을 구현하였습니다.

---

## 📌 주요 기능 (Key Features)

* **식당 리스트 추가**: 입력창(`EditText`)에 식당명을 입력하고 추가 버튼을 누르면 목록에 즉시 반영됩니다.
* **항목 선택 및 상태 출력**: 리스트의 항목을 터치하면 상단 텍스트뷰를 통해 현재 선택된 모임 장소를 실시간으로 확인할 수 있습니다.
* **데이터 삭제**: 목록에서 특정 식당을 선택한 후 삭제 버튼을 누르면 리스트에서 해당 데이터가 제거됩니다.
* **사용자 피드백**: 추가/삭제/미입력 등 각 상황에 맞는 토스트(Toast) 메시지와 텍스트 알림을 제공합니다.

---

## 🛠 기술 스택 (Tech Stack)

* **언어(Language)**: Java 17
* **IDE**: Android Studio Jellyfish (또는 본인 버전)
* **UI 디자인**: Figma (Material Design 기반)
* **주요 컴포넌트**: `ListView`, `ArrayAdapter`, `ArrayList`, `LinearLayout`

---

## 📐 설계 및 구조 (Architecture)

### 1. UI/UX Design (Figma)
사용자 편의를 위해 **상단(상태 표시) - 중단(제어) - 하단(목록)**의 직관적인 3단 구조로 설계하였습니다. 
* **Color Code**: 긍정(추가) - Blue / 경고(삭제) - Red / 상태 - Green

### 2. Data Flow

* **Model**: `ArrayList<String>`을 사용하여 식당 데이터를 관리합니다.
* **Adapter**: `ArrayAdapter`가 데이터 소스와 리스트 UI 사이의 다리 역할을 수행합니다.
* **Update**: `notifyDataSetChanged()`를 호출하여 데이터 변경 사항을 UI에 동기화합니다.

---

## 🚀 실행 화면 (Screenshots)

| 메인 화면 | 항목 추가/선택 | 항목 삭제 |
| :---: | :---: | :---: |
| ![초기화면](https://via.placeholder.com/200x400?text=Initial+UI) | ![추가화면](https://via.placeholder.com/200x400?text=Item+Added) | ![삭제화면](https://via.placeholder.com/200x400?text=Item+Deleted) |
*(※ 실제 실행 화면 캡처본으로 교체하여 사용하세요!)*

---

## 📂 프로젝트 구조 (Directory Structure)

```text
app/
 └── src/
      └── main/
           ├── java/com/example/restaurantmanager/
           │    └── MainActivity.java (비즈니스 로직)
           └── res/
                └── layout/
                     └── activity_main.xml (UI 레이아웃)
