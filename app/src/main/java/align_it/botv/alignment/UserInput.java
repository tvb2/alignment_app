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

import static align_it.botv.alignment.MainActivity.APP_PREFERENCES;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_BA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_BBR;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_BBT;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FACEDIA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FBR;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FBT;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FFA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FNA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_OH;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_RATIO;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_SPACING;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_SPAN;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_TFA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_TNA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_config;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_delta1;
import static java.lang.Double.*;
//Input data for Vertical (bottom bore&face) alignment calculation
public class UserInput extends AppCompatActivity {
    Button btnAct2Backto1, btnCalculate;
    TextView inp_spacing_txt;
    EditText inp_face_dia_val, inp_oh_val, inp_BBT_val, inp_FBT_val, inp_BBR_val, inp_FBR_val;
    EditText inp_spacing_val, inp_span_val;

    double SPAN, FACEDIA, OH, BBT,FBT,BBR, FBR,SPACING,fbr,fbt,bbr,bbt,sine1,sine2,cos1,cos2,delta1;
    double delta2,delta3,ratio,BorD,FFA, FNA, BA, TFA, TNA;

    String span, facedia, oh, bbt_s, fbt_s, bbr_s,fbr_s, spacing, conf, ffa_s,fna_s,ba_s,tfa_s;
    String tna_s, ratio_s, d1_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);

        /*Initialize views to read input data*/
        inp_spacing_txt=findViewById(R.id.inp_spacing_txt);
        inp_spacing_val=findViewById(R.id.inp_spacing_val);
        inp_span_val=findViewById(R.id.inp_span_val);
        inp_face_dia_val=findViewById(R.id.inp_face_dia_val);
        inp_oh_val=findViewById(R.id.inp_OH_val);
        inp_FBT_val=findViewById(R.id.inp_FBT_val);
        inp_BBR_val=findViewById(R.id.inp_BBR_val);
        inp_FBR_val=findViewById(R.id.inp_FBR_val);
        inp_BBT_val=findViewById(R.id.inp_BBT_val);
        /*End of initialize views*/

        btnAct2Backto1 = findViewById(R.id.btnAct2Backto1);
        btnCalculate=findViewById(R.id.btnCalculate);

        /*get application data and populate EditText fields. If this is initial call of UserInput activity, data should be empty */
        SharedPreferences data=getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        span=data.getString(APP_PREFERENCES_SPAN,"");
        facedia=data.getString(APP_PREFERENCES_FACEDIA,"");
        oh=data.getString(APP_PREFERENCES_OH,"");
        bbt_s=data.getString(APP_PREFERENCES_BBT,"");
        fbt_s=data.getString(APP_PREFERENCES_FBT,"");
        bbr_s=data.getString(APP_PREFERENCES_BBR,"");
        fbr_s=data.getString(APP_PREFERENCES_FBR,"");
        spacing=data.getString(APP_PREFERENCES_SPACING,"0");
        conf=data.getString(APP_PREFERENCES_config,"2");

        /*Set values of input data to values stored in data file. If this is the first call, data will be empty*/
        inp_span_val.setText(span);
        inp_face_dia_val.setText(facedia);
        inp_oh_val.setText(oh);
        inp_BBT_val.setText(bbt_s);
        inp_FBT_val.setText(fbt_s);
        inp_BBR_val.setText(bbr_s);
        inp_FBR_val.setText(fbr_s);
        inp_spacing_val.setText(spacing);
        int config=Integer.parseInt(conf);
        /*End of data fill*/

        /*change visibility of Machine spacing fields based on configuration (tool on Movable - Spacing required)*/
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if(config==2|config==3){
            inp_spacing_txt.setVisibility(View.VISIBLE);
            inp_spacing_val.setVisibility(View.VISIBLE);
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////


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
                BBT=parseDouble(inp_BBT_val.getText().toString());
                FBT=parseDouble(inp_FBT_val.getText().toString());
                BBR=parseDouble(inp_BBR_val.getText().toString());
                FBR=parseDouble(inp_FBR_val.getText().toString());
                SPACING=parseDouble(inp_spacing_val.getText().toString());
                /*End of data aquisition*/

                /*Convert values in microns/thous to mm/inches*/
                bbt=BBT/1000;
                fbt=FBT/1000;
                bbr=BBR/1000;
                fbr=FBR/1000;

                //intermediate calculations common to all configurations
                sine1=fbr/FACEDIA;
                cos1=Math.sqrt(1-Math.pow(sine1,2));
                sine2=fbt/FACEDIA;
                cos2=Math.sqrt(1-Math.pow(sine2,2));
                delta1=SPAN*(sine1/cos1);
                delta2=SPAN*(sine2/cos2);
                delta3=delta1-delta2;
                ratio=SPAN/(OH+SPACING);//In case of si and so configuration, Machine spacing is set to zero.
                BorD=delta3/ratio;
                //End of intermediate calculations

                /*Calculations specific to configuration*/
                FFA=Math.round((delta3+BorD)*1000); //Face only far foot adjustment
                switch (config) {
                    case 0://stationary-inside
                        FNA = Math.round(BorD * 1000); //Face only near foot adj
                        BA = (BBT - BBR) / 2; //Bore only adj
                        break;
                    case 1://stationary - outside
                        FNA = Math.round(BorD * 1000); //Face only near foot adj
                        BA = -(BBT - BBR) / 2; //Bore only adj
                        break;
                    case 2://movable - inside
                        FNA = Math.round(BorD * 1000); //Face only near foot adj
                        BA = -(BBT - BBR) / 2; //Bore only adj
                        break;
                    case 3://movable - outside
                        FNA = Math.round(BorD * 1000); //Face only near foot adj
                        BA = (BBT - BBR) / 2; //Bore only adj
                        break;
                    default:
                        break;
                }
                TFA=FFA+BA; //Total far foot adj
                TNA=FNA+BA; //Total near foot adj

                //Update data file with current values from EditText fields and calculation
                //Convert numeric values to string
                span = String.valueOf(SPAN);
                facedia=String.valueOf(FACEDIA);
                oh=String.valueOf(OH);
                bbt_s=String.valueOf(BBT);
                fbt_s=String.valueOf(FBT);
                bbr_s=String.valueOf(BBR);
                fbr_s=String.valueOf(FBR);
                ffa_s=String.valueOf(FFA);
                fna_s=String.valueOf(FNA);
                ba_s=String.valueOf(BA);
                tfa_s=String.valueOf(TFA);
                tna_s=String.valueOf(TNA);
                ratio_s=String.valueOf(ratio);
                d1_s=String.valueOf(delta1);
                spacing=String.valueOf(SPACING);
                //End of data convert to string

                /*Update data file */
                data.edit().putString(APP_PREFERENCES_SPAN,span).apply();
                data.edit().putString(APP_PREFERENCES_FACEDIA,facedia).apply();
                data.edit().putString(APP_PREFERENCES_OH,oh).apply();
                data.edit().putString(APP_PREFERENCES_BBT,bbt_s).apply();
                data.edit().putString(APP_PREFERENCES_FBT,fbt_s).apply();
                data.edit().putString(APP_PREFERENCES_BBR,bbr_s).apply();
                data.edit().putString(APP_PREFERENCES_FBR,fbr_s).apply();
                data.edit().putString(APP_PREFERENCES_SPACING,spacing).apply();
                data.edit().putString(APP_PREFERENCES_BA,ba_s).apply();
                data.edit().putString(APP_PREFERENCES_FFA,ffa_s).apply();
                data.edit().putString(APP_PREFERENCES_FNA,fna_s).apply();
                data.edit().putString(APP_PREFERENCES_TFA,tfa_s).apply();
                data.edit().putString(APP_PREFERENCES_TNA,tna_s).apply();
                data.edit().putString(APP_PREFERENCES_RATIO,ratio_s).apply();
                data.edit().putString(APP_PREFERENCES_delta1,d1_s).apply();
                /*End of update data file*/

                //Open Results Activity
                Intent int2 = new Intent(UserInput.this, Results.class);
                startActivity(int2);
            }
        });
    }
}