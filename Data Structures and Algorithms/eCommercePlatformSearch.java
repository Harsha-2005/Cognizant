class Product{
    private int productId;
    private String productName;
    private String category;

    public Product(int productId,String productName,String category){
        this.productId=productId;
        this.productName=productName;
        this.category=category;
    }
    public int getProductId(){
        return productId;
    }

    public String getProductName(){
        return productName;
    }

    public String getCategory(){
        return category;
    }

    public void setProductId(int productId){
        this.productId=productId;
    }

    public void setProductName(String productName){
        this.productName=productName;
    }

    public void setCategory(String category){
        this.category=category;
    }
}
public class eCommercePlatformSearch {
    public static void main(String[] args){

    }
}