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

        // 스피너(콤보박스) 선택 이벤트
        spRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstSelection) {
                    isFirstSelection = false;
                    return;
                }
                selectedPosition = position;
                String selectedItem = dataList.get(position);
                tvSelected.setText("현재 선택된 맛집: " + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // [수정] 맛집 추가 버튼: 빈 값 체크 추가
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etRestaurant.getText().toString().trim();

                if (input.isEmpty()) {
                    // 입력값이 없을 때 토스트 메시지 출력
                    Toast.makeText(getApplicationContext(), "추가할 맛집 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    dataList.add(input);
                    adapter.notifyDataSetChanged();
                    spRestaurant.setSelection(dataList.size() - 1);
                    tvSelected.setText(input + " 가 리스트에 추가되었습니다.");
                    etRestaurant.setText("");
                }
            }
        });

        // [수정] 맛집 삭제 버튼: 리스트가 비었을 때 예외 처리 추가
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList.size() > 0) {
                    // 삭제할 아이템이 있는 경우
                    String removedItem = dataList.get(selectedPosition);
                    dataList.remove(selectedPosition);
                    adapter.notifyDataSetChanged();

                    if (dataList.size() > 0) {
                        tvSelected.setText(removedItem + " : 삭제되었습니다.");
                        spRestaurant.setSelection(0);
                    } else {
                        // 마지막 남은 아이템을 삭제했을 때
                        tvSelected.setText("맛집 리스트가 비어 있습니다.");
                    }
                } else {
                    // [핵심] 리스트가 이미 비어있는데 삭제를 누른 경우
                    Toast.makeText(getApplicationContext(), "삭제할 맛집이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}