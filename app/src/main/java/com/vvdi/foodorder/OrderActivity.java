package com.vvdi.foodorder;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Random;

import static com.vvdi.foodorder.MainActivity.dbHelper;


public class OrderActivity extends AppCompatActivity
{
    String TAG = "TAG";
    private int positionOfType;     //Позиция блюда в списке доступных спинера
    final String SAVED_TEXT = "saved_orders";
    RadioGroup radioGroup;
    Spinner spinner, spinnerCountHam;
    Spinner spinnerOrders;
    private  Pizza pizza;
    private Hamburger hamburger;
    LinearLayout pizzaLaySmall, hamLaySmall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Новый заказ");
        setContentView(R.layout.activity_order);

        //Кнопка "Назад"
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        radioGroup = (RadioGroup) findViewById(R.id.radioGr);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerCountHam = (Spinner) findViewById(R.id.spinnerHamburger);

        pizzaLaySmall = (LinearLayout)findViewById(R.id.pizzaLaySmall);
        hamLaySmall = (LinearLayout)findViewById(R.id.hamLaySmall);

        pizza = new Pizza();
        hamburger = new Hamburger();

        pizza.set_diametr(25);
        spinnerOrders = (Spinner)findViewById(R.id.spinner2) ;
        spinnerOrders.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionOfType = position;
                switch (positionOfType) {
                    case 0:
                        pizzaLaySmall.setVisibility(View.VISIBLE);
                        hamLaySmall.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        pizzaLaySmall.setVisibility(View.INVISIBLE);
                        hamLaySmall.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId) {
                    case -1:
                        break;
                    case R.id.radioButton25:
                        pizza.set_diametr(25);
                        break;
                    case R.id.radioButton35:
                        pizza.set_diametr(35);
                        break;
                    case R.id.radioButton45:
                        pizza.set_diametr(45);
                        break;
                    default:
                        break;
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pizza.set_count(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });

        spinnerCountHam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hamburger.set_count(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });
    }

    String GenerateId(String type) //Генератор id заказа
    {
        String  orderId = "";
        Random random = new Random();
        orderId = String.valueOf(Math.abs(random.nextInt(9000)));
        return type + orderId;
    }

    @Override   //Возврат к предыдущему экрану приложения
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void SaveOrderOnPhone(String id) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put("orderId", id);
        cv.put("status", "Создан");
        db.insert("ordersTable", null, cv);

        dbHelper.close();
}

    public void SendOrder (View v) {
        int SHORT_DELAY = 2000; // 2 seconds

        switch (positionOfType) {
            case 0:
            ArrayList<Ingridients> ingredients = new ArrayList<>();
            Ingridients ingredient = new Ingridients();
            ScrollView scrollView = (ScrollView) findViewById(R.id.scrollIngr);
            LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                LinearLayout linearLayout1 = (LinearLayout) linearLayout.getChildAt(i);
                CheckBox checkBox1 = (CheckBox) linearLayout1.getChildAt(2);

                if (checkBox1.isChecked()) {
                    switch (checkBox1.getId()) {
                        case R.id.checkBoxSauce:
                            ingredient.set_sause(true);
                            break;
                        case R.id.checkBoxCheese:
                            ingredient.set_cheese(true);
                            break;
                        case R.id.checkBoxOnion:
                            ingredient.set_onion(true);
                            break;
                        case R.id.checkBoxPepper:
                            ingredient.set_pepper(true);
                            break;
                        case R.id.checkBoxSausage:
                            ingredient.set_sausage(true);
                            break;
                        case R.id.checkBoxMushrooms:
                            ingredient.set_mushrooms(true);
                            break;
                        case R.id.checkBoxOlive:
                            ingredient.set_olive(true);
                            break;
                        case R.id.checkBoxPipperony:
                            ingredient.set_pipperony(true);
                            break;
                        case R.id.checkBoxChicken:
                            ingredient.set_chiken(true);
                            break;
                        case R.id.checkBoxMozzarella:
                            ingredient.set_mozzarella(true);
                            break;
                        case R.id.checkBoxGreens:
                            ingredient.set_greens(true);
                            break;
                        case R.id.checkBoxHam:
                            ingredient.set_ham(true);
                            break;
                    }
                }
            }
            pizza.setIngridients(ingredient);
            break;
                case 1:
                ArrayList<IngridientsH> ingridientsH = new ArrayList<>();
                IngridientsH ingrh = new IngridientsH();
                ScrollView scrollView1 = (ScrollView) findViewById(R.id.scrollHamIngr);
                LinearLayout linearLayout1 = (LinearLayout) scrollView1.getChildAt(0);

                for (int i = 0; i < linearLayout1.getChildCount(); i++) {
                    LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(i);
                    CheckBox checkBox1 = (CheckBox) linearLayout2.getChildAt(2);

                    if (checkBox1.isChecked()) {
                        switch (checkBox1.getId()) {
                            case R.id.checkBoxSauce:
                                ingrh.set_sause(true);
                                break;
                            case R.id.checkBoxBeef:
                                ingrh.set_beefPatt(true);
                                break;
                            case R.id.checkBoxChicken:
                                ingrh.set_chickenPatt(true);
                                break;
                            case R.id.checkBoxMayo:
                                ingrh.set_mayo(true);
                                break;
                            case R.id.checkBoxCheeseH:
                                ingrh.set_cheeseH(true);
                                break;
                            case R.id.checkBoxTomato:
                                ingrh.set_tomatoes(true);
                                break;
                            case R.id.checkBoxCucumbers:
                                ingrh.set_picCucumbers(true);
                                break;
                            case R.id.checkBoxSalad:
                                ingrh.set_salad(true);
                                break;
                            case R.id.checkBoxLightBr:
                                ingrh.set_lightBread(true);
                                break;
                            case R.id.checkBoxDarkBr:
                                ingrh.set_darkBread(true);
                                break;
                            case R.id.checkBoxOnion:
                                ingrh.set_onion(true);
                                break;
                        }
                    }
                }
                hamburger.setIngridientsH(ingrh);
                break;
        }
        Gson gson = new Gson();

        String type = "pz";
        String id ="";
        String jsonString="";
        switch (positionOfType) {   //Тип заказа в зависимости от выбора
            case 0:
                type = "pz";
                id = GenerateId(type);
                pizza.set_id(id);
                jsonString = gson.toJson(pizza);
                break;

            case 1:
                type = "bg";
                id = GenerateId(type);
                hamburger.set_id(id);
                jsonString = gson.toJson(hamburger);
                break;
        }

        SaveOrderOnPhone(id);
        Log.d(TAG, "*** ЗАКАЗ ***: " + jsonString);
        SocketConecter.SendData(jsonString);
        Toast toast = Toast.makeText(getApplicationContext(), "Заказ создан", Toast.LENGTH_SHORT);
        toast.show();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketConecter.Close();
    }
}