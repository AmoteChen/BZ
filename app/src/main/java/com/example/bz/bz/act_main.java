package com.example.bz.bz;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bz.bz.Black_list.Black_list_main;

public class act_main extends AppCompatActivity implements View.OnClickListener{
    Button btn_mooc;
    Button btn_mine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_main);

        btn_mooc=(Button)findViewById(R.id.btn_mooc);
        btn_mooc.setOnClickListener(this);
        btn_mine=(Button)findViewById(R.id.btn_mine);
        btn_mine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_mooc:
                changeFragment(new MoocFragment());
                break;
            case R.id.btn_mine:
                Intent toBlack_list = new Intent(act_main.this, Black_list_main.class);
                startActivity(toBlack_list);
                break;
            default:
                break;
        }
    }

    private void changeFragment(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.framelayout_main,fragment);
        transaction.commit();
    }
}
