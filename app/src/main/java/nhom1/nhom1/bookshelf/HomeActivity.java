package nhom1.nhom1.bookshelf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {
    public User inform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle bundle = new Bundle();
        inform = (User) bundle.getSerializable("users");
    }
}
