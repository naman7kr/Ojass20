package ojass20.nitjsr.in.ojass.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import ojass20.nitjsr.in.ojass.Models.NotificationModal;
import ojass20.nitjsr.in.ojass.R;

/**
 * Created by Abhishek on 06-Feb-18.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context context;
    private ArrayList<NotificationModal> notificationModals;

    public NotificationAdapter(Context context, ArrayList<NotificationModal> notificationModals){
        this.context = context;
        this.notificationModals = notificationModals;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationModal Noti = notificationModals.get(position);
        holder.tvTitle.setText(Noti.getTitle());
        holder.tvBody.setText(Noti.getBody());
    }

    @Override
    public int getItemCount() {
        return notificationModals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvBody;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_noti_title);
            tvBody = itemView.findViewById(R.id.tv_noti_body);
        }
    }
}
