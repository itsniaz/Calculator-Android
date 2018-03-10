package calculator.niaz.com.calculator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class History extends AppCompatActivity {
    TextView ht ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ht = (TextView) findViewById(R.id.editText3);


//        ht.setText("Test String\n Another String \n Third String");
        showData();
    }

    public  void  showData()
    {

        ht.setText(getIntent().getStringExtra("history"));

    }
}
