package es.unileon.happycow.utils.list.rows;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import es.unileon.happycow.R;
import es.unileon.happycow.model.Farm;
import es.unileon.happycow.utils.list.EntradaLista;
import es.unileon.happycow.utils.list.Rows;
import es.unileon.happycow.utils.list.holders.CardFarmHolder;

public class EntradaCardFarm extends EntradaLista {
    //private int image;
    private Farm farm;
    //private String farmName, farmID, farmerName, farmerID, numCows, numEvaluations;

    public EntradaCardFarm(Farm farm){
        super(Rows.CARD_FARM);
        this.farm=farm;
    }

//    public EntradaCardFarm(int image, String farmName, String farmID, String farmerName, String farmerID, String numCows, String numEvaluations) {
//        super(Rows.CARD_FARM);
//        this.image = image;
//        this.farmName = farmName;
//        this.farmID = farmID;
//        this.farmerName = farmerName;
//        this.farmerID = farmerID;
//        this.numCows = numCows;
//        this.numEvaluations = numEvaluations;
//    }
//
//    public EntradaCardFarm(String farmName, String farmID, String farmerName, String farmerID, String numCows, String numEvaluations) {
//        super(Rows.CARD_FARM);
//        this.farmName = farmName;
//        this.farmID = farmID;
//        this.farmerName = farmerName;
//        this.farmerID = farmerID;
//        this.numCows = numCows;
//        this.numEvaluations = numEvaluations;
//    }


    @Override
    public View onView(LayoutInflater inflater, View view, ViewGroup parent) {
        CardFarmHolder holder=null;

        //si existe la vista, recojo el holder
        if(view!=null){
            holder=(CardFarmHolder)view.getTag();
        }

        //si no hay holder, lo creo
        if(holder==null){
            holder=new CardFarmHolder();
            view = inflater.inflate(R.layout.card_farm_item, parent, false);

            //pillo los elementos de la vista y los guardo en el holder que queda guardado en el view
            holder.farmerID = (TextView) view.findViewById(R.id.farmerID);
            holder.farmerName = (TextView) view.findViewById(R.id.farmerName);
            holder.farmName = (TextView) view.findViewById(R.id.farmName);
            holder.farmID= (TextView) view.findViewById(R.id.farmID);
            holder.numCows = (TextView) view.findViewById(R.id.numCows);
            holder.numEvaluations = (TextView) view.findViewById(R.id.numEvaluations);
            holder.image = (ImageView) view.findViewById(R.id.iconFarmer);

            view.setTag(holder);
        }

        //relleno los datos de la fila
        holder.farmerName.setText(farm.getFarmerName());
        holder.farmerID.setText(farm.getDniFarmer());
        holder.farmName.setText("Farm name: " + farm.getFarmName());
        holder.farmID.setText("Farm ID: " + farm.getFarmIdentifier());
        holder.numCows.setText("Number cows: " + farm.getCowNumber());
        holder.numEvaluations.setText("Evaluations: " + farm.getListEvaluation().size());
        holder.image.setImageResource(R.drawable.icon_farmer);

        return view;
    }

    @Override
    public View onViewExpandable(LayoutInflater inflater, View view, ViewGroup viewGroup, boolean isExpand) {
        return onView(inflater,view, viewGroup);
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }


}
