package com.itsmohitgoel.beatbuff.utils;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.models.MusicItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MusicManager {
    private static MusicManager sMusicManager;
    private ArrayList<MusicItem> mMusicItems;

    public static MusicManager getInstance() {
        if (sMusicManager == null) {
            sMusicManager = new MusicManager();
        }

        return sMusicManager;
    }

    private MusicManager() {
        mMusicItems = new ArrayList<>();

    }

    public ArrayList<MusicItem> getMusicItems() {
            MusicItem musicItem = new MusicItem();
            musicItem.setResourceId(R.raw.cheap_thrills_by_sia);

        mMusicItems.add(musicItem);
        musicItem = new MusicItem();
        musicItem.setResourceId(R.raw.faded_by_alan_walker);
        mMusicItems.add(musicItem);

        return mMusicItems;
    }

    public MusicItem getMusicItem(UUID uuid) {
        for (MusicItem song : mMusicItems) {
            if (song.getId().equals(uuid)) {
                return song;
            }
        }
        return null;
    }
}
