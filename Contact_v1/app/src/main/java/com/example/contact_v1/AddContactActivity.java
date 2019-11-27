package com.example.contact_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.contactv4.R;

public class AddContactActivity extends AppCompatActivity {

    Contact contact;
    ImageButton imgBtnCancel, imgBtnCheck;
    EditText addName, addPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        addName = findViewById(R.id.add_name);
        addPhone = findViewById(R.id.add_phone);
        imgBtnCheck = findViewById(R.id.imgBtnCheck);
        imgBtnCancel = findViewById(R.id.imgBtnCancel);
        imgBtnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = addName.getText().toString();
                String phone = addPhone.getText().toString();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                contact = new Contact(name, phone);
                bundle.putSerializable("contact", contact);
                intent.putExtra("package", bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        imgBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                finish();
            }
        });
    }

}
