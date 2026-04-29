package kr.ac.kopo.midterm_restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends Activity {

    static final int REQUEST_DETAIL = 1;
    int selectedPosition = 0;

    TextView tvSelected;
    Spinner spRestaurant;

    // 카테고리별로 리스트를 아예 따로 만듭니다 (이게 가장 쉽습니다)
    ArrayList<String> listAll = new ArrayList<>();
    ArrayList<String> listKorean = new ArrayList<>();
    ArrayList<String> listWestern = new ArrayList<>();
    ArrayList<String> listJapanese = new ArrayList<>();

    // 전화번호도 세트로 따로 관리합니다
    ArrayList<String> numsAll = new ArrayList<>();
    ArrayList<String> numsKorean = new ArrayList<>();
    ArrayList<String> numsWestern = new ArrayList<>();
    ArrayList<String> numsJapanese = new ArrayList<>();

    // 현재 스피너가 쓰고 있는 "활성 리스트" 참조 변수
    ArrayList<String> currentData;
    ArrayList<String> currentNums;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSelected = findViewById(R.id.tv_selected);
        spRestaurant = findViewById(R.id.sp_restaurant);

        // 1. 초기 데이터 강제 삽입 (예시)
        listAll.add("[한식] 전주식당"); numsAll.add("02-111-1111");
        listKorean.add("[한식] 전주식당"); numsKorean.add("02-111-1111");

        listAll.add("[양식] 아웃백"); numsAll.add("02-222-2222");
        listWestern.add("[양식] 아웃백"); numsWestern.add("02-222-2222");

        // 초기 데이터 예시 추가
        listAll.add("[일식] 미도리초밥"); numsAll.add("02-333-3333");
        listJapanese.add("[일식] 미도리초밥"); numsJapanese.add("02-333-3333");

        // 2. 시작할 때는 '전체 리스트'를 연결
        currentData = listAll;
        currentNums = numsAll;

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currentData);
        spRestaurant.setAdapter(adapter);

        // 3. 버튼 클릭 시 리스트 "교체" (복잡한 filter 함수 대신 직접 대입)
        findViewById(R.id.btn_cat_all).setOnClickListener(v -> {
            changeCategory(listAll, numsAll);
        });

        findViewById(R.id.btn_cat_korean).setOnClickListener(v -> {
            changeCategory(listKorean, numsKorean);
        });

        findViewById(R.id.btn_cat_western).setOnClickListener(v -> {
            changeCategory(listWestern, numsWestern);
        });
        findViewById(R.id.btn_cat_jap).setOnClickListener(v -> {
            changeCategory(listJapanese, numsJapanese);
        });

        // 스피너 선택 처리
        spRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                selectedPosition = pos;
                tvSelected.setText("선택: " + currentData.get(pos));
            }
            @Override
            public void onNothingSelected(AdapterView<?> p) {}
        });

        // 상세 보기 버튼 (현재 활성화된 리스트에서 데이터 추출)
        findViewById(R.id.btn_detail).setOnClickListener(v -> {
            if (currentData.isEmpty()) return;
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("name", currentData.get(selectedPosition));
            intent.putExtra("number", currentNums.get(selectedPosition));
            startActivityForResult(intent, REQUEST_DETAIL);
        });
    }

    // 리스트를 통째로 갈아끼우는 아주 쉬운 함수
    private void changeCategory(ArrayList<String> newData, ArrayList<String> newNums) {
        currentData = newData;
        currentNums = newNums;

        // 새로운 리스트로 어댑터 다시 설정
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currentData);
        spRestaurant.setAdapter(adapter);
        tvSelected.setText("카테고리가 변경되었습니다.");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DETAIL && resultCode == RESULT_OK) {
            String updatedNumber = data.getStringExtra("updatedNumber");
            // 현재 리스트의 번호 수정
            currentNums.set(selectedPosition, updatedNumber);
            tvSelected.setText("연락처가 수정되었습니다.");
        }
    }
}