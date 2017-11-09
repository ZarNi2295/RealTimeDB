package app.znmsw.realtimedb.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by WT on 10/13/2017.
 */
@IgnoreExtraProperties
public class Survey {

    public String name;
    public String email;
    public String question;
    public String choice1;
    public String choice2;
    public String choice3;
    public String answer;

    // Default constructor required for calls to
    // DataSnapshot.getValue(Survey.class)
    public Survey() {
    }

    public Survey(String name, String email, String question, String choice1, String choice2, String choice3, String answer) {
        this.name = name;
        this.email = email;
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
    }
}
