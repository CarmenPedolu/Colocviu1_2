package ro.pub.cs.systems.eim.colocviu1_2;

import android.content.Context;
import android.util.Log;
import android.content.Intent;
import java.util.Date;

public class ProcessingThread extends Thread{
private boolean isRunning = true;
    private int sum;
    private final Context context;

    public ProcessingThread(int sum, Context context) {
        this.context = context;
        this.sum = sum;
    }

    @Override
    public void run() {
        Log.d("ProcessingThreadTag", "Thread has started! PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid());
        while (isRunning) {
            sleep();
            sendMessage();
        }
        Log.d("ProcessingThreadTag", "Thread has stopped!");
    }

    private void sleep() {
        try {
            Thread.sleep(2000); // 2 seconds
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction("ro.pub.cs.systems.eim.colocviu1_2.intent.action.SEND_BROADCAST");
        String message = new Date(System.currentTimeMillis()) + " " + sum;
        intent.putExtra("sum", sum);
        intent.putExtra("BROADCAST_RECEIVER_EXTRA", message);
        context.sendBroadcast(intent);
    }

    public void stopThread() {
        isRunning = false;
    }

}
