package rw.ac.utb.garbagemanagementapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ViewStaffAdapter extends RecyclerView.Adapter<ViewStaffAdapter.ViewEmployeeAdadpterViewHolder> {


    private Context mcontext;
    private List<Staff> muploads;

    private OnItemClickListener mListener;

    public ViewStaffAdapter(Context context, List<Staff> uploads) {

        this.mcontext = context;
        this.muploads = uploads;
    }

    @NonNull
    @Override
    public ViewEmployeeAdadpterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowliststaff, parent, false);


        return new ViewEmployeeAdadpterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewEmployeeAdadpterViewHolder holder, int position) {


        Staff uploadCurrent = muploads.get(position);
        holder.tv_Names.setText(uploadCurrent.getNames());
        holder.tv_Phone.setText(uploadCurrent.getPhone());
        holder.tv_MainLocation.setText(uploadCurrent.getMainLocation());
        holder.tv_Nid.setText(uploadCurrent.getNid());
        holder.tv_SubLocation.setText(uploadCurrent.getSubLocation());



    }

    @Override
    public int getItemCount() {
        return muploads.size();
    }

    public class ViewEmployeeAdadpterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_Names,tv_Phone,tv_MainLocation,tv_SubLocation,tv_Nid;
        public ViewEmployeeAdadpterViewHolder(@NonNull View itemView) {
            super(itemView);


            tv_Names=itemView.findViewById(R.id.tv_Names);
            tv_Phone=itemView.findViewById(R.id.tv_Phone);
            tv_MainLocation=itemView.findViewById(R.id.tv_MainLocation);
            tv_SubLocation=itemView.findViewById(R.id.tv_SubLocation);
            tv_Nid=itemView.findViewById(R.id.tv_Nid);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;

    }


}

