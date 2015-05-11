package es.unileon.happycow.utils.list.rows;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import es.unileon.happycow.R;
import es.unileon.happycow.model.composite.Evaluation;
import es.unileon.happycow.model.InformationEvaluation;
import es.unileon.happycow.utils.list.EntradaLista;
import es.unileon.happycow.utils.list.Rows;
import es.unileon.happycow.utils.list.holders.CardEvaluationHolder;

public class EntradaCardEvaluation extends EntradaLista {

    private Evaluation evaluation;
    private InformationEvaluation infoEvaluation;

    public EntradaCardEvaluation(Evaluation evaluation){
        super(Rows.CARD_EVALUATION);
        this.evaluation = evaluation;
        this.infoEvaluation = this.evaluation.getInfo();
    }

    public EntradaCardEvaluation(InformationEvaluation infoEvaluation){
        super(Rows.CARD_EVALUATION);
        this.infoEvaluation = infoEvaluation;
    }



    @Override
    public View onView(LayoutInflater inflater, View view, ViewGroup parent) {
        CardEvaluationHolder holder=null;

        //si existe la vista, recojo el holder
        if(view!=null){
            holder=(CardEvaluationHolder)view.getTag();
        }

        //si no hay holder, lo creo
        if(holder==null){
            holder=new CardEvaluationHolder();
            view = inflater.inflate(R.layout.card_evaluation_item, parent, false);



            //pillo los elementos de la vista y los guardo en el holder que queda guardado en el view
            holder.dateEvaluation = (TextView) view.findViewById(R.id.dateEvaluation);
            holder.averageEvaluation = (TextView) view.findViewById(R.id.averageEvaluation);
            holder.feedingEvaluation = (TextView) view.findViewById(R.id.feedingEvaluation);
            holder.healthEvaluation = (TextView) view.findViewById(R.id.healthEvaluation);
            holder.behaviourEvaluation= (TextView) view.findViewById(R.id.behaviourEvaluation);
            holder.comfortableEvaluation= (TextView) view.findViewById(R.id.comfortableEvaluation);
            holder.numCowsEvaluation = (TextView) view.findViewById(R.id.numCowsEvaluation);

            holder.iconCow= (ImageView) view.findViewById(R.id.iconNumberCows);
            holder.iconEvaluation = (ImageView) view.findViewById(R.id.iconEvaluation);
            holder.iconCow.setImageResource(R.drawable.cow);
            holder.iconEvaluation.setImageResource(R.drawable.evaluation);

            view.setTag(holder);
        }

        //relleno los datos de la fila
        holder.averageEvaluation.setText("Average: " + infoEvaluation.getNota());
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        holder.dateEvaluation.setText("Date: " + format.format(infoEvaluation.getFecha()));
        holder.feedingEvaluation.setText("Feeding: " + infoEvaluation.getAlimentacion());
        holder.healthEvaluation.setText("Health: " + infoEvaluation.getSalud());
        holder.behaviourEvaluation.setText("Behaviour: " + infoEvaluation.getComportamiento());
        holder.comfortableEvaluation.setText("Comfortable: " + infoEvaluation.getComfort());
        holder.iconCow.setImageResource(R.drawable.cow);
        holder.iconEvaluation.setImageResource(R.drawable.evaluation);

        return view;
    }

    @Override
    public View onViewExpandable(LayoutInflater inflater, View view, ViewGroup viewGroup, boolean isExpand) {
        return onView(inflater,view, viewGroup);
    }

}
