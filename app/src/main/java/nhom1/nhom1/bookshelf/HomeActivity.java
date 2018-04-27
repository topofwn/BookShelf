package nhom1.nhom1.bookshelf;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {
    public User inform;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    String drawTitle,title;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle bundle = new Bundle();
        inform = (User) bundle.getSerializable("users");
        drawerLayout = (DrawerLayout) findViewById(R.id.Drawerlayout);
        title = drawTitle = (String) getTitle();
        drawerToggle = new ActionBarDrawerToggle(HomeActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
