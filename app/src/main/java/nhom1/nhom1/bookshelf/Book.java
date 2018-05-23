package nhom1.nhom1.bookshelf;

import android.net.Uri;

public class Book {
    public String Name;
    public String Description;
    public String author;
    public String Expired_date;
    public Uri image;
    public String userId;
    public String cost;
    public int type;
public void Book(){

}

    public Uri getImage() {
        return image;
    }

    public String getAuthor() {
        return author;
    }

    public int getType() {
        return type;
    }

    public String getUserId() {
       return userId;
   }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getExpired_date() {
        return Expired_date;
    }

    public String getCost() {
        return cost;
    }

}
