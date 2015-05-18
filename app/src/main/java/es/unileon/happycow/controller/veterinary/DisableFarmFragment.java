package es.unileon.happycow.controller.veterinary;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import es.unileon.happycow.R;

public class DisableFarmFragment extends Fragment {

    public DisableFarmFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_disable, container, false);
        return rootView;
    }
}
