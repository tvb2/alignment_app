package align_it.botv.alignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import align_it.botv.alignment.R;

public class MainActivity extends AppCompatActivity {
    RadioGroup toolLocation, sweepDiaLocation;
    Button btnNext;
    int location;//Tool location - statinory or movable
    int sweep;//sweep diameter - inside or outside
    int eq_conf;//
    int config;//0=si; 1=so; 2=mi; 3=mo
    //public TextView txout;//0=si; 1=so; 2=mi; 3=mo
    /*Create data file to be used by different Activities. Initialize with empty values on Login
     * Have no idea why, but if not initialize parameters below to different values, get some crap
     * when populate EditText view with actual values... */
    public static final String APP_PREFERENCES="app_data";
    public static final String APP_PREFERENCES_SPAN = "10";
    public static final String APP_PREFERENCES_FACEDIA = "20";
    public static final String APP_PREFERENCES_OH = "30";
    public static final String APP_PREFERENCES_BBT = "40";
    public static final String APP_PREFERENCES_FBT = "50";
    public static final String APP_PREFERENCES_BBR = "60";
    public static final String APP_PREFERENCES_FBR = "70";
    public static final String APP_PREFERENCES_SPACING = "80";
    public static final String APP_PREFERENCES_3BT = "90";
    public static final String APP_PREFERENCES_3FT = "100";
    public static final String APP_PREFERENCES_3BR = "110";
    public static final String APP_PREFERENCES_3FR = "120";
    public static final String APP_PREFERENCES_9BT = "130";
    public static final String APP_PREFERENCES_9BR = "140";
    public static final String APP_PREFERENCES_FFA = "150";
    public static final String APP_PREFERENCES_FNA = "160";
    public static final String APP_PREFERENCES_TFA = "170";
    public static final String APP_PREFERENCES_TNA = "180";
    public static final String APP_PREFERENCES_BA = "190";
    public static final String APP_PREFERENCES_RATIO="200";
    public static final String APP_PREFERENCES_delta1 = "210";
    public static final String APP_PREFERENCES_config = "2";
/*
    public static final String APP_PREFERENCES_sine1 = "130";
    public static final String APP_PREFERENCES_sine2 = "140";
    public static final String APP_PREFERENCES_cos1 = "150";
    public static final String APP_PREFERENCES_cos2 = "160";
    public static final String APP_PREFERENCES_delta1 = "170";
    public static final String APP_PREFERENCES_delta2 = "180";
    public static final String APP_PREFERENCES_delta3 = "190";
    public static final String APP_PREFERENCES_ratio = "200";
    public static final String APP_PREFERENCES_BorD = "210";
    public static final String APP_PREFERENCES_FFA = "220";
    public static final String APP_PREFERENCES_FNA = "230";
    public static final String APP_PREFERENCES_BA = "240";
    public static final String APP_PREFERENCES_TFA = "250";
    public static final String APP_PREFERENCES_TNA = "260";
*/
    SharedPreferences app_data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolLocation=findViewById(R.id.toolLocation);
        sweepDiaLocation=findViewById(R.id.sweedDiaLocation);
        btnNext=findViewById(R.id.btnNext);
        app_data=getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int checkId1 = toolLocation.getCheckedRadioButtonId();
                int checkId2=sweepDiaLocation.getCheckedRadioButtonId();
                //One of the radio buttons is selected
                if (checkId1 == -1 || checkId2==-1)
                {
                    // No radiobuttons selected
                    Message.message(getApplicationContext(), "Please select tool location and sweep diameter");
                } else {
                    findRadioButton(checkId1, checkId2);
                    config = defineEquipmentConfiguration(location, sweep);
                    String a = Integer.toString(config);
                    /*Initialize application data with empty values on login */
                    SharedPreferences.Editor editor=app_data.edit();
                    editor.putString(APP_PREFERENCES_SPACING,"0");
                    editor.putString(APP_PREFERENCES_SPAN,"");
                    editor.putString(APP_PREFERENCES_FACEDIA,"");
                    editor.putString(APP_PREFERENCES_OH,"");
                    editor.putString(APP_PREFERENCES_BBT,"");
                    editor.putString(APP_PREFERENCES_FBT,"");
                    editor.putString(APP_PREFERENCES_BBR,"");
                    editor.putString(APP_PREFERENCES_FBR,"");
                    editor.putString(APP_PREFERENCES_3BT,"");
                    editor.putString(APP_PREFERENCES_3FT,"");
                    editor.putString(APP_PREFERENCES_3BR,"");
                    editor.putString(APP_PREFERENCES_3FR,"");
                    editor.putString(APP_PREFERENCES_9BT,"");
                    editor.putString(APP_PREFERENCES_9BR,"");
                    editor.putString(APP_PREFERENCES_FFA,"");
                    editor.putString(APP_PREFERENCES_FNA,"");
                    editor.putString(APP_PREFERENCES_BA,"");
                    editor.putString(APP_PREFERENCES_TFA,"");
                    editor.putString(APP_PREFERENCES_TNA,"");
                    editor.putString(APP_PREFERENCES_RATIO,"");
                    editor.putString(APP_PREFERENCES_delta1,"");
                    editor.putString(APP_PREFERENCES_config,a);// pass 'config' to user input activity to change visibility of Machine spacing
                    /*
                    editor.putString(APP_PREFERENCES_sine1,"");
                    editor.putString(APP_PREFERENCES_sine2,"");
                    editor.putString(APP_PREFERENCES_cos1,"");
                    editor.putString(APP_PREFERENCES_cos2,"");
                    editor.putString(APP_PREFERENCES_delta1,"");
                    editor.putString(APP_PREFERENCES_delta2,"");
                    editor.putString(APP_PREFERENCES_delta3,"");
                    editor.putString(APP_PREFERENCES_ratio,"");
                    editor.putString(APP_PREFERENCES_BorD,"");
                    editor.putString(APP_PREFERENCES_FFA,"");
                    editor.putString(APP_PREFERENCES_FNA,"");
                    editor.putString(APP_PREFERENCES_BA,"");
                    editor.putString(APP_PREFERENCES_TFA,"");
                    editor.putString(APP_PREFERENCES_TNA,"");
                    */
                    editor.apply();

                    ////////////////////////////////////////////////////////////////////////////////
                        switch (v.getId()) {
                            case R.id.btnNext:
                                //Move to the next screen to start user data input process
                                Intent intent = new Intent(getApplicationContext(), Bott_or_side.class);
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                    }
                }
                //txout.setText(a);//Output of selected configuration to textview
                //Toast.makeText(MainActivity.this,a,Toast.LENGTH_SHORT).show();//Toast message with selected configuration number 0=si; 1=so; 2=mi; 3=mo.
        });
    }

    /*Funtcion will process radio button selection and set values for variables 'location' and 'sweep'*/
    private void findRadioButton(int checkId1, int checkId2) {
        switch (checkId1){
            case R.id.rbStationary:
                //Message.message(getApplicationContext(),"You've chosen Stationary");
                location=0;
                break;
            case R.id.rbMovable:
                //Message.message(getApplicationContext(),"You've chosen Movable");
                location=1;
                break;
        }
        switch (checkId2){
            case R.id.rbInside:
                //Message.message(getApplicationContext(),"You've chosen Inside");
                sweep=2;
                break;
            case R.id.rbOutside:
                //Message.message(getApplicationContext(),"You've chosen Outside");
                sweep=3;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + checkId2);
        }
    }
     /*Function will process selected configuration aquired in 'findRadioButton' function and return corresponding number 0=si; 1=so; 2=mi; 3=mo*/
    private int defineEquipmentConfiguration(int loc, int sw){
        if(loc==0) {
            if (sw == 2) {
                eq_conf = 0;//Stationary-inside
            } else {
                eq_conf = 1;//Stationary-outside
            }
        }
        else{
            if(sw==2){
                eq_conf=2;//Movable-inside
            }else{
                eq_conf=3;//Movable-outside
            }
        }
        return (eq_conf);
    }
}