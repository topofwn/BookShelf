package nhom1.nhom1.bookshelf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;




public class EditMyProfileActivity extends AppCompatActivity {
    private Uri filePath = null;
    private static final int PICK_IMAGE_REQUEST = 71;
    private String name, pass, email, phone;
    public ImageView imgAvatar, imgIcon;
    public ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    public Button btnChoose,btnDone;
    public EditText edtName, edtEmail;
    private FirebaseUser users;
    public FirebaseUser user;
    private LinearLayout click_hide_keyboard;
    protected final String TAG = "book";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);
        ScrollView sc = (ScrollView) findViewById(R.id.scrollView);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        edtName = (EditText) findViewById(R.id.edtUsernameR);
        edtEmail = (EditText) findViewById(R.id.edtEmailR);
       btnDone = (Button) findViewById(R.id.btnDone);
      click_hide_keyboard = (LinearLayout) findViewById(R.id.outClick);
        imgIcon = (ImageView) findViewById(R.id.ImgIcon);
        imgAvatar = (ImageView) findViewById(R.id.ImgAvatar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = FirebaseAuth.getInstance().getCurrentUser();
        Glide.with(this).load(R.drawable.icon_bookshelf_new).apply(RequestOptions.centerInsideTransform()).into(imgIcon);
        click_hide_keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide_keyboard();
            }
        });
        final Intent inte = new Intent();
        if (user.getPhotoUrl() != null) {
            Glide.with(this).load(user.getPhotoUrl()).into(imgAvatar);
        }
        edtName.setText(user.getDisplayName());
        edtEmail.setText(user.getEmail());
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
        Button btnChangePW = (Button) findViewById(R.id.btnChangePassword);
        btnChangePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMyProfileActivity.this, ChangePasswordActivity.class);
                startActivityForResult(intent, 10);
            }
        });
        if (filePath == null){
            filePath = user.getPhotoUrl();
        }
        Glide.with(this).load(filePath).apply(RequestOptions.centerCropTransform()).into(imgAvatar);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(edtName.getText().toString())
                        .setPhotoUri(filePath)
                        .build();
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(EditMyProfileActivity.this, "User profile updated", Toast.LENGTH_SHORT).show();
                                    inte.putExtra("name",edtName.getText().toString());
                                    inte.putExtra("email",edtEmail.getText().toString());
                                    inte.putExtra("photo",filePath.toString());
                                    setResult(Activity.RESULT_OK, inte);
                                    finish();
                                    hideProgressDialog();
                                }
                            }
                        });
                if (!edtEmail.getText().toString().equals(user.getEmail())) {
                    user.updateEmail(edtName.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(EditMyProfileActivity.this, "User email address updated.", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(EditMyProfileActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
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
    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK){
                filePath = data.getData();
        }




        if (requestCode == 10 && resultCode == RESULT_OK && data != null){
            String newPass = (String) data.getStringExtra("change_pass");
            FirebaseUser ss = FirebaseAuth.getInstance().getCurrentUser();
            ss.updatePassword(newPass)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditMyProfileActivity.this, "User's password updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private void hide_keyboard() {
        View view = this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("LOADING...");
            mProgressDialog.setIndeterminate(true);
        }
    }
}
