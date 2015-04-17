package es.unileon.happycow.handler;


/**
 *
 * @author dorian
 */
public class IdCategory implements IdHandler{
    private Category category;

    public IdCategory(Category category) {
        this.category = category;
    }
    
    public IdCategory(String category){
        this.category=Category.getEnum(category);
    }
    
    public Category getCategory(){
        return category;
    }

    public IdCategory(IdHandler id) {
        try {
            IdCategory idCat=(IdCategory)id;
            this.category=idCat.getCategory();
        } catch (Exception e) {
            throw new IllegalArgumentException("No es un identificador de categoría");
        }
    }

    @Override
    public String toString() {
        return Category.getName(category);
    }

    @Override
    public int compareTo(IdHandler another) {
        return this.toString().compareTo(another.toString());
    }
}
