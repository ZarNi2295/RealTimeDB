package app.znmsw.realtimedb.adapters;

/**
 * Created by WT on 10/17/2017.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import app.znmsw.realtimedb.R;
import app.znmsw.realtimedb.model.Hero;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Hero> movies;
    private int rowLayout;
    public Context context;
    DatabaseReference mFirebaseDatabase;
    FirebaseDatabase mFirebaseInstance;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data, timestamp;
        // TextView movieDescription;
        //TextView rating;
        ImageView loadImage, imgProfile;
        LinearLayout linearLayout;
        CardView postLayout;


        public MovieViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.post);
            movieTitle = (TextView) v.findViewById(R.id.postTitle);
            data = (TextView) v.findViewById(R.id.tvPost);
            timestamp = (TextView) v.findViewById(R.id.tvTime);
            // movieDescription = (TextView) v.findViewById(R.id.description);
            // rating = (TextView) v.findViewById(R.id.rating);
            loadImage = (ImageView) v.findViewById(R.id.imgPost);
            imgProfile = (ImageView) v.findViewById(R.id.imgproile);
            linearLayout = (LinearLayout) v.findViewById(R.id.post);
            postLayout = (CardView) v.findViewById(R.id.postLayout);


        }
    }


    public MoviesAdapter(List<Hero> movies, int rowLayout, Context context) {
        Collections.reverse(movies);
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("book");
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.movieTitle.setText(movies.get(position).getName());
        holder.data.setText("\t\t" + movies.get(position).getCreatedby());
        holder.timestamp.setText(DateUtils.getRelativeTimeSpanString(
                Long.parseLong(movies.get(position).getTimestamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));
        // holder.movieDescription.setText(movies.get(position).getPublisher());
        // holder.rating.setText(movies.get(position).getTeam().toString());
        //  Toast.makeText(context,movies.get(position).getImageurl(),Toast.LENGTH_LONG).show();


        if (movies.get(position).getImageurl().equals("")) {
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 350);
            parms.setMargins(4, 0, 4, 0);
            holder.loadImage.setVisibility(View.GONE);
            holder.postLayout.setLayoutParams(parms);

        } else {
            Picasso.with(context).load(movies.get(position).getImageurl()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.star).error(R.drawable.car).into(holder.loadImage);
            Picasso.with(context).load(movies.get(position).getImageurl()).placeholder(R.drawable.star).error(R.drawable.car).into(holder.imgProfile);

        }
        //Team c = gson.fromJson("{\"team\" : { \"team\":\"good\",\"team1\":\"better\",\"team2\":\"best\"}}", Team.class);
        // Toast.makeText(context, c.getTeam1().toString(), Toast.LENGTH_SHORT).show();
        holder.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String test = movies.get(position).getTeamObj().get(position).getTeam2().toString();
                String a = movies.get(position).getImageurl();
                // DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                //mDatabase.push().setValue(1);
                // mFirebaseDatabase.child("0").child("name").setValue("Aye Aung");
               /* mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
                Survey survey = new Survey("grr", "groo", "What is that?", "a", "b", "c", "a");
                mFirebaseDatabase.push().setValue(survey);
                Login login = new Login("Mg MgSS", "1234mg");
                mFirebaseDatabase.child("grr").push().setValue(login);
                String aa = mFirebaseDatabase.push().getKey();*/
                //  String aa= mFirebaseDatabase.child("grr").getKey();


                // mFirebaseInstance.getReference("Testing").setValue("Realtime Database");
                // Toast.makeText(context, a, Toast.LENGTH_SHORT).show();
                Log.e("Here", "click click image");

                CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                        Long.parseLong(String.valueOf(timestamp.getTime())),
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
                Toast.makeText(context, timeAgo, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}