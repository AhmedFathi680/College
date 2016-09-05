import java.util.Scanner;

public class SortAlgorithms {

    public static void main(String[] args) {

        double startTime, endTime;
        startTime = System.currentTimeMillis();

        Scanner in = new Scanner(System.in);
        System.out.print("Enter the size: ");
        int size = in.nextInt();

        int j = 0;
        int[] x = new int[size];
        System.out.println("");
        while (size > 0) {
            //x = new int[size];
            System.out.print("Enter the " + j + "th element: ");
            x[j] = in.nextInt();
            //int[] x = {6, 5, 8, 1, 4, 9, 0, 4, 10};
            //bubbleSort(x);
            size--;
            j++;
            //selectionSort(x);
        }
        inserstionSort(x);
        for (int i = 0; i < x.length; i++) {
            System.out.print(x[i] + " ");
        }

        endTime = System.currentTimeMillis();

        System.out.println("\nEccution time :" + (endTime - startTime));
    }

    public static void bubbleSort(int[] x) {
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x.length - (i + 1); j++) {
                if (x[j] > x[j + 1]) {
                    int temp = x[j];
                    x[j] = x[j + 1];
                    x[j + 1] = temp;
                }
            }
        }
    }

    public static void inserstionSort(int[] x) {
        for (int i = 0; i < x.length; i++) {
            for (int j = i; j > 0; j--) {
                if (x[j] < x[j - 1]) {
                    int temp = x[j];
                    x[j] = x[j - 1];
                    x[j - 1] = temp;
                }
            }
        }
    }

    public static void selectionSort(int[] x) {
        int max;
        int ind;
        for (int i = 0; i < x.length; i++) {
            max = x[0];
            ind = 0;
            for (int j = 0; j < x.length - i; j++) {
                if (x[j] > max) {
                    max = x[j];
                    ind = j;
                }
            }
            int temp = x[x.length - (i + 1)];
            x[x.length - (i + 1)] = x[ind];
            x[ind] = temp;
        }
    }
}
