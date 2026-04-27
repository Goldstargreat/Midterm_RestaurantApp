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

    // 상세페이지를 구분하기 위한 요청 코드 (아무 숫자나 상관없음)
    static final int REQUEST_DETAIL = 1;

    // 현재 선택된 위치를 저장하는 변수
    int selectedPosition = 0;

    // 화면에서 계속 사용할 뷰들은 전역 변수로 선언
    TextView tvSelected;
    Spinner spRestaurant;

    // 맛집 이름과 번호를 저장하는 리스트
    ArrayList<String> dataList;
    ArrayList<String> numberList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 뷰 연결
        tvSelected   = (TextView)  findViewById(R.id.tv_selected);
        spRestaurant = (Spinner)   findViewById(R.id.sp_restaurant);
        EditText etRestaurant = (EditText) findViewById(R.id.et_restaurant);
        EditText etNumber     = (EditText) findViewById(R.id.et_number);
        Button btnAdd    = (Button) findViewById(R.id.btn_add);
        Button btnDelete = (Button) findViewById(R.id.btn_delete);
        Button btnDetail = (Button) findViewById(R.id.btn_detail);

        // 기본 데이터 추가
        dataList = new ArrayList<String>();
        dataList.add("술탄케밥");
        dataList.add("아웃백 스테이크하우스");
        dataList.add("맥도날드");

        numberList = new ArrayList<String>();
        numberList.add("02-111-1111");
        numberList.add("02-222-2222");
        numberList.add("02-333-3333");

        // 스피너(콤보박스)에 리스트 연결
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRestaurant.setAdapter(adapter);

        // 스피너에서 항목을 선택했을 때
        spRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                tvSelected.setText("현재 선택된 맛집: " + dataList.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택 안 했을 때는 아무것도 안 함
            }
        });

        // 추가 버튼을 눌렀을 때
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName   = etRestaurant.getText().toString().trim();
                String inputNumber = etNumber.getText().toString().trim();

                // 이름이 비어있으면 추가하지 않음
                if (inputName.isEmpty()) {
                    return;
                }

                // 번호가 비어있으면 "번호 없음"으로 저장
                if (inputNumber.isEmpty()) {
                    inputNumber = "번호 없음";
                }

                dataList.add(inputName);
                numberList.add(inputNumber);
                adapter.notifyDataSetChanged();                 // 스피너 화면 새로고침
                spRestaurant.setSelection(dataList.size() - 1); // 방금 추가한 항목 선택

                tvSelected.setText(inputName + " 가 추가되었습니다.");
                etRestaurant.setText("");
                etNumber.setText("");
            }
        });

        // 삭제 버튼을 눌렀을 때
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 리스트가 비어있으면 아무것도 안 함
                if (dataList.size() == 0) {
                    return;
                }

                String removedName = dataList.get(selectedPosition);
                dataList.remove(selectedPosition);
                numberList.remove(selectedPosition);
                adapter.notifyDataSetChanged(); // 스피너 화면 새로고침

                tvSelected.setText(removedName + " : 삭제되었습니다.");
                selectedPosition = 0;

                if (dataList.size() > 0) {
                    spRestaurant.setSelection(0);
                }
            }
        });

        // 상세 보기 버튼을 눌렀을 때
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 리스트가 비어있으면 아무것도 안 함
                if (dataList.size() == 0) {
                    return;
                }

                // DetailActivity로 이동
                // startActivityForResult: 상세페이지에서 수정한 값을 돌려받기 위해 사용
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("name",   dataList.get(selectedPosition));
                intent.putExtra("number", numberList.get(selectedPosition));
                startActivityForResult(intent, REQUEST_DETAIL);
            }
        });
    }

    // DetailActivity에서 [저장] 버튼을 누르고 돌아오면 이 메소드가 자동으로 호출됨
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // requestCode: 어디서 돌아왔는지 확인
        // resultCode : RESULT_OK면 저장 버튼을 누른 것, RESULT_CANCELED면 돌아가기를 누른 것
        if (requestCode == REQUEST_DETAIL && resultCode == RESULT_OK) {
            String updatedNumber = data.getStringExtra("updatedNumber");
            numberList.set(selectedPosition, updatedNumber); // 리스트에서 해당 번호 교체
            tvSelected.setText(dataList.get(selectedPosition) + " 연락처가 수정되었습니다.");
        }
    }
}