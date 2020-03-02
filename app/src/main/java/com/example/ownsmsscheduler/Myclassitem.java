package com.example.ownsmsscheduler;

public class Myclassitem {
    public Myclassitem() {
    }

    private static     int id;
    private static   String message,status,number;

     private static long date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Myclassitem(int id, String message, String status, String number, long date) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.number = number;
        this.date = date;

    }
}
