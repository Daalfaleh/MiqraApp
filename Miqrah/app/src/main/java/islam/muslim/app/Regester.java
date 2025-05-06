package islam.muslim.app;

import androidx.appcompat.app.AppCompatActivity;
import islam.muslim.app.ui.main.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Regester extends AppCompatActivity {
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);
        btnNext=findViewById(R.id.loginbtn);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
                LoginActivity.Checklogin=true;
            }
        });

    }
}