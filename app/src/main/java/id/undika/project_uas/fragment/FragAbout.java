package id.undika.project_uas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import id.undika.project_uas.R;

public class FragAbout extends Fragment {

    public View onCreView(LayoutInflater inflater, ViewGroup container,
                          Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        return view;
    }
}
