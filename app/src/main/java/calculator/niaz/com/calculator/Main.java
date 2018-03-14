package calculator.niaz.com.calculator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.Expression;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main extends Activity implements View.OnClickListener{
      Button btn[] = new Button[23];
      TextView textBox;
      Expression e1;
      StringBuilder sb;
      String result;
      SharedPreferences sharedPreferences;
      SharedPreferences.Editor editor;
    static final int READ_BLOCK_SIZE = 100;
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
        int previousTextLength;
        if(v.getId() == R.id.btnEqual)
        {
            try
            {
                previousTextLength = previousText.length()-1;
                if(previousText == "")
                {

                }
                else if(previousText.charAt(previousTextLength) == '.')
                {
                    previousText = previousText+'0';

                }
                else if(previousText.equals(".") || previousText.equals("0") || previousText.equals("-"))
                {

                }
            }
            catch (Exception e)
            {

            }



            try
            {
                result = getExpressionResultMx(previousText);
            }

            catch (Exception e)
            {

            }

            if(!result.equals("NaN"))
            {
                String hist = previousText + " = " + result + "\n\n";
                //Saving to prefrence
//                appendHistoryData(hist);
                this.textBox.setText(result);

                //Saving to Internal Storage
                appendFileData(hist);
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
            try{
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
            catch(Exception e)
            {

            }
        }
        else if(v.getId() == R.id.btnMinus)
        {
            try{
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
            catch(Exception e)
            {
                this.textBox.setText(previousText+'-');
            }
        }
        else if(v.getId() == R.id.btnMultiply)
        {
            try{
            char previousChar = previousText.charAt(previousText.length()-1);
            if(previousChar == '+' || previousChar == '-' || previousChar == '*' || previousChar == '÷')
            {
                StringBuilder sb = new StringBuilder(previousText);
                sb.deleteCharAt(sb.length()-1);
                this.textBox.setText(sb.toString()+'*');
            }
            else
            {
                this.textBox.setText(previousText+'*');
            }
          }
            catch(Exception e)
            {

            }
        }
        else if(v.getId() == R.id.btnDivide)
        {
            try{
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
            catch(Exception e)
            {

            }
        }
        else if(v.getId() == R.id.btnHistory)
        {
            Intent history = new Intent(getBaseContext(),History.class);
            startActivity(history);

        }

        else if(v.getId() == R.id.btnAC)
        {
            this.textBox.setText("");
        }

        else if(v.getId() == R.id.btnMC)
        {
            setMemory(null);
        }

        else if(v.getId() == R.id.btnMR)
        {
            if(!(getMemory() == null))
            {
                textBox.setText(previousText+getMemory());
            }
        }

        else if(v.getId() == R.id.btnMemPlus)
        {
            try
            {
                if(getMemory() == null)
                {

                    if(!HelperClass.containsOperator(previousText))
                    {
                        setMemory(previousText);
                    }
                }
                else
                {
                    String mem = getExpressionResultMx(getMemory() +"+"+ previousText);
                    setMemory(mem);
                }
            }
            catch (Exception e)
            {

            }
        }
        else if(v.getId() == R.id.btnMemMinus)
        {
            try
            {
                if(getMemory().equals("0"))
                {
                    if(!HelperClass.containsOperator(previousText))
                    {
                        setMemory(previousText);
                    }
                }
                else
                {
                    String mem = getExpressionResultMx(getMemory() +"-"+previousText);
                    setMemory(mem);
                }
            }
            catch (Exception e)
            {

            }
        }
    }

    public  void initButtons()
    {
        textBox = findViewById(R.id.textBox);
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


        btn[10] = findViewById(R.id.btnEqual);
        btn[11] = findViewById(R.id.btnDot);

        btn[12] = findViewById(R.id.btnPlus);
        btn[13] = findViewById(R.id.btnMinus);
        btn[14] = findViewById(R.id.btnMultiply);
        btn[15] = findViewById(R.id.btnDivide);

        btn[16] = findViewById(R.id.btnMemPlus);
        btn[17] = findViewById(R.id.btnMemMinus);
        btn[18] = findViewById(R.id.btnMR);
        btn[19] = findViewById(R.id.btnMC);
        btn[20] = findViewById(R.id.btnDel);
        btn[21] = findViewById(R.id.btnHistory);
        btn[22] = findViewById(R.id.btnAC);
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

        String result = String.valueOf(expressionResult);
        return result.equals("NaN")?expr:result;
    }



    //History

    private   boolean setMemory(String data)
    {

        editor.putString("hist",data);
        editor.commit();

        return true;
    }
    public  String getMemory()
    {
        return sharedPreferences.getString("hist", null);
    }
    public boolean appendHistoryData(String data)
    {
        editor.putString("hist",getMemory() == null ? "0" : getMemory()+data);
        editor.commit();
        return true;
    }

    //

    private  void setFileData(String text)
    {

        try {
            FileOutputStream fileout = openFileOutput("history.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(text);
            outputWriter.close();

        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
        }


    }

    private  String getFileData()
    {
        String s="";
        try {
            FileInputStream fileIn=openFileInput("history.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();



        } catch (Exception e) {

        }

        return  s;
    }

    private void appendFileData(String text)
    {
        setFileData(getFileData()+text);
    }
}
