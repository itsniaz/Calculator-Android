package calculator.niaz.com.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class History extends Activity
{
    TextView ht ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ht = (TextView) findViewById(R.id.textViewHist);

        showData();
    }

    public  void  showData()
    {

        ht.setText(getIntent().getStringExtra("history"));

    }
}
