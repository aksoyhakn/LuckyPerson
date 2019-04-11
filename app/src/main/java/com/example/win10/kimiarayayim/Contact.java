package com.example.win10.kimiarayayim;

public class Contact {


    String contactName;
    String contactPhoneNumber;
    int contactImage;

    public Contact(String contactName, int contactImage, String contactPhoneNumber)
    {
        this.contactImage = contactImage;
        this.contactName = contactName;
        this.contactPhoneNumber = contactPhoneNumber;
    }
    public String getContactName()
    {
        return contactName;
    }

    public int getContactImage()
    {
        return contactImage;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }
}