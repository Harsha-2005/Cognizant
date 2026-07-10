/* Sorting Algorithms Overview:
 * -> Bubble Sort   : Repeatedly swaps adjacent elements if they are in the wrong order.
 *                    Time Complexity: Best O(n), Average O(n²), Worst O(n²). Space: O(1).
 * -> Insertion Sort: Builds the sorted array one item at a time by inserting each element.
 *                    Time Complexity: Best O(n), Average O(n²), Worst O(n²). Space: O(1).
 * -> Merge Sort    : Divides array in halves, sorts each half, then merges them.
 *                    Time Complexity: O(n log n) in all cases. Space: O(n).
 * -> Quick Sort    : Picks a pivot, partitions around it, and recursively sorts sub-arrays.
 *                    Time Complexity: Best O(n log n), Average O(n log n), Worst O(n²). Space: O(log n). */

import java.util.Arrays;

/* Order class representing a customer order */
class Order {
    private int orderId;
    private String customerName;
    private double totalPrice;

    public Order(int orderId, String customerName, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return String.format("OrderID: %-5d | Customer: %-20s | Total: $%.2f", orderId, customerName, totalPrice);
    }
}

public class SortingCustomerOrders {

    /*
     * Bubble Sort: Compares adjacent elements and swaps them if needed.
     * In each pass the largest unsorted element "bubbles up" to its correct
     * position.
     * Time Complexity: Best O(n) [already sorted], Worst/Average O(n²)
     */
    public static void bubbleSort(Order[] orders) {
        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (orders[j].getTotalPrice() > orders[j + 1].getTotalPrice()) {
                    // Swap adjacent elements
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                    swapped = true;
                }
            }
            // Early exit if no swaps were made in this pass (already sorted)
            if (!swapped)
                break;
        }
    }

    /*
     * Quick Sort: Uses a pivot element to partition the array.
     * Elements smaller than pivot go left; larger go right; then recurse on both
     * halves.
     * Time Complexity: Best/Average O(n log n), Worst O(n²) [rare with good pivot
     * choice]
     */
    public static void quickSort(Order[] orders, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(orders, low, high);
            quickSort(orders, low, pivotIndex - 1); // Sort left partition
            quickSort(orders, pivotIndex + 1, high); // Sort right partition
        }
    }

    /*
     * Partition helper: Chooses the last element as pivot, places it in correct
     * position
     */
    private static int partition(Order[] orders, int low, int high) {
        double pivot = orders[high].getTotalPrice();
        int i = low - 1; // Index of smaller element

        for (int j = low; j < high; j++) {
            if (orders[j].getTotalPrice() <= pivot) {
                i++;
                // Swap orders[i] and orders[j]
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }
        // Place pivot in its correct sorted position
        Order temp = orders[i + 1];
        orders[i + 1] = orders[high];
        orders[high] = temp;
        return i + 1;
    }

    private static void printOrders(Order[] orders) {
        for (Order o : orders) {
            System.out.println("  " + o);
        }
    }

    public static void main(String[] args) {
        Order[] originalOrders = {
                new Order(1001, "Alice Johnson", 450.75),
                new Order(1002, "Bob Smith", 120.00),
                new Order(1003, "Carol White", 899.99),
                new Order(1004, "David Brown", 55.20),
                new Order(1005, "Eve Davis", 340.50),
                new Order(1006, "Frank Wilson", 1200.00),
                new Order(1007, "Grace Lee", 75.80)
        };

        System.out.println("=== Sorting Customer Orders Demo ===\n");
        System.out.println("Original (unsorted) orders:");
        printOrders(originalOrders);

        // ---- Bubble Sort ----
        Order[] bubbleOrders = Arrays.copyOf(originalOrders, originalOrders.length);
        bubbleSort(bubbleOrders);
        System.out.println("\nAfter Bubble Sort (ascending by total price):");
        printOrders(bubbleOrders);

        // ---- Quick Sort ----
        Order[] quickOrders = Arrays.copyOf(originalOrders, originalOrders.length);
        quickSort(quickOrders, 0, quickOrders.length - 1);
        System.out.println("\nAfter Quick Sort (ascending by total price):");
        printOrders(quickOrders);
    }
}

// -------- Analysis --------

/*
 * Time Complexity Comparison:
 * ┌──────────────┬────────────┬──────────────┬────────────┬───────────────┐
 * │ Algorithm │ Best Case │ Average Case │ Worst Case │ Space │
 * ├──────────────┼────────────┼──────────────┼────────────┼───────────────┤
 * │ Bubble Sort │ O(n) │ O(n²) │ O(n²) │ O(1) │
 * │ Quick Sort │ O(n log n) │ O(n log n) │ O(n²) │ O(log n) │
 * └──────────────┴────────────┴──────────────┴────────────┴───────────────┘
 *
 * Why Quick Sort is generally preferred over Bubble Sort:
 * -> Quick Sort is significantly faster in practice due to its O(n log n)
 * average complexity.
 * -> Bubble Sort's O(n²) average makes it impractical for large datasets (e.g.,
 * thousands of orders).
 * -> Quick Sort uses in-place sorting with only O(log n) stack space for
 * recursion.
 * -> For an e-commerce platform with potentially millions of orders, Quick Sort
 * (or Tim Sort,
 * used by Java's Arrays.sort()) is the algorithm of choice.
 * -> Bubble Sort is mainly used for educational purposes or nearly-sorted tiny
 * datasets.
 */
