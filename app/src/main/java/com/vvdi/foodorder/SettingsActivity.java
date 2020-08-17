package com.vvdi.foodorder;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.vvdi.foodorder.MainActivity.sPref;

public class SettingsActivity extends AppCompatActivity
{
    EditText et1, et2, et3, et4, etp;
    private String ip_address;
    int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Настройки");
        setContentView(R.layout.activity_settings);

        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        et4 = (EditText) findViewById(R.id.editText4);

        etp = (EditText) findViewById(R.id.editTextPort);

        //Кнопка "Назад"
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    public boolean IpCheck() {
        String okt1 = et1.getText().toString();
        String okt2 = et2.getText().toString();
        String okt3 = et3.getText().toString();
        String okt4 = et4.getText().toString();

        try {
            port = Integer.valueOf(etp.getText().toString());
        }
        catch (Exception e) {
            return false;
        }

        if (Integer.valueOf(okt1)>255 || Integer.valueOf(okt2)>255 || Integer.valueOf(okt3)>255 || Integer.valueOf(okt4)>255 || port>65355) {
            return false;
        }

        ip_address = okt1+"."+okt2+"."+okt3+"."+okt4;
        return true;
    }

    @Override   //Возврат к предыдущему экрану приложения
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    public void Set(View view) {
        if (view.getId() == R.id.buttonSet) {
            if (IpCheck()) {
                if(ip_address!=null&port!=0) {
                    SharedPreferences.Editor sE = sPref.edit();
                    sE.putString("ip_address", ip_address);
                    sE.putInt("port", port);
                    sE.apply();

                    Toast toast = Toast.makeText(getApplicationContext(), "Сохранено", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Не корректные данные", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "Не корректные данные", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}



