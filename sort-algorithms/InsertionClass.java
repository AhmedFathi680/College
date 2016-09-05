package algorithms;

public class InsertionClass {

    public static void main() {
        int[] a = {9, 8, 7, 6, 2};
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
               if (a[j]<a[j-1]){
                   int temp ;
                   temp = a[j];
                   a[j] = a[j-1];
                   a[j-1] = temp;
               }          
            }
        }
    }
}
