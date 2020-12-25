package com.projectdeepblue.crowdcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ListView lvMain;
    ArrayAdapter<String> ad;
    DatabaseReference dbServers = FirebaseDatabase.getInstance().getReference("Servers");
    Intent it;

    String server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        lvMain = findViewById(R.id.lvMain);
        ad = new ArrayAdapter<String>(getApplicationContext(),R.layout.listview_layout);
        lvMain.setAdapter(ad);

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

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                server = lvMain.getItemAtPosition(i).toString();
                it = new Intent(MainActivity.this,MainActivity2.class);
                it.putExtra("server",server);
                startActivity(it);
            }
        });
    }
}