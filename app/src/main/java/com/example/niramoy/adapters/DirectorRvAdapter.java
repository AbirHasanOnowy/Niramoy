package com.example.niramoy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.niramoy.R;
import com.example.niramoy.classes.DirectorMainClass;

import java.util.ArrayList;

public class DirectorRvAdapter extends RecyclerView.Adapter<DirectorRvAdapter.AdminViewHolder> {
    private static AdminAdapter.CustomClickListener mCustomClickListener;
    private final ArrayList<DirectorMainClass> directorArray;
    private DirectorMainClass directorAdminClass;
    private  String dir;
    private final Context mContext;
    private final int bookedColor;
    private final int unBookedColor;

    //constructor
    public DirectorRvAdapter(Context context, int bookedColor, int unbookedColor, ArrayList<DirectorMainClass> directorAdminClassArrayList) {
        this.directorArray = directorAdminClassArrayList;
        this.mContext = context;
        this.bookedColor = bookedColor;
        this.unBookedColor = unbookedColor;
    }

    public void setCustomClickListener(AdminAdapter.CustomClickListener customClickListener) //called from mainactivity
    {
        mCustomClickListener = customClickListener; //setting data
    }

    @NonNull
    @Override
    public DirectorRvAdapter.AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  //an object of roomview holder which contain itemview
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_director, parent, false);
        return new DirectorRvAdapter.AdminViewHolder(view); //passed in itemview

    }

    @Override
    public void onBindViewHolder(@NonNull DirectorRvAdapter.AdminViewHolder holder, int position) {
        directorAdminClass = directorArray.get(position);
        dir=directorAdminClass.getEmployeeName();
        holder.empNameTV.setText(dir);
        dir=directorAdminClass.getEmployeeEmail();
        holder.employeeEmailTV.setText(dir);
        dir=directorAdminClass.getHospitalID();
        holder.hospitalIdTV.setText(dir);
        dir=directorAdminClass.getEmployeePosition();
        holder.empPos.setText(dir);
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
        TextView employeeEmailTV, empPos;
        CardView containerCardView;
        TextView hospitalIdTV, empNameTV;
        //ImageView directorProfilePicIV;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
//            roomNameTextView = itemView.findViewById(R.id.tvRoomName);
//            containerCardView = itemView.findViewById(R.id.llContainerCardView);
//            roomRatingTextView = itemView.findViewById(R.id.tvRoomRating);
            //  containerCardView=itemView.findViewById(R.id.llContainerCardView);
            employeeEmailTV=itemView.findViewById(R.id.empEmailTV);

            hospitalIdTV=itemView.findViewById(R.id.hospitalIdTV);
            empNameTV=itemView.findViewById(R.id.empNameTV);
            empPos=itemView.findViewById(R.id.empPositionTV);

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

