package com.example.student.test05;

/**
 * Created by student on 2017-07-04.
 */

public class ContactVO {
    private String name;
    private String number;
/////////////////////////////////////////////////////////////
    public ContactVO(){
    }
    public ContactVO(String name, String number) {
        this.name = name;
        this.number = number;
    }
//////////////////////////////////////////////////////////////
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
}
