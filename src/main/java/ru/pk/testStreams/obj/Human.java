package ru.pk.testStreams.obj;

import java.util.StringJoiner;

public class Human {
    String firstName;
    String middleName;
    String lastName;
    long age;

    public static Human getNull() {
        return new Human();
    }

    private Human() {
        this.firstName = null;
        this.middleName = null;
        this.lastName = null;
        this.age = Long.MIN_VALUE;
    }

    public Human(String firstName, String middleName, String lastName, long age) {
        if (lastName == null) throw new NullPointerException("lastName");

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getAge() {
        return age;
    }

    public String getFio() {
        StringBuilder sb = new StringBuilder("")
                .append(lastName).append(" ").append(firstName).append(" ").append(middleName);
        return sb.toString();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Human.class.getSimpleName() + "[", "]")
                .add(getFio())
                .add("age=" + age)
                .toString();
    }
}
