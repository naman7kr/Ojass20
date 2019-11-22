package ojass20.nitjsr.in.ojass.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import ojass20.nitjsr.in.ojass.Models.FeedPost;
import ojass20.nitjsr.in.ojass.R;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<FeedPost> feedPosts;

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout feedLayout;
        public TextView subevent_name;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            feedLayout = (LinearLayout) itemView;
            subevent_name=itemView.findViewById(R.id.feed_post_sub_event_name);
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
                feedPosts.get(position).getEvent()
        );

        //Log.e("HEEE", " event = "+feedPosts.get(position).getEvent() );
        //Log.e("HEEE", " subevent = "+feedPosts.get(position).getSubEvent() );

        ((TextView) (customViewHolder.feedLayout.findViewById(R.id.feed_post_sub_event_name))).setText(
                feedPosts.get(position).getSubEvent()
        );

        //((CustomViewHolder) holder).subevent_name.setText(feedPosts.get(position).getSubEvent());
        //Toast.makeText(context, "subevent name "+feedPosts.get(position).getEvent(), Toast.LENGTH_SHORT).show();

        ((TextView) (customViewHolder.feedLayout.findViewById(R.id.feed_post_content))).setText(
                feedPosts.get(position).getContent()
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
        Log.e("Hey", feedPosts.get(position).getImageURL());
        if (feedPosts.get(position).getImageURL() == null) {
            postImageView.setVisibility(View.GONE);
        } else {
            Glide.with(context)
                    .load(feedPosts.get(position).getImageURL())
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

        return feedPosts.size();
    }

    public FeedAdapter(Context context, ArrayList<FeedPost> mfeedPosts) {
        this.context = context;
        feedPosts = new ArrayList<>();
        this.feedPosts = mfeedPosts;
        Log.e("VIVZ", "FeedAdapter: called COUNT = " + mfeedPosts.size());
    }
}
