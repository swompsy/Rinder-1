package rinder.com.mod;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;

import rinder.com.Database;

public class user implements Serializable{
    public static user curr;
    public static final String TABLE_USERS = "users";
    public static final String KEY_ID = "userId";
    private static final String KEY_NAME = "fullName", KEY_UNAME = "userName", KEY_EMAIL = "email", KEY_PASS = "password", KEY_DOB = "dateOfBirth", KEY_VERIFIED = "verified";
    public static final String CREATE_TABLE_USERS = String.format("CREATE TABLE IF NOT EXIST %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT);",TABLE_USERS, KEY_ID, KEY_NAME, KEY_UNAME, KEY_EMAIL, KEY_PASS, KEY_DOB, KEY_VERIFIED);

    public static void INSERT(user data){
        ContentValues val = new ContentValues();
        val.put(KEY_NAME, data.getFullName());
        val.put(KEY_UNAME, data.getUserName());
        val.put(KEY_EMAIL, data.getEmail());
        val.put(KEY_PASS, data.getPassword());
        val.put(KEY_DOB, data.getDOB());
        val.put(KEY_VERIFIED, data.getVerified());
        Database.sqlite.insert(TABLE_USERS, val);
    }

    public static user GET_USER(String email, String password){
        Cursor curr = Database.sqlite.read(TABLE_USERS, new String[] {KEY_ID, KEY_NAME, KEY_UNAME, KEY_EMAIL, KEY_PASS, KEY_DOB, KEY_VERIFIED}, KEY_EMAIL + " = ?", new String[] {email}, null);
        if(curr != null && curr.moveToFirst() && curr.getCount() > 0){
            int currID = Integer.parseInt(curr.getString(0));
            String currFullName = curr.getString(1);
            String currUserName = curr.getString(2);
            String currEmail = curr.getString(3);
            String currPass = curr.getString(4);
            String currDOB = curr.getString(5);
            boolean currVerified = Boolean.parseBoolean(curr.getString(6));
            return new user(currID, currFullName, currUserName, currEmail, currPass, currDOB, currVerified);
        }
        return null;
    }

    public static user GET_USER(int id){
        Cursor curr = Database.sqlite.read(TABLE_USERS, new String[] {KEY_ID, KEY_NAME, KEY_UNAME, KEY_EMAIL, KEY_PASS, KEY_DOB, KEY_VERIFIED}, KEY_ID + " = ?", new String[] {String.valueOf(id)}, null);
        if(curr != null && curr.moveToFirst() && curr.getCount() > 0){
            int currID = Integer.parseInt(curr.getString(0));
            String currFullName = curr.getString(1);
            String currUserName = curr.getString(2);
            String currEmail = curr.getString(3);
            String currPass = curr.getString(4);
            String currDOB = curr.getString(5);
            boolean currVerified = Boolean.parseBoolean(curr.getString(6));
            return new user(currID, currFullName, currUserName, currEmail, currPass, currDOB, currVerified);
        }
        return null;
    }

    public static void UPDATE_USER(int id, String verified){
        ContentValues val = new ContentValues();
        val.put(KEY_VERIFIED, verified);
        Database.sqlite.update(TABLE_USERS, val, KEY_ID + " = ?", new String[] {String.valueOf(id)});
        user.curr = GET_USER(id);
    }

    private int ID;
    private String fullName;
    private String userName;
    private String email;
    private String password;
    private String dateOfBirth;
    private boolean verified;

    public user(int ID, String fullName, String userName, String email, String password, String dateOfBirth, boolean verified){
        this.ID = ID;
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.verified = verified;
    }

    public user(String fullName, String userName, String email, String password, String dateOfBirth){
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.verified = false;
    }

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getDOB(){
        return dateOfBirth;
    }

    public void setDOB(String dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    public boolean getVerified(){
        return verified;
    }

    public void setVerified(boolean verified){
        this.verified = verified;
    }
}