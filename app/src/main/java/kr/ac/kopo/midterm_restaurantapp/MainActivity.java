package kr.ac.kopo.midterm_restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends Activity {

    static final int REQUEST_DETAIL = 1;
    int selectedPosition = 0;

    TextView tvSelected;
    Spinner spRestaurant;
    EditText etRestaurant, etNumber;

    // 카테고리별 데이터 관리 리스트
    ArrayList<String> listAll = new ArrayList<>();
    ArrayList<String> listKorean = new ArrayList<>();
    ArrayList<String> listWestern = new ArrayList<>();
    ArrayList<String> listJapanese = new ArrayList<>();

    ArrayList<String> numsAll = new ArrayList<>();
    ArrayList<String> numsKorean = new ArrayList<>();
    ArrayList<String> numsWestern = new ArrayList<>();
    ArrayList<String> numsJapanese = new ArrayList<>();

    // 현재 활성화된 데이터 참조
    ArrayList<String> currentData;
    ArrayList<String> currentNums;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML 뷰 연결
        tvSelected = findViewById(R.id.tv_selected);
        spRestaurant = findViewById(R.id.sp_restaurant);
        etRestaurant = findViewById(R.id.et_restaurant);
        etNumber = findViewById(R.id.et_number);

        // 버튼 연결
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnDelete = findViewById(R.id.btn_delete);
        Button btnDetail = findViewById(R.id.btn_detail);

        // 초기 카테고리 설정
        currentData = listAll;
        currentNums = numsAll;

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currentData);
        spRestaurant.setAdapter(adapter);

        // --- 1. 추가 버튼 (가장 중요한 부분!) ---
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etRestaurant.getText().toString().trim();
                String phone = etNumber.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(MainActivity.this, "이름과 번호를 모두 입력하세요!", Toast.LENGTH_SHORT).show();
                } else {
                    // 현재 리스트에 직접 추가
                    currentData.add(name);
                    currentNums.add(phone);

                    // 어댑터 새로고침 (이걸 해야 화면에 보임)
                    adapter.notifyDataSetChanged();

                    // 입력창 비우기
                    etRestaurant.setText("");
                    etNumber.setText("");
                    Toast.makeText(MainActivity.this, "맛집 추가 완료", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // --- 2. 삭제 버튼 ---
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentData.size() > 0) {
                    currentData.remove(selectedPosition);
                    currentNums.remove(selectedPosition);
                    adapter.notifyDataSetChanged();
                    tvSelected.setText("삭제되었습니다.");
                }
            }
        });

        // --- 3. 상세보기 버튼 ---
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentData.isEmpty()) return;

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("name", currentData.get(selectedPosition));
                intent.putExtra("number", currentNums.get(selectedPosition));
                startActivityForResult(intent, REQUEST_DETAIL);
            }
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

        // 카테고리 버튼들 (All, Korean 등)
        findViewById(R.id.btn_cat_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { changeCategory(listAll, numsAll); }
        });

        findViewById(R.id.btn_cat_korean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { changeCategory(listKorean, numsKorean); }
        });
        // ... (나머지 카테고리 버튼도 동일한 방식으로 작성 가능)
    }

    private void changeCategory(ArrayList<String> newData, ArrayList<String> newNums) {
        currentData = newData;
        currentNums = newNums;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currentData);
        spRestaurant.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DETAIL && resultCode == RESULT_OK) {
            String updatedNumber = data.getStringExtra("updatedNumber");
            currentNums.set(selectedPosition, updatedNumber);
            tvSelected.setText("연락처 수정 완료!");
        }
    }
}