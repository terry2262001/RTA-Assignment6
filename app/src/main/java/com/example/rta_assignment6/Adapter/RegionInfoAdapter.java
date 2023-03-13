package com.example.rta_assignment6.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rta_assignment6.Model.RegionInfo;
import com.example.rta_assignment6.R;

import java.util.ArrayList;

public class RegionInfoAdapter extends RecyclerView.Adapter<RegionInfoAdapter.RegionInfoVH>{
    private Context mContext;
    private ArrayList<RegionInfo> regionInfoList;
    onItemClickListener listener;

    public RegionInfoAdapter(Context mContext, ArrayList<RegionInfo> regionInfoList) {
        this.mContext = mContext;
        this.regionInfoList = regionInfoList;
    }
    public RegionInfoAdapter(Context mContext, ArrayList<RegionInfo> regionInfoList,onItemClickListener listener) {
        this.mContext = mContext;
        this.regionInfoList = regionInfoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RegionInfoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_infor,parent,false);
        return new RegionInfoAdapter.RegionInfoVH(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RegionInfoVH holder, int position) {
        RegionInfo regionInfo = regionInfoList.get(position);

        holder.tvRegion.setText(regionInfo.getRegion());
        holder.tvWhere_coordinates.setText(regionInfo.getWhere_coordinates());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick( regionInfo,holder.getAdapterPosition());


            }
        });


    }

    @Override
    public int getItemCount() {
        return regionInfoList.size();
    }

    class RegionInfoVH extends RecyclerView.ViewHolder{

        TextView tvRegion,tvWhere_coordinates;



        public RegionInfoVH(@NonNull View itemView) {

            super(itemView);

            tvRegion = itemView.findViewById(R.id.tvRegion);
            tvWhere_coordinates = itemView.findViewById(R.id.tvWhere_coordinates);


        }
    }
    public interface onItemClickListener{
        void OnItemClick( RegionInfo regionInfo, int pos);
    }
}

