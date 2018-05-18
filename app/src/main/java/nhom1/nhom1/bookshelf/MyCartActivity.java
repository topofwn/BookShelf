package nhom1.nhom1.bookshelf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyCartActivity extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseFirestore db;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
         myRef = database.getReference("Books");

        setContentView(R.layout.activity_my_cart);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String s = user.getUid();
        db = FirebaseFirestore.getInstance();
    }
    @Override
    protected void onResume() {
        super.onResume();

        myRef.setValue("Hello, World!");


    }


}
