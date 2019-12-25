package main.first.inside;

import annotations.MyComponent;

@MyComponent(name = "inside")
public class Inside {

    public void insideMethod() {
        System.out.println("Inside method");
    }
}
