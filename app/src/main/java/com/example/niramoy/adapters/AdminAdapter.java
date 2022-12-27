package com.example.niramoy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.niramoy.R;
import com.example.niramoy.classes.DirectorAdminClass;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {
    private static CustomClickListener mCustomClickListener;
    private final ArrayList<DirectorAdminClass> directorArray;
    private DirectorAdminClass directorAdminClass;
    private  String dir;
    private final Context mContext;
    private final int bookedColor;
    private final int unBookedColor;

    //constructor
    public AdminAdapter(Context context, int bookedColor, int unbookedColor, ArrayList<DirectorAdminClass> directorAdminClassArrayList) {
        this.directorArray = directorAdminClassArrayList;
        this.mContext = context;
        this.bookedColor = bookedColor;
        this.unBookedColor = unbookedColor;
    }

    public void setCustomClickListener(CustomClickListener customClickListener) //called from mainactivity
    {
        mCustomClickListener = customClickListener; //setting data
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  //an object of roomview holder which contain itemview
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_admin, parent, false);
        return new AdminViewHolder(view); //passed in itemview

    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.AdminViewHolder holder, int position) {
        directorAdminClass = directorArray.get(position);
        dir=directorAdminClass.getDirectorName();
        holder.directorNameTV.setText(dir);
        dir=directorAdminClass.getDirectorEmail();
        holder.directorEmailTV.setText(dir);
        dir=directorAdminClass.getHospitalID();
        holder.hospitalIdTV.setText(dir);
//        if(!directorArray.isEmpty())
//        {
//
//        }
//        if (!ratingsList.isEmpty()) rating = ratingsList.get(position);
//        holder.roomNameTextView.setText("room no " + room.getRoomNo());
//        holder.roomRatingTextView.setText(rating);
//        if (room.getIsBooked()) {
//            Drawable buttonDrawable = holder.containerCardView.getBackground();
//            buttonDrawable = DrawableCompat.wrap(buttonDrawable);
//            //the color is a direct color int and not a color resource
//            DrawableCompat.setTint(buttonDrawable, bookedColor);
//            holder.containerCardView.setBackground(buttonDrawable);
//            holder.containerCardView.setCardBackgroundColor(bookedColor);
//        } else {
//
//            Drawable buttonDrawable = holder.containerCardView.getBackground();
//            buttonDrawable = DrawableCompat.wrap(buttonDrawable);
//            //the color is a direct color int and not a color resource
//            DrawableCompat.setTint(buttonDrawable, unBookedColor);
//            holder.containerCardView.setBackground(buttonDrawable);
//            holder.containerCardView.setCardBackgroundColor(unBookedColor);
//
//        }
    }

    @Override
    public int getItemCount() {
        return directorArray.size();
    }

    public interface CustomClickListener {
        void customOnClick(int position, View v);

        void customOnLongClick(int position, View v);
        //declaring method which will provide to main activity //position and view will also be provided
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView directorEmailTV;
        CardView containerCardView;
        TextView hospitalIdTV, directorNameTV;
        ImageView directorProfilePicIV;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
//            roomNameTextView = itemView.findViewById(R.id.tvRoomName);
//            containerCardView = itemView.findViewById(R.id.llContainerCardView);
//            roomRatingTextView = itemView.findViewById(R.id.tvRoomRating);
            directorEmailTV=itemView.findViewById(R.id.directorEmailTV);
          //  containerCardView=itemView.findViewById(R.id.llContainerCardView);
            hospitalIdTV=itemView.findViewById(R.id.hospitalIdTV);
            directorNameTV=itemView.findViewById(R.id.directorNameTV);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        } //to create view of every list item

        @Override
        public void onClick(View view) {
            mCustomClickListener.customOnClick(getAdapterPosition(), view);  //position and view setting to provide to mainactivity


        }

        public boolean onLongClick(View view) {

            mCustomClickListener.customOnLongClick(getAdapterPosition(), view);  //position and view setting to provide to mainactivity
            return true;

        }
    }


}

