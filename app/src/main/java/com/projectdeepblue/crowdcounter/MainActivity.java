package com.projectdeepblue.crowdcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ListView lvMain;
    TextView tvMain;
    Button btMain;
    ArrayAdapter<String> ad;
    DatabaseReference dbServers = FirebaseDatabase.getInstance().getReference("Servers");
    DatabaseReference dbMall = FirebaseDatabase.getInstance().getReference("Mall/count");
    SharedPreferences sp;

    String server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_main);

        sp  = getSharedPreferences("server",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();

        lvMain = findViewById(R.id.lvMain);
        tvMain = findViewById(R.id.tvMain);
        btMain = findViewById(R.id.btMain);

        btMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MallActivity.class));
            }
        });
        
        ad = new ArrayAdapter<String>(getApplicationContext(),R.layout.listview_layout);
        lvMain.setAdapter(ad);

        dbMall.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvMain.setText("Count : "+snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        dbServers.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                ad.clear();
                for(DataSnapshot i : snapshot.getChildren())
                {
                    ad.add(i.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                server = lvMain.getItemAtPosition(i).toString();
                ed.putString("name",server);
                serverInfo(ed);
                Intent it = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(it);
            }
        });
    }

    private void serverInfo(SharedPreferences.Editor ed)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Servers/"+server);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.getChildren())
                {
                    if(!i.getKey().equals("Cameras"))
                    {
                        ed.putString(i.getKey(),i.getValue().toString());
                    }
                }
                ed.commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}