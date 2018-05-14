package nhom1.nhom1.bookshelf;

import android.accounts.Account;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    String drawTitle,title;
    private FirebaseAuth mAuth;
    private TextView textName;
    public ImageView ava;
    private LinearLayout rContent;
    private FirebaseUser user;
    private User account;
    private String email,password;

    private Toolbar mToolbar;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
         email = intent.getStringExtra("email_login");
         password = intent.getStringExtra("password_login");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                          user = mAuth.getCurrentUser();
                          account.id = user.getUid();
                          account.email = user.getEmail();

                            // chuyen qua home kem theo userID va email

                        } else {
                            // If sign in fails, display a message to the user.


                        }


                    }
                });

         ava = (ImageView) findViewById(R.id.smallAvatar);
        mToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        textName = (TextView) findViewById(R.id.txtDisplayName);
        setSupportActionBar(mToolbar);
        if(account.name == null ){
            textName.setText("Unnamed");
        }else{
            textName.setText(account.name);
        }
        if (account.avatar != null)
        {
            Glide.with(this).load(account.avatar).apply(RequestOptions.circleCropTransform()).into(ava);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.Drawerlayout);
        title = drawTitle = (String) getTitle();
        drawerToggle = new ActionBarDrawerToggle(HomeActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    //    ava.setImageBitmap(inform.avatar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        Button btnHome = (Button)findViewById(R.id.HomePage);
        Button btnMyCart = (Button)findViewById(R.id.myCart);
        Button btnMyShelf = (Button)findViewById(R.id.myShelf);
        Button btnLogOut = (Button)findViewById(R.id.logout);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.HomePage){
            Intent i = new Intent(HomeActivity.this, MyProfileActivity.class);
            i.putExtra("user",account);
            i.putExtra("user_pass",password);
            startActivity(i);
        }else if (id == R.id.myShelf){
            //startActivity(new Intent(HomeActivity.this, MyShelfActivity.class));
        }else if (id == R.id.myCart){
            //startActivity(new Intent(HomeActivity.this, MyCartActivity.class));
        }else if (id == R.id.logout)
        {
           // startActivity(new Intent(HomeActivity.this,LoginActivity.class));
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        rContent = findViewById(R.id.randomContent);
    }
}
