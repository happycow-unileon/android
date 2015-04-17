package es.unileon.happycow.model.composite;

import es.unileon.happycow.handler.*;
import es.unileon.happycow.model.InformationEvaluation;
import es.unileon.happycow.model.table.Entity;

public class Evaluation extends Composite {
	private static final Entity TYPE = Entity.EVALUATION;
        private InformationEvaluation info;
	
	public Evaluation(IdHandler idHandler, InformationEvaluation info) {
		super(idHandler);
                this.info=info;
	}
	
	public Evaluation(IdHandler idHandler, int weighing, InformationEvaluation info){
		super(idHandler, weighing);
                this.info=info;
	}
        
        @Override
        public InformationEvaluation getInformation(){
            return info;
        }
	
	@Override
	public Entity getLevel() {
		return TYPE;
	}

}
