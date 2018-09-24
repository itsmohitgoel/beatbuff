package com.itsmohitgoel.beatbuff.utils;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.models.CategoryDataModel;
import com.itsmohitgoel.beatbuff.models.CategorySingleItemModel;
import com.itsmohitgoel.beatbuff.models.MusicItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

public class MusicManager {
    private static final String TAG = MusicManager.class.getSimpleName();

    private static MusicManager sMusicManager;
    private ArrayList<MusicItem> mMusicItems;
    private Context mContext;

    public static MusicManager getInstance(Context context) {
        if (sMusicManager == null) {
            sMusicManager = new MusicManager(context);
        }

        return sMusicManager;
    }

    private MusicManager(Context context) {
        mContext = context;
        mMusicItems = new ArrayList<>();

    }

    public ArrayList<MusicItem> getMusicItems() {
        if (mMusicItems != null && mMusicItems.size() > 0) {
            return mMusicItems;
        }

        int[] resIds = getAllRawMusicResourceIds();

        for (int resId : resIds) {
            MusicItem musicItem = new MusicItem();
            addMetadataInfo(musicItem, resId);
            mMusicItems.add(musicItem);

        }

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

    public void loadAllMusicData(ArrayList<CategoryDataModel> allCategoriesData) {
        if (allCategoriesData == null) {
            return;
        }
        for (MusicItem item : mMusicItems) {

        }

    }

    public CategoryDataModel getAllGenreData() {
        CategoryDataModel genreDataModel = new CategoryDataModel();
        genreDataModel.setHeaderTitle("Browse By Genre");

        ArrayList<CategorySingleItemModel> genreItemsList = new ArrayList<>();
        String[] genreTitles = new String[]{"Rock", "Pop", "HipHop", "Electronic", "Folk", "Metal"};
        int[] genreImageResources = new int[]{
                R.drawable.genre_rock, R.drawable.genre_pop, R.drawable.genre_hiphop,
                R.drawable.genre_electronic, R.drawable.genre_folk, R.drawable.genre_metal};
        for (int i = 0; i < genreImageResources.length; i++) {
            genreItemsList.add(new CategorySingleItemModel(genreTitles[i], genreImageResources[i]));
        }

        genreDataModel.setAllItemsInCategory(genreItemsList);

        return genreDataModel;
    }

    public CategoryDataModel getAllArtistData() {
        CategoryDataModel artistDataModel = new CategoryDataModel();
        artistDataModel.setHeaderTitle("Browse By Artists");

        ArrayList<CategorySingleItemModel> artistsItemsList = new ArrayList<>();
        String[] artistTitles = new String[]{"Charlie Puth", "Sia", "Chainsmokers",
                "Alan Walker", "Marshmello", "Maroon 5", "Drake", "Justine Bieber", "Selena"};
        int[] artistImageResources = new int[]{
                R.drawable.artist_charlie, R.drawable.artist_sia, R.drawable.artist_chainsmokers,
                R.drawable.artist_alan_walker, R.drawable.artist_marshmellow,
                R.drawable.artist_maroon5, R.drawable.artist_drake, R.drawable.artist_justine,
                R.drawable.artist_selena};
        for (int i = 0; i < artistImageResources.length; i++) {
            artistsItemsList.add(new CategorySingleItemModel(artistTitles[i], artistImageResources[i]));
        }

        artistDataModel.setAllItemsInCategory(artistsItemsList);

        return artistDataModel;
    }

    public CategoryDataModel getAllAlbumsData() {
        CategoryDataModel albumsDataModel = new CategoryDataModel();
        albumsDataModel.setHeaderTitle("Top Albums");

        ArrayList<CategorySingleItemModel> albumItemsList = new ArrayList<>();
        String[] albumTitles = new String[]{"Voicenotes", "This is Acting", "Collage",
                "Speak Your Mind", "Red Pill Blues", "How Long", "Wolves"};
        int[] albumImageResources = new int[]{
                R.drawable.album_voicenotes, R.drawable.album_this_is_acting, R.drawable.album_collage,
                R.drawable.album_speak_your_mind, R.drawable.album_red_pill_blues,
                R.drawable.album_how_long, R.drawable.album_wolves};
        for (int i = 0; i < albumImageResources.length; i++) {
            albumItemsList.add(new CategorySingleItemModel(albumTitles[i], albumImageResources[i]));
        }

        albumsDataModel.setAllItemsInCategory(albumItemsList);

        return albumsDataModel;
    }

    public CategoryDataModel getAllSongsData() {
        CategoryDataModel allSongsDataModel = new CategoryDataModel();
        allSongsDataModel.setHeaderTitle("All Songs");

        ArrayList<CategorySingleItemModel> allSongsItemsList = new ArrayList<>();
        String[] albumTitles = new String[]{"Attention", "Cheap Thrills", "Closer",
                "Darkside", "Faded", "Friends", "Girls Like You", "How Long",
                 "No Brainer", "Side Effects", "Wolves"};
        int[] albumImageResources = new int[]{
                R.drawable.artist_charlie, R.drawable.artist_sia, R.drawable.artist_chainsmokers,
                R.drawable.alan_walker, R.drawable.artist_alan_walker,
                R.drawable.artist_marshmellow, R.drawable.artist_maroon5,
        R.drawable.artist_charlie, R.drawable.artist_justine, R.drawable.artist_chainsmokers,
        R.drawable.artist_selena};
        for (int i = 0; i < albumImageResources.length; i++) {
            allSongsItemsList.add(new CategorySingleItemModel(albumTitles[i], albumImageResources[i]));
        }

        allSongsDataModel.setAllItemsInCategory(allSongsItemsList);

        return allSongsDataModel;
    }

    /**
     * Retrieve all mp3 files from res/raw folder at once during runtime
     */
    private int[] getAllRawMusicResourceIds() {
        Field[] fields = R.raw.class.getDeclaredFields();
        int[] ids = new int[fields.length];
        R.raw r = new R.raw();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            try {
                ids[i] = field.getInt(r);
            } catch (IllegalAccessException iae) {
                Log.d(TAG, "cannot access int id from R file", iae);
            }
        }

        return ids;
    }

    private void addMetadataInfo(MusicItem musicItem, int resourceId) {
        Uri baseUriForAudioFiles = Uri.parse("android.resource://" + mContext.getPackageName() + "/raw");
        Uri uri = ContentUris.withAppendedId(baseUriForAudioFiles, resourceId);

        MediaMetadataRetriever mData = new MediaMetadataRetriever();
        mData.setDataSource(mContext, uri);
        String artist = mData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String duration = mData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        duration = TimeConvertor.milliSecondsToTimer(Integer.parseInt(duration));
        String title = mData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        byte art[] = mData.getEmbeddedPicture();
        Bitmap coverImage = BitmapFactory.decodeByteArray(art, 0, art.length);

        musicItem.setTitle(title);
        musicItem.setArtistName(artist);
        musicItem.setDuration(duration);
        musicItem.setCoverArt(coverImage);
        musicItem.setUriPath(uri);

        Log.i(TAG, String.format("Artist : [%s];\t\t Duration : [%s]; \t\t Title : [%s]",
                artist, duration, title));
    }
}



















