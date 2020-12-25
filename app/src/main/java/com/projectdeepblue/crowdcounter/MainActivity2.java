package com.projectdeepblue.crowdcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity2 extends AppCompatActivity {

    ArrayAdapter<String> ad;
    ListView lvCamMain2;
    DatabaseReference dbCameras,serverRef;
    String server,ip,port,camera;
    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);

        lvCamMain2 = findViewById(R.id.lvCamMain2);
        ad = new ArrayAdapter<String>(getApplicationContext(),R.layout.listview_layout);
        lvCamMain2.setAdapter(ad);

        it = getIntent();
        server = it.getStringExtra("server");

        serverRef = FirebaseDatabase.getInstance().getReference("Servers/"+server);
        dbCameras = FirebaseDatabase.getInstance().getReference("Servers/"+server+"/Cameras");

        serverRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                ip = snapshot.child("ip").getValue().toString();
                port = snapshot.child("port").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });

        dbCameras.addValueEventListener(new ValueEventListener()
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

        lvCamMain2.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                camera = lvCamMain2.getItemAtPosition(i).toString();
                String url = "http://"+ip+":"+port+"/"+camera;
                Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();
                it = new Intent(MainActivity2.this,MainActivity3.class);
                it.putExtra("url",url);
                startActivity(it);
            }
        });
    }
}