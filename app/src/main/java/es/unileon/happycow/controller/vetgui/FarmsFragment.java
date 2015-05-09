package es.unileon.happycow.controller.vetgui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import es.unileon.happycow.utils.list.rows.EntradaCardFarm;
import es.unileon.happycow.utils.list.rows.EntradaHeader;
import es.unileon.happycow.utils.list.EntradaLista;
import es.unileon.happycow.utils.list.ListAdapter;
import es.unileon.happycow.R;

public class FarmsFragment extends Fragment {

    private ListView listViewFarms;
    static int i = 0;
    private ListAdapter adapter;
    static List<EntradaLista> cardFarmsList = new ArrayList<>();
    public FarmsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_farms, container, false);
        listViewFarms = (ListView) rootView.findViewById(R.id.listViewFarms);


        cardFarmsList.add(new EntradaHeader());

        adapter=new ListAdapter(getActivity(), cardFarmsList);
        listViewFarms.setAdapter(adapter);


        registerForContextMenu(listViewFarms);

        ImageButton fabButton = (ImageButton) rootView.findViewById(R.id.fab_image_button);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardFarmsList.add(new EntradaCardFarm("Rancho " +i , "7492433-HTR-" + i,"Antonio Molina",
                        "75674545-W","567", String.valueOf(i)));
                i++;
                adapter.notifyDataSetChanged();
            }
        });



        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater mInflater = getActivity().getMenuInflater();

        if(v.getId() == R.id.listViewFarms) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

            menu.setHeaderTitle("List farms");

            mInflater.inflate(R.menu.context_menu, menu);

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.contextMenuAdd:
                //TODO contex menu add functionality
                return true;
            case R.id.contextMenuDelete:
                //TODO contex menu delete functionality
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

}
