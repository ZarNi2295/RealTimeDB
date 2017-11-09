package app.znmsw.realtimedb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import app.znmsw.realtimedb.R;
import app.znmsw.realtimedb.adapters.MoviesAdapter;
import app.znmsw.realtimedb.api.Api;
import app.znmsw.realtimedb.api.ConnectivityReceiver;
import app.znmsw.realtimedb.api.MyApplication;
import app.znmsw.realtimedb.model.Hero;
import app.znmsw.realtimedb.model.Survey;
import app.znmsw.realtimedb.model.Team;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputName, inputEmail;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    ProgressBar progressBar;
    boolean isConnected;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout newPost;
    int appTitle;
    private String userId;

    public void onRefresh() {
        getHeroe();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Displaying toolbar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher);

       /* txtDetails = (TextView) findViewById(R.id.txt_user);
        inputName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        btnSave = (Button) findViewById(R.id.btn_save);*/
        onBindView();
        if (checkConnection()) {
            progressBar.setVisibility(View.VISIBLE);
            //calling the method to display the heroes
            getHeroe();
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Grrrrr ! You are not connected to Internet!", Toast.LENGTH_LONG).show();
        }

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("survey");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");
        //mFirebaseInstance.getReference("post_count").setValue("8");
        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        // Save / update the user
     /*   btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();

                // Check for already existed userId
                if (TextUtils.isEmpty(userId)) {
                    createUser(name, email);
                } else {
                    updateUser(name, email);
                }
            }
        });*/

        //toggleButton();
    }



    private void onBindView() {

        //  listView = (ListView) findViewById(R.id.listViewHeroes);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewHeroes);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        newPost = (LinearLayout) findViewById(R.id.new_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout.setOnRefreshListener(this);
        newPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.new_post) {

            //Survey survey = new Survey("Testing", "test@test.com", "What is that?", "a", "b", "c", "a");
            //justTest();
            startActivity(new Intent(getApplicationContext(),PostingActivity.class));

           // mFirebaseDatabase.child(appTitle + "").setValue(hero);
            // Log.e(mFirebaseInstance.getReference("post_count").toString(),"<< Here grrrr");
            //   mFirebaseDatabase.child("2").setValue(hero);
        }
    }

    // Changing button text
    private void toggleButton() {
        if (TextUtils.isEmpty(userId)) {
            btnSave.setText("Save");
        } else {
            btnSave.setText("Update");
        }
    }

    /**
     * Creating new user node under 'users'
     */
    private void createUser(String name, String email) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
            // userId ="2";
        }

        Survey survey = new Survey(name, email, "What is that?", "a", "b", "c", "a");

        mFirebaseDatabase.child(userId).setValue(survey);

        addUserChangeListener();
    }

    /**
     * Survey data change listener
     */
    private void addUserChangeListener() {
        // Survey data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Survey survey = dataSnapshot.getValue(Survey.class);

                // Check for null
                if (survey == null) {
                    Log.e(TAG, "Survey data is null!");
                    return;
                }

                Log.e(TAG, "Survey data is changed!" + survey.name + ", " + survey.email);

                // Display newly updated name and email
                txtDetails.setText(survey.name + ", " + survey.email);

                // clear edit text
                inputEmail.setText("");
                inputName.setText("");
                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(String name, String email) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(userId).child("name").setValue(name);

        if (!TextUtils.isEmpty(email))
            mFirebaseDatabase.child(userId).child("email").setValue(email);
    }

    // Method to manually check connection status
    private boolean checkConnection() {
        return isConnected = ConnectivityReceiver.isConnected();

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected)
            Toast.makeText(getApplicationContext(), "No Internet!!!!", Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(getApplicationContext(), "Internet Access", Toast.LENGTH_LONG).show();
            getHeroe();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    private void getHeroe() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Hero>> call = api.getHeroes();
        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                //displaying the string array into listview
                // listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, heroes));
                List<Hero> movies = response.body();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.test, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}