package align_it.botv.alignment;

import static java.lang.Double.parseDouble;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import align_it.botv.alignment.databinding.ActivityFaceDiameterBinding;

import static align_it.botv.alignment.MainActivity.APP_PREFERENCES;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FACEDIA;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_3FT;
import static align_it.botv.alignment.MainActivity.APP_PREFERENCES_FBT;


public class FaceDiameter extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityFaceDiameterBinding binding;

    TextView introduction,inp_face_dia_drawing_txt,inp_face_dia_measured_txt,inp_3FT_txt,inp_FBT_txt,res_New_FBT,res_New_F3T;
    EditText inp_face_dia_drawing_val, inp_face_dia_measured_val, inp_3FT_val, inp_FBT_val, res_new_FBT_val,res_new_F3T_val;
    Button btnCalcNewFT, btnBack;
    CheckBox ck_box_use_new_FT;
    double facedia_new, facedia_mid, fbt,f3t, new_fbt, new_f3t;
    String facedia_s, fbt_s, f3t_s, res_new_fbt_s, res_new_f3t_s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFaceDiameterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*Initialize views*/
        introduction=findViewById(R.id.introduction);
        inp_face_dia_drawing_txt=findViewById(R.id.inp_face_dia_drawing_txt);
        inp_face_dia_measured_txt=findViewById(R.id.inp_face_dia_measured_txt);
        inp_3FT_txt=findViewById(R.id.inp_3FT_txt);
        inp_FBT_txt=findViewById(R.id.inp_FBT_txt);
        res_New_FBT=findViewById(R.id.res_New_FBT);
        res_New_F3T=findViewById(R.id.res_New_F3T);
        inp_face_dia_drawing_val=findViewById(R.id.inp_face_dia_drawing_val);
        inp_face_dia_measured_val=findViewById(R.id.inp_face_dia_measured_val);
        inp_3FT_val=findViewById(R.id.inp_3FT_val);
        inp_FBT_val=findViewById(R.id.inp_FBT_val);
        res_new_FBT_val=findViewById(R.id.res_new_FBT_val);
        res_new_F3T_val=findViewById(R.id.res_new_F3T_val);
        btnCalcNewFT=findViewById(R.id.btnCalcNewFT);
        btnBack=findViewById(R.id.btnBack);
        ck_box_use_new_FT=findViewById(R.id.ck_box_use_new_FT);
        //Do not display widgets until button is pressed
        res_new_FBT_val.setVisibility(View.GONE);
        res_new_F3T_val.setVisibility(View.GONE);
        res_New_FBT.setVisibility(View.GONE);
        res_New_F3T.setVisibility(View.GONE);
        ck_box_use_new_FT.setVisibility(View.GONE);

        /*get application data and populate EditText fields. If this is initial call of UserInput activity, data should be empty */
        SharedPreferences data=getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        facedia_s=data.getString(APP_PREFERENCES_FACEDIA,"");
        fbt_s=data.getString(APP_PREFERENCES_FBT,"");
        f3t_s=data.getString(APP_PREFERENCES_3FT,"");

        /*Set values of input data to values stored in data file. If this is the first call, data will be empty*/
        inp_face_dia_drawing_val.setText(facedia_s);
        inp_FBT_val.setText(fbt_s);
        inp_3FT_val.setText(f3t_s);
        /*End of data fill*/

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                //see if user checked box to use new targets for face bottom and face 3 o'clock
                if (ck_box_use_new_FT.isChecked()) {
                    //update database values with new calculated targets and measured face diameter
                    facedia_s = String.valueOf(facedia_new);
                    data.edit().putString(APP_PREFERENCES_FACEDIA, facedia_s).apply();
                    data.edit().putString(APP_PREFERENCES_3FT, res_new_f3t_s).apply();
                    data.edit().putString(APP_PREFERENCES_FBT, res_new_fbt_s).apply();
                }
                //Return to previous page (Bott_or_side)
                Intent intent = new Intent(getApplicationContext(),Bott_or_side.class);
                startActivity(intent);
            }
        });

        btnCalcNewFT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get input values
                facedia_new=parseDouble(inp_face_dia_measured_val.getText().toString());
                facedia_mid=parseDouble(inp_face_dia_drawing_val.getText().toString());
                fbt=parseDouble(inp_FBT_val.getText().toString());
                f3t=parseDouble(inp_3FT_val.getText().toString());
                //Calculate new targets
                new_fbt=fbt*(facedia_new/facedia_mid);
                new_f3t=f3t*(facedia_new/facedia_mid);
                //display widgets with results
                res_New_FBT.setVisibility(View.VISIBLE);
                res_New_F3T.setVisibility(View.VISIBLE);
                res_new_FBT_val.setVisibility(View.VISIBLE);
                res_new_F3T_val.setVisibility(View.VISIBLE);
                ck_box_use_new_FT.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);

                //convert double to string values and fill in results to appropriate fields
                res_new_fbt_s=String.valueOf(new_fbt);
                res_new_f3t_s=String.valueOf(new_f3t);
                res_new_FBT_val.setText(res_new_fbt_s);
                res_new_F3T_val.setText(res_new_f3t_s);
            }
        });
    }

    }