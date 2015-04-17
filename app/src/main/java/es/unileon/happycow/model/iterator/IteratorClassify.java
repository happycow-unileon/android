package es.unileon.happycow.model.iterator;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import es.unileon.happycow.model.composite.Component;
import es.unileon.happycow.model.composite.Composite;
import es.unileon.happycow.handler.*;

public class IteratorClassify implements Iterator<Component>{
	private Queue<Component> queue;
	private Component _current;
	private Component _categoria;
	private Component _criterio;
	private Component _raiz;
	private boolean found = false;
	
	public IteratorClassify(Component raiz, Component categoria, Component criterio) {
		_categoria = categoria;
		_criterio = criterio;
		_raiz = raiz;
		queue = new LinkedList<Component>();
		queue .add(raiz);
	}
	
	public IteratorClassify(Component raiz, IdHandler categoria, IdHandler criterio) {
		_raiz = raiz;
		
		if(_raiz != null)
			_categoria = _raiz.search(categoria); //Si existe
		
		if(_categoria != null)
			_criterio = _categoria.search(criterio); //Si existe
		
		queue = new LinkedList<Component>();
		queue .add(raiz);
	}
	
	@Override
	public Component next() {
		if (_categoria != null && _criterio != null) {

			_current = queue.poll();

			if (!_current.isLeaf()) {
				Composite categorias = (Composite) _current; // Buscamos en las categorias
				for (Component c1 : categorias.getList()) {
					if (c1.getId() == _categoria.getId()) { // Si la encontramos
						Composite criterios = (Composite) c1; // Buscamos en sus criterios
						for (Component c2 : criterios.getList()) {
							if (c2.getId() == _criterio.getId()) {// Si encontramos el criterio
								// anhadimos todas sus valoraciones a la cola
								Composite valoraciones = (Composite) c2;
								for (Component v : valoraciones.getList()) {
									queue.add(v);
									found = true;
								}
							}
						}
					}

				}
			}

		}else{
			throw new NoSuchElementException();
		}
		
		if(_current == _raiz)
			_current = queue.poll();
		
		if(!found)
			throw new NoSuchElementException();
		
		return _current;
	}

	@Override
	public boolean hasNext() {
		return !queue.isEmpty();
	}
}
