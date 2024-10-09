
package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * the main class
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvAnswer;
    TextView tvExpression;

    MaterialButton btnC;
    MaterialButton btnOpen;
    MaterialButton btnClose;
    MaterialButton btnDivide;
    MaterialButton btnMultiply;
    MaterialButton btnAdd;
    MaterialButton btnSubtract;
    MaterialButton btnEquals;
    MaterialButton btnAc;
    MaterialButton btnDot;
    MaterialButton btn0;
    MaterialButton btn1;
    MaterialButton btn2;
    MaterialButton btn3;
    MaterialButton btn4;
    MaterialButton btn5;
    MaterialButton btn6;
    MaterialButton btn7;
    MaterialButton btn8;
    MaterialButton btn9;

    boolean firstInput = true;


    /**
     * initialization function
     * @param savedInstanceState default parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvAnswer = findViewById(R.id.tv_answer);
        tvExpression = findViewById(R.id.tv_expression);

        // default text
        tvAnswer.setText("ID: 49976313");
        tvExpression.setText("MCDONALD");

        // assigning all the buttons to the same function
        assignID(btnC, R.id.btn_c);
        assignID(btnOpen, R.id.btn_open);
        assignID(btnClose, R.id.btn_close);
        assignID(btnDivide, R.id.btn_divide);
        assignID(btnMultiply, R.id.btn_multiply);
        assignID(btnAdd, R.id.btn_add);
        assignID(btnSubtract, R.id.btn_subtract);
        assignID(btnEquals, R.id.btn_equals);
        assignID(btnAc, R.id.btn_ac);
        assignID(btnDot, R.id.btn_dot);
        assignID(btn0, R.id.btn_0);
        assignID(btn1, R.id.btn_1);
        assignID(btn2, R.id.btn_2);
        assignID(btn3, R.id.btn_3);
        assignID(btn4, R.id.btn_4);
        assignID(btn5, R.id.btn_5);
        assignID(btn6, R.id.btn_6);
        assignID(btn7, R.id.btn_7);
        assignID(btn8, R.id.btn_8);
        assignID(btn9, R.id.btn_9);
    }


    /**
     * assigns a button to the onClick function
     * @param btn the button being assigned to onClick
     * @param id the id of the button
     */
    void assignID(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }


    /**
     * the function ran when any button is pressed
     * @param view the button being pressed
     */
    @Override
    public void onClick(View view) {

        // this gets rid of the default text
        if (firstInput) {
            tvExpression.setText("");
            tvAnswer.setText("0.0");

            firstInput = false;
        }

        MaterialButton btn = (MaterialButton)view;
        String btnText = btn.getText().toString();
        String expressionString = tvExpression.getText().toString();

        if (btnText.equals("AC")) {
            tvExpression.setText("");
            tvAnswer.setText("0.0");

            return;
        }
        if (btnText.equals("=")) {
            tvExpression.setText(tvAnswer.getText());

            return;
        }
        if (btnText.equals("C")) {
            expressionString = expressionString.substring(0, expressionString.length() - 1);
            tvExpression.setText(expressionString);

            return;
        }

        expressionString += btnText;
        tvExpression.setText(expressionString);

        String result = getResult(expressionString);

        if (!result.equals("INVALID")) {
            tvAnswer.setText(result);
        }
    }


    /**
     * evaluates a math expression passed as a string and returns the result as a string
     * @param expressionString the string being evaluated
     * @return the result of the math expression
     */
    String getResult(String expressionString) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();

            return context.evaluateString(scriptable, expressionString, "Javascript", 1, null).toString();

        } catch (Exception e) {
            return "INVALID";
        }
    }
}
