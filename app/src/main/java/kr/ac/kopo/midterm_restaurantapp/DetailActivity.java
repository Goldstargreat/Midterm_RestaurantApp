package kr.ac.kopo.midterm_restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvName = findViewById(R.id.tv_detail_name);
        TextView tvNumber = findViewById(R.id.tv_detail_number);
        Button btnBack = findViewById(R.id.btn_back);

        Intent intent = getIntent();
        tvName.setText(intent.getStringExtra("name"));
        tvNumber.setText("연락처: " + intent.getStringExtra("number"));

        btnBack.setOnClickListener(v -> finish());
    }
}