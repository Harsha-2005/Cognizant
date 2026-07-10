/* Exercise 7: Financial Forecasting
 * Scenario: Predict future financial values using a recursive algorithm based on past growth rates.
 *
 * Recursion Explained:
 * -> Recursion is a technique where a method calls itself to solve a smaller sub-problem
 *    of the same type, until a base case is reached.
 * -> It simplifies problems that have a naturally recursive structure, such as:
 *    - Fibonacci sequences, factorial calculations, tree traversals, etc.
 * -> Key components:
 *    1. Base Case  : The termination condition that stops the recursion.
 *    2. Recursive Case: The step that reduces the problem closer to the base case.
 *
 * Financial Forecasting Formula (Compound Growth):
 *    FutureValue(n) = PresentValue × (1 + growthRate)^n
 * -> Using recursion:
 *    FutureValue(0)  = PresentValue                             [Base Case]
 *    FutureValue(n)  = FutureValue(n-1) × (1 + growthRate)     [Recursive Case]
 *
 * Optimization with Memoization:
 * -> Without memoization, each recursive call recomputes intermediate values.
 * -> With memoization, results are stored in a cache (array/map) and reused,
 *    reducing redundant computation from O(2^n) to O(n). */

import java.util.HashMap;
import java.util.Map;

public class FinancialForecasting {

    /* Basic recursive calculation of future value after n years.
     * Time Complexity: O(n) — one recursive call per year, depth n.
     * Space Complexity: O(n) — call stack depth is n.
     *
     * @param presentValue  The starting (current) value
     * @param growthRate    Annual growth rate (e.g., 0.07 for 7%)
     * @param years         Number of years into the future
     * @return              Predicted future value after 'years' years */
    public static double futureValueRecursive(double presentValue, double growthRate, int years) {
        // Base Case: 0 years in the future = present value
        if (years == 0) {
            return presentValue;
        }
        // Recursive Case: future value of (years - 1) multiplied by (1 + growth rate)
        return futureValueRecursive(presentValue, growthRate, years - 1) * (1 + growthRate);
    }

    /* Memoized recursive calculation of future value.
     * Stores previously computed results to avoid redundant recursive calls.
     * Time Complexity: O(n) — each sub-problem is computed exactly once.
     * Space Complexity: O(n) — for the memoization cache + call stack.
     *
     * @param presentValue  The starting (current) value
     * @param growthRate    Annual growth rate (e.g., 0.07 for 7%)
     * @param years         Number of years into the future
     * @param memo          Cache map storing results for each 'years' value
     * @return              Predicted future value after 'years' years */
    public static double futureValueMemoized(double presentValue, double growthRate, int years, Map<Integer, Double> memo) {
        // Base Case
        if (years == 0) {
            return presentValue;
        }
        // Check if result is already cached
        if (memo.containsKey(years)) {
            return memo.get(years);
        }
        // Compute and cache the result
        double result = futureValueMemoized(presentValue, growthRate, years - 1, memo) * (1 + growthRate);
        memo.put(years, result);
        return result;
    }

    /* Iterative (non-recursive) calculation for comparison.
     * This is the most efficient approach: O(n) time, O(1) space.
     * Eliminates call stack overhead entirely. */
    public static double futureValueIterative(double presentValue, double growthRate, int years) {
        double value = presentValue;
        for (int i = 0; i < years; i++) {
            value *= (1 + growthRate);
        }
        return value;
    }

    public static void main(String[] args) {
        System.out.println("=== Financial Forecasting Tool Demo ===\n");

        double presentValue = 10_000.00;  // Starting investment: $10,000
        double growthRate   = 0.08;       // 8% annual growth rate
        int    forecastYears = 10;        // Forecast over 10 years

        System.out.printf("Initial Investment  : $%.2f%n", presentValue);
        System.out.printf("Annual Growth Rate  : %.0f%%%n", growthRate * 100);
        System.out.printf("Forecast Period     : %d years%n%n", forecastYears);

        // ---- Recursive Prediction ----
        double recursiveResult = futureValueRecursive(presentValue, growthRate, forecastYears);
        System.out.printf("Recursive Forecast  : $%.2f%n", recursiveResult);

        // ---- Memoized Recursive Prediction ----
        Map<Integer, Double> memo = new HashMap<>();
        double memoResult = futureValueMemoized(presentValue, growthRate, forecastYears, memo);
        System.out.printf("Memoized Forecast   : $%.2f%n", memoResult);

        // ---- Iterative Prediction (for comparison) ----
        double iterativeResult = futureValueIterative(presentValue, growthRate, forecastYears);
        System.out.printf("Iterative Forecast  : $%.2f%n", iterativeResult);

        // ---- Year-by-Year Breakdown ----
        System.out.println("\n--- Year-by-Year Forecast (Recursive) ---");
        System.out.printf("%-8s | %-15s%n", "Year", "Projected Value");
        System.out.println("---------|----------------");
        for (int year = 0; year <= forecastYears; year++) {
            double value = futureValueRecursive(presentValue, growthRate, year);
            System.out.printf("Year %-4d| $%,.2f%n", year, value);
        }
    }
}

// -------- Analysis --------

/* Time Complexity of the Recursive Algorithm:
 * -> Basic Recursion    : O(n) — each call reduces 'years' by 1 until base case (years=0).
 *                          Call stack depth = n, so space is also O(n).
 *
 * Optimization: Memoization
 * -> Without memoization: If we compute futureValueRecursive multiple times for different
 *    'years' values (e.g., in a year-by-year breakdown), each call duplicates work.
 *    Total calls for years 0..n = 0 + 1 + 2 + ... + n = O(n²) repeated computations.
 *
 * -> With memoization: Each unique 'years' value is computed only once.
 *    All repeated calls use cached results → O(n) total computations for years 0..n.
 *
 * -> Iterative approach: Even better — O(n) time and O(1) space (no call stack overhead).
 *    Recommended for production use since recursion can cause stack overflow for very large n.
 *
 * Summary:
 * ┌────────────────────────┬────────────────┬───────────────┐
 * │ Approach               │ Time           │ Space         │
 * ├────────────────────────┼────────────────┼───────────────┤
 * │ Basic Recursion        │ O(n)           │ O(n) stack    │
 * │ Memoized Recursion     │ O(n)           │ O(n) cache    │
 * │ Iterative              │ O(n)           │ O(1)          │
 * └────────────────────────┴────────────────┴───────────────┘
 */
