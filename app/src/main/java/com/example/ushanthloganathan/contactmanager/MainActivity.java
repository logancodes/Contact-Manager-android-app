package com.example.ushanthloganathan.contactmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText nametxt, phonetxt, emailtxt, addresstxt;

    List<Content> contact = new ArrayList<Content>();

    ListView contactListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nametxt = (EditText) findViewById(R.id.txtName);
        phonetxt = (EditText) findViewById(R.id.txtphone);
        emailtxt = (EditText) findViewById(R.id.txtemail);
        addresstxt = (EditText) findViewById(R.id.txtaddress);
        contactListView = (ListView) findViewById(R.id.listView);
        TabHost tabhost = (TabHost) findViewById(R.id.tabHost);

        tabhost.setup();

        TabHost.TabSpec tabSpec = tabhost.newTabSpec("creator");
        tabSpec.setContent(R.id.tabCreator);
        tabSpec.setIndicator("Creator");
        tabhost.addTab(tabSpec);

        tabSpec = tabhost.newTabSpec("list");
        tabSpec.setContent(R.id.tabContactList);
        tabSpec.setIndicator("List");
        tabhost.addTab(tabSpec);

        final Button addBtn = (Button) findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContacts(nametxt.getText().toString(),phonetxt.getText().toString(),emailtxt.getText().toString(),addresstxt.getText().toString());
                populateList();
                Toast.makeText(getApplicationContext(),"Your contact "+nametxt.getText().toString()+" has been created",Toast.LENGTH_SHORT).show();
            }
        });


        nametxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(!nametxt.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    private void populateList(){
        ArrayAdapter<Content> adapter = new ContactListAdapter();
        contactListView.setAdapter(adapter);
    }

    private void addContacts(String name, String phone, String email, String address){
        contact.add(new Content(name,phone,email,address));
    }

    private class ContactListAdapter extends ArrayAdapter<Content> {
        public ContactListAdapter() {
            super(MainActivity.this, R.layout.listview_item, contact);

        }
        @Override
        public View getView(int position, View view, ViewGroup parent){
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item,parent,false);

            Content currentcontact = contact.get(position);

            TextView name = (TextView) view.findViewById(R.id.ContactName);
            name.setText(currentcontact.getName());

            TextView phone = (TextView) view.findViewById(R.id.PhoneNumber);
            phone.setText(currentcontact.get_phone());

            TextView email = (TextView) view.findViewById(R.id.emailAddress);
            email.setText(currentcontact.getMail());

            TextView address = (TextView) view.findViewById(R.id.Address);
            address.setText(currentcontact.getAddress());

            return view;

        }
    }
}
