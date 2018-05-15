package nhom1.nhom1.bookshelf;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import javax.microedition.khronos.opengles.GL;

public class MyProfileActivity extends AppCompatActivity {
    private TextView Name,Email;
    private Button Edit,Delete;
    private ImageButton btnBack;
    private ImageView avatar;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Name = findViewById(R.id.textName);
        Email = findViewById(R.id.txtEmailPE);
        Edit = findViewById(R.id.btnEditP);
        btnBack = findViewById(R.id.btnImgBackP);
        avatar = findViewById(R.id.ImgAvatarP);
        Delete = findViewById(R.id.btnDelete);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        user = FirebaseAuth.getInstance().getCurrentUser();
        Name.setText(user.getDisplayName());
        Email.setText(user.getEmail());
        if (user.getPhotoUrl() != null){
            Glide.with(this).load(user.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(avatar);
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
                startActivityForResult(i,2);
            }
        });
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2){
            if (resultCode == RESULT_OK){
           String name = (String) data.getStringExtra("name");
           String email = (String) data.getStringExtra("email");
           String phtoUrl = (String) data.getStringExtra("photo");
           Name.setText(name);
           Email.setText(email);
           if (phtoUrl != ""){
               Glide.with(this).load(Uri.parse(phtoUrl)).apply(RequestOptions.circleCropTransform()).into(avatar);
           }
            }
        }

    }
    public void showAlertDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm delete");
        builder.setMessage("Do you want to delete this user");
        builder.setCancelable(false);
        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(MyProfileActivity.this, LoginActivity.class));
                                }
                            }
                        });
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
