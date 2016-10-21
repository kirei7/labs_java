package ua.vntu.lab6;

//створюємо наш клас з трьома типами, які мають бути визначені пізніше
public class Generics <T, Y, U> {

    //три поля класу(змінні), які мають невизначені типи
    private T firstValue;
    private Y secondValue;
    private U thirdValue;

    //конструктор, який приймає три змінні невідомих типів
    public Generics(T firstValue, Y secondValue, U thirdValue) {
        //присвоюємо полям класу значення трьох переданих
        //в конструкторі параметрів
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
    }

    //main-метод, який виконується при запуску проги
    public static void main(String[] args) {
        //якщо результат виконання методу
        //genericsTest дорівнює true
        if (genericsTest() == true) {
            //то виводимо повідомлення
            System.out.println("Test passed!");
        }
    }

    //у цьому методі ми тестуємо, чи правильно рацює наш generic-клас, для цього
    public static boolean genericsTest() {
        // ініціалізуємо три змінні типів, що вказані у варіанті
        // а от ТУТ оговорка - у варіанті вказані типи int, double, String
        // а ініціалізуються типи Integer, Double і String
        // чому?
        // тому що int та double - "примітивні типи", а generics із примітивними
        // типами не працюють, тому я використав Integer та Double як типи-обгортки
        // для примітивних типів; по суті це ті самі int та double, тільки
        // загорнуті в об'єкти
        Integer firstVariable = 0;
        Double secondVariable = 0.d;
        String thirdVariable = "тіпа текст";
        // ініціалізуємо екземпляр класу Generics, який нам потрібно протестувати
        // для цього у кутових дужках вказуємо типи з яким буде працювати об'єкт
        // і передаємо в конструктор три змінні, які ми ініціалізували трьома стрічками вище
        Generics<Integer, Double, String> obj = new Generics<>(
                firstVariable,
                secondVariable,
                thirdVariable
        );
        // далі код для тестування
        // зберігаємо у змінну клас першого поля об'єкту що тестується(беремо його
        // з функції classOfFirst, вона власне і повертає клас першого поля
        // об'єкта, але описана вона буде нижче)
        Class firstVarClass = obj.classOfFirst(),
                // аналогічно зберігаємо у змінну клас другого поля об'єкту що тестується
                secondVarClass = obj.classOfSecond(),
                // аналогічно зберігаємо у змінну клас третього поля об'єкту що тестується
                thirdVarClass = obj.classOfThird();
        // в консолі виводимо імена класів полів об'єкту що тестується
        System.out.printf(
                "Class variables types: %s, %s, %s %n",
                firstVarClass.getSimpleName(),
                secondVarClass.getSimpleName(),
                thirdVarClass.getSimpleName());
        // в консолі виводимо імена класів змінних які використовувались при створенні об'єкта
        System.out.printf(
                "Test variables types: %s, %s, %s %n",
                firstVariable.getClass().getSimpleName(),
                secondVariable.getClass().getSimpleName(),
                thirdVariable.getClass().getSimpleName());
        // якщо імена у двох стрічках що вивелись, збігаються, то можна вважати що тест пройдено
        // але потрібно ще пересвідчитись, що класи полів і змінних які використовувались
        // для створення дійсно однакові
        if (
                firstVarClass.equals(firstVariable.getClass()) &&
                obj.classOfSecond().equals(secondVariable.getClass()) &&
                obj.classOfThird().equals(thirdVariable.getClass())
                )
        //якщо вони дійсно однакові, то повертаємо true
        return true;
        //якщо ні то false
        return false;
    }

    //метод повертає клас першого поля об'єкту, наступні два роблять те саме
    public Class classOfFirst() {
        return firstValue.getClass();
    }
    public Class classOfSecond() {
        return secondValue.getClass();
    }
    public Class classOfThird() {
        return thirdValue.getClass();
    }
}
