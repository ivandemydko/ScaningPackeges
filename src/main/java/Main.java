import context.MyContext;
import second.Second;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File dir = new File("D:\\Ваня\\програмирование\\Cyber Bionics\\Projects\\ScaningPackeges\\src");

        MyContext context = new MyContext(dir);
        Second second = (Second) context.getBean("second");
        second.secondMethod();
    }
}
