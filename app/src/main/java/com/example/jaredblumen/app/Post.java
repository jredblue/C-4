package com.example.jaredblumen.app;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by jaredblumen on 8/11/17.
 */

public class Post {
    private final MainActivity activity;
    private String message;
    private TextView textView;
    private int likes;
    private TextView likeView;
    private TextView dividerGreen;
    private ImageButton upvote;
    private ImageButton downvote;
    private int secsLeft;
    private CountDownTimer CDT;
    private TextView timer;
    private LinearLayout horiz;
    private LinearLayout vote;
    private LinearLayout divider;
    private LinearLayout realDivider;
    private LinearLayout.LayoutParams realDividerParam;
    private LinearLayout.LayoutParams dividerGreenParam;
    private LinearLayout.LayoutParams voteParam;
    private LinearLayout.LayoutParams messageParam;
    private LinearLayout.LayoutParams likeParam;
    private LinearLayout.LayoutParams dividerParam;

    public Post(String message, final MainActivity activity){
        this.activity = activity;
        this.message = message;
        textView = new TextView(activity);
        textView.setText(message);
        textView.setTextSize(17);
        likes = 0;
        likeView = new TextView(activity);
        likeView.setText(stringLikes());
        likeView.setTextSize(30);
        likeView.setPadding(0,0,0,0);
        dividerGreen = new TextView(activity);
        realDivider = new LinearLayout(activity);
        realDividerParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 8);
        realDividerParam.setMargins(30,10,30,10);
        realDividerParam.gravity = Gravity.CENTER;
        divider = new LinearLayout(activity);
        divider.setOrientation(LinearLayout.HORIZONTAL);
        dividerGreenParam = new LinearLayout.LayoutParams(120, LinearLayout.LayoutParams.MATCH_PARENT);
        dividerGreenParam.setMargins(0,0,0,0);
        upvote = new ImageButton(activity);
        upvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        upvote.setBackgroundColor(Color.TRANSPARENT);
        upvote.setPadding(0,0,0,0);
        downvote = new ImageButton(activity);
        downvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        downvote.setBackgroundColor(Color.TRANSPARENT);
        downvote.setPadding(0,0,0,0);
        timer = new TextView(activity);
        secsLeft = 120;
        CDT = new CountDownTimer(120000, 1000) {
            int greenWidth = dividerGreenParam.width;
            public void onTick(long millisUntilFinished) {
                final int minutes = (int) millisUntilFinished/ (60 * 1000);
                final int seconds = (int) (millisUntilFinished/ 1000) % 60;
                String str = String.format("%d:%02d", minutes, seconds);
                timer.setText(str);
                dividerGreenParam = new LinearLayout.LayoutParams((dividerGreenParam.width - (greenWidth/120)), LinearLayout.LayoutParams.MATCH_PARENT);
                divider.removeAllViews();
                divider.addView(dividerGreen, dividerGreenParam);
                secsLeft--;
            }

            public void onFinish() {
                activity.deletePost(getUpvote().getId());
            }
        };
        horiz = new LinearLayout(activity);
        horiz.setOrientation(LinearLayout.HORIZONTAL);
        vote = new LinearLayout(activity);
        vote.setOrientation(LinearLayout.VERTICAL);
        voteParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        voteParam.setMargins(0,0,0,0);
        voteParam.gravity = Gravity.CENTER;
        dividerParam = new LinearLayout.LayoutParams(
                120, 8);
        dividerParam.setMargins(30,10,0,10);
        dividerParam.gravity = Gravity.LEFT;
        messageParam = new LinearLayout.LayoutParams(
                720, LinearLayout.LayoutParams.MATCH_PARENT);
        messageParam.setMargins(10,10,10,10);
        likeParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        likeParam.setMargins(150,25,10,30);

    }
    public void setMessage(String newMessage){
        this.message = newMessage;
    }
    public void setLikes(int newLikes){
        this.likes = newLikes;
    }
    public String getMessage(){
        return this.message;
    }
    public int getLikes(){
        return this.likes;
    }
    public String stringLikes(){
        String s = "" + this.likes + "";
        return s;
    }
    public LinearLayout getRealDivider() { return realDivider; }
    public LinearLayout.LayoutParams getRealDividerParam() { return realDividerParam; }
    public TextView getTextView() {
        return textView;
    }
    public TextView getLikeView() {
        return likeView;
    }
    public TextView getDividerGreen() {
        return dividerGreen;
    }
    public ImageButton getUpvote() {
        return upvote;
    }
    public ImageButton getDownvote() {
        return downvote;
    }
    public TextView getTimer() {
        return timer;
    }
    public int getSecsLeft() {
        return secsLeft;
    }
    public CountDownTimer getCDT() {
        return CDT;
    }
    public LinearLayout getHoriz(){
        return horiz;
    }
    public LinearLayout getVote() {
        return vote;
    }
    public LinearLayout getDivider() { return divider; }
    public LinearLayout.LayoutParams getDividerGreenParam(){ return dividerGreenParam; }
    public LinearLayout.LayoutParams getVoteParam() {
        return voteParam;
    }
    public LinearLayout.LayoutParams getMessageParam() {
        return messageParam;
    }
    public LinearLayout.LayoutParams getLikeParam() {
        return likeParam;
    }
    public LinearLayout.LayoutParams getDividerParam() {
        return dividerParam;
    }
    public void upVoteCDT(long secondsLeft, long interval) {
        getCDT().cancel();
        secsLeft = (int) secondsLeft + 61;
        if(secsLeft >= 960) {
            secsLeft = 961;
        }
        long millisLeft = (long) secsLeft*1000;
        dividerParam = new LinearLayout.LayoutParams(secsLeft, 8);
        dividerParam.gravity = Gravity.LEFT;
        dividerParam.setMargins(30, 10, 0, 10);
        dividerGreenParam = new LinearLayout.LayoutParams(secsLeft, LinearLayout.LayoutParams.MATCH_PARENT);
        divider.removeAllViews();
        divider.addView(dividerGreen, dividerGreenParam);
        CDT = new CountDownTimer(millisLeft, interval) {
            int greenWidth = dividerGreenParam.width;
            int constantSecsLeft = secsLeft;

            public void onTick(long millisUntilFinished) {
                final int minutes = (int) millisUntilFinished / (60 * 1000);
                final int seconds = (int) (millisUntilFinished / 1000) % 60;
                String str = String.format("%d:%02d", minutes, seconds);
                timer.setText(str);
                dividerGreenParam = new LinearLayout.LayoutParams((dividerGreenParam.width - (greenWidth / constantSecsLeft)), LinearLayout.LayoutParams.MATCH_PARENT);
                divider.removeAllViews();
                divider.addView(dividerGreen, dividerGreenParam);
                secsLeft--;
            }

            public void onFinish() {
                    activity.deletePost(getUpvote().getId());
                }
        };

    }
    public void downVoteCDT(long secondsLeft, long interval) {
        getCDT().cancel();
        secsLeft = (int) secondsLeft - 59;
        long millisLeft = (long) secsLeft*1000;
        dividerParam = new LinearLayout.LayoutParams(secsLeft, 8);
        dividerParam.gravity = Gravity.LEFT;
        dividerParam.setMargins(30,10,0,10);
        dividerGreenParam = new LinearLayout.LayoutParams(secsLeft, LinearLayout.LayoutParams.MATCH_PARENT);
        divider.removeAllViews();
        divider.addView(dividerGreen, dividerGreenParam);
        CDT = new CountDownTimer(millisLeft, interval) {
            int greenWidth = dividerGreenParam.width;
            int constantSecsLeft = secsLeft;
            public void onTick(long millisUntilFinished) {
                final int minutes = (int) millisUntilFinished/ (60 * 1000);
                final int seconds = (int) (millisUntilFinished/ 1000) % 60;
                String str = String.format("%d:%02d", minutes, seconds);
                timer.setText(str);
                dividerGreenParam = new LinearLayout.LayoutParams((dividerGreenParam.width - (greenWidth/constantSecsLeft)), LinearLayout.LayoutParams.MATCH_PARENT);
                divider.removeAllViews();
                divider.addView(dividerGreen, dividerGreenParam);
                secsLeft--;
            }

            public void onFinish() {
                activity.deletePost(getUpvote().getId());
            }
        };
    }
    public void setUpvoteId( int id) {
        upvote.setId(id);
    }
    public void setDownvoteId( int id) {
        downvote.setId(id);
    }

}
