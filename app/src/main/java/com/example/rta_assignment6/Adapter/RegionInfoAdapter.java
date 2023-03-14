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
import java.util.List;

public class RegionInfoAdapter extends RecyclerView.Adapter<RegionInfoAdapter.RegionInfoVH>{
    private Context mContext;
    private List<RegionInfo> regionInfoList;
    onItemClickListener listener;
    private  boolean isLoadingAdd;
    private  static  final  int TYPE_LOADING  = 2;
    private  static  final  int TYPE_ITEM  = 1;





    public RegionInfoAdapter(Context mContext, List<RegionInfo> regionInfoList,onItemClickListener listener) {
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
    public int getItemViewType(int position) {
        if (regionInfoList != null  && position == regionInfoList.size() && isLoadingAdd){
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull RegionInfoVH holder, int position) {
        RegionInfo regionInfo = regionInfoList.get(position);

        holder.tvRegion.setText(regionInfo.getRegion() + position);
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
    public void addFooterLoanding(){
        isLoadingAdd = true;
        regionInfoList.add(new RegionInfo());
    }
    public void removeFooterLoanding (){
        isLoadingAdd = false;
        int position = regionInfoList.size() -1;
        RegionInfo info = regionInfoList.get(position);
        if(info != null){
            regionInfoList.remove(position);
            notifyItemRemoved(position);
        }



    }
}

