package es.unileon.happycow.controller.admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import es.unileon.happycow.R;
import es.unileon.happycow.database.Database;
import es.unileon.happycow.handler.IdFarm;
import es.unileon.happycow.handler.IdUser;
import es.unileon.happycow.model.Farm;
import es.unileon.happycow.model.User;
import es.unileon.happycow.model.composite.Evaluation;
import es.unileon.happycow.model.excel.DataExcel;
import es.unileon.happycow.model.facade.InterfaceEvaluationModel;
import es.unileon.happycow.utils.expandableList.ExpandableListAdapter;
import es.unileon.happycow.utils.list.EntradaExcel;
import es.unileon.happycow.utils.list.EntradaLista;
import es.unileon.happycow.utils.list.ListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Excel extends Fragment {
    private Spinner spinner;
    private DataExcel model;

    public Excel() {

        // Required empty public constructor
    }

    private void fillDataList(View view){
        String target=spinner.getSelectedItem().toString();

        //iterate the data model to make the listview
        ArrayList<EntradaLista> farms=new ArrayList<>();
        HashMap<EntradaLista, List<EntradaLista>> evaluations=new HashMap<>();

        List<Farm> listFarm=Database.getInstance(null).getListFarms(new IdUser(target));
        EntradaLista entrada;
        for(Farm farm : listFarm){
            entrada=new EntradaExcel(farm.getFarmName(), false);
            farms.add(entrada);

            List<InterfaceEvaluationModel> eval=Database.getInstance(null).getListEvaluations(new IdUser(target), farm.getIdFarm());
            ArrayList<EntradaLista> listaEntradaEval=new ArrayList<>();
            EntradaLista entrada2;
            for(InterfaceEvaluationModel evaluationModel: eval){
                entrada2=new EntradaExcel(evaluationModel.toString(),false);
                listaEntradaEval.add(entrada2);
            }

            evaluations.put(entrada,listaEntradaEval);
        }

        ExpandableListView lista = (ExpandableListView) view.findViewById(R.id.listaExcel);
        ExpandableListAdapter adapter=new ExpandableListAdapter(this.getActivity(),farms,evaluations);
        lista.setAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot= inflater.inflate(R.layout.fragment_excel, container, false);
        spinner=(Spinner)viewRoot.findViewById(R.id.listUsers);

        LinkedList<String> listUsers=new LinkedList<>();
        //create the list of users and theirs lists of farms
        LinkedHashMap<User, LinkedList<Farm>> list=new LinkedHashMap<>();
        //for every user in the database...
        for (User user : Database.getInstance(null).getListUsers()) {
            //store his list of farms
            list.put(user, Database.getInstance(null).getListFarms(user.getId()));
            //store the user to the spinner
            listUsers.add(user.getName());
        }

        //set the users
        spinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listUsers));


        //store the data model
        model=new DataExcel(list);

        //fill the data
        fillDataList(viewRoot);


//        LinkedList<Farm> listFarms=Database.getInstance(null).getListFarms(new IdUser(target));



//        EntradaLista entrada;
//        for(Farm farm: listFarms){
//            entrada=new EntradaExcel(farm.getFarmName());
//        }
//
//        ListView lista = (ListView) viewRoot.findViewById(R.id.listaExcel);
//        ArrayList<EntradaLista> list=new ArrayList<>();

        //relleno lista
//        EntradaLista entrada=new EntradaExcel("hola", true);
//        list.add(entrada);
//        entrada=new EntradaExcel("pepito", true);
//        list.add(entrada);
//        entrada=new EntradaExcel("grillo", true);
//        list.add(entrada);
//        entrada=new EntradaExcel("probando", false);
//        list.add(entrada);
//        entrada=new EntradaExcel("listas", false);
//        list.add(entrada);
//        entrada=new EntradaExcel("con", false);
//        list.add(entrada);
//        entrada=new EntradaExcel("multi", true);
//        list.add(entrada);
//        entrada=new EntradaExcel("row", true);
//        list.add(entrada);
//        entrada=new EntradaExcel("woww!!", false);
//        list.add(entrada);
//
//        lista.setAdapter(new ListAdapter(this.getActivity(), list));

//        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });

        return viewRoot;
    }


}
