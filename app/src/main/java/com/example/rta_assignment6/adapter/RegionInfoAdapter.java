package com.example.rta_assignment6.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rta_assignment6.fragment.ListFragment;
import com.example.rta_assignment6.model.RegionInfo;
import com.example.rta_assignment6.R;

import java.util.List;

public class RegionInfoAdapter extends RecyclerView.Adapter<RegionInfoAdapter.RegionInfoVH>{
    private Context mContext;
    private List<RegionInfo> regionInfoList;
    onItemClickListener listener;
    private  static  final  int TYPE_LOADING  = 2;
    private  static  final  int TYPE_ITEM  = 1;


    private OnLoadMoreListener onLoadMoreListener;







    public RegionInfoAdapter(Context mContext, List<RegionInfo> regionInfoList,onItemClickListener listener) {
        this.mContext = mContext;
        this.regionInfoList = regionInfoList;
        this.listener = listener;
    }
//public RegionInfoAdapter(List<RegionInfo> regionInfoList, RecyclerView recyclerView) {
//    this.regionInfoList = regionInfoList;
//    final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//            int totalItemCount = linearLayoutManager.getItemCount();
//            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
//            if (!isLoading && totalItemCount <= (lastVisibleItemPosition + visibleItemCount)) {
//                if (onLoadMoreListener != null) {
//                    onLoadMoreListener.onLoadMore();
//                }
//                isLoading = true;
//            }
//        }
//    });
//}

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }



    @NonNull
    @Override
    public RegionInfoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_infor,parent,false);
        return new RegionInfoAdapter.RegionInfoVH(view);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_infor, parent, false);
//        return new RegionInfoVH(view);

    }


    @Override
    public void onBindViewHolder(@NonNull RegionInfoVH holder, int position) {
        RegionInfo regionInfo = regionInfoList.get(position);
            holder.tvId.setText("ID: "+ regionInfo.getId() );

        if (regionInfo.getConflict_name().isEmpty()) {
            holder.tvConflict_name.setText("- No data -");
        } else {
            holder.tvConflict_name.setText(regionInfo.getConflict_name());
        }
        if (regionInfo.getRegion().isEmpty()) {
            holder.tvRegion.setText("- No data -");
        } else {
            holder.tvRegion.setText("Region: "+regionInfo.getRegion() + (position + 1));
        }
        if (regionInfo.getCountry().isEmpty()) {
            holder.tvCountry.setText("- No data -");
        } else {
            holder.tvCountry.setText("Country: "+regionInfo.getCountry());
        }
        if (regionInfo.getWhere_coordinates().isEmpty()) {
            holder.tvWhere_coordinates.setText("- No data -");
        } else {
            holder.tvWhere_coordinates.setText(regionInfo.getWhere_coordinates());
        }
        if (regionInfo.getSource_article().isEmpty()) {
            holder.tvsource_article.setText("- No data -");
        } else {
            holder.tvsource_article.setText(regionInfo.getSource_article());
        }



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

        TextView tvId,tvConflict_name,tvRegion,tvCountry,tvWhere_coordinates,tvsource_article ;



        public RegionInfoVH(@NonNull View itemView) {

            super(itemView);

            tvId = itemView.findViewById(R.id.tvId);
            tvConflict_name = itemView.findViewById(R.id.tvConflict_name);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            tvsource_article = itemView.findViewById(R.id.tvsource_article);
            tvRegion = itemView.findViewById(R.id.tvRegion);
            tvWhere_coordinates = itemView.findViewById(R.id.tvWhere_coordinates);


        }
    }
    public interface onItemClickListener{
        void OnItemClick( RegionInfo regionInfo, int pos);
    }
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

}

