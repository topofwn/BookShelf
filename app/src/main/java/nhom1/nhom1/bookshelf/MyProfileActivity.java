package nhom1.nhom1.bookshelf;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import javax.microedition.khronos.opengles.GL;

public class MyProfileActivity extends AppCompatActivity {
    private TextView Name,Phonenum,Email, Password;
    private Button Edit;
    private ImageButton btnBack;
    private ImageView avatar;
    private User acc;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Name = findViewById(R.id.txtDisplayName);
        Phonenum = findViewById(R.id.txtPhonePE);
        Email = findViewById(R.id.txtEmailPE);
        Password = findViewById(R.id.txtPasswordPE);
        Edit = findViewById(R.id.btnEditP);
        btnBack = findViewById(R.id.btnImgBackP);
        avatar = findViewById(R.id.ImgAvatarP);
        Intent intent = getIntent();
       acc = (User) intent.getSerializableExtra("user");
       password = (String) intent.getStringExtra("user_password");
       Name.setText(acc.name);
       Phonenum.setText(acc.phone);
       Email.setText(acc.email);
       Password.setText(password);
       if (acc.avatar != null){
           Glide.with(this).load(acc.avatar).apply(RequestOptions.circleCropTransform()).into(avatar);
       }
       btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(MyProfileActivity.this, HomeActivity.class));
           }
       });
       Edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             Intent i = new Intent(MyProfileActivity.this, EditMyProfileActivity.class);
             i.putExtra("account_pass",password);
             startActivityForResult(i,2);
           }
       });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2){
            if (resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address, and profile photo Url
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();
                    Name.setText(name);
                    Email.setText(email);
                    if (photoUrl != null){
                        Glide.with(this).load(photoUrl).apply(RequestOptions.circleCropTransform()).into(avatar);
                    }
                    boolean emailVerified = user.isEmailVerified();

                    String uid = user.getUid();
                }
            }
        }

    }
}
