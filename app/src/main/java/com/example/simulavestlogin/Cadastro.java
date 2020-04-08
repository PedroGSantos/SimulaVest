package com.example.simulavestlogin;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DatabaseReference;
import java.math.BigInteger;
import java.security.spec.ECField;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Cadastro extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //declaração de váriaveis
    private EditText txtTelefone;
    private EditText txtEmail;
    private Button btnCad;
    private Spinner cboSexo;
    private Spinner cboPergunta;
    private EditText txtSenha;
    private EditText txtSenhaC;
    private EditText txtNome;
    private EditText txtResp;
    private TextView txtMostrar;
    Firebase objetoRef;
    Firebase objetoRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Firebase.setAndroidContext(this);

        //Sequencia de codigo
        Spinner colorredSpinner =findViewById(R.id.cbosexo);
        Spinner colorredSpinner2 =findViewById(R.id.cbopergunta);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(
                this,
                R.array.itens,
                R.layout.color_spinner_layout
        );
        ArrayAdapter adapter2=ArrayAdapter.createFromResource(
                this,
                R.array.itens2,
                R.layout.color_spinner_layout
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        colorredSpinner.setAdapter(adapter);
        colorredSpinner.setOnItemSelectedListener(this);
        colorredSpinner2.setAdapter(adapter2);
        colorredSpinner2.setOnItemSelectedListener(this);
        txtTelefone = (EditText) findViewById(R.id.txttelefone);
        txtEmail = (EditText) findViewById(R.id.txtemailc);
        btnCad = (Button) findViewById(R.id.btncad);
        txtNome = (EditText) findViewById(R.id.txtnome);
        txtSenha = (EditText) findViewById(R.id.txtsenhacad);
        txtSenhaC = (EditText) findViewById(R.id.txtconfsenha);
        txtResp = (EditText) findViewById(R.id.txtresp);
        cboPergunta = (Spinner) findViewById(R.id.cbopergunta);
        cboSexo = (Spinner) findViewById(R.id.cbosexo);



        SimpleMaskFormatter mascara = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher m = new MaskTextWatcher(txtTelefone, mascara);
        txtTelefone.addTextChangedListener(m);

        btnCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senhateste = txtSenha.getText().toString();
                String senhateste1 = txtSenhaC.getText().toString();
                String nome= txtNome.getText().toString();
                String resp = txtResp.getText().toString();
                String telefone = txtTelefone.getText().toString();
                String Pergunta = cboPergunta.getSelectedItem().toString();
                String sexo = cboSexo.getSelectedItem().toString();
                String email = txtEmail.getText().toString().trim();
                if("".equals(nome) || "".equals(senhateste) || "".equals(senhateste1) || "".equals(resp) || "".equals(telefone)){
                    Toast.makeText(getApplicationContext(), getString(R.string.teste), Toast.LENGTH_LONG ).show();

                }
                else{
                    if(txtTelefone.getText().length()<14){
                        Toast.makeText(getApplicationContext(), getString(R.string.teste), Toast.LENGTH_LONG ).show();
                        txtTelefone.setError("Você não digitou a quantidade de números correta");
                    }
                    else{

                        if (!emailValidator(email)){
                            Toast.makeText(getApplicationContext(), getString(R.string.teste), Toast.LENGTH_LONG ).show();
                            txtEmail.setError("Você não digitou um formato correto de e-mail");
                        }
                       else{

                            if(txtSenhaC.length()<8 && txtSenha.length()<8){
                                txtSenhaC.setError("Sua senha deve conter 8 ou mais caracteres");

                            }
                            else{
                                if(senhateste1.equals(senhateste)) {
                                    String id= UUID.randomUUID().toString();
                                    objetoRef = new Firebase("https://fir-vestibular.firebaseio.com/Clientes/"+id+"/");
                                    objetoRef.child("Nome").setValue(nome);
                                    objetoRef.child("E-mail").setValue(email);
                                    objetoRef.child("Sexo").setValue(sexo);
                                    objetoRef.child("Senha").setValue(md5.criptografar(senhateste));
                                    objetoRef.child("Confirmação_de_senha").setValue(md5.criptografar(senhateste1));
                                    objetoRef.child("Celular").setValue(telefone);
                                    objetoRef.child("Pergunta_Secreta").setValue(Pergunta);
                                    objetoRef.child("Resposta").setValue(resp);
                                    objetoRef.child("ID").setValue(id);
                                    objetoRef.child("Verificar").child("RespostaVeri").setValue(resp);
                                    objetoRef.child("Verificar").child("SenhaConfi").setValue(md5.criptografar(senhateste));
                                    objetoRef.child("Meta").setValue(0);
                                    objetoRef.child("Status").setValue("Offline");

                                    Toast.makeText(getApplicationContext(), getString(R.string.cadastro), Toast.LENGTH_LONG ).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), getString(R.string.teste), Toast.LENGTH_LONG).show();
                                    txtSenhaC.setError("As senhas digitadas não correspondem");
                                }
                            }
                        }
                    }
                }

            }
        });



    }
    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
