package com.vvdi.foodorder;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.view.Menu;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity
{
    final String SAVED_TEXT = "saved_orders";
    Button btnCreate, btnOrdersCheck, btnClearList;
    ListView listView;
    String TAG = "TAG";
    SocketConecter socketConecter;
    private ArrayList<String> IdsOrders;
    private ArrayList<String> OrderStatus;
    private CustomListViewAdapter<String> adapter;
    public static Set set;
    public static   SharedPreferences sPref;
    public static DBHelper dbHelper;
    public static boolean FlagConnect = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_main);
       sPref = getPreferences(MODE_PRIVATE);

       btnCreate = (Button)findViewById(R.id.create_order);
       btnOrdersCheck = (Button)findViewById(R.id.my_orders);
       listView = (ListView)findViewById(R.id.ordersList);
       btnClearList = (Button)findViewById(R.id.clearOrderList);

       dbHelper = new DBHelper(this);
       StartConnect();
       GetFromDb();

       adapter = new CustomListViewAdapter<String>(this, IdsOrders, OrderStatus);
       listView.setAdapter(adapter);

       if(IdsOrders.size() == 0)    //кнопка статуса не активна
        { btnOrdersCheck.setVisibility(View.INVISIBLE); }

        else //кнопка статуса  активна
        { btnOrdersCheck.setVisibility(View.VISIBLE); }

       btnCreate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplication(), OrderActivity.class);
                startActivity(intent);
            }
        });

       btnOrdersCheck.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {

               v.setActivated(false);
               SendStatusFromBD();
               adapter.getData().clear();
               adapter.getDataStatus().clear();
               adapter.getData().addAll(IdsOrders);
               adapter.getDataStatus().addAll(OrderStatus);
               adapter.notifyDataSetChanged();
               Log.d(TAG, "onClick: "+ IdsOrders.size());
               v.setActivated(true);
           }
       });

       btnClearList.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {
               getApplicationContext().deleteDatabase("myDB");
               adapter.getData().clear();
               adapter.getDataStatus().clear();
               adapter.notifyDataSetChanged();
               Toast toast = Toast.makeText(getApplicationContext(), "Список заказов очищен", Toast.LENGTH_SHORT);
               toast.show();
           }
       });
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetFromDb();
        if(IdsOrders.size() == 0)
        {
            //кнопка статуса не активна
            btnOrdersCheck.setVisibility(View.INVISIBLE);
        }
        else //кнопка статуса  активна
        { btnOrdersCheck.setVisibility(View.VISIBLE); }
    }

    private void SendStatusFromBD() {
        dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<RecordStatus> recordStatuses = new ArrayList<>();

        Cursor c = db.query("ordersTable", null, null, null, null, null, null);
        if (c.moveToFirst()) {

            int idColIndex = c.getColumnIndex("id");
            int OrderId = c.getColumnIndex("orderId");
            int StatusOrder = c.getColumnIndex("status");

            do {
                RecordStatus recordStatus = new RecordStatus();
                recordStatus.setOrderId(c.getString(OrderId));
                recordStatus.setStatus(c.getString(StatusOrder));
                recordStatuses.add(recordStatus);
            }while (c.moveToNext());

        }
        Gson gson = new Gson();
        String a = gson.toJson(recordStatuses);
        SocketConecter.SendData(a);

        dbHelper.close();
    }


    private void GetFromDb() {
        try {
            dbHelper = new DBHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //db.update("ordersTable", cv, "orderId = ?", new String[]{ c.getString(OrderId)  } );
            ArrayList<RecordStatus> recordStatuses = new ArrayList<>();

            Cursor c = db.query("ordersTable", null, null, null, null, null, null);

            IdsOrders = new ArrayList<>();
            OrderStatus = new ArrayList<>();
            if (c.moveToFirst()) {
                int idColIndex = c.getColumnIndex("id");
                int OrderId = c.getColumnIndex("orderId");
                int StatusOrder = c.getColumnIndex("status");

                do {
                    IdsOrders.add(c.getString(OrderId));
                    if (c.getString(StatusOrder) != null)
                        OrderStatus.add(c.getString(StatusOrder));
                    else {
                        OrderStatus.add(" ");
                    }
                }
                while (c.moveToNext());
            }
            dbHelper.close();
        }
        catch (Exception e) {
            IdsOrders.clear();
            Log.d(TAG, "GetFromDb: " + e.getMessage());
        }
    }

    private void StartConnect() {
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                socketConecter = new SocketConecter(new SocketConecter.OnReceiveData() {
                    @Override
                    public void RecvData(String text) {
                        Log.d(TAG, "RecvData: !!!!!!"+ text);
                        Gson js = new Gson();
                        try {
                            Type listType = new TypeToken<List<RecordStatus>>() {
                            }.getType();
                            ArrayList<RecordStatus> recordStatuses = js.fromJson(text, listType);

                            dbHelper = new DBHelper(getApplicationContext());
                            SQLiteDatabase db = dbHelper.getWritableDatabase();

                            ContentValues contentValues = new ContentValues();

                            for (RecordStatus re : recordStatuses) {
                                contentValues.put("orderId", re.getOrderId());
                                contentValues.put("status", re.getStatus());
                                db.update("ordersTable", contentValues, "orderId = ?", new String[]{re.getOrderId()});
                            }
                            dbHelper.close();
                            GetFromDb();
                            adapter.getData().clear();
                            adapter.getDataStatus().clear();
                            adapter.getData().addAll(IdsOrders);
                            adapter.getDataStatus().addAll(OrderStatus);
                            adapter.notifyDataSetChanged();
                        }
                        catch (Exception e){
                        }
                    }

                    @Override
                    public void Connected() {
                        SendStatusFromBD();
                        SocketConecter.Recv();
                    }
                });

                while (FlagConnect) {
                    socketConecter.ConnectCheck();
                    try { Thread.sleep(3000); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
            }
        };
        Thread tr = new Thread(runnable);
        tr.start();
    }

    @Override //Меню приложения
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override //Обработка кнопок меню
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch(id) {
            case R.id.action_settings :
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.prjInfo:
                intent = new Intent(this, AboutProject.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() { super.onDestroy(); }
}