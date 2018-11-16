package my.com.trainingtrial.loancalculator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class CalculatorActivity extends AppCompatActivity {
    private EditText etLoanAmount, etDownPayment, etTerm, etAnnualInterestRate;
    private TextView tvMonthlyPayment, tvTotalRepayment, tvTotalInterest,
            tvAverageMonthlyInterest;
    private  SharedPreferences sp;

    private static DecimalFormat df2 = new DecimalFormat(".##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_calculator);
        sp = getSharedPreferences("Calulator", MODE_PRIVATE);
        Float fltAmt = sp.getFloat("Amt", 0);
        Float fltDownPayAmt = sp.getFloat("DownPayAmt", 0);
        Float fltInterestRate = sp.getFloat("InterestRate", 0);
        Integer intterm = sp.getInt("term", 0);
        Float flttltMonthlyPayment = sp.getFloat("tltMonthlyPayment", 0);
        Float flttltRepayment = sp.getFloat("tltRepayment", 0);
        Float flttltInterest = sp.getFloat("tltInterest", 0);
        Float fltaverageInterest = sp.getFloat("averageInterest", 0);

        etLoanAmount = findViewById(R.id.loan_amount);
        etDownPayment = (EditText) findViewById(R.id.down_payment);
        etTerm = (EditText) findViewById(R.id.term);
        etAnnualInterestRate = (EditText) findViewById(R.id.annual_interest_rate);
        tvMonthlyPayment = (TextView) findViewById(R.id.monthly_repayment);
        tvTotalRepayment = (TextView) findViewById(R.id.total_repayment);
        tvTotalInterest = (TextView) findViewById(R.id.total_interest);
        tvAverageMonthlyInterest = (TextView)
                findViewById(R.id.average_monthly_interest);

        etLoanAmount.setText(fltAmt.toString());
        etDownPayment.setText(fltDownPayAmt.toString());
        etTerm.setText(intterm.toString());
        etAnnualInterestRate.setText(fltInterestRate.toString());
        tvMonthlyPayment.setText(flttltMonthlyPayment.toString());
        tvTotalRepayment.setText(flttltRepayment.toString());
        tvTotalInterest.setText(flttltInterest.toString());
        tvAverageMonthlyInterest.setText(fltaverageInterest.toString());


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_calculate:
                calculate();
                break;
            case R.id.button_reset:
                reset();
                break;
        }

        Log.d("Check","Button clicked!");
    }

    private void calculate() {
        // testing
        String amount = etLoanAmount.getText().toString();
        String downPayment = etDownPayment.getText().toString();
        String interestRate = etAnnualInterestRate.getText().toString();
        String term = etTerm.getText().toString();
        double loanAmount = Double.parseDouble(amount) -
                Double.parseDouble(downPayment);
        double interest = Double.parseDouble(interestRate) / 12 / 100;
        double noOfMonth = (Integer.parseInt(term) * 12);
        if (noOfMonth > 0) {
            double monthlyRepayment = loanAmount *
                    (interest+(interest/(java.lang.Math.pow((1+interest),
                            noOfMonth)-1)));
            double totalRepayment = monthlyRepayment * noOfMonth;
            double totalInterest = totalRepayment - loanAmount;
            double monthlyInterest = totalInterest / noOfMonth;
        //    double monthlyRepayment2 = df2.format(totalRepayment);
            //String.format("%.2f", d)
            tvMonthlyPayment.setText(String.format("%.2f",monthlyRepayment));
          //  yourTextView.setText(String.format("Value of a: %.2f", a));
          //  tvMonthlyPayment.setText(df2.format(String.valueOf(monthlyRepayment)));
            tvTotalRepayment.setText(String.format("%.2f",totalRepayment)); //String.format("%.2f",totalRepayment)
            tvTotalInterest.setText(String.format("%.2f",totalInterest)); //String.format("%.2f",totalInterest)
            tvAverageMonthlyInterest.setText(String.format("%.2f",monthlyInterest)); //String.format("%.2f",monthlyInterest)
        }


        sp.edit()
              .putFloat("Amt", Float.parseFloat(amount))
                .putFloat("DownPayAmt", Float.parseFloat(downPayment))
                .putFloat("InterestRate", Float.parseFloat(interestRate))
                .putInt("term", Integer.parseInt(term))
                .putFloat("tltMonthlyPayment", Float.parseFloat(tvMonthlyPayment.getText().toString()))
                .putFloat("tltRepayment", Float.parseFloat(tvTotalRepayment.getText().toString()))
                .putFloat("tltInterest", Float.parseFloat(tvTotalInterest.getText().toString()))
                .putFloat("averageInterest", Float.parseFloat(tvAverageMonthlyInterest.getText().toString()))
        .commit();



    }

    private void reset() {
        etLoanAmount.setText("");
        etDownPayment.setText("");
        etTerm.setText("");
        etAnnualInterestRate.setText("");
        tvMonthlyPayment.setText(R.string.default_result);
        tvTotalRepayment.setText(R.string.default_result);
        tvTotalInterest.setText(R.string.default_result);
        tvAverageMonthlyInterest.setText(R.string.default_result);
    }
}
