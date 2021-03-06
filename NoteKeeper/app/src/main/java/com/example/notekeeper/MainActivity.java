package com.example.notekeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private NoteRecyclerAdapter mNoteRecyclerAdapter;
    private RecyclerView mRecyclerItems;
    private LinearLayoutManager mNotesLayoutManager;
    private CourseRecyclerAdapter mCourseRecyclerAdapter;
    private GridLayoutManager mCoursesLayoutManager;
    private NoteKeeperOpenHelper mDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbOpenHelper = new NoteKeeperOpenHelper(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NoteActivity.class));
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initializeDisplayContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Anytime our NoteListActivity is resumed, the data set is refreshed
        mNoteRecyclerAdapter.notifyDataSetChanged();
        updateNavHeader();
    }

    @Override
    protected void onDestroy() {
        //closes helper on activity destruction
        mDbOpenHelper.close();
        super.onDestroy();
    }

    private void updateNavHeader() {
        //Reference to navigation view
        NavigationView navigationView = findViewById(R.id.nav_view);
        //Reference to navigation view header
        View headerView = navigationView.getHeaderView(0);
        //Reference to each of the textViews using the headerView
        TextView textUserName = headerView.findViewById(R.id.text_user_name);
        TextView textEmailAddress = headerView.findViewById(R.id.text_email_address);

        //To interact with preference systems with reference of type SharedPreferences
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        //Get the values
        String userName = pref.getString("user_display_name", "Your Name");
        String emailAddress = pref.getString("user_email_address", "yourname@yourhost.com");

        textUserName.setText(userName);
        textEmailAddress.setText(emailAddress);
    }

    //Method that sets up our recyclerview, layout managers and adapters
    private void initializeDisplayContent() {
        //Calling the method from DataManager
        DataManager.loadFromDatabase(mDbOpenHelper);
        //Get reference to recycler view that was loaded by our layout resource
        mRecyclerItems = findViewById(R.id.list_items);
        //Instance of layout manager
        mNotesLayoutManager = new LinearLayoutManager(this);
        mCoursesLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.course_grid_span));

        //Get notes to be displayed within the RecyclerView
        //Create instance of DataManager
        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        //Create instance of NoteRecyclerAdapter
        mNoteRecyclerAdapter = new NoteRecyclerAdapter(this, notes);

        //Get courses to be displayed within the RecyclerView
        //Create instance of DataManager
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        //Create instance of CourseRecyclerAdapter
        mCourseRecyclerAdapter = new CourseRecyclerAdapter(this, courses);

        displayNotes();
    }

    //Called to display the notes in the Recycler view
    private void displayNotes() {
        //Associate adapter with the RecyclerView
        mRecyclerItems.setAdapter(mNoteRecyclerAdapter);
        //Associated the layout manager to the recycler view
        mRecyclerItems.setLayoutManager(mNotesLayoutManager);
        selectNavigationMenuItem(R.id.nav_notes);
    }

    //The menu item is highlighted on starting the app
    private void selectNavigationMenuItem(int id) {
        //Reference to the navigation view
        NavigationView navigationView = findViewById(R.id.nav_view);
        //Reference to the menu in the nav view
        Menu menu = navigationView.getMenu();
        //Reference to a particular menu item
        menu.findItem(id).setChecked(true);
    }

    //Called to display the courses in the Recycler view
    private void displayCourses() {
        //Associate adapter with the RecyclerView
        mRecyclerItems.setAdapter(mCourseRecyclerAdapter);
        //Associated the layout manager to the recycler view
        mRecyclerItems.setLayoutManager(mCoursesLayoutManager);
        selectNavigationMenuItem(R.id.nav_courses);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    //Method called when user makes a selection from the options
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            displayNotes();
        } else if (id == R.id.nav_courses) {
            displayCourses();
        } else if (id == R.id.nav_share) {
//            handleSelection(R.string.nav_share_message);
            handleShare();
        } else if (id == R.id.nav_send) {
            handleSelection(R.string.nav_send_message);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleShare() {
        View view = findViewById(R.id.list_items);
        Snackbar.make(view, "Share to - " +
                PreferenceManager.getDefaultSharedPreferences(this).getString("user_favorite_social", "http://twitter.com"),
                Snackbar.LENGTH_LONG).show();
    }

    private void handleSelection(int message_id) {
        View view = findViewById(R.id.list_items);
        Snackbar.make(view, message_id, Snackbar.LENGTH_LONG).show();
    }
}