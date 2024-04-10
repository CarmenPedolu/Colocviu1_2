package ro.pub.cs.systems.eim.colocviu1_2;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Colocviul2_MainActivity extends AppCompatActivity {

    Button addButton, computeButton;
    TextView nextTermTextView, allTermsTextView, showResultTextView;
    ActivityResultLauncher<Intent> activityResultLauncher;

    private IntentFilter intentFilter = new IntentFilter();
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        computeButton = findViewById(R.id.computeButton);
        nextTermTextView = findViewById(R.id.nextTermView);
        allTermsTextView = findViewById(R.id.allTermsView);
        showResultTextView = findViewById(R.id.showResultView);

        addButton.setOnClickListener(view -> {
            String currentText = nextTermTextView.getText().toString();
            // daca allTermsTextView e gol, punem in el doar currentText
            // daca nu gaseste un numar se va ignora
            if (currentText.matches(".*\\d.*")) {
                if (allTermsTextView.getText().toString().equals("")) {
                    allTermsTextView.setText(currentText);
                } else {
                    allTermsTextView.setText(allTermsTextView.getText().toString() + " + " + currentText);
                }
                checkSumAndStartService();
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                int sum = result.getData().getExtras().getInt("sum");
                Toast.makeText(this, "The activity returned with message OK and sum" + sum, Toast.LENGTH_LONG).show();
                showResultTextView.setText(String.valueOf(sum));
            } else {
                Toast.makeText(this, "The activity returned with message CANCEL", Toast.LENGTH_LONG).show();
            }
        });

        computeButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Colocviu_2SecondaryActivity.class);
            intent.putExtra("allTerms", allTermsTextView.getText().toString());
            activityResultLauncher.launch(intent);
        });

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("nextTermTextView")) {
                nextTermTextView.setText(savedInstanceState.getString("nextTermTextView"));
            }
            if (savedInstanceState.containsKey("allTermsTextView")) {
                allTermsTextView.setText(savedInstanceState.getString("allTermsTextView"));
            }
            if (savedInstanceState.containsKey("showResultTextView")) {
                showResultTextView.setText(savedInstanceState.getString("showResultTextView"));
            }
        }

        intentFilter.addAction("ro.pub.cs.systems.eim.colocviu1_2.intent.action.SEND_BROADCAST");

        if (showResultTextView.getText().toString().equals("")) {
            showResultTextView.setText(String.valueOf(0));
        }

    }
    private void checkSumAndStartService() {
        int sumFinalCompute = 0;
        String[] terms = allTermsTextView.getText().toString().split("\\+");
        for (String term : terms) {
            try {
                sumFinalCompute += Integer.parseInt(term.trim());
            } catch (NumberFormatException e) {
                // daca nu e numar, ignoram
            }
        }
        Log.d("sumFinalCompute", "sumFinalCompute: " + String.valueOf(sumFinalCompute));
        if (sumFinalCompute > 10) {
            Log.d("sumFinalCompute", "sumFinalCompute > 10: " + String.valueOf(sumFinalCompute));
            Intent intent = new Intent(getApplicationContext(), Colocviu2_Service.class);
            intent.putExtra("sum", sumFinalCompute);
            getApplicationContext().startService(intent);
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("nextTermTextView", nextTermTextView.getText().toString());
        savedInstanceState.putString("allTermsTextView", allTermsTextView.getText().toString());
        savedInstanceState.putString("showResultTextView", showResultTextView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("nextTermTextView")) {
            nextTermTextView.setText(savedInstanceState.getString("nextTermTextView"));
        } else {
            nextTermTextView.setText(String.valueOf(0));
        }
        if (savedInstanceState.containsKey("allTermsTextView")) {
            allTermsTextView.setText(savedInstanceState.getString("allTermsTextView"));
        } else {
            allTermsTextView.setText(String.valueOf(0));
        }

        if (savedInstanceState.containsKey("showResultTextView")) {
            showResultTextView.setText(savedInstanceState.getString("showResultTextView"));
        } else {
            showResultTextView.setText(String.valueOf(0));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageBroadcastReceiver);
    }
}