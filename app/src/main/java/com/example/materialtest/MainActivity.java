package com.example.materialtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MaterialTest";
    DrawerLayout mDrawerLayout;

    private Fruit[] fruits={new Fruit("Apple",R.drawable.apple),
            new Fruit("Banana",R.drawable.banana),
            new Fruit("Orange",R.drawable.orange),
            new Fruit("Watermelon",R.drawable.watermelon),
            new Fruit("Pear",R.drawable.pear),
            new Fruit("Grape",R.drawable.grape),
            new Fruit("Pineapple",R.drawable.pineapple),
            new Fruit("Strawberry",R.drawable.strawberry),
            new Fruit("Cherry",R.drawable.cherry),
            new Fruit("Mango",R.drawable.mango)};
    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.level = LogUtil.INFO;
        LogUtil.i(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();//ActionBar实际由toolBar来完成
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);//显示导航按钮
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);//设置导航按钮图标
        }

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        //navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_call:{
                        mDrawerLayout.closeDrawers();
                        break;
                    }
                    default:
                        break;
                }

                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"fab",Toast.LENGTH_SHORT).show();

                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_INDEFINITE)
                        .setAction("NO",new View.OnClickListener(){
                            @Override
                            public void onClick(View v){
                                Toast.makeText(MainActivity.this,"cancel",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        initFruits();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                refreshFruits();
            }
        });
    }

    public void initFruits(){
        fruitList.clear();
        for(int i=0;i<50;i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    public void refreshFruits(){
        new Thread(new Runnable() {
            @Override
            public void run() {
            /*    try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            */
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            }
            case R.id.backup:{
                Toast.makeText(this,"Backup",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.delete:{
                Toast.makeText(this,"Delete",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.settings:{
                Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onStart(){
        LogUtil.i(TAG,"onStart");
        super.onStart();
    }

    @Override
    protected void onResume(){
        LogUtil.i(TAG,"onResume");
        super.onResume();
    }

    @Override
    protected void onPause(){
        LogUtil.i(TAG,"onPause");
        super.onPause();
    }

    @Override
    protected void onStop(){
        LogUtil.i(TAG,"onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        LogUtil.i(TAG,"onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        LogUtil.i(TAG,"onRestart");
        super.onRestart();
    }
}
