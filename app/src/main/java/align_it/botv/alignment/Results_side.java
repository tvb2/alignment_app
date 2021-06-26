package align_it.botv.alignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static align_it.botv.alignment.MainActivity.APP_PREFERENCES;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_BA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FACEDIA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FFA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FNA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_OH;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_SPAN;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_TFA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_TNA;

public class Results_side extends AppCompatActivity {

    Button btnBack;
    TextView out_BA_val, out_FNA_val, out_FFA_val,out_TNA_val,out_TFA_val;
    String ba, fna, ffa, tna, tfa, conf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_side);
        out_BA_val=findViewById(R.id.out_BA_val);
        out_FNA_val=findViewById(R.id.out_FNA_val);
        out_FFA_val=findViewById(R.id.out_FFA_val);
        out_TNA_val=findViewById(R.id.out_TNA_val);
        out_TFA_val=findViewById(R.id.out_TFA_val);

        /*get application data and populate Text fields.  */
        SharedPreferences data=getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        ba=data.getString(APP_PREFERENCES_BA,"");
        fna=data.getString(APP_PREFERENCES_FNA,"");
        ffa=data.getString(APP_PREFERENCES_FFA,"");
        tfa=data.getString(APP_PREFERENCES_TFA,"");
        tna=data.getString(APP_PREFERENCES_TNA,"");

        out_BA_val.setText(ba);
        out_FNA_val.setText(fna);
        out_FFA_val.setText(ffa);
        out_TNA_val.setText(tna);
        out_TFA_val.setText(tfa);

        btnBack=findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Results_side.this,Side_input.class);
                startActivity(intent);
            }
        });
    }
}