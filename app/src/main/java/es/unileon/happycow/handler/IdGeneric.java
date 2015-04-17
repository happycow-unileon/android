package es.unileon.happycow.handler;


public class IdGeneric implements IdHandler{
	private String id;

	public IdGeneric(String id) {
		this.id = id;
	}

	public IdGeneric(IdHandler another) {
		this(another.toString());
	}

	@Override
	public int compareTo(IdHandler another) {
		return this.id.compareTo(another.toString());
	}

	@Override
	public String toString() {
		return this.id;
	}

}
