package align_it.botv.alignment;
import android.content.Context;
import android.widget.Toast;

public class Message {
    public static void message(Context context, String msg){
        //Toast.setGravity(Gravity.CENTER,0,0);
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
