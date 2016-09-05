import java.util.Arrays;
import java.util.Scanner;

public class QuickSort {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        int[] A = new int[T];
        for (int i = 0; i < T; i++) {
            A[i] = in.nextInt();
        }
        //QuickSort(A, 0, A.length-1);
        RandomQuickSort(A, 0, A.length-1);
        System.out.println(Arrays.toString(A));
    }

    public static void QuickSort(int[] a, int p, int r) {
        if (p < r) {
            int q = Partition(a, p, r);
            QuickSort(a, p, q - 1);
            QuickSort(a, q + 1, r);
        }
    }

    private static int Partition(int[] a, int p, int r) {
        int x = a[r];
        int i = p - 1;
        int t;
        for (int j = p; j < r; j++) {
            if (a[j] <= x) {
                t = a[++i];
                a[i] = a[j];
                a[j] = t;
            }
        }

        t = a[++i];
        a[i] = a[r];
        a[r] = t;
        return i;
    }

    public static void RandomQuickSort(int[] a, int p, int r) {
        if (p < r) {
            int q = RandomPartition(a, p, r);
            RandomQuickSort(a, p, q - 1);
            RandomQuickSort(a, q + 1, r);
        }
    }

    private static int RandomPartition(int[] a, int p, int r) {
        int rand = (int) (Math.random()*(r-p) +p);
        int x = a[rand];
        int i = p - 1;
        int t;
        for (int j = p; j < r; j++) {
            if (a[j] <= x) {
                t = a[++i];
                a[i] = a[j];
                a[j] = t;
            }
        }
        t = a[++i];
        a[i] = a[r];
        a[r] = t;
        return Partition(a, p, r);
    }

    
}
