package nhom1.nhom1.bookshelf;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;


public class User implements Serializable{
    public String phone;
    public String id;
    public String address;


    public String getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }



    }


