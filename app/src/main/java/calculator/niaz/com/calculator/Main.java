package calculator.niaz.com.calculator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mariuszgromada.math.mxparser.Expression;

public class Main extends Activity implements View.OnClickListener{
      Button btn[] = new Button[20];
      TextView textBox;
      Expression e1;
      StringBuilder sb;
      String result;
      SharedPreferences sharedPreferences;
      SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initButtons();
        actionListenerForDigits();

        sharedPreferences = this.getSharedPreferences("history",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public  void onClick(View v)
    {
        String previousText = this.textBox.getText().toString();
        Button btnPressed = (Button) v;
        String btnText = btnPressed.getText().toString();

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

            if(!result.equals("NaN"))
            {
                String hist = previousText + " = " + result + "\n\n";
                appendHistoryData(hist);
                this.textBox.setText(result);
            }

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

        else if(HelperClass.isDigit(btnPressed) == true)
        {
            this.textBox.setText(previousText+btnText);
        }

        //Operations of Operators
        else if(v.getId() == R.id.btnPlus)
        {
            char previousChar = previousText.charAt(previousText.length()-1);
            if(previousChar == '+' || previousChar == '-' || previousChar == '*' || previousChar == '÷')
            {
                StringBuilder sb = new StringBuilder(previousText);
                sb.deleteCharAt(sb.length()-1);
                this.textBox.setText(sb.toString()+'+');
            }
            else
            {
                this.textBox.setText(previousText+'+');
            }
        }
        else if(v.getId() == R.id.btnMinus)
        {
            char previousChar = previousText.charAt(previousText.length()-1);
            if(previousChar == '+' || previousChar == '-' || previousChar == '*' || previousChar == '÷')
            {
                StringBuilder sb = new StringBuilder(previousText);
                sb.deleteCharAt(sb.length()-1);
                this.textBox.setText(sb.toString()+'-');
            }
            else
            {
                this.textBox.setText(previousText+'-');
            }
        }
        else if(v.getId() == R.id.btnMultiply)
        {
            char previousChar = previousText.charAt(previousText.length()-1);
            if(previousChar == '+' || previousChar == '-' || previousChar == '*' || previousChar == '÷')
            {
                StringBuilder sb = new StringBuilder(previousText);
                sb.deleteCharAt(sb.length()-1);
                this.textBox.setText(sb.toString()+"*");
            }
            else
            {
                this.textBox.setText(previousText+'*');
            }
        }
        else if(v.getId() == R.id.btnDivide)
        {
            HelperClass.showToast(this,Character.toString(previousText.charAt(previousText.length()-1)));
            char previousChar = previousText.charAt(previousText.length()-1);
            if(previousChar == '+' || previousChar == '-' || previousChar == '*' || previousChar == '÷')
            {
                StringBuilder sb = new StringBuilder(previousText);
                sb.deleteCharAt(sb.length()-1);
                this.textBox.setText(sb.toString()+'÷');
            }
            else
            {
                this.textBox.setText(previousText+'÷');
            }
        }
        else if(v.getId() == R.id.btnHistory)
        {
            Intent history = new Intent(getBaseContext(),History.class);
            history.putExtra("history",getHistoryData());
//            HelperClass.showToast(this,"HIstory");
            startActivity(history);

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



    //History

    public  boolean setHistoryData(String data)
    {

        editor.putString("hist",data);
        editor.apply();

        return true;
    }
    public  String getHistoryData()
    {
        return sharedPreferences.getString("hist",null);
    }
    public boolean appendHistoryData(String data)
    {
        editor.putString("hist",getHistoryData() == null ? "" : getHistoryData()+data);
        editor.apply();
        return true;
    }


}
