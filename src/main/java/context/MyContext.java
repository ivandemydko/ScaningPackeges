package context;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyContext {
    private Map<String, Object> context = new HashMap<>();


    public MyContext(File file) {
        MyContextLogic myContextLogic = new MyContextLogic(this);
        try {
            myContextLogic.projectScan(file);
            myContextLogic.injectDependency();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    void addToContext(String key, Object obj) {
        context.put(key, obj);
    }

    Map<String, Object> getContext() {
        return context;
    }

    public void displayContext() {
        System.out.println(context);
    }

   public Object getBean(String name) {
       return context.get(name);
   }
}
