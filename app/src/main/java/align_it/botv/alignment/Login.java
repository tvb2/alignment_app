package align_it.botv.alignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button btnLogin;
    TextView txt_enterpass;
    EditText inp_pass;
    String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txt_enterpass=findViewById(R.id.txt_enterpass);
        inp_pass=findViewById(R.id.inp_pass);
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String input=inp_pass.getText().toString();
                if(inp_pass.getText().toString().equals("9031093448")){
                    //Toast.makeText(Login.this,input,Toast.LENGTH_SHORT).show();
                    Intent log_in = new Intent(Login.this,MainActivity.class);
                    startActivity(log_in);
                }else{
                    Toast.makeText(Login.this,"Wrong!",Toast.LENGTH_SHORT).show();
                    inp_pass.setText("");
                }
            }
        });
    }
}