package es.unileon.happycow.controller.vetgui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import es.unileon.happycow.R;
import es.unileon.happycow.database.Database;
import es.unileon.happycow.handler.IdFarm;
import es.unileon.happycow.handler.IdHandler;
import es.unileon.happycow.model.Farm;

public class AddFarmFragment extends Fragment {
    private TextView etFarmerName, etFarmerID, etFarmID, etFarmName, etCowsNum, etAddressFarm, etOtherData;
    private IdHandler idFarm;
    //private AddFarmView addFarmView;

    /*
    private static class AddFarmView implements View.OnFocusChangeListener {


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


    public AddFarmFragment(){
        idFarm=null;

}

    public void setIdFarm(IdHandler idFarm) {
        this.idFarm = idFarm;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
        Button add=(Button)rootView.findViewById(R.id.buttonAddFarm);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //addFarmView = new AddFarmView(getActivity());

    }


    public void saveFarm(){
        Farm farm;
        if(controlFarm()){
            if(idFarm!=null){
                farm= Database.getInstance(null).getFarm(idFarm);
                farm.setAddress(etAddressFarm.getText().toString());
                farm.setCowNumber(Integer.parseInt(etCowsNum.getText().toString()));
                farm.setDniFarmer(etFarmerID.getText().toString());
                farm.setFarmName(etFarmName.getText().toString());
                farm.setFarmerName(etFarmerName.getText().toString());
                farm.setOtherData(etOtherData.getText().toString());
            }else{
                IdHandler identificationfarm=new IdFarm(Database.getInstance(null).nextIdFarm());
                farm=new Farm(identificationfarm, etFarmName.getText().toString(), etFarmID.getText().toString(),
                        etAddressFarm.getText().toString(), etFarmerName.getText().toString(),
                        etFarmerID.getText().toString(), Integer.parseInt(etCowsNum.getText().toString()),
                        Database.getInstance(null).getUser().getId(), etOtherData.getText().toString());
            }

            if(idFarm!=null){
                if(!Database.getInstance(null).modifiedFarm(farm)){
                    Toast toast1 =
                            Toast.makeText(this.getActivity().getApplicationContext(),
                                    "Errores al guardar la granja, inténtelo de nuevo", Toast.LENGTH_SHORT);

                    toast1.show();

                }else{
                    //father.seeListFarm();
                    //volver a la lista de granjas una vez añadido
                }
            }else{
                if(!Database.getInstance(null).newFarm(farm)){
                    Toast toast1 =
                            Toast.makeText(this.getActivity().getApplicationContext(),
                                    "Errores al guardar la granja, inténtelo de nuevo", Toast.LENGTH_SHORT);

                    toast1.show();
                }else{
                    //father.seeListFarm();
                    //volver a la lista de granjas una vez añadido
                }
            }
        }
    }

    private boolean controlFarm(){
        boolean correct=true;
        //StringBuilder errores=new StringBuilder();
        if(etFarmName.getText().toString().trim().compareToIgnoreCase("")==0){
            correct=false;
            etFarmName.setError("Debe dar un nombre a la granja.\n" +
                    "Use el identificador de la granja como nombre si no se le ocurre uno.");
            //errores.append("\nDebe dar un nombre a la granja.\nUse el identificador de la granja como nombre si no se le ocurre uno.");
        }

        if(etCowsNum.getText().toString().trim().compareTo("")==0){
            correct=false;
            etCowsNum.setError("Debe introducir el número de vacas exactas/aproximadas que tiene la granja.");
            //errores.append("\nDebe introducir el número de vacas exactas/aproximadas que tiene la granja.");
        }else if(!isNumeric(etCowsNum.getText().toString())){
            correct=false;
            etCowsNum.setError("Debe introducir un número entero de vacas.");
            //errores.append("\nDebe introducir un número entero de vacas.");
        }

        //if(!correct){
            //JOptionPane.showMessageDialog(panel, errores, "Mala entrada", JOptionPane.WARNING_MESSAGE);
        //}
        //cambiar por borrar el setError cuando se recupera el foco en el campo

        return correct;
    }

    private boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}