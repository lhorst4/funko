package model;

public class Pet {
    String name;
    String breed;
    int age;

    public Pet(String thisName, String thisBreed, int thisAge){
        name = thisName;
        breed = thisBreed;
        age = thisAge;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public void setName(String newName){
        name = newName;
    }

    public void setBreed(String newBreed){
        breed = newBreed;
    }

    public void setAge(int newAge){
        age = newAge;
    }

}
