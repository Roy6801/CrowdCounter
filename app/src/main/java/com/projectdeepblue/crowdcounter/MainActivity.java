package com.projectdeepblue.crowdcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    ArrayAdapter<String> ad;
    DatabaseReference dbMall = FirebaseDatabase.getInstance().getReference("Mall");
    DatabaseReference dbServers = FirebaseDatabase.getInstance().getReference("Servers");
    DatabaseReference dbHosts = FirebaseDatabase.getInstance().getReference("Hosts");
    SharedPreferences sp;

    String server;
    int serverCount,totalCount;
    double crowdDensity, mallArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_main);

        sp  = getSharedPreferences("server",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();

        lvMain = findViewById(R.id.lvMain);
        tvMain = findViewById(R.id.tvMain);
        
        ad = new ArrayAdapter<String>(getApplicationContext(),R.layout.listview_layout);
        lvMain.setAdapter(ad);

        dbMall.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.getChildren())
                {
                    if(i.getKey().equals("area"))
                    {
                        mallArea = Double.parseDouble(i.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        mallCount(tvMain, mallArea);

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
                ed.apply();
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
                    if(i.getKey() != "Cameras")
                    {
                        ed.putString(i.getKey(),i.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void mallCount(TextView tvMain,Double mallArea)
    {
        dbHosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalCount = 0;
                crowdDensity = 0.0;
                serverCount = (int) snapshot.getChildrenCount();
                for(DataSnapshot i : snapshot.getChildren())
                {
                    crowdDensity = crowdDensity + Double.parseDouble(i.getValue().toString());
                }
                totalCount = (int) Math.ceil((mallArea * crowdDensity)/serverCount);
                tvMain.setText("Count : "+Integer.toString(totalCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}