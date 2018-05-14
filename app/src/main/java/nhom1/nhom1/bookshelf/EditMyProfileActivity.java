package nhom1.nhom1.bookshelf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class EditMyProfileActivity extends AppCompatActivity {
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST =71 ;
    private String name,pass,email,phone;
    public ImageView imgAvatar;
    private FirebaseAuth mAuth;
    public Button btnChoose;
    public EditText edtName,edtPass,edtConfirm,edtEmail,edtPhone;
    private FirebaseUser users;
    public FirebaseUser user;
    protected final String TAG = "book";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);
        ScrollView sc=(ScrollView)findViewById(R.id.scrollView);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Intent inte = getIntent();

        pass = (String) inte.getStringExtra("account_pass");
        ImageView imgIcon = (ImageView)findViewById(R.id.ImgIcon);

        imgAvatar = (ImageView)findViewById(R.id.ImgAvatar);
        if (user.getPhotoUrl() != null){
        Glide.with(this).load(user.getPhotoUrl()).into(imgAvatar);
        }
        btnChoose = (Button) findViewById(R.id.btnChoose);
        edtName = (EditText) findViewById(R.id.edtUsernameR);
        edtEmail = (EditText) findViewById(R.id.edtEmailR);
        edtPhone = (EditText) findViewById(R.id.edtPhoneNumberR);
        Button btnDone = (Button) findViewById(R.id.btnDone);
        edtName.setText(user.getDisplayName());
        edtEmail.setText(user.getEmail());
        edtPhone.setText(user.getPhoneNumber());
        edtPhone.setEnabled(false);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
        Button btnChangePW =(Button) findViewById(R.id.btnChangePassword);
        btnChangePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMyProfileActivity.this, ChangePasswordActivity.class);
                startActivityForResult(intent, 10);
            }
        });
         btnDone.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                         .setDisplayName(edtName.getText().toString())
                         .setPhotoUri(filePath)
                         .build();

                 if (!edtEmail.getText().toString().equals(user.getEmail())){
                     user.updateEmail(edtName.getText().toString())
                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if (task.isSuccessful()) {
                                         Log.d(TAG, "User email address updated.");
                                     }
                                 }
                             });
                     boolean emailVerified = user.isEmailVerified();
                     if (emailVerified == false) {
                         user.sendEmailVerification()
                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         if (task.isSuccessful()) {
                                             Log.d(TAG, "Email sent.");
                                         }
                                     }

                                 });
                     }
                 }

             }
         });
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
            Glide.with(this).load(filePath).apply(RequestOptions.centerCropTransform()).into(imgAvatar);
        }
        if (requestCode == 10 && resultCode == RESULT_OK && data != null){
            String newPass = (String) data.getStringExtra("change_pass");
            FirebaseUser ss = FirebaseAuth.getInstance().getCurrentUser();
            ss.updatePassword(newPass)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User password updated.");
                            }
                        }
                    });
        }
    }
}
