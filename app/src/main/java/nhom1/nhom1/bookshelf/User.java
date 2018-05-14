package nhom1.nhom1.bookshelf;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;


public class User implements Serializable{
    public String name;

    public String email;
    public String phone;
    public String id;
    public Uri avatar;

    public String getEmail() {
        return email;
    }



    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }



    public String getId() {
        return id;
    }

    public Uri getAvatar() {
        return avatar;
    }


    }


