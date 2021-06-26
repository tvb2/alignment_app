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

import static align_it.botv.alignment.MainActivity.APP_PREFERENCES;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_3BR;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_3BT;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_3FR;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_3FT;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_9BR;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_9BT;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_BA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_BBR;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_BBT;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FACEDIA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FBR;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FBT;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FFA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FNA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_OH;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_SPACING;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_SPAN;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_TFA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_TNA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_config;
import static java.lang.Double.*;

public class Side_input extends AppCompatActivity {
    EditText inp_span_val, inp_face_dia_val, inp_oh_val, inp_spacing_val, inp_3BT_val, inp_9BT_val,inp_3FT_val,inp_3BR_val,inp_9BR_val,inp_3FR_val;
    TextView inp_spacing_txt;

    String span, facedia, oh, b3t_s, f3t_s, b3r_s,f3r_s, b9t_s,b9r_s, ba_s, ffa_s, fna_s, tfa_s, tna_s, spacing, conf;

    Button btnAct2Backto1, btnCalculate;

    Double SPAN,FACEDIA,OH, B3T, F3T, B9T, B9R, B3R, F3R, SPACING;
    Double b3t, f3t, b3r, f3r, b9t,b9r;
    Double sine1,cos1,sine2,cos2,delta1, delta2, delta3, ratio, BorD, x1, x2;
    Double FFA, FNA, BA, TFA, TNA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_input);
        inp_spacing_txt=findViewById(R.id.inp_spacing_txt);
        inp_spacing_val=findViewById(R.id.inp_spacing_val);
        inp_span_val=findViewById(R.id.inp_span_val);
        inp_face_dia_val=findViewById(R.id.inp_face_dia_val);
        inp_oh_val=findViewById(R.id.inp_OH_val);
        inp_3BR_val=findViewById(R.id.inp_3BR_val);
        inp_3BT_val=findViewById(R.id.inp_3BT_val);
        inp_3FR_val=findViewById(R.id.inp_3FR_val);
        inp_3FT_val=findViewById(R.id.inp_3FT_val);
        inp_9BT_val=findViewById(R.id.inp_9BT_val);
        inp_9BR_val=findViewById(R.id.inp_9BR_val);
        btnAct2Backto1=findViewById(R.id.btnAct2Backto1);
        btnCalculate=findViewById(R.id.btnCalculate);

        /*get application data and populate EditText fields. If this is initial call of UserInput activity, data should be empty */
        SharedPreferences data=getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        span=data.getString(APP_PREFERENCES_SPAN,"");
        facedia=data.getString(APP_PREFERENCES_FACEDIA,"");
        oh=data.getString(APP_PREFERENCES_OH,"");
        b3t_s=data.getString(APP_PREFERENCES_3BT,"");
        f3t_s=data.getString(APP_PREFERENCES_3FT,"");
        b3r_s=data.getString(APP_PREFERENCES_3BR,"");
        f3r_s=data.getString(APP_PREFERENCES_3FR,"");
        b9t_s=data.getString(APP_PREFERENCES_9BT,"");
        b9r_s=data.getString(APP_PREFERENCES_9BR,"");
        spacing=data.getString(APP_PREFERENCES_SPACING,"0");
        conf=data.getString(APP_PREFERENCES_config,"2");
        inp_span_val.setText(span);
        inp_face_dia_val.setText(facedia);
        inp_oh_val.setText(oh);
        inp_3BT_val.setText(b3t_s);
        inp_3FT_val.setText(f3t_s);
        inp_3BR_val.setText(b3r_s);
        inp_3FR_val.setText(f3r_s);
        inp_9BT_val.setText(b9t_s);
        inp_9BR_val.setText(b9r_s);
        inp_spacing_val.setText(spacing);
        int config=Integer.parseInt(conf);

        if(config==2 | config==3){
            inp_spacing_txt.setVisibility(View.VISIBLE);
            inp_spacing_val.setVisibility(View.VISIBLE);
        }
        btnAct2Backto1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                //Return to previous page (Main view)
                Intent intent = new Intent(getApplicationContext(),Bott_or_side.class);
                startActivity(intent);
            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Get values from editview fields*/
                SPAN= parseDouble(inp_span_val.getText().toString());
                FACEDIA=parseDouble(inp_face_dia_val.getText().toString());
                OH=parseDouble(inp_oh_val.getText().toString());
                B3T=parseDouble(inp_3BT_val.getText().toString());
                F3T=parseDouble(inp_3FT_val.getText().toString());
                B3R=parseDouble(inp_3BR_val.getText().toString());
                F3R=parseDouble(inp_3FR_val.getText().toString());
                B9T=parseDouble(inp_9BT_val.getText().toString());
                B9R=parseDouble(inp_9BR_val.getText().toString());
                SPACING=parseDouble(inp_spacing_val.getText().toString());

                //convert to inches or mm from thou or microns
                b3t=B3T/1000;
                f3t=F3T/1000;
                b3r=B3R/1000;
                f3r=F3R/1000;
                b9t=B9T/1000;
                b9r=B9R/1000;

                //intermediate calculations common to all configurations
                x1=(b9t-b3t)/2;
                x2=(b9r-b3r)/2;
                sine1=f3r/(FACEDIA/2);
                cos1= Math.sqrt(1- Math.pow(sine1,2));
                sine2=f3t/(FACEDIA/2);
                cos2= Math.sqrt(1- Math.pow(sine2,2));
                delta1=SPAN*(sine1/cos1);
                delta2=SPAN*(sine2/cos2);
                delta3=delta2-delta1;//this is different from Bottom bore and face calcs
                ratio=SPAN/(OH+SPACING);//In case of si and so configuration, Machine spacing is set to zero.
                BorD=delta3/ratio;
                //End of intermediate calculations

                switch (config) {
                    case 0://stationary-inside
                        FFA= Double.valueOf(Math.round(-(delta3+BorD)*1000)); //Face only far foot adjustment
                        FNA = Double.valueOf(Math.round(-BorD * 1000)); //Face only near foot adj
                        BA = Double.valueOf(Math.round(-(x1 - x2)*1000));
                        break;
                    case 1://stationary - outside
                        FFA= Double.valueOf(Math.round(-(delta3+BorD)*1000)); //Face only far foot adjustment
                        FNA = Double.valueOf(Math.round(-BorD * 1000)); //Face only near foot adj
                        BA = Double.valueOf(Math.round((x1 - x2)*1000));
                        break;
                    case 2://movable - inside
                        FFA= Double.valueOf(Math.round((delta3+BorD)*1000)); //Face only far foot adjustment
                        FNA = Double.valueOf(Math.round((BorD * 1000))); //Face only near foot adj
                        BA = Double.valueOf(Math.round(-(x1 - x2)*1000));
                        break;
                    case 3://movable - outside
                        FFA= Double.valueOf(Math.round((delta3+BorD)*1000)); //Face only far foot adjustment
                        FNA = Double.valueOf(Math.round((BorD * 1000))); //Face only near foot adj
                        BA = Double.valueOf(Math.round((x1 - x2)*1000));
                        break;
                    default:
                        break;
                }
                TFA= Double.valueOf(Math.round(FFA+BA)); //Total far foot adj
                TNA= Double.valueOf(Math.round(FNA+BA)); //Total near foot adj

                //Update data file with current values from EditText fields and calculation
                span = String.valueOf(SPAN);
                facedia=String.valueOf(FACEDIA);
                oh=String.valueOf(OH);
                b3t_s=String.valueOf(B3T);
                f3t_s=String.valueOf(F3T);
                b3r_s=String.valueOf(B3R);
                f3r_s=String.valueOf(F3R);
                b9t_s=String.valueOf(B9T);
                b9r_s=String.valueOf(B9R);
                ffa_s=String.valueOf(FFA);
                fna_s=String.valueOf(FNA);
                ba_s=String.valueOf(BA);
                tfa_s=String.valueOf(TFA);
                tna_s=String.valueOf(TNA);
                spacing=String.valueOf(SPACING);
                data.edit().putString(APP_PREFERENCES_SPAN,span).apply();
                data.edit().putString(APP_PREFERENCES_FACEDIA,facedia).apply();
                data.edit().putString(APP_PREFERENCES_OH,oh).apply();
                data.edit().putString(APP_PREFERENCES_3BT,b3t_s).apply();
                data.edit().putString(APP_PREFERENCES_3FT,f3t_s).apply();
                data.edit().putString(APP_PREFERENCES_3BR,b3r_s).apply();
                data.edit().putString(APP_PREFERENCES_3FR,f3r_s).apply();
                data.edit().putString(APP_PREFERENCES_9BT,b9t_s).apply();
                data.edit().putString(APP_PREFERENCES_9BR,b9r_s).apply();
                data.edit().putString(APP_PREFERENCES_BA,ba_s).apply();
                data.edit().putString(APP_PREFERENCES_FFA,ffa_s).apply();
                data.edit().putString(APP_PREFERENCES_FNA,fna_s).apply();
                data.edit().putString(APP_PREFERENCES_TFA,tfa_s).apply();
                data.edit().putString(APP_PREFERENCES_TNA,tna_s).apply();
                data.edit().putString(APP_PREFERENCES_SPACING,spacing).apply();
                String a = Integer.toString(config);
                data.edit().putString(APP_PREFERENCES_config,a);// pass 'config' to user input activity to change visibility of Machine spacing
                Intent int2 = new Intent(Side_input.this, Results_side.class);
                startActivity(int2);
        }
            });
    }
}