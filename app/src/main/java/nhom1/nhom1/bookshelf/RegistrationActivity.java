package nhom1.nhom1.bookshelf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
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

import java.io.FileNotFoundException;
import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST =71 ;
    String name,pass,Conpass,email,phone;
    public ImageView imgAvatar;
    public Button btnChoose;
    public EditText edtName,edtPass,edtConfirm,edtEmail,edtPhone;
 public FirebaseFirestore db;
 public User user;
  protected final String TAG = "book";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ScrollView sc=(ScrollView)findViewById(R.id.scrollView);
       // db = FirebaseFirestore.getInstance();
        user = new User();
        ImageView imgIcon = (ImageView)findViewById(R.id.ImgIcon);
        imgAvatar = (ImageView)findViewById(R.id.ImgAvatar);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        edtName = (EditText) findViewById(R.id.edtUsernameR);
        edtPass = (EditText) findViewById(R.id.edtPasswordR);
        edtConfirm = (EditText) findViewById(R.id.edtConfirmPasswordR);
        edtEmail = (EditText) findViewById(R.id.edtEmailR);
        edtPhone = (EditText) findViewById(R.id.edtPhoneNumberR);
        Button btnSignup = (Button) findViewById(R.id.btnSignUp);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.name = edtName.getText().toString();
                user.pass = edtPass.getText().toString();
                user.confirm_pass = edtConfirm.getText().toString();
                user.email = edtEmail.getText().toString();
                user.phone = edtPhone.getText().toString();
                user.key =  ServiceUtils.convertSha256(user.name+user.pass+user.confirm_pass+user.email+user.phone);
                FirebaseUtils.ReadData(user);
                Login(user.key);


            }
        });
    }
private void Login(String key){
    Intent i = new Intent(RegistrationActivity.this,HomeActivity.class);
   i.putExtra("key_access",key);
    startActivity(i);
}
    private void ChooseImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null &&data.getData()!=null){
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imgAvatar.setImageBitmap(bitmap);
                user.avatar = filePath;
            }  catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
