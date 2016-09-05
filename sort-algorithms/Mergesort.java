package algorithms;

public class Mergesort {

    public static void main(String[] args) {
        final String BLANKS = "  "; // A String of two blanks
        int i;                      // Array index

        int[] data = {1000, 80, 10, 50, 70, 60, 90, 20, 30, 40, 0, -1000};

        // Print the array before sorting:
        System.out.println("Here is the entire original array:");
        for (i = 0; i < data.length; i++) {
            System.out.print(data[i] + BLANKS);
        }
        System.out.println();

        // Sort the numbers, and print the result with two blanks after each number.
        mergesort(data, 0, data.length);
        System.out.println("I have sorted all but the first and last numbers.");
        System.out.println("The numbers are now:");
        for (i = 0; i < data.length; i++) {
            System.out.print(data[i] + BLANKS);
        }
        System.out.println();
    }

    public static void mergesort(int[] data, int first, int n) {
        int n1; // Size of the first half of the array
        int n2; // Size of the second half of the array

        if (n > 1) {
            // Compute sizes of the two halves
            n1 = n / 2;
            n2 = n - n1;

            mergesort(data, first, n1);// Sort data[first] through data[first+n1-1]
            mergesort(data, first + n1, n2); // Sort data[first+n1] to the end

            // Merge the two sorted halves.
            merge(data, first, n1, n2);
        }
    }

    private static void merge(int[] data, int first, int n1, int n2) {
        int[] temp = new int[n1 + n2]; // Allocate the temporary array
        int copied = 0; // Number of elements copied from data to temp
        int copied1 = 0; // Number copied from the first half of data
        int copied2 = 0; // Number copied from the second half of data
        int i;           // Array index to copy from temp back into data

        // Merge elements, copying from two halves of data to the temporary array.
        while ((copied1 < n1) && (copied2 < n2)) {
            if (data[first + copied1] < data[first + n1 + copied2]) {
                temp[copied++] = data[first + (copied1++)];
            } else {
                temp[copied++] = data[first + n1 + (copied2++)];
            }
        }

        // Copy any remaining entries in the left and right subarrays.
        while (copied1 < n1) {
            temp[copied++] = data[first + (copied1++)];
        }
        while (copied2 < n2) {
            temp[copied++] = data[first + n1 + (copied2++)];
        }

        // Copy from temp back to the data array.
        for (i = 0; i < n1 + n2; i++) {
            data[first + i] = temp[i];
        }
    }

}
