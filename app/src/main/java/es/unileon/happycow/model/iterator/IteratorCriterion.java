package es.unileon.happycow.model.iterator;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import es.unileon.happycow.model.composite.Component;
import es.unileon.happycow.model.composite.Composite;
import es.unileon.happycow.handler.*;

public class IteratorCriterion implements Iterator<Component> {
	private Queue<Component> queue;
	private Component _current;
	private Component _criterio;
	private Component _raiz;
	private boolean _found;
	/**
	 * Iterador que dado un criterio te devuelve todas las valoraciones
	 * @param criterio
	 */
	public IteratorCriterion(Component raiz, IdHandler criterio) {
		_found = false;
		_raiz = raiz;
		if(_raiz != null)
			_criterio = _raiz.search(criterio);
		queue = new LinkedList<Component>();
		queue.add(raiz);
	}
	
	@Override
	public Component next() {
		if(!_found){
			queue.poll(); // para quitar la raiz de la cola y dejarla vacia
			Composite root = null;
			if(!_raiz.isLeaf()){
				root = (Composite)_raiz;
				for(Component category : root.getList()){
					 if(!category.isLeaf()){
						 Composite c1 = (Composite)category;
						 for(Component criterion : c1.getList()){
							 if(criterion.getId() == _criterio.getId()){
								 if(!criterion.isLeaf()){
									 Composite c2 = (Composite)criterion;
									 for(Component c : c2.getList()){
										 queue.add(c);
									 }
								 }
							 }
						 }
					 }
				
				}
			}
			
			if(!queue.isEmpty()){
				_found = true;
				_current = queue.poll();
			}else{
				throw new NoSuchElementException();
			}
		
		}else{
			if(!queue.isEmpty())
				_current = queue.poll();
			else
				throw new NoSuchElementException();
		}
		
		return _current;
	}

	@Override
	public boolean hasNext() {
		return !queue.isEmpty();
	}

}
