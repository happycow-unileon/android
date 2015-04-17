package es.unileon.happycow.model.iterator;

import java.util.LinkedList;
import java.util.Stack;

import es.unileon.happycow.model.composite.Component;
import es.unileon.happycow.model.composite.Composite;

public class IteratorStack implements Iterator<Component> {
	
	private Stack<Component> stack;
	private Component _current;
	
	public IteratorStack(Component component) {
		stack = new Stack<Component>();
		stack .add(component);
	}
	
	@Override
	public Component next() {
		_current = stack.pop();
		
		if(!_current.isLeaf()){
			Composite children = (Composite)_current;
			for(Component c : children.getList()){
				stack.push(c);
			}
		}
		
		return _current;
	}

	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

}
