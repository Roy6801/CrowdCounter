package com.projectdeepblue.crowdcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private boolean flag;
    private EditText etUid,etPwd;
    private Button btReg,btLog;
    private TextView tvNote;
    private static DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference("Users");
    private User user;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        SharedPreferences sp = getSharedPreferences("info",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();

        user = new User();

        etUid = findViewById(R.id.etUid);
        etPwd = findViewById(R.id.etPwd);
        btReg = findViewById(R.id.btReg);
        btLog = findViewById(R.id.btLog);
        tvNote = findViewById(R.id.tvNote);

        btReg.setOnClickListener(view ->
        {
            if(uidCheck(etUid.getText().toString()))
            {
                if(etPwd.getText().toString().length()>=8) {
                    tvNote.setText("");
                    flag = false;
                    dbUsers.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            for (DataSnapshot i : snapshot.getChildren())
                            {
                                if (i.getKey().equals(etUid.getText().toString()))
                                {
                                    tvNote.setText("Username Exists");
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag)
                            {
                                user.setUid(etUid.getText().toString());
                                user.setPwd(etPwd.getText().toString());
                                dbUsers.child(user.getUid() + "/Pwd").setValue(user.getPwd());
                                tvNote.setText("Registration Success!");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {
                            Toast.makeText(LoginActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    etPwd.setError("Length should be more than or equal to 8!");
                }
            }
            else
            {
                etUid.setError("Length should be more than 4!\nMust contain one capital letter, one small letter and a number!\n Should not contain special characters!");
            }
        });

        btLog.setOnClickListener(view ->
        {
            flag = false;
            dbUsers.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    for (DataSnapshot i : snapshot.getChildren())
                    {
                        if (i.getKey().equals(etUid.getText().toString()))
                        {
                            if(i.child("Pwd").getValue().toString().equals(etPwd.getText().toString()))
                            {
                                flag = true;
                            }
                            break;
                        }
                    }
                    if(flag)
                    {
                        user.setUid(etUid.getText().toString());
                        user.setPwd(etPwd.getText().toString());
                        ed.putString("uid",user.getUid());
                        ed.putString("pwd",user.getPwd());
                        ed.apply();
                        Intent it = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(it);
                        finish();
                    }
                    else
                    {
                        tvNote.setText("Wrong Username/Password!");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {
                    Toast.makeText(LoginActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
    public boolean uidCheck(String uid)
    {
        flag = false;
        if(uid.length()>4)
        {
            flag = Pattern.matches("[A-Za-z0-9]+",uid);
        }
        return flag;
    }
}