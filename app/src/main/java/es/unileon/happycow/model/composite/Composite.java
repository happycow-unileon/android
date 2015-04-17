package es.unileon.happycow.model.composite;



import es.unileon.happycow.handler.*;
import es.unileon.happycow.model.InformationEvaluation;
import es.unileon.happycow.model.iterator.ConcreteIterator;
import es.unileon.happycow.model.iterator.Iterator;
import es.unileon.happycow.model.iterator.IteratorClassify;
import es.unileon.happycow.model.iterator.IteratorCriterion;
import es.unileon.happycow.model.iterator.IteratorException;
import es.unileon.happycow.model.iterator.IteratorStack;
import es.unileon.happycow.model.table.Table;
import java.util.LinkedList;

public abstract class Composite implements Component{
	
	private final IdHandler _idHandler;
	private final LinkedList<Component> _list;
	private float _weighing;
        private Component parent;
        private Component root;

	public Composite(IdHandler idHandler) {
		_idHandler = idHandler;
		_list = new LinkedList<>();
                _weighing=1;
	}
	
	public Composite(IdHandler idHandler, int weighing) {
		_idHandler = idHandler;
		_list = new LinkedList<Component>();
		_weighing = weighing;
	}
        
        public LinkedList<Component> getList(){
            return _list;
        }

	@Override
	public boolean isLeaf() {
		return false;
	}
	
	public boolean add(Component component) {
		if(component != null) {
			if(this.search(component.getId()) == null) {
				if(Table.getInstance().canAdd(getLevel(), component.getLevel())){
                                        component.setParent(this);
                                        component.setRoot(root);
					return _list.add(component);
				}
			}
		}
		
                return false;
		
	}
	
	public boolean remove(Component component) {
		return _list.remove(component);
	}
        
        public boolean delete(Component component) {
            boolean isRemoved = false;
            
            isRemoved = _list.remove(component);
            
            if(!isRemoved){
                for(int i=0; i < _list.size() && !isRemoved; i++){
                    if(!_list.get(i).isLeaf()){
                        Composite aux = (Composite)_list.get(i);
                        isRemoved = aux.delete(component);
                    }
                }
                    
            }
            
            return isRemoved;
        }
        
	@Override
	public void show(int depth) {  
		System.out.println(_idHandler.toString() + " --> " + depth);	
		for(int i=0; i<_list.size(); i++)
			_list.get(i).show(depth+1);
	}


	@Override
	public Component search(IdHandler id) {
		Component find = null;
		if(_idHandler.compareTo(id) == 0){
			find = this;
		}else{
			for(int i=0; i < _list.size() && find == null; i++)
                            find = _list.get(i).search(id);
		}
		
		return find;
	}

	@Override
	public IdHandler getId() {
		return _idHandler;
	}

	// Es el encargado de instanciar uno u otro iterador concreto
	@Override
	public Iterator<Component> iterator(String... args) throws IteratorException {
		if(args != null){
			if(args.length >= 0){
				switch (args.length) {
				case 0:
					return new IteratorStack(this);
				case 1:
					return new IteratorCriterion(this, new IdGeneric(args[0]));
				case 2:
					return new IteratorClassify(this, new IdGeneric(args[0]), new IdGeneric(args[1]));
				default:
					return new ConcreteIterator(this);
				}
			}else {
				throw new IteratorException();
			}
		}else{
			throw new IteratorException();
		}
		
	}

	public LinkedList<Component> getChildren() {
		return _list;
	}
	
        @Override
	public float getWeighing() {
		return _weighing;
	}
        
        @Override
	public void setWeighing(float weighing) {
		this._weighing = weighing;
	}
        
        @Override
        public Component getParent(){
            return parent;
        }
        
        @Override
        public Component getRoot(){
            return root;
        }

        @Override
        public void setParent(Component parent){
            this.parent=parent;
        }
        
        @Override
        public void setRoot(Component root){
            this.root=root;
        }
        
        @Override
        public InformationEvaluation getInformation(){
            return this.root.getInformation();
        }
}
