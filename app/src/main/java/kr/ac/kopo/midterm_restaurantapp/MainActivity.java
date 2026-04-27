package kr.ac.kopo.midterm_restaurantapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner; // Spinner 추가
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends Activity {

    // 현재 콤보박스에서 선택된 위치 저장
    private int selectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvSelected = (TextView) findViewById(R.id.tv_selected);
        final EditText etRestaurant = (EditText) findViewById(R.id.et_restaurant);
        Button btnAdd = (Button) findViewById(R.id.btn_add);
        Button btnDelete = (Button) findViewById(R.id.btn_delete);
        final Spinner spRestaurant = (Spinner) findViewById(R.id.sp_restaurant); // Spinner 연결

        final ArrayList<String> dataList = new ArrayList<String>();
        dataList.add("술탄케밥");
        dataList.add("아웃백 스테이크하우스");
        dataList.add("맥도날드");

        // Spinner 전용 어댑터 설정 (내장 레이아웃 사용)
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRestaurant.setAdapter(adapter);

        // 콤보박스(Spinner) 항목 선택 시 이벤트
        spRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                String selectedItem = dataList.get(position);
                tvSelected.setText("현재 선택된 맛집: " + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // 맛집 추가 버튼
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etRestaurant.getText().toString().trim();
                if (!input.isEmpty()) {
                    dataList.add(input);
                    adapter.notifyDataSetChanged();
                    spRestaurant.setSelection(dataList.size() - 1); // 추가된 항목 바로 선택
                    tvSelected.setText(input + " 가 리스트에 추가되었습니다.");
                    etRestaurant.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 맛집 삭제 버튼
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList.size() > 0) {
                    String removedItem = dataList.get(selectedPosition);
                    dataList.remove(selectedPosition);
                    adapter.notifyDataSetChanged();

                    tvSelected.setText(removedItem + " : 삭제되었습니다.");
                    // 리스트가 비어있지 않으면 첫 번째 항목 자동 선택
                    if (dataList.size() > 0) spRestaurant.setSelection(0);
                } else {
                    Toast.makeText(MainActivity.this, "삭제할 맛집이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}