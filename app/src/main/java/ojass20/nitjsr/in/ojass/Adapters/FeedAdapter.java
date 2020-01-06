package ojass20.nitjsr.in.ojass.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import ojass20.nitjsr.in.ojass.Activities.MainActivity;
import ojass20.nitjsr.in.ojass.Fragments.CommentsFragment;
import ojass20.nitjsr.in.ojass.Models.FeedPost;
import ojass20.nitjsr.in.ojass.R;
import ojass20.nitjsr.in.ojass.Utils.RecyclerClickInterface;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<FeedPost> feedPosts;
    private String mcurrentuid;
    private boolean is_already_liked=false;
    private String mpost_id="";
    public CommentClickInterface clickInterface;
    private FragmentManager manager;

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout feedLayout;
        public TextView subevent_name,like_text,eventname,content, time;
        public ImageView like_icon,postImage;
        public LinearLayout like_layout,comment_layout,share_layout;
        RelativeLayout postImageView;
        ProgressBar progressBar;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            feedLayout = (LinearLayout) itemView;
            progressBar=itemView.findViewById(R.id.feed_post_image_progress_bar);
            postImageView=itemView.findViewById(R.id.feed_post_image_parent);
            postImage=itemView.findViewById(R.id.feed_post_image);
            subevent_name=itemView.findViewById(R.id.feed_post_sub_event_name);
            eventname=itemView.findViewById(R.id.feed_post_event_name);
            content=itemView.findViewById(R.id.feed_post_content);
            like_text=itemView.findViewById(R.id.like_textview);
            like_icon=itemView.findViewById(R.id.like_icon);
            like_layout=itemView.findViewById(R.id.feed_post_upvote);
            comment_layout=itemView.findViewById(R.id.comments_post);
            share_layout=itemView.findViewById(R.id.feed_post_share);
            time = itemView.findViewById(R.id.feed_post_time);
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout feedLayout = (LinearLayout) (LayoutInflater.from(context).inflate(
                R.layout.feed_item, parent, false));
        return new CustomViewHolder(feedLayout);
    }

    public FeedAdapter(Context context, FragmentManager manager, ArrayList<FeedPost> mfeedPosts, String currentuid) {
        this.context = context;
        this.feedPosts = mfeedPosts;
        this.mcurrentuid = currentuid;
        this.manager = manager;
        clickInterface = (CommentClickInterface) context;
        Log.e("VIVZ", "FeedAdapter: called COUNT = " + mfeedPosts.size());
    }

    @Override
    public int getItemCount() {
        return feedPosts.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        holder.eventname.setText(feedPosts.get(position).getEvent());
        holder.subevent_name.setText(feedPosts.get(position).getSubEvent());
        holder.content.setText(feedPosts.get(position).getContent());
        holder.like_text.setText(feedPosts.get(position).getLikes().size()+" Likes");
        holder.postImageView.setVisibility(View.VISIBLE);
        holder.progressBar.setVisibility(View.VISIBLE);
        Log.e("Hey", feedPosts.get(position).getImageURL());
        if (feedPosts.get(position).getImageURL() == null || feedPosts.get(position).getImageURL().equals("")) {
            holder.postImageView.setVisibility(View.GONE);
        } else {
            Glide.with(context)
                    .load(feedPosts.get(position).getImageURL())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.postImage);
        }
        mpost_id=feedPosts.get(position).getPostid();

        is_already_liked=feedPosts.get(position).isIs_already_liked();
        if(is_already_liked==true){
            holder.like_icon.setImageResource(R.drawable.upvoted);
        }

        Log.i( "onBindViewHolder: ",is_already_liked+"");

        long post_time = Integer.parseInt(feedPosts.get(position).getTimestamp());
        long curr_time = System.currentTimeMillis() / 1000;
        long diff = curr_time - post_time;
        String suffix, prefix;
        if(diff < 5){
            prefix = "just now";
            suffix = "";
        }
        else if(diff < 60){
            suffix = "s ago";
            prefix = diff + "";
        }
        else if(diff < 3600){
            suffix = "m ago";
            prefix = diff / 60 + "";
        }
        else if(diff < 86400){
            suffix = "hr ago";
            prefix = diff / 3600 + "";
        }
        else if(diff < 2628003){
            suffix = "d ago";
            prefix = diff / 86400 + "";
        }
        else if(diff < 31536000){
            suffix = "mo ago";
            prefix = diff / 2628003 + "";
        }
        else{
            suffix = "y ago";
            prefix = diff / 31536000 + "";
        }

        holder.time.setText(prefix + suffix);


        final DatabaseReference dref= FirebaseDatabase.getInstance().getReference().child("Feeds");

        holder.comment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                clickInterface.onCommentClick(v,feedPosts.get(position));
            }
        });

        holder.share_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,feedPosts.get(position).getEvent()+"\n"+
                        feedPosts.get(position).getSubEvent()+"\n"+
                        feedPosts.get(position).getContent() );
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent,"Share this article via:"));

            }
        });

        holder.like_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("PostNo",Integer.toString(position));
                editor.apply();

                HashMap<String,Object> nc=new HashMap<>();
                if(feedPosts.get(position).isIs_already_liked()){
                    Log.e("TAG", "onClick: level 2");
                    nc.put(mcurrentuid,null);
                    dref.child(feedPosts.get(position).getPostid()).child("likes").child(mcurrentuid).setValue(null);

                }
                else{
                    Log.e("TAG", "onClick: level 6");
                    dref.child(feedPosts.get(position).getPostid()).child("likes").child(mcurrentuid).setValue(mcurrentuid);
                }

            }
        });

    }
    public interface CommentClickInterface{
        void onCommentClick();
    }
}
