package es.unileon.happycow.model.composite;

//import es.unileon.happycow.database.*;
import es.unileon.happycow.handler.*;
import es.unileon.happycow.model.InformationEvaluation;
import es.unileon.happycow.model.iterator.Iterator;
import es.unileon.happycow.model.table.Entity;

public class Valoration implements Component {

    public static final Entity TYPE = Entity.VALORATION;
    private IdHandler _idHandler;
    private float _weighing;
    private float nota;
    private Component parent;
    private Component root;
    
    public Valoration(IdHandler idHandler, float nota) {
        _idHandler = idHandler;
        this.nota = nota;
        //por defecto el peso es 1
        this._weighing = 1;
    }

    public Valoration(float nota) {
        this((IdHandler) new IdValoration(1),//Database.getInstance().nextIdValoration()),
                nota);

    }

    public IdHandler getIdCategory() {
        //parent es el criterio, getparent me da la categoria
        return parent.getParent().getId();
    }

    public IdHandler getIdEvaluation(){
        return this.root.getInformation().getIdEvaluation();
    }
    
    public IdHandler getIdCriterion(){
        return this.parent.getId();
    }
    
    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public void show(int depth) {
        System.out.println(_idHandler.toString() + " --> " + depth);
    }

    @Override
    public Component search(IdHandler id) {
        if (_idHandler.compareTo(id) == 0) {
            return this;
        }

        return null;
    }

    @Override
    public IdHandler getId() {
        return _idHandler;
    }

    @Override
    public Iterator<Component> iterator(String[] args) {
        return null;
    }

    @Override
    public Entity getLevel() {
        return TYPE;
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
