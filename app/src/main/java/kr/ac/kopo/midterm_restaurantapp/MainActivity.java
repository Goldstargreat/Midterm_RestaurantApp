package kr.ac.kopo.midterm_restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends Activity {

    static final int REQUEST_DETAIL = 1; // 상세페이지 이동용 요청 코드
    int selectedPosition = 0;

    TextView tvSelected;
    Spinner spRestaurant;
    EditText etRestaurant, etNumber;
    RadioGroup rgCategory;

    // 카테고리별 데이터 관리 리스트
    ArrayList<String> listAll = new ArrayList<>();
    ArrayList<String> listKorean = new ArrayList<>();
    ArrayList<String> listWestern = new ArrayList<>();
    ArrayList<String> listJapanese = new ArrayList<>();

    ArrayList<String> numsAll = new ArrayList<>();
    ArrayList<String> numsKorean = new ArrayList<>();
    ArrayList<String> numsWestern = new ArrayList<>();
    ArrayList<String> numsJapanese = new ArrayList<>();

    // 현재 스피너에 표시되고 있는 리스트를 가리키는 참조 변수
    ArrayList<String> currentData;
    ArrayList<String> currentNums;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSelected = findViewById(R.id.tv_selected);
        spRestaurant = findViewById(R.id.sp_restaurant);
        etRestaurant = findViewById(R.id.et_restaurant);
        etNumber = findViewById(R.id.et_number);
        rgCategory = findViewById(R.id.rg_category);

        Button btnAdd = findViewById(R.id.btn_add);
        Button btnDelete = findViewById(R.id.btn_delete);

        // 초기 화면 설정 (전체 리스트)
        currentData = listAll;
        currentNums = numsAll;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currentData);
        spRestaurant.setAdapter(adapter);

        // 1. 맛집 추가 로직
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etRestaurant.getText().toString().trim();
                String phone = etNumber.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "맛집 이름을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.isEmpty()) phone = "번호 없음";

                // 전체 리스트에 추가
                listAll.add(name);
                numsAll.add(phone);

                // 라디오 버튼 선택 결과에 따라 해당 카테고리 리스트에 추가
                int checkedId = rgCategory.getCheckedRadioButtonId();
                if (checkedId == R.id.rb_korean) {
                    listKorean.add(name);
                    numsKorean.add(phone);
                } else if (checkedId == R.id.rb_western) {
                    listWestern.add(name);
                    numsWestern.add(phone);
                } else if (checkedId == R.id.rb_japanese) {
                    listJapanese.add(name);
                    numsJapanese.add(phone);
                }

                adapter.notifyDataSetChanged(); // 화면 갱신
                etRestaurant.setText("");
                etNumber.setText("");
                Toast.makeText(MainActivity.this, "맛집이 추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 2. 삭제 로직
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentData.size() > 0) {
                    String nameToRemove = currentData.get(selectedPosition);
                    String numToRemove = currentNums.get(selectedPosition);

                    // 모든 리스트에서 해당 데이터 삭제 (동기화)
                    listAll.remove(nameToRemove);
                    numsAll.remove(numToRemove);
                    listKorean.remove(nameToRemove);
                    numsKorean.remove(numToRemove);
                    listWestern.remove(nameToRemove);
                    numsWestern.remove(numToRemove);
                    listJapanese.remove(nameToRemove);
                    numsJapanese.remove(numToRemove);

                    adapter.notifyDataSetChanged();
                    tvSelected.setText("삭제 완료");
                }
            }
        });

        // 3. 카테고리 필터 버튼 설정
        findViewById(R.id.btn_cat_all).setOnClickListener(v -> changeCategory(listAll, numsAll));
        findViewById(R.id.btn_cat_korean).setOnClickListener(v -> changeCategory(listKorean, numsKorean));
        findViewById(R.id.btn_cat_western).setOnClickListener(v -> changeCategory(listWestern, numsWestern));
        findViewById(R.id.btn_cat_japanese).setOnClickListener(v -> changeCategory(listJapanese, numsJapanese));

        // 4. 스피너 선택 시: 상세페이지(DetailActivity)로 이동
        spRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                selectedPosition = pos;
                tvSelected.setText("선택: " + currentData.get(pos));

                // 상세 정보를 담아 이동
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("name", currentData.get(pos));
                intent.putExtra("number", currentNums.get(pos));
                startActivityForResult(intent, REQUEST_DETAIL);
            }
            @Override
            public void onNothingSelected(AdapterView<?> p) {}
        });
    }

    // 카테고리 전환 메소드
    private void changeCategory(ArrayList<String> newData, ArrayList<String> newNums) {
        currentData = newData;
        currentNums = newNums;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currentData);
        spRestaurant.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    // 5. 상세페이지에서 돌아왔을 때 (전화번호 수정 반영)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_DETAIL && resultCode == RESULT_OK) {
            String updatedNumber = data.getStringExtra("updatedNumber");
            String targetName = currentData.get(selectedPosition);

            // 1. 현재 리스트 번호 업데이트
            currentNums.set(selectedPosition, updatedNumber);

            // 2. 전체 리스트 번호 동기화
            int allIdx = listAll.indexOf(targetName);
            if (allIdx != -1) numsAll.set(allIdx, updatedNumber);

            // 3. 다른 카테고리 리스트들도 있다면 동기화 (생략 가능하나 일관성을 위해 권장)
            int korIdx = listKorean.indexOf(targetName);
            if (korIdx != -1) numsKorean.set(korIdx, updatedNumber);

            int wesIdx = listWestern.indexOf(targetName);
            if (wesIdx != -1) numsWestern.set(wesIdx, updatedNumber);

            int japIdx = listJapanese.indexOf(targetName);
            if (japIdx != -1) numsJapanese.set(japIdx, updatedNumber);

            adapter.notifyDataSetChanged();
            Toast.makeText(this, "연락처가 수정되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}