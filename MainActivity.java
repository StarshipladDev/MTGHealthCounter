package com.example.healtcount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i(TAG," Yay the program is working!");
        setContentView(R.layout.activity_main);
        Button AddButton1 = (Button)(findViewById(R.id.AddButton1));
        Button SubtractButton1 = (Button)(findViewById(R.id.SubtractButton1));
        Button AddButton2 = (Button)(findViewById(R.id.AddButton2));
        Button SubtractButton2 = (Button)(findViewById(R.id.SubtractButton2));
        TextView h1 = (TextView)(findViewById(R.id.HealthText1)) ;
        ButtonListener b1 = new ButtonListener(h1);
        TextView h2 = (TextView)(findViewById(R.id.HealthText2)) ;
        ButtonListener b2 = new ButtonListener(h2);
        AddButton1.setOnClickListener(b1);
        SubtractButton1.setOnClickListener(b1);
        AddButton2.setOnClickListener(b2);
        SubtractButton2.setOnClickListener(b2);


    }
    private class ButtonListener implements View.OnClickListener{
        int number=20;
        TextView t;
        public ButtonListener(TextView t){
            this.t= t;
        }
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.SubtractButton1 || v.getId()==R.id.SubtractButton2) {
                number--;
            }else{
                number++;
            }
            t.setText("" + number + "\n");
            Log.i(TAG, "" + number + "\n");
        }
    }
}

