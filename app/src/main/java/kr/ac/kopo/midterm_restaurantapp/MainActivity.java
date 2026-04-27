package kr.ac.kopo.midterm_restaurantapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

// Activity 클래스를 상속받아 구현
public class MainActivity extends Activity {

    // -1은 아무것도 선택되지 않은 초기 상태를 의미
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 레이아웃 파일 연결

        // 위젯 연결
        final TextView tvSelected = (TextView) findViewById(R.id.tv_selected);
        final EditText etRestaurant = (EditText) findViewById(R.id.et_restaurant);
        Button btnAdd = (Button) findViewById(R.id.btn_add);
        Button btnDelete = (Button) findViewById(R.id.btn_delete);
        final ListView lvRestaurant = (ListView) findViewById(R.id.lv_restaurant);

        // 데이터 리스트 생성
        final ArrayList<String> dataList = new ArrayList<String>();
        dataList.add("술탄케밥");
        dataList.add("아웃백 스테이크하우스");
        dataList.add("맥도날드");

        // 어댑터 설정
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, dataList);
        lvRestaurant.setAdapter(adapter);

        // 항목 클릭 시 이벤트
        lvRestaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                String selectedItem = dataList.get(position);
                tvSelected.setText("오늘의 맛집: " + selectedItem);
            }
        });

        // 추가 버튼 클릭 시 이벤트
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etRestaurant.getText().toString().trim();
                if (!input.isEmpty()) {
                    dataList.add(input);
                    adapter.notifyDataSetChanged(); // 화면 갱신
                    tvSelected.setText(input + " 가 추가되었습니다.");
                    etRestaurant.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "식당명을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 삭제 버튼 클릭 시 이벤트
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition != -1) {
                    String removedItem = dataList.get(selectedPosition);
                    dataList.remove(selectedPosition);
                    adapter.notifyDataSetChanged(); // 화면 갱신

                    tvSelected.setText(removedItem + " 가 삭제되었습니다.");
                    selectedPosition = -1;
                    lvRestaurant.clearChoices();
                } else {
                    Toast.makeText(MainActivity.this, "삭제할 항목을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}