package com.example.healtcount;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.os.PowerManager;

import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    static String TAG="MainActivity";
    String player1="Starshiplad";
    String player2="Starshiplass";
    static int Health1=20;
    static int Health2=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i(TAG," Yay the program is working!");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //Taken from https://stackoverflow.com/questions/5712849/how-do-i-keep-the-screen-on-in-my-app
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //End of stolen code
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
        ImageView coinToss= (ImageView)(findViewById(R.id.CoinToss));
        coinToss.setImageResource(getResources().getIdentifier("@drawable/coinhead",null,this.getPackageName()));
        //Creates a media sound effect to play a 'Blood letting' sound on minus health
        MediaPlayer mp1 = MediaPlayer.create(this, R.raw.splat);
        MediaPlayer mp2 = MediaPlayer.create(this, R.raw.shine);
        MediaPlayer mp3 = MediaPlayer.create(this, R.raw.coin);
        //Add text veiws of scores and then create listeners
        TextView h1 = (TextView)(findViewById(R.id.HealthText1)) ;
        h1.setTextColor(Color.WHITE);
        ButtonListener b1 = new ButtonListener(h1,player1,mp1,mp2,mp3,coinToss,this);
        TextView h2 = (TextView)(findViewById(R.id.HealthText2)) ;
        h2.setTextColor(Color.WHITE);
        ButtonListener b2 = new ButtonListener(h2,player2,mp1,mp2,mp3,coinToss,this);
        View.OnLongClickListener lc1 = new HoldListener(h1,player1,mp1,mp2);
        View.OnLongClickListener lc2 = new HoldListener(h2,player2,mp1,mp2);
        //Attatch button listners
        AddButton1.setOnClickListener(b1);
        SubtractButton1.setOnClickListener(b1);
        AddButton1.setOnLongClickListener(lc1);
        SubtractButton1.setOnLongClickListener(lc1);
        AddButton2.setOnClickListener(b2);
        SubtractButton2.setOnClickListener(b2);
        AddButton2.setOnLongClickListener(lc2);
        SubtractButton2.setOnLongClickListener(lc2);
        coinToss.setOnClickListener(b2);

    }
    protected void onDestroy(){

        super.onDestroy();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    private void changeImage(int i,ImageView iV){
        if(i%2==0){
            iV.setImageResource(R.drawable.cointail);

        }else {
            iV.setImageResource(R.drawable.coinhead);
        }
    }
    private class HoldListener implements View.OnLongClickListener {
        int number;
        String player;
        TextView t;
        MediaPlayer mploss;
        MediaPlayer mphealth;

        public HoldListener(TextView t, String player,MediaPlayer mploss,MediaPlayer mphealth) {
            this.mploss=mploss;
            this.mphealth=mphealth;
            this.t = t;
            this.player = player;
            if(player.equals(player1)){
                number=Health1;
            }
            else if(player.equals(player2)){
                number=Health2;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(player.equals(player1)){
                number=Health1;
            }
            else if(player.equals(player2)){
                number=Health2;
            }
            Log.i(TAG, "onLongClick:Longclick true");
            if(v.getId()==R.id.SubtractButton1 || v.getId()==R.id.SubtractButton2) {
                if(number>0){

                    mploss.start();
                }
                number=number-5;
                if(number<0){
                    number=0;
                }
            }
            else{
                mphealth.start();
                number=number+5;
            }
            t.setText(player+"'s Health: "+ number + "\n");

            if(player.equals(player1)){
                Health1=number;
            }
            else if(player.equals(player2)){
                Health2=number;
            }
            return true;
        }
    }
    private class ButtonListener implements View.OnClickListener{
        int number;
        TextView t;
        MediaPlayer mploss;
        MediaPlayer mphealth;
        MediaPlayer mp3;
        String player;
        ImageView iV;
        AppCompatActivity c;
        boolean coinSpinning=false;
        public ButtonListener(TextView t,String player, MediaPlayer mp1, MediaPlayer mp2,MediaPlayer mp3,ImageView iV,AppCompatActivity c){
            this.t= t;
            this.player=player;
            this.mploss=mp1;
            this.mphealth=mp2;
            this.iV=iV;
            this.mp3=mp3;
            c=c;
            if(player.equals(player1)){
                number=Health1;
            }
            else if(player.equals(player2)){
                number=Health2;
            }

        }
        @Override
        public void onClick(View v) {
            if(player.equals(player1)){
                number=Health1;
            }
            else if(player.equals(player2)){
                number=Health2;
            }
            Log.i(TAG," Buttonclick used");

            t.setText(player+"'s Health: "+ number + "\n");
            Log.i(TAG, "" + number + "\n");
            //Taken from https://stackoverflow.com/questions/6151566/how-to-wait-a-thread-in-android
            if(v.getId()==R.id.SubtractButton1 || v.getId()==R.id.SubtractButton2) {
                number--;
                mploss.start();
            }
            else if(v.getId()==R.id.CoinToss){
                if(coinSpinning==false){
                    coinSpinning=true;
                    new Thread(new Runnable() {
                        Random rand = new Random();
                        int i=0;
                        public void run(){
                            mp3.start();
                            while(i<6){
                                try{
                                    Thread.sleep(300);
                                }
                                catch(Exception e){
                                    Log.i(TAG, "Thread error " +e.getMessage());
                                }
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Log.i(TAG, "Running runOnUi");
                                        changeImage(i,iV);

                                    }
                                });
                                i++;
                            }
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Log.i(TAG, "Running last runOnUi");
                                    if(rand.nextBoolean()){
                                        changeImage(1,iV);
                                    }else{
                                        changeImage(2,iV);
                                    }
                                }
                            });
                        }
                    }).start();
                coinSpinning=false;
                }
            }
            else{
                number++;
                mphealth.start();
            }
            if(player.equals(player1)){
                Health1=number;
            }
            else if(player.equals(player2)){
                Health2=number;
            }
            t.setText(player+"'s Health: "+ number + "\n");
            Log.i(TAG, "" + number + "\n");
        }
    }
}

