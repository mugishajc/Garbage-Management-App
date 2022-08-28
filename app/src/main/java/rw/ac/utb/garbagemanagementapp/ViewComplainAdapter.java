package rw.ac.utb.garbagemanagementapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewComplainAdapter extends RecyclerView.Adapter<ViewComplainAdapter.ViewEmployeeAdadpterViewHolder> {


    private Context mcontext;
    private List<Complain> muploads;

    private OnItemClickListener mListener;

    public ViewComplainAdapter(Context context, List<Complain> uploads) {

        this.mcontext = context;
        this.muploads = uploads;
    }

    @NonNull
    @Override
    public ViewEmployeeAdadpterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlistcomplain, parent, false);


        return new ViewEmployeeAdadpterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewEmployeeAdadpterViewHolder holder, int position) {


        Complain uploadCurrent = muploads.get(position);
        holder.tv_NamesComplain.setText(uploadCurrent.getClientNames());
        holder.tv_PhoneComplain.setText(uploadCurrent.getClientPhone());
        holder.tv_DateComplain.setText(uploadCurrent.getDate());
        holder.tv_ComplainStatus.setText(uploadCurrent.getStatus());

    }

    @Override
    public int getItemCount() {
        return muploads.size();
    }

    public class ViewEmployeeAdadpterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_NamesComplain,tv_PhoneComplain,tv_DateComplain,tv_ComplainStatus;
        public ViewEmployeeAdadpterViewHolder(@NonNull View itemView) {
            super(itemView);


            tv_NamesComplain=itemView.findViewById(R.id.tv_NamesComplain);
            tv_PhoneComplain=itemView.findViewById(R.id.tv_PhoneComplain);
            tv_DateComplain=itemView.findViewById(R.id.tv_DateComplain);
            tv_ComplainStatus=itemView.findViewById(R.id.tv_ComplainStatus);
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

