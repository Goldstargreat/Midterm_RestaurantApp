package kr.ac.kopo.midterm_restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 뷰 연결
        TextView tvName   = (TextView)  findViewById(R.id.tv_detail_name);
        EditText etNumber = (EditText)  findViewById(R.id.et_detail_number);
        Button btnSave    = (Button)    findViewById(R.id.btn_save);
        Button btnBack    = (Button)    findViewById(R.id.btn_back);

        // MainActivity에서 보내준 데이터 받기
        Intent intent = getIntent();
        String name   = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");

        // 화면에 표시
        tvName.setText(name);
        etNumber.setText(number);

        // 저장 버튼을 눌렀을 때
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText에서 수정된 번호 가져오기
                String updatedNumber = etNumber.getText().toString().trim();

                if (updatedNumber.isEmpty()) {
                    updatedNumber = "번호 없음";
                }

                // 수정된 번호를 MainActivity로 돌려보내기
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedNumber", updatedNumber);
                setResult(RESULT_OK, resultIntent); // RESULT_OK = 저장 성공
                finish(); // 이 화면 닫기
            }
        });

        // 돌아가기 버튼을 눌렀을 때 (저장 안 하고 그냥 닫기)
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED); // RESULT_CANCELED = 저장 안 함
                finish(); // 이 화면 닫기
            }
        });
    }
}