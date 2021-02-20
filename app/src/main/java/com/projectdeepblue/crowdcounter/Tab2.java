package com.projectdeepblue.crowdcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class Tab2 extends Fragment {

    WebView wvTab2;
    SharedPreferences sp;
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab2, container, false);
        perform(v);
        return v;
    }

    private void perform(View v) {

        sp = getActivity().getSharedPreferences("server", Context.MODE_PRIVATE);
        wvTab2 = v.findViewById(R.id.wvTab2);

        url = "";

        if(sp.getString("type","").equals("local"))
        {
            url = "http://"+sp.getString("ip","")+":5000/count";
        }
        else if(sp.getString("type","").equals("deployed"))
        {
            url = sp.getString("appURL","")+"/count";
        }

        wvTab2.setWebViewClient(new WebViewClient());
        wvTab2.getSettings().setJavaScriptEnabled(true);
        wvTab2.getSettings().setDisplayZoomControls(false);
        wvTab2.getSettings().setBuiltInZoomControls(true);
        wvTab2.getSettings().setLoadWithOverviewMode(true);
        wvTab2.getSettings().setUseWideViewPort(true);
        wvTab2.loadUrl(url);
    }
}
