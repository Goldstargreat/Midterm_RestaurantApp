package kr.ac.kopo.midterm_restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends Activity {

    static final int REQUEST_DETAIL = 1;
    int selectedPosition = 0;

    TextView tvSelected;
    Spinner spRestaurant;
    EditText etRestaurant, etNumber;
    RadioGroup rgCategory;
    Button btnDetail;

    ArrayList<String> listAll = new ArrayList<>(), listKorean = new ArrayList<>(),
            listWestern = new ArrayList<>(), listJapanese = new ArrayList<>(),
            listChina = new ArrayList<>(), listEtc = new ArrayList<>();

    ArrayList<String> numsAll = new ArrayList<>(), numsKorean = new ArrayList<>(),
            numsWestern = new ArrayList<>(), numsJapanese = new ArrayList<>(),
            numsChina = new ArrayList<>(), numsEtc = new ArrayList<>();

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
        btnDetail = findViewById(R.id.btn_detail);

        currentData = listAll;
        currentNums = numsAll;

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currentData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRestaurant.setAdapter(adapter);

        // 카테고리 버튼 리스너
        View.OnClickListener catListener = v -> {
            int id = v.getId();
            if (id == R.id.btn_cat_all) updateSpinner(listAll, numsAll);
            else if (id == R.id.btn_cat_korean) updateSpinner(listKorean, numsKorean);
            else if (id == R.id.btn_cat_western) updateSpinner(listWestern, numsWestern);
            else if (id == R.id.btn_cat_japanese) updateSpinner(listJapanese, numsJapanese);
            else if (id == R.id.btn_cat_china) updateSpinner(listChina, numsChina);
            else if (id == R.id.btn_cat_etcetra) updateSpinner(listEtc, numsEtc);
        };

        int[] catIds = {R.id.btn_cat_all, R.id.btn_cat_korean, R.id.btn_cat_western,
                R.id.btn_cat_japanese, R.id.btn_cat_china, R.id.btn_cat_etcetra};
        for (int id : catIds) findViewById(id).setOnClickListener(catListener);

        // 추가 버튼
        findViewById(R.id.btn_add).setOnClickListener(v -> {
            String name = etRestaurant.getText().toString().trim();
            String num = etNumber.getText().toString().trim();
            if (name.isEmpty()) return;

            listAll.add(name); numsAll.add(num.isEmpty() ? "번호 없음" : num);

            int checkedId = rgCategory.getCheckedRadioButtonId();
            if (checkedId == R.id.rb_korean) { listKorean.add(name); numsKorean.add(num); }
            else if (checkedId == R.id.rb_western) { listWestern.add(name); numsWestern.add(num); }
            else if (checkedId == R.id.rb_japanese) { listJapanese.add(name); numsJapanese.add(num); }
            else if (checkedId == R.id.rb_china) { listChina.add(name); numsChina.add(num); }
            else if (checkedId == R.id.rb_etcetra) { listEtc.add(name); numsEtc.add(num); }

            adapter.notifyDataSetChanged();
            etRestaurant.setText(""); etNumber.setText("");
        });

        // 삭제 버튼
        findViewById(R.id.btn_delete).setOnClickListener(v -> {
            if (currentData.size() > 0) {
                String name = currentData.get(selectedPosition);
                // 전체 리스트 동기화 삭제
                int idx = listAll.indexOf(name);
                if (idx != -1) { listAll.remove(idx); numsAll.remove(idx); }
                if (currentData != listAll) { currentData.remove(selectedPosition); currentNums.remove(selectedPosition); }
                adapter.notifyDataSetChanged();
            }
        });

        spRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                selectedPosition = pos;
                tvSelected.setText("선택됨: " + currentData.get(pos));
            }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        });

        btnDetail.setOnClickListener(v -> {
            if (currentData.size() > 0) {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("name", currentData.get(selectedPosition));
                intent.putExtra("number", currentNums.get(selectedPosition));
                startActivityForResult(intent, REQUEST_DETAIL);
            }
        });
    }

    private void updateSpinner(ArrayList<String> data, ArrayList<String> nums) {
        currentData = data; currentNums = nums;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currentData);
        spRestaurant.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_DETAIL && resultCode == RESULT_OK) {
            currentNums.set(selectedPosition, data.getStringExtra("updatedNumber"));
        }
    }
}