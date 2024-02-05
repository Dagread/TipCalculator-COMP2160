package daniil.grydin.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText etBaseAmount;
    private SeekBar seekBarTip;
    private TextView tvTipPercentage;
    private TextView tvTipAmount;
    private TextView tvTotalAmount;
    private TextView tvTipQuality;

    private double baseAmount = 0.0;
    private double tipPercentage = 15.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBaseAmount = findViewById(R.id.etBaseAmount);
        seekBarTip = findViewById(R.id.seekBarTip);
        tvTipPercentage = findViewById(R.id.tvTipPercentage);
        tvTipAmount = findViewById(R.id.tvTipAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvTipQuality = findViewById(R.id.tvTipQuality);

        // Set default values
        tvTipPercentage.setText(String.format("%.0f%%", tipPercentage));

        etBaseAmount.addTextChangedListener(baseAmountTextWatcher);

        seekBarTip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipPercentage = progress;
                tvTipPercentage.setText(String.format("%.0f%%", tipPercentage));
                calculateTip();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private TextWatcher baseAmountTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                baseAmount = Double.parseDouble(s.toString());
            } catch (NumberFormatException e) {
                baseAmount = 0.0;
            }
            calculateTip();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private void calculateTip() {
        double tipAmount = baseAmount * (tipPercentage / 100);
        double totalAmount = baseAmount + tipAmount;

        DecimalFormat df = new DecimalFormat("#0.00");
        String formattedTipAmount = "$" + df.format(tipAmount);
        String formattedTotalAmount = "$" + df.format(totalAmount);

        tvTipAmount.setText("Tip Amount: " + formattedTipAmount);
        tvTotalAmount.setText("Total Payment: " + formattedTotalAmount);

        // Set tip quality message based on tip percentage
        if (tipPercentage < 10) {
            tvTipQuality.setText("poor");
            tvTipQuality.setTextColor(0XFFFD151B);
        } else if (tipPercentage >= 10 && tipPercentage <= 15) {
            tvTipQuality.setText("acceptable");
            tvTipQuality.setTextColor(0XFFFFB30F);
        } else if (tipPercentage > 15 && tipPercentage <= 20) {
            tvTipQuality.setText("good");
            tvTipQuality.setTextColor(0XFF849324);
        } else {
            tvTipQuality.setText("amazing");
            tvTipQuality.setTextColor(0XFF01295F);
        }
    }
}