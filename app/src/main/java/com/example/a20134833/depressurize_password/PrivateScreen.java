package com.example.a20134833.depressurize_password;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class PrivateScreen extends AppCompatActivity {

    float Fpress = 0.0f;
    float temp = 0.0f;
    Button[] pwbtn = new Button[4];
    String pw = "";
    int pwbtnCount = 0;
    Spinner Ts;
    int TsPoint = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_screen);
        SharedPreferences press = getSharedPreferences("press", MODE_PRIVATE);
        Fpress = press.getFloat("press", 0.5f);

        pwbtn[0] = (Button)findViewById(R.id.V1);
        pwbtn[1] = (Button)findViewById(R.id.V2);
        pwbtn[2] = (Button)findViewById(R.id.V3);
        pwbtn[3] = (Button)findViewById(R.id.V4);

        Ts = (Spinner)findViewById(R.id.P_Text);
        TsPoint = (int) (Fpress*10) - 1;
        Ts.setSelection(TsPoint);
        Ts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TsPoint = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void CP(View v) {
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float press = event.getPressure();

                if(press>temp)  {
                    temp = press;
                }
                if(temp < Fpress)  {
                    v.setBackgroundColor(0xffffee00);
                }   else    {
                    v.setBackgroundColor(0xffff2828);
                }

                if(pwbtnCount<=4)   {
                    switch (event.getAction())  {
                        case MotionEvent.ACTION_UP:
                            v.setBackgroundResource(android.R.drawable.btn_default);
                            Log.e("Press = ", String.valueOf(temp));
                            Button TempB = (Button)v;
                            int data = Integer.parseInt(TempB.getText().toString());
                            pwbtn[pwbtnCount].setText(((Button) v).getText().toString());
                            if(temp < Fpress)  {
                                data += 96;
                                pwbtn[pwbtnCount++].setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                            }   else    {
                                data += 64;
                                pwbtn[pwbtnCount++].setBackground(getResources().getDrawable(R.drawable.circle_red));
                            }
                            pw += Character.toString((char)data);
                            Log.e("Text = ", pw +"/"+pwbtnCount);
                            temp = 0.0f;
                            break;
                    }
                }   else    {
                    Toast.makeText(getApplicationContext(), "Already Exist 4 Number", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    public void pTest(View v) {
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float press = event.getPressure();

                if (press < Float.parseFloat(String.valueOf(Ts.getItemAtPosition(TsPoint)))) {
                    v.setBackgroundColor(0xffffee00);
                } else {
                    v.setBackgroundColor(0xffff2828);
                }

                switch (event.getAction())  {
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundResource(android.R.drawable.btn_default);
                        Log.e("Press = ", String.valueOf(temp));
                        break;
                }
                return false;
            }

        });
    }

    public void CR(View view) {
        for(int i=0; i<4; i++)  {
            pwbtn[i].setBackground(getResources().getDrawable(R.drawable.circle_white));
        }
        pw="";
    }

    public void CO(View view) {
        if(pwbtnCount == 4) {
            SharedPreferences pref = getSharedPreferences("pw", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("pw", pw);
            editor.commit();

            Intent intent = new Intent(PrivateScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }   else    {
            Toast.makeText(getApplicationContext(), "Password needs 4 Number", Toast.LENGTH_SHORT).show();
        }
    }


    public void D_OK(View view) {
        SharedPreferences press = getSharedPreferences("press", MODE_PRIVATE);
        SharedPreferences.Editor editor = press.edit();
        editor.putFloat("press", Float.parseFloat(String.valueOf(Ts.getItemAtPosition(TsPoint))));
        editor.commit();
    }

    public void D_Reset(View view) {
        Ts.setSelection(TsPoint);
    }
}
