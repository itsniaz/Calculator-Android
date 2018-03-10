package calculator.niaz.com.calculator;

import android.content.Context;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.Expression;

/**
 * Created by Niaz on 3/9/2018.
 */

public class HelperClass {
    public static int countOperator(String expr)
    {
        int count = 0;
        for (int i=0; i < expr.length(); i++)
        {
            char currentChar = expr.charAt(i);
            if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/' )
            {
                count++;
            }
        }
        return count;
    }
    public  static  int countDecimal(String expr)
    {
        int count = 0;
        for (int i=0; i < expr.length(); i++)
        {
            char currentChar = expr.charAt(i);
            if (currentChar == '.')
            {
                count++;
            }
        }
        return count;
    }

    public static int maxDecimalToBeAllowed(String expr)
    {
        return countOperator(expr)+1;
    }

    public static boolean isDecimalValid(String expr)
    {
        boolean isValid = false;
        if(countOperator(expr) == 0)
        {
            if(expr.contains(".")){
                isValid = false;
            }
            else
            {
                isValid = true;
            }
        }

        if(countDecimal(expr) < maxDecimalToBeAllowed(expr))
        {
            isValid =  true;
        }
//        if(expr.charAt(expr.length()-1) == '.')
//        {
//            isValid = false;
//        }
//        if(expr.equals(""))
//        {
//            isValid=true;
//        }
//        else
//        {
//            return  false;
//        }
        return isValid;
    }

    public static void showToast(Context ct, String text)
    {
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(ct, text, duration);
        toast.show();
    }

    public  static Expression expressionBuilder(String raw)
    {

//        String textBoxExpression = this.textBox.getText().toString();
         raw = raw.replace("÷","/");
        raw = raw.replace("x","*");


        return new Expression(raw);
    }

}
