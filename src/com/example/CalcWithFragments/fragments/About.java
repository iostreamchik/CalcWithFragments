
package com.example.CalcWithFragments.fragments;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.example.CalcWithFragments.R;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;

public class About extends Fragment {
    public About() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getSupportActionBar().setSubtitle("About");
        return inflater.inflate(R.layout.about);
    }
}