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
import java.util.ArrayList;

public class MainActivity extends Activity {

    private int selectedPosition = 0;
    private boolean isFirstSelection = true; // 초기 문구 유지를 위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvSelected = (TextView) findViewById(R.id.tv_selected);
        final EditText etRestaurant = (EditText) findViewById(R.id.et_restaurant);
        Button btnAdd = (Button) findViewById(R.id.btn_add);
        Button btnDelete = (Button) findViewById(R.id.btn_delete);
        Button btnDetail = (Button) findViewById(R.id.btn_detail);
        final Spinner spRestaurant = (Spinner) findViewById(R.id.sp_restaurant);

        final ArrayList<String> dataList = new ArrayList<String>();
        dataList.add("술탄케밥");
        dataList.add("아웃백 스테이크하우스");
        dataList.add("맥도날드");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRestaurant.setAdapter(adapter);

        // 콤보박스 선택 이벤트
        spRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstSelection) {
                    isFirstSelection = false;
                    return;
                }
                selectedPosition = position;
                tvSelected.setText("현재 선택된 맛집: " + dataList.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // 맛집 추가
        btnAdd.setOnClickListener(v -> {
            String input = etRestaurant.getText().toString().trim();
            if (!input.isEmpty()) {
                dataList.add(input);
                adapter.notifyDataSetChanged();
                spRestaurant.setSelection(dataList.size() - 1);
                tvSelected.setText(input + " 가 추가되었습니다.");
                etRestaurant.setText("");
            }
        });

        // 맛집 삭제
        btnDelete.setOnClickListener(v -> {
            if (dataList.size() > 0) {
                String removed = dataList.get(selectedPosition);
                dataList.remove(selectedPosition);
                adapter.notifyDataSetChanged();
                tvSelected.setText(removed + " : 삭제되었습니다.");
                selectedPosition = 0; //  삭제 버튼 누르고 범위 오류 방지
                if (dataList.size() > 0) spRestaurant.setSelection(0);
            }
        });

        // 상세 보기 (데이터 배달)
        btnDetail.setOnClickListener(v -> {
            if (dataList.size() > 0) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("name", dataList.get(selectedPosition));
                intent.putExtra("number", "02-123-4567 (예시)");
                startActivity(intent);
            }
        });
    }
}