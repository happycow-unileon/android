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
import java.util.LinkedList;
import java.util.List;

import es.unileon.happycow.database.Database;
import es.unileon.happycow.model.Farm;
import es.unileon.happycow.utils.list.Rows;
import es.unileon.happycow.utils.list.rows.EntradaCardFarm;
import es.unileon.happycow.utils.list.rows.EntradaHeader;
import es.unileon.happycow.utils.list.EntradaLista;
import es.unileon.happycow.utils.list.ListAdapter;
import es.unileon.happycow.R;

public class FarmsFragment extends Fragment {

    private ListView listViewFarms;
    private FarmListener farmListener;
    private ListAdapter adapter;
    private List<EntradaLista> cardFarmsList;
    public FarmsFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_farms, container, false);
        listViewFarms = (ListView) rootView.findViewById(R.id.listViewFarms);

        cardFarmsList = new ArrayList<>();
        cardFarmsList.add(new EntradaHeader());
        LinkedList<Farm> listFarms= Database.getInstance(null).getListFarms();
        for(Farm farm:listFarms){
            cardFarmsList.add(new EntradaCardFarm(farm));
        }


        adapter=new ListAdapter(getActivity(), cardFarmsList);
        listViewFarms.setAdapter(adapter);


        registerForContextMenu(listViewFarms);

        ImageButton fabButton = (ImageButton) rootView.findViewById(R.id.fab_image_button);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListFarms activity = (ListFarms) getActivity();
                activity.displayView(1);
            }
        });

        listViewFarms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapter != null) {

                    EntradaLista entrada = (EntradaLista)adapter.getItem(i);

                    if(entrada.getTypeRow() == Rows.CARD_FARM){
                        EntradaCardFarm entradaFarm = (EntradaCardFarm)adapter.getItem(i);
                        farmListener.onFarmSelected(entradaFarm.getFarm());
                    }
                }
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

    public interface FarmListener {
        void onFarmSelected(Farm farm);
    }

    public FarmsFragment.FarmListener getFarmListener() {
        return farmListener;
    }

    public void setFarmListener(FarmsFragment.FarmListener farmListener) {
        this.farmListener = farmListener;
    }


}
