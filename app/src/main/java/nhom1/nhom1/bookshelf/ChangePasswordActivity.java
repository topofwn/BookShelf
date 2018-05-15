package nhom1.nhom1.bookshelf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ChangePasswordActivity extends AppCompatActivity {
       private EditText Cur, New, Con;
       private Button btnOk;
       private String s1,s2,s3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Cur = findViewById(R.id.edtCurrentPasswordCP);
        New = findViewById(R.id.edtNewPasswordCP);
        Con = findViewById(R.id.edtConfirmPasswordCP);
        s1 = Cur.getText().toString();
        s2 = New.getText().toString();
        s3 = Con.getText().toString();
        if(s2.equals(s3)){
            if(!s2.equals(""))
            {
                if (!s1.equals(s2)){
                    if (!s1.equals(s3)){
                        Intent i = new Intent();
                        i.putExtra("change_pass",s2);
                        finish();

                    }
                }
            }
        }
    }
}
