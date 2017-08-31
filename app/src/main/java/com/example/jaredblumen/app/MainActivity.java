package com.example.jaredblumen.app;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.jaredblumen.app.R.id.text;


public class MainActivity extends AppCompatActivity {
    ArrayList<Post> posts = new ArrayList<Post>();
    int color;
    int colorAccent;
    int colorPrimaryDark;
    private int postIndex = 0;
    private LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TypedValue typedValue = new TypedValue();
        TypedValue typedValueAccent = new TypedValue();
        TypedValue typedValuePrimaryDark = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        getTheme().resolveAttribute(R.attr.colorAccent, typedValueAccent, true);
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValuePrimaryDark, true);
        color = typedValue.data;
        colorAccent = typedValueAccent.data;
        colorPrimaryDark = typedValuePrimaryDark.data;
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        Post post = new Post(message, this);

        post.getDividerGreen().setBackgroundColor(colorPrimaryDark);
        post.getRealDivider().setBackgroundColor(Color.LTGRAY);
        post.getUpvote().setColorFilter(Color.BLACK);
        post.setUpvoteId(postIndex);
        post.getUpvote().setOnClickListener(upLikeListener);
        post.getDownvote().setColorFilter(Color.BLACK);
        post.setDownvoteId(postIndex);
        post.getDownvote().setOnClickListener(downLikeListener);
        post.getTextView().setTextColor(Color.BLACK);
        post.getLikeView().setTextColor(colorPrimaryDark);
        post.getTimer().setTextColor(Color.BLACK);
        post.getDivider().setBackgroundColor(colorAccent);

        post.getCDT().start();

        ll = (LinearLayout) findViewById(R.id.post_list);
        ll.setBackgroundColor(Color.WHITE);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,0);

        post.getVote().addView(post.getUpvote(), post.getVoteParam());
        post.getVote().addView(post.getLikeView(), post.getVoteParam());
        post.getVote().addView(post.getDownvote(), post.getVoteParam());
        post.getVote().addView(post.getTimer(), post.getVoteParam());

        post.getHoriz().addView(post.getTextView(), post.getMessageParam());
        post.getHoriz().addView(post.getVote(), post.getLikeParam());
        post.getDivider().addView(post.getDividerGreen(), post.getDividerGreenParam());
        ll.addView(post.getRealDivider(), 0, post.getRealDividerParam());
        ll.addView(post.getDivider(), 0, post.getDividerParam());
        ll.addView(post.getHoriz(), 0, params);

        posts.add(post);
        postIndex++;
    }

    View.OnClickListener upLikeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Post post = posts.get(v.getId());
            if(post.getSecsLeft() > 0) {
                post.setLikes(post.getLikes() + 1);
                post.getLikeView().setText(post.stringLikes());
                View C = post.getDivider();
                post.upVoteCDT(post.getSecsLeft(), 1000);
                ViewGroup parent = (ViewGroup) C.getParent();
                int index = parent.indexOfChild(C);
                parent.removeView(C);
                parent.addView(post.getDivider(), index, post.getDividerParam());
                post.getCDT().start();
            }
        }
    };
    View.OnClickListener downLikeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Post post = posts.get(v.getId());
            if(post.getSecsLeft() > 0) {
                post.setLikes(post.getLikes() - 1);
                post.getLikeView().setText(post.stringLikes());
                View C = post.getDivider();
                post.downVoteCDT(post.getSecsLeft(), 1000);
                ViewGroup parent = (ViewGroup) C.getParent();
                int index = parent.indexOfChild(C);
                parent.removeView(C);
                parent.addView(post.getDivider(), index, post.getDividerParam());
                post.getCDT().start();
            }
        }
    };

    public void deletePost(int id){
        Post post = posts.get(id);
        View A = post.getHoriz();
        View B = post.getDivider();
        View C = post.getRealDivider();
        ViewGroup parent = (ViewGroup) A.getParent();
        parent.removeView(A);
        parent.removeView(B);
        parent.removeView(C);
    }

}
