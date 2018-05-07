package nhom1.nhom1.bookshelf;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    String drawTitle,title;
    private FirebaseAuth mAuth;
    public ImageView ava;

    private Toolbar mToolbar;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
         String key = intent.getStringExtra("key_access");
         ava = (ImageView) findViewById(R.id.smallAvatar);
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//           db.collection("Users").whereEqualTo("key",key).get();
        mToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(mToolbar);
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
            drawerLayout.closeDrawers();
        }else if (id == R.id.myShelf){
            //startActivity(new Intent(HomeActivity.this, MyShelfActivity.class));
        }else if (id == R.id.myCart){
            //startActivity(new Intent(HomeActivity.this, MyCartActivity.class));
        }else if (id == R.id.logout)
        {
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
        }
        return false;
    }


    public void AccessInformation() throws IOException {
        User account = null;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            // Name, email address, and profile photo Url
            account.name = user.getDisplayName();
            account.email = user.getEmail();
            account.avatar = user.getPhotoUrl();
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),account.avatar);
            ava.setImageBitmap(bitmap);
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
    }
}
