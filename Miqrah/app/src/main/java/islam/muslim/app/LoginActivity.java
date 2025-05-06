package islam.muslim.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import islam.muslim.app.ui.dashboard.DashboardFragment;
import islam.muslim.app.ui.main.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class LoginActivity extends Fragment {
      Button btnNext;
    ImageView btnNextReg;
      public static Boolean Checklogin = false;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.activity_login, container, false);
          btnNext=rootView.findViewById(R.id.loginbtn);
          btnNextReg=rootView.findViewById(R.id.regeste);
          btnNext.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  Intent intent= new Intent(view.getContext(), MainActivity.class);
                  startActivity(intent);
                  Checklogin=true;
              }
          });
        btnNextReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(view.getContext(), Regester.class);
                startActivity(intent);
                Checklogin=true;
            }
        });
        return rootView;
    }
}