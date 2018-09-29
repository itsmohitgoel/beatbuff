package com.itsmohitgoel.beatbuff.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.models.MusicItem;
import com.itsmohitgoel.beatbuff.utils.MusicManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for Playlist Screen.
 */
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private ArrayList<MusicItem> allMusicItems;
    private Context mContext;

    public PlaylistAdapter(Context context) {
        mContext = context;
        this.allMusicItems = MusicManager.getInstance(context).getMusicItems();
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.track_list_item, parent, false);

        return new PlaylistViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        holder.bindMusic(allMusicItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        return allMusicItems.size();
    }


    public class PlaylistViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.track_artist_text_view)
        TextView artistTextView;
        @BindView(R.id.track_duration_text_view)
        TextView durationTextView;
        @BindView(R.id.track_number_text_view)
        TextView numberTextView;
        @BindView(R.id.track_title_text_view)
        TextView titleTextView;
        @BindView(R.id.track_image_view)
        ImageView trackImageView;
        @BindView(R.id.top_separator_view)
        View topSeparatorView;

        public PlaylistViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindMusic(MusicItem musicItem, int position) {
            if (position == 0) {
                topSeparatorView.setVisibility(View.VISIBLE);
            }
            artistTextView.setText(musicItem.getArtistName());
            durationTextView.setText(musicItem.getDuration());
            titleTextView.setText(musicItem.getTitle());
            if (musicItem.getCoverArt() != null) {
                trackImageView.setImageBitmap(musicItem.getCoverArt());
            }else {
                Bitmap tempBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.alan_walker);
                trackImageView.setImageBitmap(tempBitmap);

            }
            numberTextView.setText(++position + "");
        }
    }

}
