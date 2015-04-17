package es.unileon.happycow.model.iterator;

import es.unileon.happycow.model.composite.Component;

public interface Aggregate {
	public Iterator<Component> iterator(String... args) throws IteratorException;
}
