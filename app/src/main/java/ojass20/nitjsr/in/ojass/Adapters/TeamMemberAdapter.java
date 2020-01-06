package ojass20.nitjsr.in.ojass.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ojass20.nitjsr.in.ojass.Models.TeamMember;
import ojass20.nitjsr.in.ojass.R;

public class TeamMemberAdapter extends RecyclerView.Adapter<TeamMemberAdapter.TeamMemberViewModel>{

    OnClickItem onClickItem;
    ArrayList<TeamMember> list;

    public TeamMemberAdapter(OnClickItem onClickItem, ArrayList<TeamMember> list) {
        this.onClickItem = onClickItem;
        this.list = list;
    }



    @NonNull
    @Override
    public TeamMemberViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_team_member_item,parent,false);
        TeamMemberViewModel viewModel=new TeamMemberViewModel(view);
        return viewModel;
    }

        @Override
        public void onBindViewHolder(@NonNull TeamMemberViewModel holder, final int position) {
            holder.name.setText(list.get(position).name);
            holder.designation.setText(list.get(position).desig);
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem.onSelected(list.get(position));
                }
            });
            holder.imageView.setImageResource(list.get(position).img);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TeamMemberViewModel extends RecyclerView.ViewHolder {
        ImageView imageView;
        LinearLayout linearLayout;
        TextView name,designation;
        public TeamMemberViewModel(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.single_team_member_image);
            name=itemView.findViewById(R.id.single_team_member_name);
            designation=itemView.findViewById(R.id.single_team_member_desig);
            linearLayout=itemView.findViewById(R.id.singleItemTeamMember);
        }
    }
    public interface OnClickItem{
        public void onSelected(TeamMember teamMember);
    }
}
