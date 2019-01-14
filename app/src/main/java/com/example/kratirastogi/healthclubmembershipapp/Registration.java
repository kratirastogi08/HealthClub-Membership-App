package com.example.kratirastogi.healthclubmembershipapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    EditText txtid, txtpass;
    Button btnreg;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        txtid = findViewById(R.id.txtid);
        txtpass = findViewById(R.id.txtpass);


        btnreg = findViewById(R.id.btnreg);
        btnreg.setOnClickListener(this);
        sharedPreferences = getSharedPreferences("details", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public void onClick(View v) {
        String id = txtid.getText().toString();

        String pass = txtpass.getText().toString();
        int len = pass.length();
        if (len < 8) {
            Toast.makeText(this, "password should be greater than 8 characters", Toast.LENGTH_SHORT).show();

        } else {
            editor.putString("id", id);
            editor.putString("pass", pass);

            editor.commit();

            Toast.makeText(Registration.this, "datawritten", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }
}
