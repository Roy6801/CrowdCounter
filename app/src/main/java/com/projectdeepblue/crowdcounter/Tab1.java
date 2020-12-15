package com.projectdeepblue.crowdcounter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class Tab1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.layout_tab1, container, false);
        perform(v);
        return v;
    }

    private void perform(View v)
    {
        new Client(v,"http://192.168.1.203:8000/video_feed");
    }
}