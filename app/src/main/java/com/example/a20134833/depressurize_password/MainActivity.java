package com.example.a20134833.depressurize_password;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button[] btn = new Button[12];
    float press = 0.0f;
    float temp = 0.0f;
    int pwingcount = 0;
    ImageView[] pwimg = new ImageView[4];
    String Spw;
    Float Fpress;
    String pw = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn[0] = (Button)findViewById(R.id.B0);
        btn[1] = (Button)findViewById(R.id.B1);
        btn[2] = (Button)findViewById(R.id.B2);
        btn[3] = (Button)findViewById(R.id.B3);
        btn[4] = (Button)findViewById(R.id.B4);
        btn[5] = (Button)findViewById(R.id.B5);
        btn[6] = (Button)findViewById(R.id.B6);
        btn[7] = (Button)findViewById(R.id.B7);
        btn[8] = (Button)findViewById(R.id.B8);
        btn[9] = (Button)findViewById(R.id.B9);
        btn[10] = (Button)findViewById(R.id.BL);
        btn[11] = (Button)findViewById(R.id.BR);

        pwimg[0] = (ImageView)findViewById(R.id.pw1);
        pwimg[1] = (ImageView)findViewById(R.id.pw2);
        pwimg[2] = (ImageView)findViewById(R.id.pw3);
        pwimg[3] = (ImageView)findViewById(R.id.pw4);

        SharedPreferences isfirst = getSharedPreferences("isfirst", MODE_PRIVATE);
        boolean first = isfirst.getBoolean("isfirst", false);
        if(first==false)    {
            FirstPopup firstPopup = new FirstPopup(this);

            // 커스텀 다이얼로그를 호출한다.
            // 커스텀 다이얼로그의 결과를 출력할 TextView를 매개변수로 같이 넘겨준다.

            firstPopup.show();
        }

        SharedPreferences pw = getSharedPreferences("pw", MODE_PRIVATE);
        Spw = pw.getString("pw", "aBcD");

        SharedPreferences press = getSharedPreferences("press", MODE_PRIVATE);
        Fpress = press.getFloat("press", 0.5f);
        /*
        SharedPreferences.Editor editor = isfirst.edit();
            editor.putBoolean("isfirst", true);
            editor.commit();

         */

    }

    public void BtnTouch(View v)    {
        Log.e("Btn name = ", String.valueOf(v.getId()));
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                press = event.getPressure();
                if(press>temp)  {
                    temp = press;
                }
                if(temp < Fpress)  {
                    v.setBackgroundColor(0xffffee00);
                }   else    {
                    v.setBackgroundColor(0xffff2828);
                }

                switch (event.getAction())  {
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundResource(android.R.drawable.btn_default);
                        Log.e("Press = ", String.valueOf(temp));
                        Button TempB = (Button)v;
                        int data = Integer.parseInt(TempB.getText().toString());
                        if(temp < Fpress)  {
                            data += 96;
                        }   else    {
                            data += 64;
                        }
                        pw += Character.toString((char)data);
                        pwimg[pwingcount++].setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_on));
                        Log.e("Text = ", pw +"/"+pwingcount);
                        temp = 0.0f;
                        break;
                }
                /*
                press = event.getPressure();
                if(press > temp)    {
                    temp = press;
                }
                Log.e("Press = ", String.valueOf(temp));
                */
                return false;
            }
        });

        if(pwingcount==4)   {
            if(pw.equals(Spw))   {
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, PrivateScreen.class);
                startActivity(intent);
                finish();
            }   else    {
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                pwingcount = 0;
                for(int i=0; i<4; i++)  {
                    pwimg[i].setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_off));
                }
                pw = "";
                Log.e("Text = ", pw);


            }
        }
    }

    public void BtnReset(View view) {
        pwingcount = 0;
        for(int i=0; i<4; i++)  {
            pwimg[i].setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_off));
        }
        pw = "";
        Log.e("Text = ", pw);
    }

    public void BtnDelete(View view) {
        pwimg[--pwingcount].setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_off));
        pw = pw.substring(0, pw.length()-1);
        Log.e("Text = ", pw);
    }
}