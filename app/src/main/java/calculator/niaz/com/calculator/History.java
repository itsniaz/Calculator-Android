package calculator.niaz.com.calculator;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class History extends Activity implements View.OnClickListener
{
    TextView ht ;
    Button clearBtn;
    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setTitle("History");


        ht = (TextView) findViewById(R.id.textViewHist);
        clearBtn = (Button) findViewById(R.id.btnClear);
        clearBtn.setOnClickListener(this);
        showPrefData();
    }

    public  void  showPrefData()
    {
      ht.setText(getFileData());

    }

    @Override
    public  void onClick(View v)
    {


        try
        {
            File dir = getFilesDir();
            File file = new File(dir, "history.txt");
            if(file.delete())
            {
                HelperClass.showToast(this,"History Cleared !");
            }
        }

        catch (Exception e)
        {

        }

        showPrefData();

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

}
