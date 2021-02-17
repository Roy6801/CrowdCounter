package com.projectdeepblue.crowdcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MallActivity extends AppCompatActivity {

    ListView lvMall;
    ArrayAdapter<String> ad;
    DatabaseReference dbHist = FirebaseDatabase.getInstance().getReference("Mall/History");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_mall);

        lvMall = findViewById(R.id.lvMall);

        ad = new ArrayAdapter<String>(getApplicationContext(),R.layout.listview_layout);
        lvMall.setAdapter(ad);

        dbHist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ad.clear();
                for(DataSnapshot i : snapshot.getChildren())
                {
                    ad.add(Process.toDatetime(i.getKey()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        lvMall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String timestamp = lvMall.getItemAtPosition(i).toString();
                Intent it = new Intent(MallActivity.this,MallActivity2.class);
                it.putExtra("timestamp",Process.toEpoch(timestamp));
                startActivity(it);
            }
        });
    }
}