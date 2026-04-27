package kr.ac.kopo.midterm_restaurantapp;

import android.app.Activity;
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

    private int selectedPosition = 0;
    // [추가] 처음 앱이 켜질 때 자동 선택되는 것을 막기 위한 변수
    private boolean isFirstSelection = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvSelected = (TextView) findViewById(R.id.tv_selected);
        final EditText etRestaurant = (EditText) findViewById(R.id.et_restaurant);
        Button btnAdd = (Button) findViewById(R.id.btn_add);
        Button btnDelete = (Button) findViewById(R.id.btn_delete);
        final Spinner spRestaurant = (Spinner) findViewById(R.id.sp_restaurant);

        final ArrayList<String> dataList = new ArrayList<String>();
        dataList.add("술탄케밥");
        dataList.add("아웃백 스테이크하우스");
        dataList.add("맥도날드");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRestaurant.setAdapter(adapter);

        // 콤보박스 선택 이벤트 수정
        spRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // [수정] 처음 실행될 때(isFirstSelection이 true일 때)는 텍스트를 바꾸지 않음
                if (isFirstSelection) {
                    isFirstSelection = false; // 다음부터는 작동하도록 상태 변경
                    return; // 여기서 실행을 멈춤 (문구 안 바뀜)
                }

                selectedPosition = position;
                String selectedItem = dataList.get(position);
                tvSelected.setText("현재 선택된 맛집: " + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // 맛집 추가 버튼 (이전과 동일)
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etRestaurant.getText().toString().trim();
                if (!input.isEmpty()) {
                    dataList.add(input);
                    adapter.notifyDataSetChanged();
                    spRestaurant.setSelection(dataList.size() - 1);
                    tvSelected.setText(input + " 가 리스트에 추가되었습니다.");
                    etRestaurant.setText("");
                }
            }
        });

        // 맛집 삭제 버튼 (이전과 동일)
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList.size() > 0) {
                    String removedItem = dataList.get(selectedPosition);
                    dataList.remove(selectedPosition);
                    adapter.notifyDataSetChanged();
                    tvSelected.setText(removedItem + " : 삭제되었습니다.");
                    if (dataList.size() > 0) spRestaurant.setSelection(0);
                }
            }
        });
    }
}