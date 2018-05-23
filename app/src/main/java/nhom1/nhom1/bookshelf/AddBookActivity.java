package nhom1.nhom1.bookshelf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddBookActivity extends AppCompatActivity {
  private ImageView Bookimage;
  private Button btnSave;
  private EditText name,author,brief,expiredDate,price;
  private TextView   perMonth;
  private RadioButton btnSale, btnLease;
    private FirebaseUser users;
    private Book book;
    private boolean loai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Bookimage = findViewById(R.id.bookPhoto);
        btnSave = findViewById(R.id.btnSave);
        name = findViewById(R.id.edtBookName);
        author = findViewById(R.id.edtBookAuthor);
        brief = findViewById(R.id.edtBookBrief);
        expiredDate = findViewById(R.id.textDate);
        price = findViewById(R.id.Price);
        perMonth = findViewById(R.id.textPerMonth);
        btnSale = findViewById(R.id.choiceSale);
        btnLease = findViewById(R.id.choiceLease);
        users = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(btnLease.isChecked()){
            perMonth.setVisibility(View.VISIBLE);

        }else {
            perMonth.setVisibility(View.GONE);

        }
        book.userId = users.getUid();
    btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            book.author = author.getText().toString();
            book.Name = name.getText().toString();
            book.Description = brief.getText().toString();
            book.Expired_date = expiredDate.getText().toString();
            book.cost = price.getText().toString();
            if(btnLease.isChecked()){
               book.type = 1;

            }else {
               book.type = 2;

            }
        }
    });
    }
}
