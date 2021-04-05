package uk.com.v3softech.online.store;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.widget.Toast;


public class popups {


    private static boolean FLAG_CONFIRM = false;
    private Context context;
    public popups(Context c){
        this.context = c;

    }
public boolean confirm(String title, String question){

    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
    alertDialog.setTitle(title);
    alertDialog.setIcon(R.drawable.ic_cart);
    alertDialog.setMessage(question);
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                 FLAG_CONFIRM = true;
                    dialog.dismiss();
                }
            });
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    FLAG_CONFIRM = false;
                    dialog.dismiss();
                }
            });
    alertDialog.show();
    return FLAG_CONFIRM;
}
    public void progress(Context _context){
        try {
            ProgressDialog pd = new ProgressDialog(_context);
            pd.show();
            pd.setContentView(R.layout.item_loading);
            pd.getWindow().setBackgroundDrawableResource(R.color.transparent);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                 pd.dismiss();
                }
            },CONSTANTS.DELAY_NORMAL);
        }catch (Exception e){
            message(e.toString());
        }
    }

    public void message(String content){
        Toast.makeText(context,content,Toast.LENGTH_LONG).show();
    }
    public void alert(Context c,String title,String message){
        AlertDialog alertDialog = new AlertDialog.Builder(c).create();
        alertDialog.setTitle(title);
        alertDialog.setIcon(R.drawable.ic_info);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
