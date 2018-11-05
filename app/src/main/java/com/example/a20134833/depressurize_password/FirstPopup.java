package com.example.a20134833.depressurize_password;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;

import static android.content.Context.MODE_PRIVATE;

public class FirstPopup extends Dialog{

    Button OKBtn;
    CheckBox notshow;
    public FirstPopup(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.activity_first_popup);     //다이얼로그에서 사용할 레이아웃입니다.

        notshow = (CheckBox)findViewById(R.id.notshow);

        OKBtn = (Button)findViewById(R.id.OKBtn);
        OKBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(notshow.isChecked()) {
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("isfirst", true);
                    editor.commit();
                }
                dismiss();
            }
        });
    }

}
