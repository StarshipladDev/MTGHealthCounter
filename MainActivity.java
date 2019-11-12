package com.example.healtcount;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.view.animation.*;
import java.util.Random;
import java.util.Timer;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MainActivity extends AppCompatActivity {
    static String TAG="MainActivity";
    String player1="Starshiplad";
    String player2="Starshiplass";
    static int Health1=20;
    static int Health2=20;
    static int[] counterTypes1 = new int[]{0,0,0,0};
    static int[] counterTypes2 = new int[]{0,0,0,0};
    static ImageView[] counters = new ImageView[8];
    static int counterMargin=50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.i(TAG," Yay the program is working!");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //Taken from https://stackoverflow.com/questions/5712849/how-do-i-keep-the-screen-on-in-my-app
        //Keeps phone from turning off
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //End of stolen code
        //Draw image before others so other items draw over it
        ImageView bg = (ImageView)(findViewById(R.id.Background));
        bg.setImageResource(getResources().getIdentifier("@drawable/background",null,this.getPackageName()));
        //Add/initialize buttons with attached Images
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
        ImageView addCounter1 = (ImageView)(findViewById(R.id.addCounter1));
        ImageView addCounter2 = (ImageView)(findViewById(R.id.addCounter2));
        addCounter1.setImageResource(R.drawable.counterbutton);
        addCounter2.setImageResource(R.drawable.counterbutton);
        //Creates a media sound effect to play a 'Blood letting' sound on minus health
        MediaPlayer mp1 = MediaPlayer.create(this, R.raw.splat);
        MediaPlayer mp2 = MediaPlayer.create(this, R.raw.shine);
        MediaPlayer mp3 = MediaPlayer.create(this, R.raw.coin);
        //Add text veiws of scores & counter button ,rotate score 2 and counter 2
        TextView h1 = (TextView)(findViewById(R.id.HealthText1)) ;
        TextView h2 = (TextView)(findViewById(R.id.HealthText2)) ;
        RotateAnimation rotate= (RotateAnimation)AnimationUtils.loadAnimation(this,R.anim.animation);
        h2.setAnimation(rotate);
        //addCounter2.setAnimation(rotate);
        h1.setTextColor(Color.WHITE);
        h2.setTextColor(Color.WHITE);
        //Create Buton Listeners
        ButtonListener b1 = new ButtonListener(h1,player1,mp1,mp2,mp3,coinToss,this);
        ButtonListener b2 = new ButtonListener(h2,player2,mp1,mp2,mp3,coinToss,this);
        CounterListner countList = new CounterListner();
        View.OnLongClickListener lc1 = new HoldListener(h1,player1,mp1,mp2);
        View.OnLongClickListener lc2 = new HoldListener(h2,player2,mp1,mp2);
        //Attach button listeners
        AddButton1.setOnClickListener(b1);
        SubtractButton1.setOnClickListener(b1);
        AddButton1.setOnLongClickListener(lc1);
        SubtractButton1.setOnLongClickListener(lc1);
        AddButton2.setOnClickListener(b2);
        SubtractButton2.setOnClickListener(b2);
        AddButton2.setOnLongClickListener(lc2);
        SubtractButton2.setOnLongClickListener(lc2);
        coinToss.setOnClickListener(b2);
        addCounter1.setOnClickListener(countList);
        addCounter2.setOnClickListener(countList);
        //Call final display parameters
        h1.setText(player1+"'s Health: "+ Health1 + "\n");
        h2.setText(player2+"'s Health: "+ Health2 + "\n");
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
    private class CounterListner implements View.OnClickListener {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.addCounter1){
                int i=0;
                while(i<counterTypes1.length){
                    Log.i(TAG,"i="+i+", counters[i] is "+counterTypes1[i]);
                    if(counterTypes1[i]==0){

                        break;
                    }
                    i++;
                }
                if (i==counterTypes1.length){
                    Log.i(TAG,"Counters full");
                }else{
                    counterTypes1[i]=1;
                    counters[i]= new ImageView(MainActivity.this);
                    counters[i].setImageResource(R.drawable.counter1);
                    counters[i].setId(ViewCompat.generateViewId());
                    ConstraintLayout relativeLayout = (ConstraintLayout) findViewById(R.id.overall);
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    counters[i].setLayoutParams(layoutParams);
                    relativeLayout.addView(counters[i]);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(relativeLayout);
                    constraintSet.connect(counters[i].getId(), ConstraintSet.BOTTOM, relativeLayout.getId(), ConstraintSet.BOTTOM, 20);
                    constraintSet.connect(counters[i].getId(), ConstraintSet.RIGHT, relativeLayout.getId(), ConstraintSet.RIGHT, (100*i)+counterMargin);
                    constraintSet.applyTo(relativeLayout);
                    counters[i].setOnTouchListener(new OnSwipeTouchListener(MainActivity.this,i,relativeLayout){
                        public void onSwipeRight() {
                            Log.i(TAG,"Right called on "+this.iv);
                            Log.i(TAG,"countersType2[iv-4] is "+(counterTypes1[this.iv]));
                            if (counterTypes1[this.iv]==1){
                                counterTypes1[this.iv]=2;
                                counters[this.iv].setImageResource(R.drawable.counter2);
                            }else if(counterTypes1[this.iv]==2){
                                counterTypes1[this.iv]=3;
                                counters[this.iv].setImageResource(R.drawable.counter3);
                            }
                        }
                        public void onSwipeLeft() {
                            Log.i(TAG,"Left called on "+this.iv);
                            Log.i(TAG,"countersType2[iv-4] is "+(counterTypes1[this.iv]));
                            if (counterTypes1[this.iv]==2){
                                counterTypes1[this.iv]=1;
                                counters[this.iv].setImageResource(R.drawable.counter1);
                            }else if(counterTypes1[this.iv]==3){
                                counterTypes1[this.iv]=2;
                                counters[this.iv].setImageResource(R.drawable.counter2);
                            }
                            else if(counterTypes1[this.iv]==1){
                                counterTypes1[this.iv]=0;
                                counters[this.iv].setVisibility(View.INVISIBLE);
                                this.cl.removeView(counters[this.iv]);
                            }
                        }

                    });
                }
            }
            //If player 2 (Upside down) create counter
            else if(v.getId()==R.id.addCounter2){
                int i=0;
                while(i<counterTypes2.length){
                    Log.i(TAG,"i="+i+", counters[i] is "+counterTypes2[i]);
                    if(counterTypes2[i]==0){

                        break;
                    }
                    i++;
                }
                if (i==counterTypes2.length){
                    Log.i(TAG,"Counters full");
                }else{
                    counterTypes2[i]=1;
                    counters[i+4]= new ImageView(MainActivity.this);
                    counters[i+4].setImageResource(R.drawable.counter1);
                    counters[i+4].setId(ViewCompat.generateViewId());
                    ConstraintLayout relativeLayout = (ConstraintLayout) findViewById(R.id.overall);
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    counters[i+4].setLayoutParams(layoutParams);
                    relativeLayout.addView(counters[i+4]);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(relativeLayout);
                    constraintSet.connect(counters[i+4].getId(), ConstraintSet.TOP, relativeLayout.getId(), ConstraintSet.TOP, 20);
                    constraintSet.connect(counters[i+4].getId(), ConstraintSet.LEFT, relativeLayout.getId(), ConstraintSet.LEFT, (100*i)+counterMargin);
                    constraintSet.applyTo(relativeLayout);
                    counters[i+4].setOnTouchListener(new OnSwipeTouchListener(MainActivity.this,i+4,relativeLayout){
                        public void onSwipeRight() {
                            Log.i(TAG,"Right called on "+this.iv);
                            Log.i(TAG,"countersType2[iv-4] is "+(counterTypes2[this.iv-4]));
                            if (counterTypes2[this.iv-4]==1){
                                counterTypes2[this.iv-4]=2;
                                counters[this.iv].setImageResource(R.drawable.counter2);
                            }else if(counterTypes2[this.iv-4]==2){
                                counterTypes2[this.iv-4]=3;
                                counters[this.iv].setImageResource(R.drawable.counter3);
                            }
                        }
                        public void onSwipeLeft() {
                            Log.i(TAG,"Left called on "+this.iv);
                            Log.i(TAG,"countersType2[iv-4] is "+(counterTypes2[this.iv-4]));
                            if (counterTypes2[this.iv-4]==2){
                                counterTypes2[this.iv-4]=1;
                                counters[this.iv].setImageResource(R.drawable.counter1);
                            }else if(counterTypes2[this.iv-4]==3){
                                counterTypes2[this.iv-4]=2;
                                counters[this.iv].setImageResource(R.drawable.counter2);
                            }
                            else if(counterTypes2[this.iv-4]==1){
                                counterTypes2[this.iv-4]=0;
                                counters[this.iv].setVisibility(View.INVISIBLE);
                                this.cl.removeView(counters[this.iv+4]);
                            }
                        }

                    });
                }

            }

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
                            while(i<8){
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
    //Taken from https://stackoverflow.com/questions/4139288/android-how-to-handle-right-to-left-swipe-gestures
    private class OnSwipeTouchListener implements OnTouchListener {
        public int iv=-1;
        public ConstraintLayout cl=null;
        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener (Context ctx,int i,ConstraintLayout cl){
            gestureDetector = new GestureDetector(ctx, new GestureListener());
            this.iv=i;
            this.cl=cl;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }
}

