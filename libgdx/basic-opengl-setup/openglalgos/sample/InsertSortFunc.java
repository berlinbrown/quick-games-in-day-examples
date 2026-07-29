// Just basic Sample Insert Sort java

import java.util.concurrent.atomic.AtomicInteger;

public class InsertSort {

  private static final AtomicInteger costCounterAtomic = new AtomicInteger(0);

  private static int staticMemoryKey = 0;

  private static void printIndexAndValue(final Integer[] A, final int j) {
    System.out.println(" [ J Index: " + j + " -> [ " + A[j] + " ]");
  }

  public static void sort(final Integer[] A) {
    for (int j = 1; j < A.length; j++) {
      // c1
      costCounterAtomic.incrementAndGet();
      // Print statements will say cost zero
      printIndexAndValue(A, j);

      // c2
      costCounterAtomic.incrementAndGet();
      staticMemoryKey = A[j];
      // Insert A[j] into the sorted sequence A[0..j-1]      
      // 
      costCounterAtomic.incrementAndGet();

      // c4
      int i = j - 1;

      // Move elements of array[0..i-1] that are greater than key
      // to one position ahead of their current position
      while (i >= 0 && A[i] > staticMemoryKey) {

        // c6
        costCounterAtomic.incrementAndGet();
        A[i + 1] = A[i];

        // c7
        costCounterAtomic.incrementAndGet();
        i = i - 1;
      }
      // Insert the key into its correct sorted slot
      // c8
      costCounterAtomic.incrementAndGet();
      A[i + 1] = staticMemoryKey;
    }
  }

  public static void main(final String[] args) {
    System.out.println("Running");
    final Integer[] A = { 5, 2, 4, 6, 1, 3 };
    System.out.println(" { Size of Array: " + A.length);
    sort(A);
    System.out.println();
    System.out.println(">> Sorted Array:");
    for (int i = 0; i < A.length; i++) {
      System.out.println(" [ " + A[i] + " ]");
    }
    System.out.println(">> Cost Counter: " + costCounterAtomic.get());
  }
}
