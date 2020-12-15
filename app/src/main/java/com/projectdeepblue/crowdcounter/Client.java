package com.projectdeepblue.crowdcounter;

import android.view.View;
import android.webkit.WebView;

public class Client
{
    WebView webView;

    public Client(View v, String url)
    {
        webView = v.findViewById(R.id.wvTab1);
        webView.loadUrl(url);
    }
}