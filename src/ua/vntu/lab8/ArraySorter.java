package ua.vntu.lab8;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class ArraySorter {
    // тут зберігаємо масиви для сортування
    private int[] firstArray;
    private int[] secondArray;

    // час, який пішов на сортування першого і другого масиву
    private long firstTime;
    private long secondTime;

    // звичайний конструктор, в параметри якого передаються масиви, що
    // необхідно відсотувати
    public ArraySorter(int[] firstArray, int[] secondArray) {
        this.firstArray = firstArray;
        this.secondArray = secondArray;
    }

    // "ядро" програми
    public void start() {
        // створюємо перший потік
        Thread thread1 = new Thread(
                // передаємо у нього "лямбду", тобто код, який він
                // повинен виконати
                // все шо в дужках після стрілочки - це і є код,
                // який буде виконуватись потоком
                // сам код - сортує масив і зберігає у змінну
                // час (в мілісекундах) витрачений на сортування
                    () -> {firstTime = sort(firstArray);}
        ),
                // аналогічно створюємо другий потік
                thread2 = new Thread(
                        () -> {secondTime = sort(secondArray);}
                );
        //запускаємо обидва потоки
        thread1.start();
        thread2.start();
        // перехоплюємо виключення, яке може "викинутись"
        try {
            // чекаємо, доки виконаються обидва потоки
            // це потрібно щоб ми могли порівняти час виконання обидвох
            // що неможливо зробити, поки обидва не виконаються
            thread1.join();
            thread2.join();
        } catch (InterruptedException ex) {
            // при виникненні виключення друкуємо його текст в консоль
            ex.printStackTrace();
        }
        // виводимо час виконання потоків
        System.out.println("First thread time: " + firstTime);
        System.out.println("Second thread time: " + secondTime);
        // формуємо строку з результатом(точніше її кінець)
        String resultString = "was faster.";
        // в залежності від того який потік завершився швидше,
        // додаємо до строки результату відповідний початок
        if (firstTime < secondTime) {
            resultString = "First thread " + resultString;
        } else if (firstTime > secondTime) {
            resultString = "Second thread " + resultString;
        } else {
            // у разі якщо обидва потоки завершились одночасно,
            // повідомлення буде таке:
            resultString = "Miracle! Threads draw!";
        }
        // виводимо повідомлення з результатом
        System.out.println(resultString);
    }

    // функція сортування масиву(бульбашковим методом), яка повертає
    // час, витрачений на сортування
    private long sort(int[] array) {
        // беремо поточний час (він буде часом початку)
        long startTime = System.currentTimeMillis();
        // далі реалізація бульбашкового алгоритму
        // флаг, який показує, чи відбувались переміщення елементів
        // масиву в ході сортування
        boolean isChanged;
        // запускаємо цикл, який буде працювати доти, доки
        // відбуваються зміни в масиві що сортується
        do {
            // на кожному кроці припускаємо, що змін не відбулося
            isChanged = false;
            // проходимо по кожному елементу масиву і перевіряємо
            for (int i = 1; i < array.length; i++) {
                // чи він менше попереднього, якщо так то
                if (array[i] < array[i - 1]) {
                    // міняємо місцями поточний і попередній
                    int temp = array[i - 1];
                    array[i - 1] = array[i];
                    array[i] = temp;
                    // а також вказуємо, що відбулась зміна в масиві
                    isChanged = true;
                }
            }
            // коли змін не було (isChanged дорівнює false)
            // виходимо з цикла
        } while (isChanged);
        // беремо поточний час (це час завершення сортування)
        long endTime = System.currentTimeMillis();
        // повертаємо різницю між часом завершення і початку
        return endTime - startTime;
    }

    // тестуємо роботу нашого класу
    public static void main(String[] args) {
        // створюємо два масиви з випадковими числами
        int[] array1 = createRandomArray();
        int[] array2 = createRandomArray();
        // якщо хочеться подивитись, які саме масиви були створені -
        // розкоментуй наступні три стрічки
        /*System.out.println("Origin arrays:");
        System.out.println(Arrays.toString(array1));
        System.out.println(Arrays.toString(array2));*/
        // створюємо екземпляр нашого класу і стартуємо його
        ArraySorter sorter = new ArraySorter(array1, array2);
        sorter.start();
        // якщо хочеться подивитись, як воно відсортувало масиви -
        // розкоментуй наступні дві стрічки
        /*System.out.println(Arrays.toString(array1));
        System.out.println(Arrays.toString(array2));*/
    }
    // допоміжна функція для створення масивів з випадковими числами
    // в нашому випадку необхідна, бо якщо створювати масиви вручну, то це 10-20
    // чисел, а процесор сортує їх швидше 1 мілісекунди, і тому різницю в
    // щвидкості побачити неможливо
    private static int[] createRandomArray() {
        // задаємо розмір масиву, макс і мін значення для його чисел
        int size = 300,
        min = 0,
        max = 99;
        // сам масив розміром size
        int[] array = new int[size];
        // пробігаємось по кожному елементу масиву
        for (int i = 0; i < size; i++) {
            // заповнюємо його рандомним числом з діапазону [min, max]
            array[i] = ThreadLocalRandom.current().nextInt(min, max + 1);
        }
        // повертаємо згенерований масив
        return array;
    }
}
