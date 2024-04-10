package ro.pub.cs.systems.eim.colocviu1_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Colocviul2_MainActivity extends AppCompatActivity {

    Button addButton, computeButton;
    TextView nextTermTextView, allTermsTextView;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        computeButton = findViewById(R.id.computeButton);
        nextTermTextView = findViewById(R.id.nextTermView);
        allTermsTextView = findViewById(R.id.allTermsView);

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
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                int sum = result.getData().getExtras().getInt("sum");
                Toast.makeText(this, "The activity returned with message OK and sum" + sum, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "The activity returned with message CANCEL", Toast.LENGTH_LONG).show();
            }
        });

        computeButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Colocviu_2SecondaryActivity.class);
            intent.putExtra("allTerms", allTermsTextView.getText().toString());
            activityResultLauncher.launch(intent);
        });
    }
}