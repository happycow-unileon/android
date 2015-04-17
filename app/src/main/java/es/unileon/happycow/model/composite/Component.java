package es.unileon.happycow.model.composite;

import es.unileon.happycow.handler.*;
import es.unileon.happycow.model.InformationEvaluation;
import es.unileon.happycow.model.iterator.Aggregate;
import es.unileon.happycow.model.table.Entity;
import java.io.Serializable;

public interface Component extends Aggregate, Serializable {
	public boolean isLeaf();
	public Component search(IdHandler id);
	public void show(int depth);
	public Entity getLevel();
	public IdHandler getId();
        public float getWeighing();
        public void setWeighing(float weighing);
        
        public Component getParent();
        public Component getRoot();
        public void setParent(Component parent);
        public void setRoot(Component root);
        public InformationEvaluation getInformation();
}
