import java.util.*;
/*The best data structures that can be used for this real world application is HashMap that will helps us in adding the items,lookups ans deletions with a time complexity of the O(1)*/
class Product{
    private int productId;
    private String productName;
    private int quantity;
    private double price;
    /*The constructor calling helps us in assigning the values to the objects when objects are invoked*/
    public Product(int productId,String productName,int quantity,double price){
        this.productId=productId;
        this.productName=productName;
        this.quantity=quantity;
        this.price=price;
    }
    /*When a user has clicked on the product getProductId will give the product id */
    public int getProductId(){
        return productId;
    }
    /*Helps in displaying the Product name to the user*/
    public String getProductName(){
        return productName;
    }
    /*Helps in getting the hoe many items were available in the store*/
    public int getQuantity(){
        return quantity;
    }
    /*Helps in getting the information about the price of the item*/
    public double getPrice() {
        return price;
    }
    /*It will helps in setting the */
    public void setQuantity(int quantity) {
        this.quantity=quantity;
        System.out.println("New Stock has arrived");
    }
    /*It will be helps in setting thr price at later stages to any product*/
    public void setPrice(double price){
        this.price=price;
        System.out.println("The price has been updated");
    }
    /*The override toString functions helps in formatting the output as per our wish in a structured format*/
    @Override
    public String toString() {
        return String.format("ID: %-5d | Name: %-20s | Qty: %-5d | Price: $%.2f",
                productId, productName, quantity, price);
    }
}
class InventoryManager{
    private final Map<Integer,Product> inventory;

    public InventoryManager(){
        this.inventory=new HashMap<>();
    }

    public boolean addProduct(Product p){
        if(inventory.containsKey(p.getProductId())) {
            System.out.println("Product already exists");
            return false;
        }
        inventory.put(p.getProductId(),p);
        return true;
    }

    public boolean modifyProduct(int id,int newQuantity,double newPrice){
        Product p=inventory.get(id);
        if(p==null){
            System.out.println("Product not found");
            return false;
        }
        p.setQuantity(newQuantity);
        p.setPrice(newPrice);
        System.out.println("Product ID "+id+" Updated Successfully.");
        return true;
    }
    public boolean removeProduct(int id){
        if(!inventory.containsKey(id)){
            System.out.println("Error: Product ID "+id+" not found");
            return false;
        }
        inventory.remove(id);
        System.out.println("Product ID "+id+" Removed Successfully.");
        return true;
    }

    public void printInventory(){
        if(inventory.isEmpty()){
            System.out.println("No inventory found");
            return;
        }
        System.out.println("<-----Current Warehouse Inventory----->");
        for(Product p:inventory.values()){
            System.out.println(p);
        }
        System.out.println("<------------------>");
    }
}

public class InventoryManagementSystem {
    public static void main(String[] args){
        InventoryManager inventory=new InventoryManager();
        inventory.addProduct(new Product(101,"Wireless Mouse",50,50.00));
        inventory.addProduct(new Product(102,"Wireless Keyboard",100,100));
        inventory.addProduct(new Product(103,"Wireless HeadPhones",200,200));
        inventory.addProduct(new Product(104,"Wireless Tablets",300,300));

        inventory.printInventory();

    }
}

//--------Analaysis--------

/*The analysis of the time complexity for developing this software is O(1) for adding the new product to the inventory, Updating the product(price, quantity, etc), deleting the product in the inventory.
This time complexity is achieved through the using of HashMap collection datastructure. This was the most appropriate optimization that can be possible there are other like we can use the ArrayList but it is inefficient while adding the products in the middle of the inventory or deleting the product in the inventory. The time complexity when we use the ArrayList is O(n).*/
