package com.example.ushanthloganathan.contactmanager;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText nametxt, phonetxt, emailtxt, addresstxt;
    ImageView contactimgview;

    List<Content> contact = new ArrayList<Content>();

    ListView contactListView;
    Uri imageuri = Uri.parse("android.resource://com.example.ushanthloganathan.contactmanager/drawable/user.png");
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nametxt = (EditText) findViewById(R.id.txtName);
        phonetxt = (EditText) findViewById(R.id.txtphone);
        emailtxt = (EditText) findViewById(R.id.txtemail);
        addresstxt = (EditText) findViewById(R.id.txtaddress);
        contactListView = (ListView) findViewById(R.id.listView);
        contactimgview = (ImageView) findViewById(R.id.imgViewContactImage);
        dbHandler = new DatabaseHandler(getApplicationContext());

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
            public void onClick(View v) { //this function is for only one contact when user clicks on the button
                Content cntct = new Content(dbHandler.getContactsCount(), String.valueOf(nametxt.getText()), String.valueOf(phonetxt.getText()), String.valueOf(emailtxt.getText()), String.valueOf(addresstxt.getText()), imageuri);
                if (!contactExists(cntct)) { //if contact doesnt exist
                    dbHandler.createContact(cntct); //create the contact in db
                    contact.add(cntct); //add to contact list
                    Toast.makeText(getApplicationContext(), "Your contact " + String.valueOf(nametxt.getText()).toString() + " has been created", Toast.LENGTH_SHORT).show();
                    return;
                }
                //if it exists
                Toast.makeText(getApplicationContext(),String.valueOf(nametxt.getText())+" already exists. Please use a different name.",Toast.LENGTH_SHORT).show();

            }
        });


        nametxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(String.valueOf(nametxt.getText()).trim().length()>0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contactimgview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Contact Image"),1);
            }
        } );

        if(dbHandler.getContactsCount()!=0)
            contact.addAll(dbHandler.getAllContacts());

        populateList();
    }

    public boolean contactExists(Content contct){
        String name = contct.getName();
        int contactCount = contact.size();

        for(int i = 0;i<contactCount;i++){
            if (name.compareToIgnoreCase(contact.get(i).getName())==0)
                return true;

        }
        return false;
    }

   public void onActivityResult(int reqCode, int resCode, Intent data){
        if(resCode==RESULT_OK){
            if (reqCode==1)
                imageuri = data.getData();
                contactimgview.setImageURI(data.getData());
        }
    }


    private void populateList(){
        ArrayAdapter<Content> adapter = new ContactListAdapter();
        contactListView.setAdapter(adapter);
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

            ImageView ivContactimage = (ImageView)view.findViewById(R.id.ivcontactImage);
            ivContactimage.setImageURI(currentcontact.getImageURI());

            return view;

        }
    }
}
