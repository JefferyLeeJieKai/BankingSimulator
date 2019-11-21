package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;

import com.jefferystudio.bankingsimulator.BankNote.RedeemBanknoteFragment;
import com.jefferystudio.bankingsimulator.DepositPackage.DepositAHFragment;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsAddFragment;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsAllFragment;
import com.jefferystudio.bankingsimulator.TransferFundsPackage.TransferAmountFragment;
import com.jefferystudio.bankingsimulator.ViewTransactionsPackage.ViewTransactionsFragment;
import com.jefferystudio.bankingsimulator.WithdrawalPackage.WithdrawalAHFragment;
import com.jefferystudio.bankingsimulator.ProfilePageAndSettingsPackage.ProfilePage;

import java.io.File;
import java.io.FileInputStream;

public class HomeScreenUser extends AppCompatActivity {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Fragment fragment;
    private ImageButton buttontransfer;
    private Bundle args;
    private String userID;
    private String currentBalance;
    private ImageButton btnprofile;
    private Context context;
    private ImageButton profilePic;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_user);
        args = getIntent().getExtras();
        args.putString("accountType", "AccountHolder");
        userID = args.getString("userID");
        currentBalance = args.getString("currentBalance");
        context = this;

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

                profilePic = (ImageButton)findViewById(R.id.profileBtn);
                try {
                    ContextWrapper cw = new ContextWrapper(getApplication());
                    File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                    File profilePicFile = new File(directory, "ProfilePicture.jpg");
                    Bitmap picture = BitmapFactory.decodeStream(new FileInputStream(profilePicFile));
                    profilePic.setImageBitmap(picture);
                }
                catch(Exception e) {

                }
            }



        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        fragment = new HomeFragmentUser();
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

                    fragment = new HomeFragmentUser();
                    fragment.setArguments(args);
                }
                else if(item.getItemId() == R.id.depositAH) {

                    fragment = new DepositAHFragment();
                    fragment.setArguments(args);
                }
                else if(item.getItemId() == R.id.withdrawalAH) {

                    fragment = new WithdrawalAHFragment();
                    fragment.setArguments(args);
                }
                else if(item.getItemId() == R.id.transferAH) {

                    fragment = new TransferAmountFragment();
                    fragment.setArguments(args);
                }
                else if(item.getItemId() == R.id.transaction) {

                    fragment = new ViewTransactionsFragment();
                    fragment.setArguments(args);
                }
                else if(item.getItemId() == R.id.addSavingGoals) {

                    fragment = new SavingGoalsAddFragment();
                    fragment.setArguments(args);
                }
                else if(item.getItemId() == R.id.viewSavingGoals) {

                    fragment = new SavingGoalsAllFragment();
                    fragment.setArguments(args);
                }
                else if(item.getItemId() == R.id.redeemBanknote) {

                    fragment = new RedeemBanknoteFragment();
                    fragment.setArguments(args);
                }

                /*
                else if(item.getItemId() == R.id.enableFingerprint) {

                    SharedPreferences pref = getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);

                    if(pref.getString("userID", "NotFound").equals("NotFound")) {

                        SharedPreferences myPrefs = getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = myPrefs.edit();
                        editor.putString("userID", args.getString("userID"));
                        editor.putString("username", args.getString("userName"));
                        editor.apply();

                        String result = "";

                        try {

                            result = new FingerprintLoginAsync(context, "enablefingerprint", args.getString("userID"))
                                    .execute()
                                    .get(5000, TimeUnit.MILLISECONDS);
                        }
                        catch(Exception e) {

                        }

                        String[] resultArray = result.split(",");

                        if(resultArray[0].equals("Success")) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);

                            builder.setTitle("DigiBank Alert");
                            builder.setMessage("Fingerprint enabled!");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialogInterface, int i) {


                                }
                            });

                            AlertDialog fingerprintSettingsDialog = builder.create();
                            fingerprintSettingsDialog.show();
                        }
                    }
                    else {

                        SharedPreferences myPrefs = getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = myPrefs.edit();
                        editor.clear();
                        editor.apply();

                        String result = "";

                        try {

                            result = new FingerprintLoginAsync(context, "disablefingerprint", args.getString("userID"))
                                    .execute()
                                    .get(5000, TimeUnit.MILLISECONDS);
                        }
                        catch(Exception e) {

                        }

                        String[] resultArray = result.split(",");

                        if(resultArray[0].equals("Success")) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);

                            builder.setTitle("DigiBank Alert");
                            builder.setMessage("Fingerprint disabled!");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialogInterface, int i) {


                                }
                            });

                            AlertDialog fingerprintSettingsDialog = builder.create();
                            fingerprintSettingsDialog.show();
                        }
                    }
                }

               */

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

                Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                intent.putExtras(args);
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
}