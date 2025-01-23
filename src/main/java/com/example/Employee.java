package com.example;

public class Employee {
    private String id;
    private String name;
    private String age;
    private String position;
    private String department;
    private String salary;

    public Employee(String id, String name, String age, String position, String department, String salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.position = position;
        this.department = department;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }
}