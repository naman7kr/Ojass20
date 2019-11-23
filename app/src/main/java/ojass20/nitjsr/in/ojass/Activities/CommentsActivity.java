package ojass20.nitjsr.in.ojass.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import ojass20.nitjsr.in.ojass.Models.Comments;
import ojass20.nitjsr.in.ojass.R;

public class CommentsActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private ImageView back;
    private RecyclerView comments_recycler_view;
    private LinearLayoutManager linearLayoutManager;

    private CircleImageView self_dp;
    private EditText self_comment_text;
    private TextView send_comment_button;

    private ArrayList<Comments> comment_list;

    private DatabaseReference dref;
    private FirebaseAuth mauth;
    private String current_user_id="83";
    private String current_post_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initilise();


        current_post_id=getIntent().getExtras().get("PostId").toString();

        comment_list=new ArrayList<>();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setSupportActionBar(mtoolbar);

        mauth=FirebaseAuth.getInstance();
        //current_user_id=mauth.getCurrentUser().getUid();
        dref= FirebaseDatabase.getInstance().getReference().child("Feeds").child(current_post_id).child("comments");

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comment_list.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String msender=ds.child("sender").getValue().toString();
                    String mmessage=ds.child("message").getValue().toString();
                    String mtime=ds.child("time").getValue().toString();

                    String m_image_url="";
                    //m_image_url=mauth.getCurrentUser().getPhotoUrl().toString();

                    Comments mcomment=new Comments(msender,mmessage,mtime,m_image_url);
                    comment_list.add(mcomment);
                }
                setuprecyclerview();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        send_comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
    }

    private void sendComment() {
        if(validate()){
            HashMap<String,Object> hs=new HashMap<>();
            hs.put("sender",current_user_id);
            hs.put("message",self_comment_text.getText().toString().trim());
            hs.put("time","2 min");

            String push_id=dref.push().getKey().toString();
            dref.child(push_id).setValue(hs).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        self_comment_text.setText("");
                        comments_recycler_view.scrollToPosition(0);
                    }
                    else{
                        Toast.makeText(CommentsActivity.this, "Comment not sent...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(this, "Add a comment", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validate() {
        if(self_comment_text.getText().toString().trim().equals("")){
            return false;
        }
        return true;
    }

    public void setuprecyclerview()
    {
        ArrayList<Comments> temp=new ArrayList<>();
        for(int i=comment_list.size()-1;i>=0;i--)
        {
            temp.add(comment_list.get(i));
        }

        linearLayoutManager=new LinearLayoutManager(CommentsActivity.this);
        comments_recycler_view.setLayoutManager(linearLayoutManager);
        comments_adapter myadap=new comments_adapter(temp);
        comments_recycler_view.setAdapter(myadap);
    }

    private void initilise() {
        mtoolbar=findViewById(R.id.toolbar_comment_activity);
        back=findViewById(R.id.comment_back_button);
        comments_recycler_view=findViewById(R.id.comments_recycler_view);
        self_dp=findViewById(R.id.self_profile_pic);
        self_comment_text=findViewById(R.id.self_comment_edittext);
        send_comment_button=findViewById(R.id.comment_send_button);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comments_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.share_toolbar){
            Toast.makeText(this, "thoda wait kr lo..", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class comments_adapter extends RecyclerView.Adapter<comments_adapter.myviewholder>
    {
        ArrayList<Comments> mdatalist;
        public comments_adapter(ArrayList<Comments> list_of_data) {
            this.mdatalist=list_of_data;
        }

        @NonNull
        @Override
        public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
            myviewholder mviewholder=new myviewholder(view);
            return mviewholder;
        }

        @Override
        public void onBindViewHolder(@NonNull final myviewholder holder, int position) {
            holder.username.setText(mdatalist.get(position).getSender());
            holder.time.setText(mdatalist.get(position).getTime());
            holder.content.setText(mdatalist.get(position).getMessage());

//            Glide.with(CommentsActivity.this)
//                    .load(mdatalist.get(position).getSender_image_url())
//                    .listener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            holder.sender_pic.setImageResource(R.drawable.ic_mood_black_24dp);
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            return false;
//                        }
//                    })
//                    .into(holder.sender_pic);

        }

        @Override
        public int getItemCount() {
            return mdatalist.size();
        }

        public class myviewholder extends RecyclerView.ViewHolder
        {
            TextView username,time,content;
            CircleImageView sender_pic;

            public myviewholder(@NonNull View itemView) {
                super(itemView);
                username=itemView.findViewById(R.id.comment_item_sender);
                time=itemView.findViewById(R.id.comment_item_time);
                content=itemView.findViewById(R.id.comment_item_message);
                sender_pic=itemView.findViewById(R.id.comment_item_photo);
            }
        }

    }
}
