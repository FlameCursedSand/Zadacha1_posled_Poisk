import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Zadacha_1_Posled_Poisk1 {

    // 4. Вынесение констант для удобства изменения условий задачи
    private static final int MAX_VALUE = 10000;
    private static final int TARGET_DIVISOR = 14;
    private static final String INPUT_FILE_NAME = "C:\\Users\\Acer\\IdeaProjects\\first\\src\\PoslPoisk.txt";

    public static void main(String[] args) {
        // Массив для подсчета частоты каждого числа от 0 до 10000
        int[] elementFrequencies = new int[MAX_VALUE + 1];
        int totalElementsRead = 0;

        System.out.println("Начало обработки файла: " + INPUT_FILE_NAME);

        // 1. Однопроходное чтение файла и заполнение частот
        try (Scanner scanner = new Scanner(new File(INPUT_FILE_NAME))) {
            while (scanner.hasNextInt()) {
                int currentNumber = scanner.nextInt();

                // Защита от некорректных данных, превышающих условие задачи
                if (currentNumber >= 0 && currentNumber <= MAX_VALUE) {
                    elementFrequencies[currentNumber]++;
                    totalElementsRead++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: Файл '" + INPUT_FILE_NAME + "' не найден.");
            System.out.println("Создайте файл в корневой папке проекта и запишите в него числа.");
            return;
        }

        System.out.println("Успешно прочитано элементов: " + totalElementsRead);

        int maxValidResult = -1;

        // 2. Поиск максимального подходящего элемента R
        // Идем сверху вниз (от 10000 до 0), чтобы первый найденный вариант был максимальным
        for (int currentCandidate = MAX_VALUE; currentCandidate >= 0; currentCandidate--) {

            // Проверяем базовые условия: число есть в массиве и кратно 14
            if (elementFrequencies[currentCandidate] > 0 && currentCandidate % TARGET_DIVISOR == 0) {

                // Временно "изымаем" кандидата из массива, чтобы он не мог быть множителем самого себя
                // (если только в массиве не было нескольких таких чисел)
                elementFrequencies[currentCandidate]--;

                // Проверяем, можно ли получить currentCandidate произведением двух ДРУГИХ элементов
                if (canBeFormedByTwoOthers(currentCandidate, elementFrequencies)) {
                    maxValidResult = currentCandidate;
                    break; // Так как идем от большего к меньшему, первый найденный - и есть максимум
                }

                // Возвращаем кандидата обратно, если он не подошел (для чистоты данных)
                elementFrequencies[currentCandidate]++;
            }
        }

        // 3. Наличие сообщений в выводе
        if (maxValidResult != -1) {
            System.out.println("Максимальный подходящий элемент R: " + maxValidResult);
        } else {
            System.out.println("Подходящий элемент не найден. Результат: " + maxValidResult);
        }
    }

    /**
     * Проверяет, можно ли представить target как произведение двух чисел,
     * которые присутствуют в массиве (с учетом их доступного количества).
     */
    private static boolean canBeFormedByTwoOthers(int target, int[] frequencies) {
        // Особый случай для 0: чтобы 0 был произведением двух других элементов,
        // в исходном массиве должно быть минимум два нуля.
        // Так как мы уже уменьшили frequency[target] на 1, проверяем, остался ли еще хотя бы один 0.
        if (target == 0) {
            return frequencies[0] >= 1;
        }

        // Перебираем все возможные делители от 1 до квадратного корня из target
        for (int divisor = 1; divisor * divisor <= target; divisor++) {
            if (target % divisor == 0) {
                int factor1 = divisor;
                int factor2 = target / divisor;

                // Убеждаемся, что множители не выходят за границы нашего массива частот
                if (factor1 <= MAX_VALUE && factor2 <= MAX_VALUE) {

                    // Случай А: множители равны (например, 14 * 14 = 196)
                    // Нам нужно, чтобы это число встречалось в массиве минимум 2 раза
                    if (factor1 == factor2) {
                        if (frequencies[factor1] >= 2) {
                            return true;
                        }
                    }
                    // Случай Б: множители разные (например, 2 * 7 = 14)
                    // Нам нужно, чтобы каждый из них встречался минимум 1 раз
                    else {
                        if (frequencies[factor1] >= 1 && frequencies[factor2] >= 1) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}