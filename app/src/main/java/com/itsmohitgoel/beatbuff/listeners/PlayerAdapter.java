package com.itsmohitgoel.beatbuff.listeners;

/**
 * Allows @PlayerActivity to control media playback of @MediaPlayerHolder.
 */
public interface PlayerAdapter {

    void loadMedia(int resourceId);

    void release();

    boolean isPlaying();

    void play();

    void pause();

    void reset();

    void initializeProgressCallback();

    void seekTo(int position);
}