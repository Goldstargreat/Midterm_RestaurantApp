package kr.ac.kopo.midterm_restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvName   = (TextView) findViewById(R.id.tv_detail_name);
        EditText etNumber = (EditText) findViewById(R.id.et_detail_number);

        // TextView → Button 으로 변경
        Button btnSave = (Button) findViewById(R.id.btn_save);
        Button btnBack = (Button) findViewById(R.id.btn_back);
        Button btnOrder = (Button) findViewById(R.id.btn_order); // 추가된 버튼

        Intent intent = getIntent();
        String name   = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");

        tvName.setText(name);
        etNumber.setText(number);

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String updatedNumber = etNumber.getText().toString().trim();
                if (updatedNumber.isEmpty()) updatedNumber = "번호 없음";

                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedNumber", updatedNumber);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        // 2. 주문하기 버튼: 입력된 번호로 전화 다이얼 연결
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentNumber = etNumber.getText().toString().trim();

                if (currentNumber.isEmpty() || currentNumber.equals("번호 없음")) {
                    Toast.makeText(DetailActivity.this, "전화번호를 먼저 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // ACTION_DIAL은 권한 설정 없이 다이얼러 화면으로 이동시켜줍니다.
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse("tel:" + currentNumber));
                    startActivity(dialIntent);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}