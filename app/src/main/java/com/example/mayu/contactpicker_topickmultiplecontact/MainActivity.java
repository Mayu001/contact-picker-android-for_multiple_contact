package com.example.mayu.contactpicker_topickmultiplecontact;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.app.Activity.RESULT_OK;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_PICK_CONTACT=1;
    private static int PICK=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void PickContact(View view)
    {
        if(view.getId()==R.id.PickContactButton1) {
            Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(i, RESULT_PICK_CONTACT);
            PICK=1;
        }
        else if(view.getId()==R.id.PickContactButton2)
        {
            Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(i, RESULT_PICK_CONTACT);
            PICK=2;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }
    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            String phoneNo = cursor.getString(phoneIndex);
            String name = cursor.getString(nameIndex);
            EditText ed1=(EditText)findViewById(R.id.Name1);
            EditText ed2=(EditText)findViewById(R.id.Phone1);

            EditText ed3=(EditText)findViewById(R.id.Name2);
            EditText ed4=(EditText)findViewById(R.id.Phone2);
            if(PICK==1) {
                ed2.setText(phoneNo);
                ed1.setText(name);
            }
            else if(PICK==2)
            {
                ed4.setText(phoneNo);
                ed3.setText(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }
    }
}
