package app.znmsw.realtimedb.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;

import app.znmsw.realtimedb.R;
import app.znmsw.realtimedb.model.Hero;
import app.znmsw.realtimedb.model.Team;

public class PostingActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name, post;
    Button btnPost;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    int appTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onBindUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void onBindUI() {
        name = (EditText) findViewById(R.id.name);
        post = (EditText) findViewById(R.id.testPost);
        btnPost = (Button) findViewById(R.id.testPostButton);
        btnPost.setOnClickListener(this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.testPostButton) {

            justTest(name.getText().toString(),post.getText().toString());

        }
    }

    public void justTest(final String name, final String post) {
        mFirebaseDatabase = mFirebaseInstance.getReference("book");
        mFirebaseInstance.getReference("post_count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Log.e(TAG, "App title updated");
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                appTitle = Integer.parseInt(dataSnapshot.getValue(String.class));
                appTitle++;
                Team team = new Team("ha ha", "wow", "love");
                Hero hero = new Hero(name, "My Myo My", "This is first", post, "Innwa", "", "He is very good at programming", team,String.valueOf(timestamp.getTime()));
                mFirebaseDatabase.child(appTitle + "").setValue(hero);
                mFirebaseInstance.getReference("post_count").setValue(appTitle + "");
                // update toolbar title
                // getSupportActionBar().setTitle(appTitle + " ");
                Toast.makeText(getApplicationContext(), "Successful!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });
       /* mFirebaseDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        //user.email now has your email value
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/
    }
}
