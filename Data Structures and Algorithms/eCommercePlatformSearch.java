/*Asymptotic Notation is the mathematical representation of the time complexity of completing the execution of an algorithm
* Big(O) Notation : This Notation gives the Upper bound of an algorithm's running time. In a simple word I can say that it represents the worst case scenario of running the algorithm
* Eg for the Big(O) Notation is In searching the element through the Linear Search if element is at the end of the array we have to traverse throughout the array that makes the worst case scenario.
* The different types of scenarios are
* -> Best Case: The absolute minimum time an algorithm takes to find a match
* -> Average Case: The expected time required to find a match distributed randomly across the dataset.
* -> Worst-Case: The absolute maximum time required to find a match or determine it doesn't exist */


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