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
import com.example.niramoy.classes.TestClass;

import java.util.ArrayList;

public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.TestViewHolder> {
    private static TestsAdapter.CustomClickListener mCustomClickListener;
    private final ArrayList<TestClass> prescriptionClassArray;
    private TestClass directorAdminClass;
    private  String dir;
    private final Context mContext;
    private final int bookedColor;
    private final int unBookedColor;

    //constructor
    public TestsAdapter(Context context, int bookedColor, int unbookedColor, ArrayList<TestClass> directorAdminClassArrayList) {
        this.prescriptionClassArray = directorAdminClassArrayList;
        this.mContext = context;
        this.bookedColor = bookedColor;
        this.unBookedColor = unbookedColor;
    }

    public void setCustomClickListener(TestsAdapter.CustomClickListener customClickListener) //called from mainactivity
    {
        mCustomClickListener = customClickListener; //setting data
    }

    @NonNull
    @Override
    public TestsAdapter.TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  //an object of roomview holder which contain itemview
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_test, parent, false);
        return new TestsAdapter.TestViewHolder(view); //passed in itemview

    }


    @Override
    public void onBindViewHolder(@NonNull TestsAdapter.TestViewHolder holder, int position) {
        directorAdminClass = prescriptionClassArray.get(position);
        dir=directorAdminClass.getPatientId();
        holder.patientIdTV.setText(dir);
        dir=directorAdminClass.getDoctorName();
        holder.doctorNameTV.setText(dir);
        dir=directorAdminClass.getDoctorSpecs();
        holder.doctorSpecsTV.setText(dir);
        dir=directorAdminClass.getHospitalID();
        holder.hospitalIdTV.setText(dir);
        dir=directorAdminClass.getTestName();
        holder.testnameTV.setText(dir);
        dir=directorAdminClass.getTestReport();
        holder.TestReportTV.setText(dir);
        dir=directorAdminClass.getTestSummery();
        holder.TestSummeryTV.setText(dir);
        dir=directorAdminClass.getDateAndTime();
        holder.dateAndTimeTV.setText(dir);

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
        return prescriptionClassArray.size();
    }

    public interface CustomClickListener {
        void customOnClick(int position, View v);

        void customOnLongClick(int position, View v);
        //declaring method which will provide to main activity //position and view will also be provided
    }

    public class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        CardView containerCardView;
        TextView testnameTV, patientIdTV, doctorNameTV, doctorSpecsTV, hospitalIdTV, TestReportTV,TestSummeryTV,dateAndTimeTV;
        //ImageView directorProfilePicIV;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
//            roomNameTextView = itemView.findViewById(R.id.tvRoomName);
//            containerCardView = itemView.findViewById(R.id.llContainerCardView);
//            roomRatingTextView = itemView.findViewById(R.id.tvRoomRating);
            patientIdTV=itemView.findViewById(R.id.patientIdTestTV);
            doctorNameTV=itemView.findViewById(R.id.doctorNameTestTV);
            doctorSpecsTV=itemView.findViewById(R.id.doctorSpecsTestTV);
            testnameTV=itemView.findViewById(R.id.TestNameTV);
            hospitalIdTV=itemView.findViewById(R.id.hospitalIdTestTV);
            TestReportTV=itemView.findViewById(R.id.testReportTV);
            TestSummeryTV=itemView.findViewById(R.id.testSummeryTV);
            dateAndTimeTV=itemView.findViewById(R.id.dateAndTimeTestTV);


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

