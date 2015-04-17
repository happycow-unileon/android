package es.unileon.happycow.model.composite;

import es.unileon.happycow.handler.*;
import es.unileon.happycow.model.table.Entity;

public class CategoryComposite extends Composite{
	public static final Entity TYPE = Entity.CATEGORY;
	
	public CategoryComposite(IdHandler idHandler) {
		super(idHandler);
	}
	
	public CategoryComposite(IdHandler idHandler, int weighing) {
		super(idHandler, weighing);
	}
	
	@Override
	public Entity getLevel() {
		return TYPE;
	}
	
}
