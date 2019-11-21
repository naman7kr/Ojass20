package ojass20.nitjsr.in.ojass.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import ojass20.nitjsr.in.ojass.Models.FeedPost;
import ojass20.nitjsr.in.ojass.R;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private FeedPost[] feedPosts;

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout feedLayout;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            feedLayout = (LinearLayout) itemView;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout feedLayout = (LinearLayout) (LayoutInflater.from(context).inflate(
                R.layout.feed_item, parent, false));
        return new CustomViewHolder(feedLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CustomViewHolder customViewHolder = (CustomViewHolder) holder;
        ((TextView) (customViewHolder.feedLayout.findViewById(R.id.feed_post_event_name))).setText(
                feedPosts[position].getEvent()
        );
        ((TextView) (customViewHolder.feedLayout.findViewById(R.id.feed_post_sub_event_name))).setText(
                feedPosts[position].getSubEvent()
        );
        ((TextView) (customViewHolder.feedLayout.findViewById(R.id.feed_post_content))).setText(
                feedPosts[position].getContent()
        );

        RelativeLayout postImageView = (RelativeLayout) (customViewHolder.feedLayout.findViewById(
                R.id.feed_post_image_parent
        ));
        postImageView.setVisibility(View.VISIBLE);

        final ProgressBar progressBar = (ProgressBar) (customViewHolder.feedLayout.findViewById(
                R.id.feed_post_image_progress_bar
        ));
        progressBar.setVisibility(View.VISIBLE);

        final ImageView postImage = (ImageView) (customViewHolder.feedLayout.findViewById(R.id.feed_post_image));
        if (feedPosts[position].getImageURL() == null) {
            postImageView.setVisibility(View.GONE);
        } else {
            Glide.with(context)
                    .load(feedPosts[position].getImageURL())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(postImage);
        }

    }

    @Override
    public int getItemCount() {
        return feedPosts.length;
    }

    public FeedAdapter(Context context, FeedPost[] feedPosts) {
        this.context = context;
        this.feedPosts = feedPosts;
    }
}
