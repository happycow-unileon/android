package es.unileon.happycow.controller.admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.unileon.happycow.R;

public class NewUser extends Fragment {

    public NewUser(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_new_user, container, false);

        return rootView;
    }
}
