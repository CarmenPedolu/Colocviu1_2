package ro.pub.cs.systems.eim.colocviu1_2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class Colocviu2_Service extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private ProcessingThread processingThread = null;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Colocviu2_Service", "onStartCommand() method was invoked");
        Log.d("Colocviu2_Service", "The message is: " + intent.getIntExtra("sum", 0));
        processingThread = new ProcessingThread(intent.getIntExtra("sum", 0), this);
        processingThread.start();
        return Service.START_REDELIVER_INTENT; // daca serviciul a fost omorat de sistem o sa scheduld pt un restart
    }
}
