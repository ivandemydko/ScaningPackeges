package second;

import annotations.MyAutowired;
import annotations.MyComponent;
import main.first.inside.Inside;


@MyComponent(name = "second")
public class Second {

    @MyAutowired
    private Inside inside;

    public void secondMethod() {
        System.out.println("second method");
        inside.insideMethod();
    }
}
