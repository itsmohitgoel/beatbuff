package com.itsmohitgoel.beatbuff.listeners;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class PlaybackInfoListener {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({State.INVALID, State.PLAYING, State.PAUSED, State.RESET, State.COMPLETED})
    @interface State {
        int INVALID = -1;
        int PLAYING = 0;
        int PAUSED = 1;
        int RESET = 2;
        int COMPLETED = 3;
    }

    public abstract void onDurationChanged(int duration);

    public abstract void onPositionChanged(int position);

//    public abstract void onStateChanged(@State int state);
//
//    public abstract void onPlaybackCompleted();
}
