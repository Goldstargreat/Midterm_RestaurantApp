package kr.ac.kopo.midterm_restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvName   = (TextView)  findViewById(R.id.tv_detail_name);
        EditText etNumber = (EditText)  findViewById(R.id.et_detail_number);

        // XML이 TextView이므로 타입을 TextView로 통일
        TextView btnSave  = (TextView)  findViewById(R.id.btn_save);
        TextView btnBack  = (TextView)  findViewById(R.id.btn_back);

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

        btnBack.setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v)
                {
                setResult(RESULT_CANCELED);
                finish();
                }
            }
        );
    }
}