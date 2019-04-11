package com.example.win10.kimiarayayim;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Cursor cursor;
    private String name, phoneNumber;
    public static final int RequestPermissionCode = 1;
    private AlertDialog dialog;
    private LottieAnimationView contactAnimation;
    public int deger, toplam;
    private ArrayList<Contact> contactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EnableRuntimePermission();
        GetContactsIntoArrayList();

        contactAnimation = findViewById(R.id.animation_view);
        contactAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deger = (int) (Math.random() * toplam);
                Log.d("Sayı", String.valueOf(deger));
                dialogShow(deger);

            }
        });


    }

    private void dialogShow(final int sayı) {

        contactAnimation.cancelAnimation();

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        View view = getLayoutInflater().inflate(R.layout.activity_deneme, null);

        TextView userName = view.findViewById(R.id.name);
        userName.setText(contactList.get(sayı).getContactName().trim());

        TextView userPhoneNumber = view.findViewById(R.id.phone);
        userPhoneNumber.setText(contactList.get(sayı).getContactPhoneNumber().trim());

        Button ara = view.findViewById(R.id.ara);
        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactAnimation.playAnimation();
                dialog.dismiss();

                String phone = contactList.get(sayı).getContactPhoneNumber().trim();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);


            }
        });

        Button degistir = view.findViewById(R.id.degistir);
        degistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactAnimation.playAnimation();
                dialog.dismiss();
            }
        });


        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    public void GetContactsIntoArrayList() {

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactList.add(new Contact(name, R.drawable.unknown, phoneNumber));

        }


        toplam = contactList.size();
        Log.d("Toplam", String.valueOf(toplam));
        cursor.close();

    }

    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this,
                Manifest.permission.READ_CONTACTS)) {

            Toast.makeText(MainActivity.this, "CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                break;
        }
    }


}