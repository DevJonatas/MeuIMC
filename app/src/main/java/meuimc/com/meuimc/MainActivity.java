package meuimc.com.meuimc;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends Activity {
    private TextView usuarioWelcome;
    private EditText nomeUsuario;
    private EditText pesoUsuario;
    private EditText alturaUsuario;
    private TextView resultado;
    private Button botaoCalcular;

    private static final String ARQUIVO_PREFERENCES = "nome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioWelcome = findViewById(R.id.txtWelcome);
        nomeUsuario = findViewById(R.id.cxNome);
        pesoUsuario = findViewById(R.id.cxPeso);
        alturaUsuario = findViewById(R.id.cxAltura);
        resultado = findViewById(R.id.txtResult);
        botaoCalcular = findViewById(R.id.btnCalc);

        botaoCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (pesoUsuario.getText().toString().equals("") || alturaUsuario.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this, "O Campo Peso ou Altura não esta preenchido! ", Toast.LENGTH_SHORT).show();
                    }else{
                        double peso =  Double.parseDouble(pesoUsuario.getText().toString());
                        double altura = Double.parseDouble(alturaUsuario.getText().toString());
                        double IMC = calculoIMC(peso, altura);

                        if (IMC < 17){
                            resultado.setText("Seu atual IMC é: " +IMC+ " Muito abaixo do peso!");
                        }else if(IMC < 18.49){
                            resultado.setText("Seu atual IMC é: " +IMC+ " Abaixo do peso!");
                        }else if(IMC < 24.99){
                            resultado.setText("Seu atual IMC é: " +IMC+ " Peso normal!");
                        }else if(IMC < 29.99){
                            resultado.setText("Seu atual IMC é: " +IMC+ " Acima do peso!");
                        }else if(IMC < 34.99){
                            resultado.setText("Seu atual IMC é: " +IMC+ " Obesidade I");
                        }else if(IMC < 39.99){
                            resultado.setText("Seu atual IMC é: " +IMC+ " Obesidade II (severa)");
                        }else{
                            resultado.setText("Seu atual IMC é: " +IMC+ " Obesidade III (mórbida)");
                        }
                    }
                    setaPreferecias();
                    salvaPreferencias();
            }
        });
        salvaPreferencias();
    }

    private double calculoIMC(double peso, double altura){
        double imc = ((peso) / (altura*altura));
        return imc;
    }

    private void setaPreferecias(){
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCES, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nome", nomeUsuario.getText().toString());
        editor.commit();
    }

    private void salvaPreferencias(){
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCES, 0);
        String salvanome = sharedPreferences.getString("nome", "Usuário");
        usuarioWelcome.setText("Olá: " +salvanome);
        nomeUsuario.setText(salvanome);
    }
}
