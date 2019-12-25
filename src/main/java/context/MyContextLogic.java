package context;

import annotations.MyAutowired;
import annotations.MyComponent;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

class MyContextLogic {

    private MyContext myContext;

    MyContextLogic(MyContext myContext) {
        this.myContext = myContext;
    }

    void projectScan(File dir) throws ClassNotFoundException {
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (item.isDirectory()) {
                    projectScan(item);
                } else {
                    addToContextIfAnnotationPresent(item);
                }
            }
        }
    }

    private void addToContextIfAnnotationPresent(File file) throws ClassNotFoundException {
        String classPath = getClassPath(file);
        Class newClass = Class.forName(classPath);
        Annotation annotation = newClass.getAnnotation(MyComponent.class);
        if (Objects.nonNull(annotation)) {
            try {
                Object obj = newClass.getConstructor().newInstance();
                MyComponent myComponent = (MyComponent) annotation;
                myContext.addToContext(myComponent.name(), obj);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private String getClassPath(File file) {
        StringBuilder sb = new StringBuilder();
        if (file.toString().endsWith(".java")) {
            String[] className = file.getPath().replace(System.getProperty("user.dir"), "").split("\\\\");
            for (int i = 4; i < className.length; i++) {
                sb.append(className[i]).append(".");
            }
            sb.delete(sb.length() - 6, sb.length());
        }
        return String.valueOf(sb);
    }

    void injectDependency() {
        Map<String, Object> objects = myContext.getContext();
        for (Map.Entry<String, Object> entry : objects.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(MyAutowired.class)) {
                    field.setAccessible(true);
                    try {
                        field.set(entry.getValue(), findDependency(field.getType().getSimpleName()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private Object findDependency(String foundClass) {
        Map<String, Object> objects = myContext.getContext();
        for (Map.Entry<String, Object> entry : objects.entrySet()) {
            if (entry.getValue().getClass().getSimpleName().equals(foundClass)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
