package com.projectdeepblue.crowdcounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tab1 extends Fragment {

    ListView lvTab1;
    TextView tvTab1;
    ArrayAdapter<String> ad;

    String camera;
    double area;

    DatabaseReference dbCameras, dbTotal;
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab1, container, false);
        perform(v);
        return v;
    }

    private void perform(View v) {

        lvTab1 = v.findViewById(R.id.lvTab1);
        tvTab1 = v.findViewById(R.id.tvTab1);

        sp = getActivity().getSharedPreferences("server", Context.MODE_PRIVATE);
        String server = sp.getString("name","");
        area = Double.parseDouble(sp.getString("coverage",""));
        dbTotal = FirebaseDatabase.getInstance().getReference("Hosts/"+server);
        dbCameras = FirebaseDatabase.getInstance().getReference("Servers/"+server+"/Cameras");

        ad = new ArrayAdapter<String>(getContext(),R.layout.listview_layout);
        lvTab1.setAdapter(ad);

        dbTotal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = (int) Math.ceil(Double.parseDouble(snapshot.getValue().toString()) * area);
                tvTab1.setText("Count : "+Integer.toString(count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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

        lvTab1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                camera = lvTab1.getItemAtPosition(i).toString();
                Intent it = new Intent(getActivity(),MainActivity3.class);
                it.putExtra("camera",camera);
                startActivity(it);
            }
        });
    }
}
