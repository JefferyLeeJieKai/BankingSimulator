package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.CreateClass;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.DeleteClass;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.EditClass;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewClass;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewStudent;
import com.jefferystudio.bankingsimulator.BankerManageAccount.account_create_ah1;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.RetriveBankerListAsync;
import com.jefferystudio.bankingsimulator.DepositPackage.DepositBanker;
import com.jefferystudio.bankingsimulator.ProfilePageBankerFragment;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.ViewTransactionsPackage.ViewTransactionsBanker;
import com.jefferystudio.bankingsimulator.profilepage;

import java.util.ArrayList;

public class HomeScreenBanker extends AppCompatActivity {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Fragment fragment;
    private ArrayList<String> bankerList;
    private ImageButton buttontransfer;
    private Bundle args;
    private String userID;
    private String currentBalance;
    private ImageButton btnprofile;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_banker);
        args = getIntent().getExtras();
        args.putString("accountType", "Banker");
        userID = args.getString("userID");
        currentBalance = args.getString("currentBalance");

        bankerList = new ArrayList<>();
        new RetriveBankerListAsync(this).execute(userID);
        args.putStringArrayList("BankerList", bankerList);

        Toolbar homeScreenToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(homeScreenToolbar);
        getSupportActionBar().setTitle("KidzSmart Bank");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        fragment = new HomeFragmentBanker();
        fragment.setArguments(args);
        FragmentTransaction homeTrans = getSupportFragmentManager().beginTransaction();
        homeTrans.replace(R.id.frame_layout, fragment);
        homeTrans.commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                fragment = null;

                if(item.getItemId() == R.id.home) {

                    fragment = new HomeFragmentBanker();
                    fragment.setArguments(args);
                }
                else if(item.getItemId() == R.id.deposit) {

                    fragment = new DepositBanker();
                    fragment.setArguments(args);
                }
                else if(item.getItemId() == R.id.transaction) {

                    fragment = new ViewTransactionsBanker();
                    fragment.setArguments(args);
                }
                else if(item.getItemId() == R.id.createclass) {

                    fragment = new CreateClass();
                    fragment.setArguments(args);
                }
                else if(item.getItemId() == R.id.editclass) {

                    fragment = new ViewClass();
                    fragment.setArguments(args);
                }
                else if(item.getItemId() == R.id.deleteclass) {

                    fragment = new DeleteClass();
                    fragment.setArguments(args);
                }
                else if(item.getItemId() == R.id.viewclass) {

                    fragment = new ViewStudent();
                    fragment.setArguments(args);
                }
                else if(item.getItemId() == R.id.changePassword) {

                    fragment = new ChangePasswordBanker();
                    fragment.setArguments(args);
                }
                else if(item.getItemId() == R.id.createAHAccount) {

                    fragment = new account_create_ah1();
                    fragment.setArguments(args);
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();
                drawer.closeDrawer(Gravity.START);

                return true;
            }
        });

        btnprofile = (ImageButton) navigationView.getHeaderView(0).findViewById(R.id.profileBtn);

        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new HomeFragmentBanker();
                fragment.setArguments(args);
                FragmentTransaction homeTrans = getSupportFragmentManager().beginTransaction();
                homeTrans.replace(R.id.frame_layout, fragment);
                homeTrans.commit();
                Intent intent = new Intent(getApplicationContext(), ProfilePageBankerFragment.class);
                startActivity(intent);
                drawer.closeDrawer(Gravity.START);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.onOptionsItemSelected(item);
            return true;
        }
        else if(item.getItemId() == R.id.logout) {

            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout() {

        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("DigiBank Alert");
        builder.setMessage("Do you want to exit the application?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                System.exit(0);
            }

        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });

        AlertDialog quitDialog = builder.create();
        quitDialog.show();
    }

    public void updateBankerList(String result) {

        try {
            String[] resultArray = result.split(",");

            for (int i = 0; i < resultArray.length; i += 2) {

                String entry = resultArray[i + 1] + "     AccountNo: " + resultArray[i];
                bankerList.add(entry);
            }
        }
        catch(Exception e) {

            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }
}