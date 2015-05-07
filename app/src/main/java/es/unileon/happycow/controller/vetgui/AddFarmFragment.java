package es.unileon.happycow.controller.vetgui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import es.unileon.happycow.R;

public class AddFarmFragment extends Fragment {
/*
    private AddFarmView addFarmView;
    private static class AddFarmView implements View.OnFocusChangeListener {
        TextView etFarmerName, etFarmerID, etFarmID, etFarmName, etCowsNum;

        public AddFarmView(Activity activity){
            etFarmerName = (TextView)activity.findViewById(R.id.editTextFarmerName);
            etFarmerName.setOnFocusChangeListener(this);

            etFarmerID = (TextView)activity.findViewById(R.id.editTextFarmerID);
            etFarmerID.setOnFocusChangeListener(this);

            etFarmName = (TextView)activity.findViewById(R.id.editTextFarmName);
            etFarmName.setOnFocusChangeListener(this);

            etFarmID = (TextView)activity.findViewById(R.id.editTextFarmID);
            etFarmID.setOnFocusChangeListener(this);

            etCowsNum = (TextView)activity.findViewById(R.id.editTextCowsNum);
            etCowsNum.setOnFocusChangeListener(this);


        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus){
                v.setBackgroundResource(R.drawable.focus_border_style);
            }else{
                v.setBackgroundResource(R.drawable.lost_focus_border_style);
            }
        }

    }
*/


    public AddFarmFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //addFarmView = new AddFarmView(getActivity());

    }
}