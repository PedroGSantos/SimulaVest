package com.example.simulavestlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class testeexemplo extends AppCompatActivity {
private Button botaopraticar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testeexemplo);
        botaopraticar= (Button) findViewById(R.id.btnpraticar);
        botaopraticar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(testeexemplo.this, Questao.class);
                startActivity(i);
            }
        });
    }
}
