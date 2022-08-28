package rw.ac.utb.garbagemanagementapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewFeedbackAdapter extends RecyclerView.Adapter<ViewFeedbackAdapter.ViewEmployeeAdadpterViewHolder> {


    private Context mcontext;
    private List<Feedback> muploads;

    private OnItemClickListener mListener;

    public ViewFeedbackAdapter(Context context, List<Feedback> uploads) {

        this.mcontext = context;
        this.muploads = uploads;
    }

    @NonNull
    @Override
    public ViewEmployeeAdadpterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlistfeedback, parent, false);


        return new ViewEmployeeAdadpterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewEmployeeAdadpterViewHolder holder, int position) {


        Feedback uploadCurrent = muploads.get(position);
//        holder.tvFMessage.setText(uploadCurrent.getMessage());
        holder.tvFsenderPhone.setText(uploadCurrent.getSenderPhone());
        holder.tvFsendernames.setText(uploadCurrent.getSenderName());
        holder.tvFdateTime.setText(uploadCurrent.getDate());

    }

    @Override
    public int getItemCount() {
        return muploads.size();
    }

    public class ViewEmployeeAdadpterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvFMessage,tvFdateTime,tvFsenderPhone,tvFsendernames;
        public ViewEmployeeAdadpterViewHolder(@NonNull View itemView) {
            super(itemView);


            tvFMessage=itemView.findViewById(R.id.tvFMessage);
            tvFdateTime=itemView.findViewById(R.id. tvFdateTime);
            tvFsenderPhone=itemView.findViewById(R.id.tvFsenderPhone);
            tvFsendernames=itemView.findViewById(R.id.tvFsendernames);
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

