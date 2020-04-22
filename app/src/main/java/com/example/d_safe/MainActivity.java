package com.example.d_safe;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.d_safe.ui.GetDataService;
import com.example.d_safe.ui.home.Invitation;
import com.example.d_safe.ui.restAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    final Context context = this;
    Boolean isContacted = false;

    public static int REQUEST_BLUETOOTH = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void checkForCheckin(String msg) {
        if(msg == "Don's Marriage") {
            showConfirmAlertForChecin("Do you want to check-in this event?");
        }else {
            showAlert("Check-in will be available only 30 minutes prior to the event. Please try again later.");
        }
    }

    public void showAlert(String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Alert");

        // set dialog message
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void showConfirmAlertForChecin(String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Alert");

        // set dialog message
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
//                        final Handler handler = new Handler();
//                        final int delay = 12000; //milliseconds
                        setBluetoothNetwork();
//                        handler.postDelayed(new Runnable(){
//                            public void run(){
//                                //do something
//                                setBluetoothNetwork();
//                                handler.postDelayed(this, delay);
//                            }
//                        }, delay);

                        dialog.cancel();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
//                        setBluetoothNetwork();
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void showCheckAlert(String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Alert");

        // set dialog message
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                        setBluetoothNetwork();

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

    }

    public void setBluetoothNetwork() {
        int permission1 = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH);
        int permission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN);
        int permission3 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permission4 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission1 != PackageManager.PERMISSION_GRANTED
                || permission2 != PackageManager.PERMISSION_GRANTED
                || permission3 != PackageManager.PERMISSION_GRANTED
                || permission4 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    642);
        }
        final BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
        BTAdapter.setName("esafe");
        if (!BTAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }

        final BroadcastReceiver bReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                    int bDistance = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                    //showAlert("Device Found" + name + bDistance);
                    //showAlert(name);
                    if(name != null && !name.isEmpty() && name.equals("esafe")) {
                        if(bDistance > -75) {
                            if(!isContacted){
                                sendPost();
                            }
                            isContacted = true;
//                            sendInteraction();
                            String sdMessage = "You are not following the social distancing rule. Kindly keep 2 meters distance from others.";
                            //showAlert("distance:" + calculateDistance(23, bDistance)+"," + name + "  RSSI: " + bDistance + "dBm                      " + sdMessage);
                            showAlert(sdMessage);
                        }
                    }

                }
            }
        };

        if (!BTAdapter.isEnabled()){
            BTAdapter.enable();
        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(bReceiver, filter);
        BTAdapter.startDiscovery();
        final Handler handler = new Handler();
        final int delay = 8000; //milliseconds
        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                BTAdapter.startDiscovery();
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void sendPost() {

        GetDataService service = restAPI.getRetrofitInstance().create(GetDataService.class);
        String userName = getIntent().getStringExtra("userName");
        // RxJava
        service.savePost(10008, userName, userName+"-esaf").enqueue(new Callback<Integer>() {

            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
            }
        });
    }

}
