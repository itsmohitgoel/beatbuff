package com.itsmohitgoel.beatbuff.fragments;

import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.listeners.PlayerAdapter;
import com.itsmohitgoel.beatbuff.utils.Blur;
import com.itsmohitgoel.beatbuff.utils.MediaPlayerHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerFragment extends Fragment {
    private static final String TAG = PlayerFragment.class.getSimpleName();

    @BindView(R.id.google_cast_button)
    ImageView googleCastButton;
    @BindView(R.id.share_button)
    ImageView shareButton;
    @BindView(R.id.equalizer_button)
    ImageView equalizerButton;
    @BindView(R.id.repeat_button)
    ImageView repeatButton;
    @BindView(R.id.shuffle_button)
    ImageView shuffleButton;
    @BindView(R.id.playlist_button)
    ImageView playlistButton;
    @BindView(R.id.background_image_view)
    ImageView backgroundImageView;
    @BindView(R.id.circle_image_view)
    CircleImageView profileImageView;
    @BindView(R.id.play_button)
    ImageView playButton;

    private PlayerAdapter mPlayerAdapter;


    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_player, container, false);

        initializeUI(view);
        initializePlaybackController();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPlayerAdapter.loadMedia(R.raw.cheap_thrills_by_sia);
    }

    private View.OnClickListener controlButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.google_cast_button:
                    setBackgroundTint(googleCastButton.getDrawable());
                    break;

                case R.id.share_button:
                    setBackgroundTint(shareButton.getDrawable());
                    break;

                case R.id.equalizer_button:
                    setBackgroundTint(equalizerButton.getDrawable());
                    break;

                case R.id.repeat_button:
                    setBackgroundTint(repeatButton.getDrawable());
                    break;

                case R.id.shuffle_button:
                    setBackgroundTint(shuffleButton.getDrawable());
                    break;

                case R.id.playlist_button:
                    setBackgroundTint(playlistButton
                            .getDrawable());
                    break;

                case R.id.play_button:
                    mPlayerAdapter.play();
                default:
                    break;
            }
        }

        private void setBackgroundTint(Drawable drawable) {
            DrawableCompat.setTint(drawable,
                    ContextCompat.getColor(getActivity(), R.color.cyan_light));
        }
    };

    private void initializeUI(View view) {
        ButterKnife.bind(this, view);

        Bitmap albumArtImage = getAlbumArtImage(R.raw.cheap_thrills_by_sia);
        profileImageView.setImageBitmap(albumArtImage);

        Bitmap albumArtBlurImage = Blur.fastblur(getActivity(), albumArtImage, 12);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(albumArtBlurImage);
        backgroundImageView.setImageBitmap(albumArtBlurImage);
        backgroundImageView.setColorFilter(Blur.getGrayScaleFilter());

        googleCastButton.setOnClickListener(controlButtonsListener);
        shareButton.setOnClickListener(controlButtonsListener);
        equalizerButton.setOnClickListener(controlButtonsListener);
        repeatButton.setOnClickListener(controlButtonsListener);
        shuffleButton.setOnClickListener(controlButtonsListener);
        playlistButton.setOnClickListener(controlButtonsListener);
        playButton.setOnClickListener(controlButtonsListener);
    }

    /**
     * Extract the Album cover from the audio resource file itself
     */
    private Bitmap getAlbumArtImage(long resId) {
        Uri baseUriForAudioFiles = Uri.parse("android.resource://" + getActivity().getPackageName() + "/raw");
        Uri uri = ContentUris.withAppendedId(baseUriForAudioFiles, resId);
        Bitmap image;

        MediaMetadataRetriever mData = new MediaMetadataRetriever();
        mData.setDataSource(getActivity(), uri);
        byte art[] = mData.getEmbeddedPicture();
        image = BitmapFactory.decodeByteArray(art, 0, art.length);

        return image;
    }

    private void initializePlaybackController() {
        MediaPlayerHolder mediaPlayerHolder = new MediaPlayerHolder(getActivity());
        Log.d(TAG, "initializePlaybackController: created MediaPlayerHolder");
        mPlayerAdapter = mediaPlayerHolder;
    }
}
