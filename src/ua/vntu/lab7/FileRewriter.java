package ua.vntu.lab7;

import java.io.*;


public class FileRewriter {
    //поле що позначає, чи можна записувати поточний символ у файл
    boolean rewritable = true;
    //поле що зберігає попередній зчитаний символ
    char prev = ' ';

    public static void main(String[] args) throws FileNotFoundException, IOException{
        //створюємо екземпляр нашого класу
        FileRewriter rewriter = new FileRewriter();
        //переписуємо зміст першого файлу у другий, видаляючи при цьому теги
        rewriter.rewrite(
                //адрес файлу звідки
                "/home/userok/workspace/idea/labs_java/lab7/test.txt",
                //адрес файлу куди
                "/home/userok/workspace/idea/labs_java/lab7/writed.txt"
        );

    }

    //сама функція, що переписує
    private void rewrite(String pathFrom, String pathTo)
            //якщо не знайде файл або якісь інші проблеми з файлами, то викине
            //виключення
            throws FileNotFoundException, IOException{

        //створюємо потік вводу(звідки будемо зчитувати)
        InputStream input = new FileInputStream(pathFrom);
        //і виводу(куди будемо записувати)
        OutputStream output = new FileOutputStream(pathTo);
        //зчитуємо перший символ з вхідного потоку
        int data = input.read();
        //поки цей символ не буде символом кінця файлу
        while(data != -1) {
            //перевіряємо чи можна записувати зчитаний символ
            if (check(data)) {
                //якщо так то записуємо його у вихідний потік
                output.write(data);
            }
            //зміннна, що зберігає попередній символ тепер зберігає
            //символ що був щойно оброблений
            //тепер змінні prev і data містять той самий символ
            prev = (char) data;
            //зчитуємо наступний символ і записуємо в data
            data = input.read();
            //тепер змінна prev містить попередній символ, а data - поточний
        }
        //закриваємо потоки входу і виходу, шоб не трапилась
        //"утєчка пам'яті" :)
        input.close();
        output.close();
    }

    //функція яка перевіряє чи можна записувати даний символ в файл
    //в нашому випадку - чи являється він частиною тега
    private boolean check(int data) {
        //приводимо тип символу до char(символьний тип)
        //щоб можна було порівняти його із потрібними нам "<" та ">"
        char character = (char) data;
        //якщо символ є початком тегу, встановлюємо заборону на
        //запис символів
        if (character == '<') {
            rewritable = false;
        }
        //доти, доки не виявиться, що попередній символ
        //був кінцем тегу
        else if (prev == '>') {
            rewritable = true;
        }
        return rewritable;
    }
}
