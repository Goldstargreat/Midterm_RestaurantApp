package kr.ac.kopo.midterm_restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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
    RadioGroup rgCategory;
    Button btnDetail;

    ArrayList<String> listAll = new ArrayList<>();
    ArrayList<String> listKorean = new ArrayList<>();
    ArrayList<String> listWestern = new ArrayList<>();
    ArrayList<String> listJapanese = new ArrayList<>();

    ArrayList<String> numsAll = new ArrayList<>();
    ArrayList<String> numsKorean = new ArrayList<>();
    ArrayList<String> numsWestern = new ArrayList<>();
    ArrayList<String> numsJapanese = new ArrayList<>();

    ArrayList<String> currentData;
    ArrayList<String> currentNums;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 연결
        tvSelected = (TextView) findViewById(R.id.tv_selected);
        spRestaurant = (Spinner) findViewById(R.id.sp_restaurant);
        etRestaurant = (EditText) findViewById(R.id.et_restaurant);
        etNumber = (EditText) findViewById(R.id.et_number);
        rgCategory = (RadioGroup) findViewById(R.id.rg_category);
        btnDetail = (Button) findViewById(R.id.btn_detail);

        // 데이터 초기화
        currentData = listAll;
        currentNums = numsAll;

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currentData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRestaurant.setAdapter(adapter);

        // 상단 카테고리 버튼 처리 (xml에 정의된 아이디와 일치시킴)
        Button btnCatAll = (Button) findViewById(R.id.btn_cat_all);
        Button btnCatKorean = (Button) findViewById(R.id.btn_cat_korean);
        Button btnCatWestern = (Button) findViewById(R.id.btn_cat_western);
        Button btnCatJapanese = (Button) findViewById(R.id.btn_cat_japanese);

        View.OnClickListener catListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.btn_cat_all) updateSpinner(listAll, numsAll);
                else if (id == R.id.btn_cat_korean) updateSpinner(listKorean, numsKorean);
                else if (id == R.id.btn_cat_western) updateSpinner(listWestern, numsWestern);
                else if (id == R.id.btn_cat_japanese) updateSpinner(listJapanese, numsJapanese);
            }
        };

        btnCatAll.setOnClickListener(catListener);
        btnCatKorean.setOnClickListener(catListener);
        btnCatWestern.setOnClickListener(catListener);
        btnCatJapanese.setOnClickListener(catListener);

        // 추가 버튼
        Button btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etRestaurant.getText().toString().trim();
                String num = etNumber.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (num.isEmpty()) num = "번호 없음";

                listAll.add(name);
                numsAll.add(num);

                int checkedId = rgCategory.getCheckedRadioButtonId();
                if (checkedId == R.id.rb_korean) {
                    listKorean.add(name);
                    numsKorean.add(num);
                } else if (checkedId == R.id.rb_western) {
                    listWestern.add(name);
                    numsWestern.add(num);
                } else if (checkedId == R.id.rb_japanese) {
                    listJapanese.add(name);
                    numsJapanese.add(num);
                }

                adapter.notifyDataSetChanged();
                etRestaurant.setText("");
                etNumber.setText("");
            }
        });

        // 삭제 버튼
        Button btnDelete = (Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentData.size() > 0 && selectedPosition < currentData.size()) {
                    String target = currentData.get(selectedPosition);
                    listAll.remove(target);
                    // 실제 인덱스 관리는 복잡하므로 간단하게 현재 리스트에서 제거
                    currentData.remove(selectedPosition);
                    currentNums.remove(selectedPosition);
                    adapter.notifyDataSetChanged();
                    tvSelected.setText("삭제되었습니다.");
                }
            }
        });

        // 스피너 선택 (위치 저장)
        spRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                selectedPosition = pos;
                tvSelected.setText("선택됨: " + currentData.get(pos));
            }
            @Override
            public void onNothingSelected(AdapterView<?> p) {}
        });

        // 상세보기 버튼 (McDonald 등 상세 페이지 이동)
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentData.size() > 0) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("name", currentData.get(selectedPosition));
                    intent.putExtra("number", currentNums.get(selectedPosition));
                    startActivityForResult(intent, REQUEST_DETAIL);
                } else {
                    Toast.makeText(MainActivity.this, "맛집을 먼저 추가하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateSpinner(ArrayList<String> newData, ArrayList<String> newNums) {
        currentData = newData;
        currentNums = newNums;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currentData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRestaurant.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DETAIL && resultCode == RESULT_OK) {
            String updatedNumber = data.getStringExtra("updatedNumber");
            currentNums.set(selectedPosition, updatedNumber);
            Toast.makeText(this, "번호가 수정되었습니다", Toast.LENGTH_SHORT).show();
        }
    }
}