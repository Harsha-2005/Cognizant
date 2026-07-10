/*Asymptotic Notation is the mathematical representation of the time complexity of completing the execution of an algorithm
 * Big(O) Notation : This Notation gives the Upper bound of an algorithm's running time. In a simple word I can say that it represents the worst case scenario of running the algorithm
 * Eg for the Big(O) Notation is In searching the element through the Linear Search if element is at the end of the array we have to traverse throughout the array that makes the worst case scenario.
 * The different types of scenarios are
 * -> Best Case: The absolute minimum time an algorithm takes to find a match
 * -> Average Case: The expected time required to find a match distributed randomly across the dataset.
 * -> Worst-Case: The absolute maximum time required to find a match or determine it doesn't exist */

import java.util.Arrays;
import java.util.Comparator;

/* Product class with attributes suitable for searching */
class Product {
    private int productId;
    private String productName;
    private String category;

    public Product(int productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("ID: %-5d | Name: %-25s | Category: %s", productId, productName, category);
    }
}

public class eCommercePlatformSearch {

    /*
     * Linear Search: Scans each element one by one until the target is found.
     * Best Case : O(1) - Element found at the first position
     * Average Case: O(n/2) ~ O(n) - Element found somewhere in the middle
     * Worst Case : O(n) - Element found at the last position or not present
     */
    public static Product linearSearch(Product[] products, String targetName) {
        for (Product product : products) {
            if (product.getProductName().equalsIgnoreCase(targetName)) {
                return product;
            }
        }
        return null; // Not found
    }

    /*
     * Binary Search: Works on a sorted array; repeatedly divides the search
     * interval in half.
     * Best Case : O(1) - Element found at the middle position
     * Average Case: O(log n) - Element found after several halvings
     * Worst Case : O(log n) - Element found at the end of subdivisions or not
     * present
     * Prerequisite: The array MUST be sorted (here sorted by productName
     * alphabetically)
     */
    public static Product binarySearch(Product[] sortedProducts, String targetName) {
        int low = 0;
        int high = sortedProducts.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = sortedProducts[mid].getProductName().compareToIgnoreCase(targetName);

            if (cmp == 0) {
                return sortedProducts[mid]; // if found
            } else if (cmp < 0) {
                low = mid + 1; // if the target is in the right half
            } else {
                high = mid - 1; // if the target is in the left half
            }
        }
        return null; // if not found
    }

    public static void main(String[] args) {
        // Unsorted array used for Linear Search
        Product[] products = {
                new Product(101, "Wireless Mouse", "Electronics"),
                new Product(102, "Running Shoes", "Footwear"),
                new Product(103, "Novel: Dune", "Books"),
                new Product(104, "Coffee Maker", "Appliances"),
                new Product(105, "Yoga Mat", "Sports"),
                new Product(106, "Laptop Stand", "Electronics"),
                new Product(107, "Bluetooth Speaker", "Electronics")
        };

        // Sorted copy for Binary Search (sorted alphabetically by productName)
        Product[] sortedProducts = products.clone();
        Arrays.sort(sortedProducts, Comparator.comparing(p -> p.getProductName().toLowerCase()));

        System.out.println("=== E-Commerce Platform Search Demo ===\n");

        // ---- Linear Search Working ----
        System.out.println("--- Linear Search ---");
        String linearTarget = "Yoga Mat";
        Product linearResult = linearSearch(products, linearTarget);
        if (linearResult != null) {
            System.out.println("Found via Linear Search: " + linearResult);
        } else {
            System.out.println("'" + linearTarget + "' not found via Linear Search.");
        }

        String linearMissing = "Headphones";
        Product linearMissResult = linearSearch(products, linearMissing);
        if (linearMissResult != null) {
            System.out.println("Found via Linear Search: " + linearMissResult);
        } else {
            System.out.println("'" + linearMissing + "' not found via Linear Search.");
        }

        // ---- Binary Search Working ----
        System.out.println("\n--- Binary Search (on sorted array) ---");
        System.out.println("Sorted product list:");
        for (Product p : sortedProducts) {
            System.out.println("  " + p);
        }

        String binaryTarget = "Laptop Stand";
        Product binaryResult = binarySearch(sortedProducts, binaryTarget);
        if (binaryResult != null) {
            System.out.println("\nFound via Binary Search: " + binaryResult);
        } else {
            System.out.println("\n'" + binaryTarget + "' not found via Binary Search.");
        }

        String binaryMissing = "Headphones";
        Product binaryMissResult = binarySearch(sortedProducts, binaryMissing);
        if (binaryMissResult != null) {
            System.out.println("Found via Binary Search: " + binaryMissResult);
        } else {
            System.out.println("'" + binaryMissing + "' not found via Binary Search.");
        }
    }
}

// -------- Analysis --------

/*
 * Time Complexity Comparison:
 * ┌──────────────────┬────────────┬──────────────┬────────────┐
 * │ Algorithm │ Best Case │ Average Case │ Worst Case │
 * ├──────────────────┼────────────┼──────────────┼────────────┤
 * │ Linear Search │ O(1) │ O(n) │ O(n) │
 * │ Binary Search │ O(1) │ O(log n) │ O(log n) │
 * └──────────────────┴────────────┴──────────────┴────────────┘
 *
 * Which is more suitable for an e-commerce platform?
 * -> Binary Search is far more suitable for large-scale product catalogs
 * because its
 * O(log n) complexity means searching 1,000,000 products takes only ~20
 * comparisons.
 * -> Linear Search is acceptable for very small datasets or unsorted data where
 * maintaining a sorted order is not feasible.
 * -> In practice, e-commerce platforms use hash-based indexes (O(1) lookup) or
 * inverted index structures (like Elasticsearch) for even faster text search.
 */