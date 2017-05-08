package metlife.lms.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.HashMap;

import me.leolin.shortcutbadger.ShortcutBadger;
import metlife.lms.R;
import metlife.lms.fragment.AppointmentLeadFragment;
import metlife.lms.fragment.HomeFragment;
import metlife.lms.fragment.NormalLeadFragment;
import metlife.lms.helper.SQLiteHandler;
import metlife.lms.helper.SessionManager;
import metlife.lms.reminder.WatchList;


public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    public static String CURRENT_TAG = TAG_HOME;
    private static final String TAG_NORMAL = "normal";
    private static final String TAG_APOINTMENT = "appointment";

    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    private SQLiteHandler db;
    private SessionManager session;
    String name;
    String role;
    private HomeFragment homeFragment;
    private NormalLeadFragment normalLeadFragment;
    private AppointmentLeadFragment appointmentLeadFragment;
    boolean flag_back=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences=getSharedPreferences("mycount",MODE_PRIVATE);
        preferences.edit().remove("count").commit();

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        HashMap<String, String> user = db.getUserDetails();

        name = user.get("name");
        role = user.get("email");

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);


        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources


        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 2;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText("Login as:"+" "+name);
        imgNavHeaderBg.setImageResource(R.drawable.nav_menu_header_bg);
        imgProfile.setImageResource(R.drawable.logo);
        // showing dot next to notifications label
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button

            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                 homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // photos
                 normalLeadFragment= new NormalLeadFragment();
                return normalLeadFragment;
            case 2:
                // movies fragment
                 appointmentLeadFragment = new AppointmentLeadFragment();
                return appointmentLeadFragment;

            default:
                return new AppointmentLeadFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }
    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_normal_lead:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_NORMAL;
                        break;
                    case R.id.nav_appointment_lead:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_APOINTMENT;
                        break;
                    case R.id.nav_reminder:
                        // launch new intent instead of loading fragment
                        Intent favoriateIntent = new Intent(MainActivity.this,WatchList.class);
                        startActivity(favoriateIntent);
                        return true;
                    case R.id.nav_logout:
                        // launch new intent instead of loading fragment
                        logoutUser();
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        String url = "https://click2cover.in/why-us";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        String url1 = "https://click2cover.in/terms-conditions";
                        Intent i1 = new Intent(Intent.ACTION_VIEW);
                        i1.setData(Uri.parse(url1));
                        startActivity(i1);
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 2;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {


        if(flag_back==true)
        {
            super.onBackPressed();
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }else {
                // This code loads home fragment when back key is pressed
                // when user is in other fragment than home

                if (shouldLoadHomeFragOnBackPress) {
                    // checking if user is on other navigation menu
                    // rather than home
                    if (navItemIndex != 2) {
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_HOME;
                        loadHomeFragment();
                        flag_back=true;
                        return;
                    }
                    if (navItemIndex == 0) {
                        homeFragment.onBackPressed();
                        flag_back=true;
                        return;
                    }
                    if (navItemIndex == 1) {
                        normalLeadFragment.onBackPressed();
                        flag_back=true;
                        return;
                    }
                    if (navItemIndex == 2) {
                        appointmentLeadFragment.onBackPressed();
                        flag_back=true;
                        return;
                    }

                }

        }

    }
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
