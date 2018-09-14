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
import com.itsmohitgoel.beatbuff.listeners.PlaybackInfoListener;
import com.itsmohitgoel.beatbuff.listeners.PlayerAdapter;
import com.itsmohitgoel.beatbuff.utils.Blur;
import com.itsmohitgoel.beatbuff.utils.CircularSeekBar;
import com.itsmohitgoel.beatbuff.utils.MediaPlayerHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerFragment extends Fragment {
    private static final String TAG = PlayerFragment.class.getSimpleName();

    @BindView(R.id.google_cast_button)
    ImageView mGoogleCastButton;
    @BindView(R.id.share_button)
    ImageView mShareButton;
    @BindView(R.id.equalizer_button)
    ImageView mEqualizerButton;
    @BindView(R.id.repeat_button)
    ImageView mRepeatButton;
    @BindView(R.id.shuffle_button)
    ImageView mShuffleButton;
    @BindView(R.id.playlist_button)
    ImageView mPlaylistButton;
    @BindView(R.id.background_image_view)
    ImageView mBackgroundImageView;
    @BindView(R.id.circle_image_view)
    CircleImageView mProfileImageView;
    @BindView(R.id.play_pause_button)
    ImageView mPlayPauseButton;
    @BindView(R.id.previous_button)
    ImageView mPreviousButton;
    @BindView(R.id.next_button)
    ImageView mNextButton;
    @BindView(R.id.circular_seekbar)
    CircularSeekBar mCircularSeekBar;

    private PlayerAdapter mPlayerAdapter;
    private boolean mUserIsSeeking = false;


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
        initializeSeekbar();
        initializePlaybackController();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPlayerAdapter.loadMedia(R.raw.cheap_thrills_by_sia);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPlayerAdapter.isPlaying()) {
            //Do nothing
        } else {
            mPlayerAdapter.release();
        }
    }

    private View.OnClickListener controlButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.google_cast_button:
                    setBackgroundTint(mGoogleCastButton.getDrawable());
                    break;

                case R.id.share_button:
                    setBackgroundTint(mShareButton.getDrawable());
                    break;

                case R.id.equalizer_button:
                    setBackgroundTint(mEqualizerButton.getDrawable());
                    break;

                case R.id.repeat_button:
                    setBackgroundTint(mRepeatButton.getDrawable());
                    break;

                case R.id.shuffle_button:
                    setBackgroundTint(mShuffleButton.getDrawable());
                    break;

                case R.id.playlist_button:
                    setBackgroundTint(mPlaylistButton
                            .getDrawable());
                    break;

                case R.id.play_pause_button:
                    mPlayerAdapter.play();

                case R.id.previous_button:
                    mPlayerAdapter.previous();
                    break;
                case R.id.next_button:
                    mPlayerAdapter.next();
                    break;
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
        mProfileImageView.setImageBitmap(albumArtImage);

        Bitmap albumArtBlurImage = Blur.fastblur(getActivity(), albumArtImage, 12);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(albumArtBlurImage);
        mBackgroundImageView.setImageBitmap(albumArtBlurImage);
        mBackgroundImageView.setColorFilter(Blur.getGrayScaleFilter());

        mGoogleCastButton.setOnClickListener(controlButtonsListener);
        mShareButton.setOnClickListener(controlButtonsListener);
        mEqualizerButton.setOnClickListener(controlButtonsListener);
        mRepeatButton.setOnClickListener(controlButtonsListener);
        mShuffleButton.setOnClickListener(controlButtonsListener);
        mPlaylistButton.setOnClickListener(controlButtonsListener);
        mPlayPauseButton.setOnClickListener(controlButtonsListener);
        mPreviousButton.setOnClickListener(controlButtonsListener);
        mNextButton.setOnClickListener(controlButtonsListener);
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
        mediaPlayerHolder.setPlaybackInfoListener(new PlaybackListener());
        Log.d(TAG, "initializePlaybackController: created MediaPlayerHolder");
        mPlayerAdapter = mediaPlayerHolder;
    }

    private void initializeSeekbar() {
        mCircularSeekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            int userSelectedPosition = 0;

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {
                mUserIsSeeking = true;
            }

            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    userSelectedPosition = progress;
                }
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {
                mUserIsSeeking = false;
                mPlayerAdapter.seekTo(userSelectedPosition);
            }
        });
    }

    /**
     * Callback listener to receive callback on events like progress change, seek position
     */
    public class PlaybackListener extends PlaybackInfoListener {
        @Override
        public void onDurationChanged(int duration) {
            mCircularSeekBar.setMax(duration);
        }

        @Override
        public void onPositionChanged(final int position) {
            if (!mUserIsSeeking) {
                Log.e(TAG, "onPositionChanged(position): " + position);

                mCircularSeekBar.post(new Runnable() {
                    @Override
                    public void run() {
                        mCircularSeekBar.setProgress(position);

                    }
                });
            }
        }

    }
}
