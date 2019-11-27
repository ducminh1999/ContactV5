package com.example.contact_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.contactv4.R;

public class EditContactActivity extends AppCompatActivity {

    ImageButton imgBtnCancel,imgBtnCheck;
    EditText editName, editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        imgBtnCancel = findViewById(R.id.imgBtnCancel);
        imgBtnCheck = findViewById(R.id.imgBtnCheck);
        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("package");
        Contact contact = (Contact) bundle.getSerializable("contact");
        editName.setText(contact.getName());
        editPhone.setText(contact.getPhone());

        imgBtnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                Contact contact = new Contact(name, phone);
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
    public void backClicked(View view) {
        this.onBackPressed();
    }
}
