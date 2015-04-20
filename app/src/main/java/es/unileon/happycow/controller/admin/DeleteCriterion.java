package es.unileon.happycow.controller.admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.unileon.happycow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteCriterion extends Fragment {


    public DeleteCriterion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_criterion, container, false);
    }


}
