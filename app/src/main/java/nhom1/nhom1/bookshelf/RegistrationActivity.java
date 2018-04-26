package nhom1.nhom1.bookshelf;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity {
 String name,pass,Conpass,email,phone;
 public FirebaseFirestore db;
 public User user;
  protected final String TAG = "book";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ScrollView sc=(ScrollView)findViewById(R.id.scrollView);
        ImageView imgIcon = (ImageView)findViewById(R.id.ImgIcon);
        ImageView imgAvatar = (ImageView)findViewById(R.id.ImgAvatar);
        Button btnChoose = (Button) findViewById(R.id.btnChoose);
        EditText edtName = (EditText) findViewById(R.id.edtUsernameR);
        EditText edtPass = (EditText) findViewById(R.id.edtPasswordR);
        EditText edtConfirm = (EditText) findViewById(R.id.edtConfirmPasswordR);
        EditText edtEmail = (EditText) findViewById(R.id.edtEmailR);
        EditText edtPhone = (EditText) findViewById(R.id.edtPhoneNumberR);
        Button btnSignup = (Button) findViewById(R.id.btnSignUp);
        db = FirebaseFirestore.getInstance();
        user = new User();
        user.name = edtName.getText().toString();
        user.pass = edtPass.getText().toString();
        user.confirm_pass = edtConfirm.getText().toString();
        user.email = edtEmail.getText().toString();
        user.phone = edtPhone.getText().toString();
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                Intent i = new Intent(RegistrationActivity.this,HomeActivity.class);
                startActivity(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("users",user);
            }
        });
    }
}
