package com.example.contact_v1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.contactv4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fba_add;
    Button btnCallHistory;
    private ListView lvContact;
    ArrayList<Contact> arrayList;
    MyDatabase db;
    EditText edt_Search;
    CustomAdapter customAdapter;
    Contact contact,k;
    int local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fba_add = findViewById(R.id.fab_add);
        btnCallHistory= findViewById(R.id.btn_call_history);
        lvContact = findViewById(R.id.lv_contact);

        db = new MyDatabase(this);
        arrayList = new ArrayList<Contact>();

        arrayList = db.getAllContact();
        customAdapter = new CustomAdapter(this, R.layout.row_listview, arrayList);
        lvContact.setAdapter(customAdapter);
        lvContact.deferNotifyDataSetChanged();
        lvContact.setTextFilterEnabled(true);
        edt_Search =  findViewById(R.id.edt_Search);
        edt_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayList = db.search(edt_Search.getText().toString());
                lvContact.setAdapter(new CustomAdapter(MainActivity.this,R.layout.row_listview,arrayList));
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                local = position;
                EditContact();
            }
        });
        lvContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = arrayList.get(position);
                arrayList.remove(position);
                db.deleteContact(contact);
                customAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        fba_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent, 123);
            }
        });
        btnCallHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallHistory();
            }
        });
    }


    private void EditContact() {
        Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("contact", arrayList.get(local));
        intent.putExtra("package", bundle);
        startActivityForResult(intent, 456);
    }
    private void CallHistory() {
        Intent intent = new Intent(MainActivity.this, CallHistory.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("contact", arrayList);
        intent.putExtra("package", bundle);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            Bundle bundle = data.getBundleExtra("package");
            contact = (Contact) bundle.getSerializable("contact");
            arrayList.add(contact);
            db.addContact(contact);
            db.getAllContact();
            customAdapter.notifyDataSetChanged();
        }
        if (requestCode == 456 && resultCode == RESULT_OK) {
            Bundle bundle = data.getBundleExtra("package");
            Contact contact = (Contact) bundle.getSerializable("contact");
            contact.setId(local + 1);
            db.updateContact(contact);
            db.getAllContact();
            arrayList.set(local, contact);
            customAdapter.notifyDataSetChanged();
        }
    }

}
