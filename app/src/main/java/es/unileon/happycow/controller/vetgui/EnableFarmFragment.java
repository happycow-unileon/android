package es.unileon.happycow.controller.vetgui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import es.unileon.happycow.R;

public class EnableFarmFragment extends Fragment {

    public EnableFarmFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_enable, container, false);
        return rootView;
    }
}
