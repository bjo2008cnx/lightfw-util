package org.lightfw.utilx.dynamic;

public class Student {

    private String name = "default";

    public Student() {
        name = "me";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void execute() {
        System.out.println(name);
        System.out.println("execute ok");
    }
}