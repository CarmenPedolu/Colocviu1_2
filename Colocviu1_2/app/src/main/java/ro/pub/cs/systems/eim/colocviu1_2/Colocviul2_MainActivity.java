package ro.pub.cs.systems.eim.colocviu1_2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

1public class Colocviul2_MainActivity extends AppCompatActivity {

    Button addButton, computeButton;
    TextView nextTermTextView, allTermsTextView;
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
    }
}