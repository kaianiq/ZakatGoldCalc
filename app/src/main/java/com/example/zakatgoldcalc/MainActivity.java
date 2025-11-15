package com.example.zakatgoldcalc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etGoldPrice;
    RadioGroup rgType;
    RadioButton rbKeep, rbWear;
    TextView tvTotalValue, tvPayableWeight, tvZakatAmount;
    Button btnCalculate, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- Setup Toolbar ---
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // Remove default black title
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        // --- Link UI elements ---
        etWeight = findViewById(R.id.etWeight);
        etGoldPrice = findViewById(R.id.etGoldPrice);
        rgType = findViewById(R.id.rgType);
        rbKeep = findViewById(R.id.rbKeep);
        rbWear = findViewById(R.id.rbWear);
        tvTotalValue = findViewById(R.id.tvTotalValue);
        tvPayableWeight = findViewById(R.id.tvPayableWeight);
        tvZakatAmount = findViewById(R.id.tvZakatAmount);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);

        // --- Calculate button logic ---
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Validate input
                if (TextUtils.isEmpty(etWeight.getText()) ||
                        TextUtils.isEmpty(etGoldPrice.getText()) ||
                        rgType.getCheckedRadioButtonId() == -1) {

                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get user input
                double weight = Double.parseDouble(etWeight.getText().toString());
                double goldPrice = Double.parseDouble(etGoldPrice.getText().toString());

                // Determine uruf based on type
                double uruf = rbKeep.isChecked() ? 85 : 200;

                // Calculate zakat
                double totalValue = weight * goldPrice;
                double payableWeight = weight - uruf;

                if (payableWeight < 0) payableWeight = 0;

                double zakatPayableValue = payableWeight * goldPrice;
                double zakatAmount = zakatPayableValue * 0.025; // 2.5%

                // Display results
                tvTotalValue.setText("Total gold value: RM" + String.format("%.2f", totalValue));
                tvPayableWeight.setText("Zakat payable weight: " + String.format("%.2f", payableWeight) + " grams");
                tvZakatAmount.setText("Zakat amount: RM" + String.format("%.2f", zakatAmount));
            }
        });

        // --- Clear button logic ---
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText("");
                etGoldPrice.setText("");
                rgType.clearCheck();
                tvTotalValue.setText("Total gold value: RM0.00");
                tvPayableWeight.setText("Zakat payable weight: 0 grams");
                tvZakatAmount.setText("Zakat amount: RM0.00");
            }
        });
    }

    // --- Menu button click handling ---
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menuAbout) {
            //Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show();
            //return true;

            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.action_share){
            String url = "https://github.com/yourname/ZakatGoldCalc"; // your app website

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this Zakat Gold Calculator App: " + url);

            startActivity(Intent.createChooser(shareIntent, "Share App"));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // --- Menu creation ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
