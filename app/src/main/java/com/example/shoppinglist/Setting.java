package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Setting extends AppCompatActivity {
    private RadioGroup radioGroup0;
    private RadioGroup radioGroup1;
    private RadioButton radioButton;
    private RadioButton radioButton1;
    private Button back;

    private String sortby;
    private String order;
    private ArrayList<ListItem> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        radioGroup0 = findViewById(R.id.radioGroup2);
        radioGroup1 = findViewById(R.id.radioGroup);

        back = findViewById(R.id.button);

        radioGroup0.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                radioButton = findViewById(id);
                Log.d("dd",radioButton.getText().toString());
            }
        });
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                radioButton1 = findViewById(id);
                Log.d("dd",radioButton1.getText().toString());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioButton != null && radioButton1 != null) {
                    Intent intent = new Intent();
                    intent.putExtra("Sort_by",radioButton.getText().toString());
                    intent.putExtra("order",radioButton1.getText().toString());
                    setResult(RESULT_OK,intent);
                }else{
                    setResult(RESULT_CANCELED);
                }
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("dd","dd");
    }
}
