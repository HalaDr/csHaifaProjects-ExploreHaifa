package com.project.explorehaifa;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.explorehaifa.model.Feedback;

import java.util.ArrayList;
public class RecyclerViewAdapterFeedbacks extends RecyclerView.Adapter<RecyclerViewAdapterFeedbacks.ViewHolder> {


    Context parentContext = null;
    ArrayList<Feedback> listFeedbacks = null;


    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        RatingBar rbRatingBar = null;
        TextView tvFeedbackMsg = null;
        TextView tvWriter = null;
        TextView tvDate = null;
        ImageView photo = null;

        public TextView getTvDate() {
            return tvDate;
        }

        public void setTvDate(TextView tvDate) {
            this.tvDate = tvDate;
        }

        public RatingBar getRbRatingBar() {
            return rbRatingBar;
        }

        public void setRbRatingBar(RatingBar rbRatingBar) {
            this.rbRatingBar = rbRatingBar;
        }

        public TextView getTvFeedbackMsg() {
            return tvFeedbackMsg;
        }

        public void setTvFeedbackMsg(TextView tvFeedbackMsg) {
            this.tvFeedbackMsg = tvFeedbackMsg;
        }

        public TextView getTvWriter() {
            return tvWriter;
        }

        public void setTvWriter(TextView tvWriter) {
            this.tvWriter = tvWriter;
        }

        public ImageView getPhoto() {
            return photo;
        }

        public void setPhoto(ImageView photo) {
            this.photo = photo;
        }
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    public RecyclerViewAdapterFeedbacks(ArrayList<Feedback> listFeedbacks)
    {
        this.listFeedbacks = listFeedbacks;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedback_row,parent,false);

        parentContext = parent.getContext();

        ViewHolder svh = new ViewHolder(v);
        svh.setRbRatingBar((RatingBar) v.findViewById(R.id.ratingBar));
        svh.getRbRatingBar().setMax(5);
        svh.setTvFeedbackMsg((TextView) v.findViewById(R.id.tv_feedback_msg));
        svh.setTvDate((TextView) v.findViewById(R.id.tv_date));
        svh.setTvWriter((TextView) v.findViewById(R.id.tv_writer));
        svh.setPhoto((ImageView) v.findViewById(R.id.feedback_img));

        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Feedback feedback = listFeedbacks.get(position);
        holder.getRbRatingBar().setRating(Float.valueOf(feedback.getRating()));
        holder.getTvFeedbackMsg().setText(feedback.getMessage());
//        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(parentContext);
//        String dateAsStr = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
//                .format(feedback.getDate());
        String TAG = "ExpHaifa-Feedbacks";
      //  Log.d(TAG, "writerName: " + feedback.getWriterName() );
        holder.getTvWriter().setText( feedback.getWriterName());
        holder.getTvDate().setText( feedback.getDate() );
        holder.getPhoto().setVisibility(View.INVISIBLE);
        if(feedback.getPhotoUri() != null) {
            Uri pictureUri = Uri.parse(feedback.getPhotoUri());
 //           RequestOptions options = new RequestOptions()
//                    .centerCrop();
//                    .placeholder(R.drawable.defaultprofilepicture)
//                    .error(R.drawable.defaultprofilepicture);

            Glide.with(holder.itemView.getContext()).load(pictureUri).into(holder.getPhoto());
            holder.getPhoto().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.listFeedbacks.size();
    }
}
