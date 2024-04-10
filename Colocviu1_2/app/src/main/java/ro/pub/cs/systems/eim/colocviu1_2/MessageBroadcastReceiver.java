package ro.pub.cs.systems.eim.colocviu1_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MessageBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BroadcastReceiverTag", "The message is: " + intent.getIntExtra("sum", 0));
        String result = intent.getStringExtra("BROADCAST_RECEIVER_EXTRA");
        Log.d("BROADCAST_RECEIVER_TAG", "The message is: " + result);
        // fac un toast cu mesajul
        Toast.makeText(context, "The message is: " + intent.getIntExtra("sum", 0), Toast.LENGTH_LONG).show();
        Toast.makeText(context, "The message is: " + result, Toast.LENGTH_LONG).show();
    }
}
