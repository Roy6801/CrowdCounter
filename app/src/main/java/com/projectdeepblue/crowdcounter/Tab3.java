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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tab3 extends Fragment {

    DatabaseReference dbHist;
    SharedPreferences sp;

    ListView lvTab3;
    ArrayAdapter<String> ad;

    String timestamp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab3, container, false);
        perform(v);
        return v;
    }

    private void perform(View v) {
        sp = getActivity().getSharedPreferences("server", Context.MODE_PRIVATE);
        lvTab3 = v.findViewById(R.id.lvTab3);

        String server = sp.getString("name","");

        ad = new ArrayAdapter<String>(getContext(),R.layout.listview_layout);
        lvTab3.setAdapter(ad);

        dbHist  = FirebaseDatabase.getInstance().getReference("History/"+server);

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

        lvTab3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                timestamp = lvTab3.getItemAtPosition(i).toString();
                Intent it = new Intent(getActivity(),MainActivity4.class);
                it.putExtra("timestamp",Process.toEpoch(timestamp));
                startActivity(it);
            }
        });
    }
}
