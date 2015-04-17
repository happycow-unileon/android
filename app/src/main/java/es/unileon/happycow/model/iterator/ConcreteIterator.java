package es.unileon.happycow.model.iterator;

import java.util.LinkedList;
import java.util.Queue;

import es.unileon.happycow.model.composite.Component;
import es.unileon.happycow.model.composite.Composite;

public class ConcreteIterator implements Iterator<Component> {
	
	private Queue<Component> queue;
	private Component _current;
	
	public ConcreteIterator(Component component) {
		queue =  new LinkedList<Component>();
		queue.add(component);
	}
	
	//Recorre en anchura
	@Override
	public Component next() {
		_current = queue.poll();
		
		if(!_current.isLeaf()){
			Composite children = (Composite)_current;
			for(Component c : children.getList()){
				queue.add(c);
			}
		}
		
		return _current;
	}

	@Override
	public boolean hasNext() {
		return !queue.isEmpty();
	}

}
