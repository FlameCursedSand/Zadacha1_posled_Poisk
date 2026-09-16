import java.util.Random;

public class SProgramirovanie2 {
    public static void main(String[] args) {
        int[] a = new int[1000];
        Random random = new Random();

        int[] count = new int[10001];


        for (int i = 0; i < 1000; i++) {
            a[i] = random.nextInt(10001);
            count[a[i]]++;
        }

        int maxR = -1;

        for (int j = 0; j < 1000; j++) {
            for (int k = j + 1; k < 1000; k++) {
                int product = a[j] * a[k];

                // Отсекаем заведомо неподходящие варианты для ускорения:
                if (product > 10000) continue;
                if (product % 14 != 0) continue;     // должно быть кратно 14
                if (product <= maxR) continue;       // поиск максимального


                int requiredCount = 1;


                if (a[j] == product) requiredCount++;
                if (a[k] == product) requiredCount++;


                if (count[product] >= requiredCount) {
                    maxR = product;
                }
            }
        }

        System.out.println("Максимальный элемент R: " + maxR);
    }
}