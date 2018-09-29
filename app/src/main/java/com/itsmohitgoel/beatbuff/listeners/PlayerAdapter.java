package com.itsmohitgoel.beatbuff.listeners;

import com.itsmohitgoel.beatbuff.models.MusicItem;

/**
 * Allows @PlayerActivity to control media playback of @MediaPlayerHolder.
 */
public interface PlayerAdapter {

    /**
     * Load and prepare the MP3 file to be played from the APK.
     * @param musicItem
     */
    void loadMedia(MusicItem musicItem);

    /**
     * Deallocate resources used by the player.
     */
    void release();

    /**
     * Check to see if audio is being played back right now
     */
    boolean isPlaying();

    /**
     * Tell the player to start playback
     */
    void play();

    /**
     * if isPlaying() is true, then pause playback
     */
    void pause();

    /**
     * stop playback and reset the MediaPlayer so that another
     * media file can be loaded into it.
     */
    void reset();

    /**
     * After mp3 file is loaded, set the maximum value to the
     * duration of our audio track.
     */
    void initializeProgressCallback();

    /**
     * allows the user to jump to any specific time location in media by
     * touching and dragging the seekbar.
     * @param position time in milliseconds
     */
    void seekTo(int position);
}
