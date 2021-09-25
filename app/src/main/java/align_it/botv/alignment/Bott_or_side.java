package align_it.botv.alignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Bott_or_side extends AppCompatActivity {
        RadioGroup rgBorS;
        Button btnNext, btnBack;
        int select=-1;
        int checkId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bott_or_side);
        rgBorS=findViewById(R.id.rgBorS);
        btnNext=findViewById(R.id.btnNext);
        btnBack=findViewById(R.id.btnBack);
        Intent intent = getIntent();
        int config=intent.getIntExtra("config",0);
        /*Debug
        String a= Integer.toString(config);
        Toast.makeText(Bott_or_side.this,a,Toast.LENGTH_SHORT).show();
         */
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkId = rgBorS.getCheckedRadioButtonId();
                if (checkId==-1){
                    Toast.makeText(Bott_or_side.this,"Select option!",Toast.LENGTH_SHORT).show();//
                }else{
                    findRadioButton(checkId);
                    if (select==1){//Side to side
                        Intent intent1=new Intent(Bott_or_side.this, Side_input.class);
                        intent1.putExtra("config", config);
                        startActivity(intent1);
                    }
                    if (select==2){
                        Intent intent2=new Intent(Bott_or_side.this, UserInput.class);
                        intent2.putExtra("config", config);
                        startActivity(intent2);
                    }
                    if (select==3){
                        Intent intent3=new Intent(Bott_or_side.this, FaceDiameter.class);
                        intent3.putExtra("config", config);
                        startActivity(intent3);
                    }
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int3=new Intent(Bott_or_side.this,MainActivity.class);
                startActivity(int3);
            }
        });
            }
    private void findRadioButton (int checkdId){
        switch (checkId){
            case R.id.rb_StoS:
                select=1;//Side to side
                break;
            case R.id.rb_F_B_bott:
                select=2;//Bottom
                break;
            case R.id.rb_F_Dia_optn:
                select=3;//Different Face diameter option
                break;
            default:
                break;
        }
    }
}