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

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    String drawTitle,title;
    private TextView textName;
    public ImageView ava;
    private LinearLayout rContent;
    private Button btnHome, btnMyShelf, btnMyCart, btnLogOut;
    private FirebaseUser user;
    private User account;
    private String email,password;
    private Toolbar mToolbar;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.Drawerlayout);
        ava =   findViewById(R.id.smallAvatar);
        textName =  findViewById(R.id.txtDisplayName);
        drawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, R.string.open, R.string.close);
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = FirebaseAuth.getInstance().getCurrentUser();
        title = drawTitle = (String) getTitle();
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(user.getDisplayName() == null){
            textName.setText("Random");
        }else if (user.getDisplayName() != null){
            textName.setText(user.getDisplayName());
        }
        if (user.getPhotoUrl() != null) {
            Glide.with(this).load(user.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(ava);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        rContent = findViewById(R.id.randomContent);
        Intent intent = getIntent();
        password = intent.getStringExtra("password_login");
        findViewById(R.id.Profile).setOnClickListener(this);
        findViewById(R.id.myCart).setOnClickListener(this);
        findViewById(R.id.myShelf).setOnClickListener(this);
        findViewById(R.id.Logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.Profile){
        Intent i = new Intent(HomeActivity.this, MyProfileActivity.class);
        startActivity(i);
    }else if (id == R.id.myShelf){
        //startActivity(new Intent(HomeActivity.this, MyShelfActivity.class));
    }else if (id == R.id.myCart){
        //startActivity(new Intent(HomeActivity.this, MyCartActivity.class));
    }else if (id == R.id.Logout)
    {
        FirebaseAuth.getInstance().signOut();
         startActivity(new Intent(HomeActivity.this,LoginActivity.class));
    }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
