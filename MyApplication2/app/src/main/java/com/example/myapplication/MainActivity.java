package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    TextView txtView;
    Button showdata,btnDelete,btnUpdate,btnAdd;
    ContentResolver content;
    EditText editTxt;
    boolean getFirsttime = false;

    private String[] mprojection = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };

    String mSelectionClause = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + "=?";
    String[] mSelectionArguments = new String[]{"Aman"};
    String morder = ContactsContract.Contacts._ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        txtView = findViewById(R.id.txtview);
        showdata = findViewById(R.id.data);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnAdd = findViewById(R.id.btnAdd);
        editTxt = findViewById(R.id.editTxt);
        content = getContentResolver();

        //For Showing all Contacts.
        showdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getFirsttime == false) {
                    getSupportLoaderManager().initLoader(1, null, MainActivity.this);
                    getFirsttime = true;
                }
                else{
                    getSupportLoaderManager().restartLoader(1,null,MainActivity.this);
                }
            }
        });

        //For Deleting Contacts.
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTxt.getText().toString() != null) {
                    removeContacts();
                }
                else{
                    Toast.makeText(MainActivity.this,"Enter a Contact",Toast.LENGTH_LONG).show();
                }
            }
        });
        //For Updating any preexisting Contact.
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContacts();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactToContacts();
            }
        });
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == 1) {
            return new CursorLoader(MainActivity.this,
                    ContactsContract.Contacts.CONTENT_URI,
                    mprojection, null, null, morder);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data != null && data.getCount() > 0) {
            StringBuilder str = new StringBuilder("");
            while (data.moveToNext()) {
                str.append(data.getString(0)).append(" , ")
                        .append(data.getString(1)).append(" , ")
                        .append(data.getString(2)).append(" , ")
                        .append(data.getString(3)).append("\n");
            }
            txtView.setText(str.toString());
        } else {
            txtView.setText("No Contacts in contact List");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // Handle loader reset if needed
    }

    private void removeContacts(){
        String whereClause = ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY+ " = '"+editTxt.getText().toString()+"'";
        getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI,whereClause,null);
    }

    private void updateContacts(){
        String[] input = editTxt.getText().toString().split(",");
        String targetvalue = null;
        String newString = null;

        if(input.length == 2){
            targetvalue = input[0];
            newString = input[1];

            String where = ContactsContract.RawContacts._ID + "= ?";
            String[] params = new String[] {targetvalue};

            ContentResolver contentResolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY,newString);
            contentResolver.update(ContactsContract.RawContacts.CONTENT_URI,contentValues,where,params);
        }
    }
    private void addContactToContacts() {
        String[] input = editTxt.getText().toString().split(",");
        String name = input[0];
        String phone = input[1];
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();

        operations.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                .build());

        operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build());

        try {
            ContentResolver resolver = getContentResolver();
            resolver.applyBatch(ContactsContract.AUTHORITY, operations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
        /*ContentResolver content = getContentResolver();
        Cursor cursor = content.query(ContactsContract.Contacts.CONTENT_URI,
                mprojection,
                null,
                null,
                morder);
        if(cursor != null && cursor.getCount()>0){
            StringBuilder str = new StringBuilder("");
            while(cursor.moveToNext()){
                str.append(cursor.getString(0)+" , "+cursor.getString(1)+" , "+cursor.getString(2)+"\n");
            }
            txtView.setText(str.toString());
        }
        else{
            txtView.setText("No Contacts in contact List");
        }
    }*/