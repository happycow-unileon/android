package es.unileon.happycow.procedures;

import es.unileon.happycow.model.facade.InterfaceEvaluationModel;
import es.unileon.happycow.strategy.AverageEvaluation;
import es.unileon.happycow.strategy.EvaluationAlgorithm;

/**
 *
 * @author amdiaz8
 */
public class Report {
    private EvaluationAlgorithm calculus;
    private InterfaceEvaluationModel model;

    public Report(EvaluationAlgorithm calculus, InterfaceEvaluationModel model) {
        this.calculus = calculus;
        this.model = model;
    }
    
    public Report(InterfaceEvaluationModel model){
        this(new AverageEvaluation(model), model);
    }
    
    @Override
    public String toString(){
        return "<html><body>" + calculus.toString() + "</body></html>";
    }
}
