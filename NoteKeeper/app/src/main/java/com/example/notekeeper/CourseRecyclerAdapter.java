package com.example.notekeeper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/*Our class NoteRecyclerAdapter extends the class RecyclerView.Adapter
and we're going to use our class NoteRecyclerAdapter.ViewHolder
to hold the information for our individual views*/
public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder>{

    //In other to create the views for the onCreateViewHolder, we will need a context
    //Context is what we often use for factoring things,
    // creating things and when we are doing UI stuff we will use our activity for that
    private final Context mContext;
    private final List<CourseInfo> mCourses;
    private final LayoutInflater mLayoutInflater;

    /*In other to create views from the layout resource, we need to use the class LayoutInflater
    and LayoutInflater's are created from a context
    * */
    public CourseRecyclerAdapter(Context context, List<CourseInfo> courses) {
        mContext = context;
        mCourses = courses;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    //Responsible to create our ViewHolder instances
    //It also needs to create the views themselves
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*The view itemView points to the root of the view that's created when that layout
        resource item_note_list is inflated.
        */
        View itemView = mLayoutInflater.inflate(R.layout.item_course_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    //Be responsible for associating data with our views
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseInfo course = mCourses.get(position);
        holder.mTextCourse.setText(course.getTitle());
        holder.mCurrentPosition = position;
    }

    @Override
    //Indicate number of data items we have
    public int getItemCount() {
        return mCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /*We make these fields public so that our NoteRecyclerAdapter
        class to be able to reference these fields directly*/
        public final TextView mTextCourse;
        //Current position of the card views
        public int mCurrentPosition;

        /*Keeps reference to any of the views that are going
        to be set at runtime for each item
        */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextCourse = itemView.findViewById(R.id.text_course);

            //When a course card is clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Show note activity for whatever the current position is using intents
                    Snackbar.make(v, mCourses.get(mCurrentPosition).getTitle(),
                            Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }
}
