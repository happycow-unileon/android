package es.unileon.happycow.controller.vetgui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import es.unileon.happycow.R;

public class ExportFragment extends Fragment {

    public ExportFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_export, container, false);
        return rootView;
    }
}
