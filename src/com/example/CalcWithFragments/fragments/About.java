
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

    public static final String TAG;

    static {
        TAG = "fragment_number";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        int i = getArguments().getInt(TAG);
//        String fragment = getResources().getStringArray(R.array.fragments)[i];
        getSupportActivity().setTitle(getResources().getStringArray(R.array.fragments)[1]);
        return inflater.inflate(R.layout.about);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setSubtitle("About");
    }
}