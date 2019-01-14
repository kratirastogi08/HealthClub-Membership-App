package com.example.kratirastogi.healthclubmembershipapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthConstants;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthManager;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_PRIVATE;

public class Login extends AppCompatActivity implements View.OnClickListener {
SharedPreferences sharedPreferences;
Button btnlog;
String id,pass;
EditText txtid,txtpass;
HealthManager healthManager;
SQLiteDatabase sqLiteDatabase;
    String clerkid,clerkpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         txtid=findViewById(R.id.txtid);
         txtpass=findViewById(R.id.txtpass);
         btnlog=findViewById(R.id.btnlog);
         btnlog.setOnClickListener(this);
         healthManager=new HealthManager(this);
         sqLiteDatabase=healthManager.openDb();
        SharedPreferences sharedPreferences = getSharedPreferences("details", MODE_PRIVATE);
         id=sharedPreferences.getString("id","");
         pass=sharedPreferences.getString("pass","");
    }

    @Override
    public void onClick(View v) {
        String userid=txtid.getText().toString().trim();
        String userpass=txtpass.getText().toString().trim();
        if(TextUtils.isEmpty(userid)|| TextUtils.isEmpty(userpass)){
            Toast.makeText(this, "please enter userid/password", Toast.LENGTH_SHORT).show();
        }
        else{
            if(id.equals(userid)&&userpass.equals(pass)){

                Intent i=new Intent(Login.this,MainActivity.class);
                startActivity(i);

            }
            else{
                String []values={userid,userpass};
                String []colnames={HealthConstants.COL_ID,HealthConstants.COL_PASS};
                Cursor c= sqLiteDatabase.query(HealthConstants.DBLOGINTABLE,colnames,HealthConstants.COL_ID+"=? and "+HealthConstants.COL_PASS+"=?",values,null,null,null) ;
                if(c!=null&&c.moveToFirst())
                {
                    /*clerkid=c.getString(c.getColumnIndex(HealthConstants.COL_ID));
                    clerkpass=c.getString(c.getColumnIndex(HealthConstants.COL_PASS));
        */

                    Intent i=new Intent(Login.this,ClerkNav.class);
                    startActivity(i);

                }
                else
                    {
                        Toast.makeText(this, "invalid userid/password", Toast.LENGTH_SHORT).show();
                    }
                c.close();
            }
        }
        txtid.setText("");
        txtpass.setText("");
    }
}