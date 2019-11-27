package com.example.contact_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.contactv4.R;

import java.util.ArrayList;

public class CallHistory extends AppCompatActivity {

    ImageButton btnBack;
    private ListView lvCallHistory;
    ArrayList<Contact> arrayList;
    CustomAdapter customAdapter;

    int local;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_history);


        lvCallHistory = findViewById(R.id.lv_callhistory);
        btnBack = findViewById(R.id.btn_Back);
        arrayList = new ArrayList<Contact>();




       // editName = findViewById(R.id.edit_name);
        //editPhone = findViewById(R.id.edit_phone);

        final Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("package");
        arrayList = (ArrayList<Contact>) bundle.getSerializable("contact");
        customAdapter = new CustomAdapter(this, R.layout.row_call_history, arrayList);

        lvCallHistory.setAdapter(customAdapter);
        lvCallHistory.deferNotifyDataSetChanged();
        lvCallHistory.setTextFilterEnabled(true);
        //editName.setText(contact.getName());
        //editPhone.setText(contact.getPhone());
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
