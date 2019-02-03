package com.grocery.buyers;

public class Employee extends Buyer
{
    private Float experience;

    public Employee(String name)
    {
        super(name);
    }

    public void setExperience(Float experience)
    {
        this.experience = experience;
    }

    public Float getExperience()
    {
        return experience;
    }
}
