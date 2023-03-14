package com.example.rta_assignment6;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class  PanigationScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager linearLayoutManager ;
    int lasst =1;

    public PanigationScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int  visibleItemCount = linearLayoutManager.getChildCount();
        int totalItemCount = linearLayoutManager.getItemCount();
        int firstVisbleitemPosition  = linearLayoutManager.findFirstVisibleItemPosition();
        int last = linearLayoutManager.findLastVisibleItemPosition();
        if(isLoading() || isLastPage()){
            return;
        }
        if(!recyclerView.canScrollVertically(1)){
            loadMoreItems();
            System.out.println("-------------->last"+ "----123123123");


        }
//        if (totalItemCount >7 &&)
//
//        if (last - firstVisbleitemPosition == 6){
//            //loadMoreItems();
//        }
        System.out.println("-------------->visibleItemCount_______"+(visibleItemCount + firstVisbleitemPosition));
        System.out.println("-------------->firstVisbleitemPosition"+firstVisbleitemPosition);
        System.out.println("-------------->last"+ last);



//        if ((visibleItemCount + firstVisbleitemPosition) >= totalItemCount && firstVisbleitemPosition >=4 ){
//            loadMoreItems();
//        }

//         if (visibleItemCount >){
//             loadMoreItems();
//         }
//        if(firstVisbleitemPosition >= 0 && (visibleItemCount + firstVisbleitemPosition) >= totalItemCount){
//
//            System.out.println("-------------->visibleItemCount"+visibleItemCount+"----goi lan "+i);
//            System.out.println("-------------->totalItemCount"+totalItemCount+"----goi lan "+i);
//            System.out.println("-------------->firstVisbleitemPosition"+firstVisbleitemPosition+"----goi lan "+i);
//            System.out.println("-------------->firstVisbleitemPosition"+last+"----goi lan "+i);
//
//
//
//        }
    }
    public abstract void loadMoreItems();
    public abstract boolean isLoading();
    public abstract boolean isLastPage();



}
