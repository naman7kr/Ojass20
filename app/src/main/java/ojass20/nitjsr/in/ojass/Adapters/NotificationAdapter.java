package ojass20.nitjsr.in.ojass.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ojass20.nitjsr.in.ojass.Models.NotificationModal;
import ojass20.nitjsr.in.ojass.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    ArrayList<NotificationModal> datalist;
    Context context;

    public NotificationAdapter(Context context, ArrayList<NotificationModal> datalist) {
        this.datalist = datalist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_notif,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.header.setText(datalist.get(datalist.size()-1-position).getHeader());
        holder.body.setText(datalist.get(datalist.size()-1-position).getBody());
        holder.root.getBackground().setAlpha(50);

        boolean isExpanded = datalist.get(datalist.size()-1-position).isExplandable();
        holder.body.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout root;
        TextView header,body;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            body = itemView.findViewById(R.id.body);
            root = itemView.findViewById(R.id.root);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NotificationModal data = datalist.get(datalist.size()-1-getAdapterPosition());
                    data.setExplandable(!data.isExplandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}