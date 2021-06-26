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
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FACEDIA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FBR;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FFA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FNA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_RATIO;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_SPAN;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_TFA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_TNA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_config;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_delta1;
import static java.lang.Double.parseDouble;

public class Results extends AppCompatActivity {
    TextView out_BA_val, out_FNA_val, out_FFA_val, out_TNA_val, out_TFA_val, out_FBR_new, out_BBR_new, txt_NewBBR,txt_NewFBR;
    EditText inp_test_case_near, inp_test_case_far;
    Button btnPredict, btnBack;
    double inp_test_case_add_near, inp_test_case_add_far;
    double bbr_new, fbr_new, newdelta1, distdirect, sin3, del1,span,ratio,facedia,bbr;
    String ba,fna,ffa,tfa,tna,ratio_s,d1_s,bbr_s,fbr,facedia_s,span_s,conf;
    Integer config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        /*Initialize views*/
        out_BA_val=findViewById(R.id.out_BA_val);
        out_FFA_val=findViewById(R.id.out_FFA_val);
        out_FNA_val=findViewById(R.id.out_FNA_val);
        out_FFA_val=findViewById(R.id.out_FFA_val);
        out_TNA_val=findViewById(R.id.out_TNA_val);
        out_TFA_val=findViewById(R.id.out_TFA_val);
        txt_NewBBR=findViewById(R.id.txt_NewBBR);
        txt_NewFBR=findViewById(R.id.txt_NewFBR);
        out_FBR_new=findViewById(R.id.out_FBR_new);
        out_BBR_new=findViewById(R.id.out_BBR_new);
        txt_NewBBR.setVisibility(View.GONE);
        txt_NewFBR.setVisibility(View.GONE);
        out_FBR_new.setVisibility(View.GONE);
        out_BBR_new.setVisibility(View.GONE);

        /*Read values from data file*/
        SharedPreferences data=getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        ba=data.getString(APP_PREFERENCES_BA,"");
        fna=data.getString(APP_PREFERENCES_FNA,"");
        ffa=data.getString(APP_PREFERENCES_FFA,"");
        tfa=data.getString(APP_PREFERENCES_TFA,"");
        tna=data.getString(APP_PREFERENCES_TNA,"");
        ratio_s=data.getString(APP_PREFERENCES_RATIO,"");
        d1_s=data.getString(APP_PREFERENCES_delta1,"");
        bbr_s=data.getString(APP_PREFERENCES_BBR,"");
        fbr=data.getString(APP_PREFERENCES_FBR,"");
        facedia_s=data.getString(APP_PREFERENCES_FACEDIA,"");
        span_s=data.getString(APP_PREFERENCES_SPAN,"");
        conf=data.getString(APP_PREFERENCES_config,"");
        /*End read values from data file*/

        /*Convert String data to numeric*/
        del1=parseDouble(d1_s);
        ratio=parseDouble(ratio_s);
        facedia=parseDouble(facedia_s);
        span=parseDouble(span_s);
        bbr=parseDouble(bbr_s);
///////////////////////////////////////////////////////////////////////////////////////////////////

        /*Write calculation results to appropriate fields
        Set output values to text view*/
        out_BA_val.setText(ba);
        out_FNA_val.setText(fna);
        out_FFA_val.setText(ffa);
        out_TNA_val.setText(tna);
        out_TFA_val.setText(tfa);
        /*End of write results*/

///////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////
        /*Predict function start*/
        btnPredict=findViewById(R.id.btnPredict);
        btnPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Make visible output fields*/
                txt_NewBBR.setVisibility(View.VISIBLE);
                txt_NewFBR.setVisibility(View.VISIBLE);
                out_BBR_new.setVisibility(View.VISIBLE);
                out_FBR_new.setVisibility(View.VISIBLE);
                /*Initialize views for input values of available shims and read data*/
                inp_test_case_near=findViewById(R.id.inp_test_case_near);
                inp_test_case_far=findViewById(R.id.inp_test_case_far);
                inp_test_case_add_near=parseDouble(inp_test_case_near.getText().toString());
                inp_test_case_add_far=parseDouble(inp_test_case_far.getText().toString());

                /*Calculate new position of equipment*/
                newdelta1=del1+(inp_test_case_add_near/1000)-(inp_test_case_add_far/1000);
                distdirect=Math.sqrt(Math.pow(newdelta1,2)+Math.pow(span,2));
                sin3=newdelta1/distdirect;
                fbr_new=Math.round(1000*(facedia*sin3));
                config=Integer.parseInt(conf);
                //different formulas depending on equipment configuration
                switch (config){
                    case 0:
                        bbr_new=Math.round(1000*(bbr/1000+2*(inp_test_case_add_near/1000)-2*((inp_test_case_add_far-inp_test_case_add_near)/1000)/ratio));
                        break;
                    case 1:
                        bbr_new=Math.round(1000*(bbr/1000-2*(inp_test_case_add_near/1000)+2*((inp_test_case_add_far-inp_test_case_add_near)/1000)/ratio));
                        break;
                    case 2:
                        bbr_new=Math.round(1000*(bbr/1000-2*(inp_test_case_add_near/1000)+2*((inp_test_case_add_far-inp_test_case_add_near)/1000)/ratio));
                        break;
                    case 3:
                        bbr_new=Math.round(1000*(bbr/1000+2*(inp_test_case_add_near/1000)-2*((inp_test_case_add_far-inp_test_case_add_near)/1000)/ratio));
                        break;
                    default:
                        break;
                }
                String FBR_n=String.valueOf(fbr_new);
                String BBR_n=String.valueOf(bbr_new);
                /*Print out predicted values to text fields*/
                out_FBR_new.setText(FBR_n);
                out_BBR_new.setText(BBR_n);
            }
        });
        btnBack=findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int3=new Intent(Results.this,UserInput.class);
                startActivity(int3);
            }
        });
    }
}