package calculator.niaz.com.calculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import org.mariuszgromada.math.mxparser.*;

public class Main extends AppCompatActivity implements View.OnClickListener{
      Button btn[] = new Button[20];
      TextView textBox;
      Expression e1;
      StringBuilder sb;
      String result;
      Context ct = getBaseContext();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initButtons();
        actionListenerForDigits();
    }

    @Override
    public  void onClick(View v)
    {
        String previousText = this.textBox.getText().toString();
        Button btnPressed = (Button) v;

        if(v.getId() == R.id.btnEqual)
        {
            if(previousText == "")
            {

            }
            else if(previousText.charAt(previousText.length()-1) == '.')
            {
                previousText = previousText+'0';
//                HelperClass.showToast(this,previousText);
            }
            else if(previousText.equals(".") || previousText.equals("0") || previousText.equals("-"))
            {

            }
            result = getExpressionResultMx(previousText);

            this.textBox.setText(result);
        }
        else if(v.getId() == R.id.btnDot)
        {
            if(HelperClass.isDecimalValid(previousText))
            {
                this.textBox.setText(previousText+'.');
            }
        }
        else if(v.getId() == R.id.btnDel)
        {

            if(!previousText.equals(""))
            {
                sb = new StringBuilder(previousText);
                sb.deleteCharAt(sb.length()-1);
                this.textBox.setText(sb.toString());
            }
        }
        else

        {
            this.textBox.setText(previousText+btnPressed.getText());

        }
    }

    public  void initButtons()
    {

        btn[0] = findViewById(R.id.btn0);
        btn[1] = findViewById(R.id.btn1);
        btn[2] = findViewById(R.id.btn2);
        btn[3] = findViewById(R.id.btn3);
        btn[4] = findViewById(R.id.btn4);
        btn[5] = findViewById(R.id.btn5);
        btn[6] = findViewById(R.id.btn6);
        btn[7] = findViewById(R.id.btn7);
        btn[8] = findViewById(R.id.btn8);
        btn[9] = findViewById(R.id.btn9);
        textBox = findViewById(R.id.textBox);

        btn[10] = findViewById(R.id.btnEqual);
        btn[11] = findViewById(R.id.btnDot);

        btn[12] = findViewById(R.id.btnPlus);
        btn[13] = findViewById(R.id.btnMinus);
        btn[14] = findViewById(R.id.btnMultiply);
        btn[15] = findViewById(R.id.btnDivide);

        btn[16] = findViewById(R.id.btnMPlus);
        btn[17] = findViewById(R.id.btnMinus);
        btn[18] = findViewById(R.id.btnM);
        btn[19] = findViewById(R.id.btnDel);
    }

    public  void actionListenerForDigits()
    {

        for (Button myBtn:btn) {
            myBtn.setOnClickListener(this);
        }

    }

    public String getExpressionResultMx(String expr)
    {
        e1 = HelperClass.expressionBuilder(expr);
        double expressionResult = e1.calculate();

//        if(Math.floor(expressionResult) == expressionResult)
//        {
//            long d = (long) expressionResult;
//            return String.valueOf(d);
//        }

        return String.valueOf(expressionResult);
    }


}
