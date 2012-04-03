package com.main;

import java.util.ArrayList;

public class GroupList {
    private String name;
    ArrayList<String> contacts = new ArrayList<String>();
    public GroupList(String name) {
        this.name = name;
    }
    
    public void addContact(String number) {
        contacts.add(number);
    }
    
    public ArrayList<String> getContacts() {
        return contacts;
    }
    
    public String getName() {
        return name;
    }

}
