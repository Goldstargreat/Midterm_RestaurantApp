package kr.ac.kopo.midterm_restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends Activity {

    static final int REQUEST_DETAIL = 1;
    int selectedPosition = 0;

    TextView tvSelected;
    Spinner spRestaurant;

    ArrayList<String> dataList;
    ArrayList<String> numberList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSelected   = (TextView)  findViewById(R.id.tv_selected);
        spRestaurant = (Spinner)   findViewById(R.id.sp_restaurant);
        EditText etRestaurant = (EditText) findViewById(R.id.et_restaurant);
        EditText etNumber     = (EditText) findViewById(R.id.et_number);

        // Button 대신 TextView로 캐스팅 (XML 구조에 맞춤)
        TextView btnAdd    = (TextView) findViewById(R.id.btn_add);
        TextView btnDelete = (TextView) findViewById(R.id.btn_delete);
        TextView btnDetail = (TextView) findViewById(R.id.btn_detail);

        dataList = new ArrayList<String>();
        dataList.add("술탄케밥");
        dataList.add("아웃백 스테이크하우스");
        dataList.add("맥도날드");

        numberList = new ArrayList<String>();
        numberList.add("02-111-1111");
        numberList.add("02-222-2222");
        numberList.add("02-333-3333");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRestaurant.setAdapter(adapter);

        spRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                tvSelected.setText("현재 선택된 맛집: " + dataList.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName   = etRestaurant.getText().toString().trim();
                String inputNumber = etNumber.getText().toString().trim();

                if (inputName.isEmpty()) return;
                if (inputNumber.isEmpty()) inputNumber = "번호 없음";

                dataList.add(inputName);
                numberList.add(inputNumber);
                adapter.notifyDataSetChanged();
                spRestaurant.setSelection(dataList.size() - 1);

                tvSelected.setText(inputName + " 가 추가되었습니다.");
                etRestaurant.setText("");
                etNumber.setText("");
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList.size() == 0) return;

                String removedName = dataList.get(selectedPosition);
                dataList.remove(selectedPosition);
                numberList.remove(selectedPosition);
                adapter.notifyDataSetChanged();

                tvSelected.setText(removedName + " : 삭제되었습니다.");
                selectedPosition = 0;
                if (dataList.size() > 0) spRestaurant.setSelection(0);
            }
        });

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList.size() == 0) return;

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("name",   dataList.get(selectedPosition));
                intent.putExtra("number", numberList.get(selectedPosition));
                startActivityForResult(intent, REQUEST_DETAIL);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DETAIL && resultCode == RESULT_OK) {
            String updatedNumber = data.getStringExtra("updatedNumber");
            numberList.set(selectedPosition, updatedNumber);
            tvSelected.setText(dataList.get(selectedPosition) + " 연락처가 수정되었습니다.");
        }
    }
}