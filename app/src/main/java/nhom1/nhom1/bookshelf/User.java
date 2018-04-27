package nhom1.nhom1.bookshelf;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class User implements Serializable {
    public String name;
    public String pass;
    public String confirm_pass;
    public String email;
    public String phone;
    public Bitmap avatar;

    public String getEmail() {
        return email;
    }

    public String getConfirm_pass() {
        return confirm_pass;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public Bitmap getAvatar() {
        return avatar;
    }
}
