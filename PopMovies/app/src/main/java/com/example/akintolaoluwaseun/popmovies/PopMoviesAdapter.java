package com.example.akintolaoluwaseun.popmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.akintolaoluwaseun.popmovies.domain.RecipeBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by AKINTOLA OLUWASEUN on 7/25/2017.
 */


public class PopMoviesAdapter extends RecyclerView.Adapter<PopMoviesAdapter.MyMoviesVH>{

    final private ListItemClickListener mListClickListener;
    private Context mContext;
    private List<RecipeBean.AllResults> MyMovie_List;
    private LayoutInflater inflater;


    public interface ListItemClickListener{
        void onItemClick(int position, List<RecipeBean.AllResults> MyMovie_List);
    }
    public PopMoviesAdapter(Context context, List<RecipeBean.AllResults> mlist, ListItemClickListener mclicklistener){
        this.MyMovie_List = mlist;
        Log.d("PopAdapter", String.valueOf(MyMovie_List));
        this.inflater = LayoutInflater.from(context);
        mContext = context;

        mListClickListener = mclicklistener;
    }
    @Override
    public MyMoviesVH onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.list_item, viewGroup, false);
        MyMoviesVH viewHolder = new MyMoviesVH(view);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return MyMovie_List.size();

    }

    @Override
    public void onBindViewHolder(MyMoviesVH mholder, int position) {
        mholder.bindview(position);
    }

    public class MyMoviesVH extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        ImageView poster_img;

        public MyMoviesVH(View itemView) {
            super(itemView);
            poster_img = (ImageView) itemView.findViewById(R.id.rv_image);
            itemView.setOnClickListener(this);
        }

        void bindview(int i){
            RecipeBean.AllResults movie = MyMovie_List.get(i);
            Picasso.with(mContext).load(movie.getPoster_path()).into(poster_img);
        }

        @Override
        public void onClick(View v) {
            int itemClickedIndex = getAdapterPosition();
            mListClickListener.onItemClick(itemClickedIndex, MyMovie_List);
        }
    }
}
