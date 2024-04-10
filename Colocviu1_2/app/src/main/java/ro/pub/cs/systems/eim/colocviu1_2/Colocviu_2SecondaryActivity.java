package ro.pub.cs.systems.eim.colocviu1_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Colocviu_2SecondaryActivity extends AppCompatActivity {

    TextView textView;
    Button okButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu2_secondary);

        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        String allTerms = intent.getStringExtra("allTerms");
        // calculez suma si o afisez
        // dau split stringului dupa plus si adun fiecare termen
        String[] terms = allTerms.split("\\+");
        int sum = 0;
        for (String term : terms) {
            try {
                sum += Integer.parseInt(term.trim());
            } catch (NumberFormatException e) {
                // daca nu e numar, ignoram
            }
        }
        textView.setText(""+sum);

        okButton = findViewById(R.id.okButton);
        int finalSum = sum;
        okButton.setOnClickListener(view -> {
            setResult(RESULT_OK, new Intent().putExtra("sum", finalSum));
            finish();
        });
    }
}