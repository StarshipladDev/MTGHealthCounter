package com.example.healtcount;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {
    static String TAG="MainActivity";
    String player1="Starshiplad";
    String player2="Starshiplass";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i(TAG," Yay the program is working!");
        setContentView(R.layout.activity_main);
        //Draw iamge before others so other draw over it
        ImageView bg = (ImageView)(findViewById(R.id.Background));
        bg.setImageResource(getResources().getIdentifier("@drawable/background",null,this.getPackageName()));
        //Add buttons with attatched Images
        ImageView AddButton1 = (ImageView)(findViewById(R.id.AddButton1));
        AddButton1.setImageResource(getResources().getIdentifier("@drawable/plus",null,this.getPackageName()));
        ImageView SubtractButton1 = (ImageView)(findViewById(R.id.SubtractButton1));
        SubtractButton1.setImageResource(getResources().getIdentifier("@drawable/minus",null,this.getPackageName()));
        ImageView AddButton2 = (ImageView)(findViewById(R.id.AddButton2));
        AddButton2.setImageResource(getResources().getIdentifier("@drawable/plus",null,this.getPackageName()));
        ImageView SubtractButton2 = (ImageView)(findViewById(R.id.SubtractButton2));
        SubtractButton2.setImageResource(getResources().getIdentifier("@drawable/minus",null,this.getPackageName()));

        //Creates a media sound effect to play a 'Blood letting' sound on minus health
        MediaPlayer mp1 = MediaPlayer.create(this, R.raw.splat);
        MediaPlayer mp2 = MediaPlayer.create(this, R.raw.shine);
        //Add text veiws of scores and then create listeners
        TextView h1 = (TextView)(findViewById(R.id.HealthText1)) ;
        h1.setTextColor(Color.WHITE);
        ButtonListener b1 = new ButtonListener(h1,player1,mp1,mp2);
        TextView h2 = (TextView)(findViewById(R.id.HealthText2)) ;
        h2.setTextColor(Color.WHITE);
        ButtonListener b2 = new ButtonListener(h2,player2,mp1,mp2);

        //Attatch button listners
        AddButton1.setOnClickListener(b1);
        SubtractButton1.setOnClickListener(b1);
        AddButton2.setOnClickListener(b2);
        SubtractButton2.setOnClickListener(b2);

    }
    private class ButtonListener implements View.OnClickListener{
        int number=20;
        TextView t;
        MediaPlayer mp1;
        MediaPlayer mp2;
        String player;
        public ButtonListener(TextView t,String player, MediaPlayer mp1, MediaPlayer mp2){
            this.t= t;
            this.player=player;
            this.mp1=mp1;
            this.mp2=mp2;
        }
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.SubtractButton1 || v.getId()==R.id.SubtractButton2) {
                number--;
                mp1.start();
            }else{
                number++;
                mp2.start();
            }
            t.setText(player+"'s Health: "+ number + "\n");
            Log.i(TAG, "" + number + "\n");
        }
    }
}

