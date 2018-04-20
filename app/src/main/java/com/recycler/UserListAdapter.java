package com.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.recycler.models.Name;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mihir on 21-04-2018.
 */

public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Name> listUser;
    private Context context;
    private SimpleDateFormat sdf;

    public UserListAdapter(Context context) {
        this.context = context;
        listUser = new ArrayList<>();
        sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Name user = listUser.get(position); // UserList

        final UserVH userVH = (UserVH) holder;

        userVH.txtFirstName.setText(user.getInformation().getName());
        userVH.txtDescription.setText(user.getInformation().getDescription());
        userVH.txtDescription.setSelected(true);

        Date date = null;
        try {
            date = new Date(sdf.parse(user.getLastlogin()).getTime());

            int days = Days.daysBetween(new DateTime(date), new DateTime()).getDays();
            if (days <= 0) {
                int hours = Hours.hoursBetween(new DateTime(date), new DateTime()).getHours();
                if (hours <= 0) {
                    int minutes = Minutes.minutesBetween(new DateTime(date), new DateTime()).getMinutes();
                    userVH.txtLastSeen.setText(minutes <= 1 ? context.getString(R.string.just_now) : minutes + " " + context.getString(R.string.minutes_ago));
                } else {
                    userVH.txtLastSeen.setText(hours <= 1 ? hours + " " + context.getString(R.string.hour_ago) : hours + " " + context.getString(R.string.hours_ago));
                }
            } else {
                userVH.txtLastSeen.setText(days == 1 ? days + " " + context.getString(R.string.day_ago) : days + " " + context.getString(R.string.days_ago));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadImage(user.getInformation().getImage())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        userVH.mProgress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // image ready, hide progress now
                        userVH.mProgress.setVisibility(View.GONE);
                        return false;   // return false if you want Glide to handle everything else.
                    }
                })
                .into(userVH.mPosterImg);
    }

    @Override
    public int getItemCount() {
        return listUser == null ? 0 : listUser.size();
    }

    private DrawableRequestBuilder<String> loadImage(@NonNull String posterPath) {
        return Glide
                .with(context)
                .load(posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                .centerCrop()
                .crossFade();
    }

    public void add(Name r) {
        listUser.add(r);
        notifyItemInserted(listUser.size() - 1);
    }

    public void remove(Name r) {
        int position = listUser.indexOf(r);
        if (position > -1) {
            listUser.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addAll(List<Name> moveUsers) {
        for (Name user : moveUsers) {
            add(user);
        }
    }

    public Name getItem(int position) {
        return listUser.get(position);
    }

    protected class UserVH extends RecyclerView.ViewHolder {
        private TextView txtFirstName;
        private TextView txtDescription;
        private TextView txtLastSeen;
        private ImageView mPosterImg;
        private ProgressBar mProgress;

        public UserVH(View itemView) {
            super(itemView);

            txtFirstName = (TextView) itemView.findViewById(R.id.firstName);
            txtDescription = (TextView) itemView.findViewById(R.id.description);
            txtLastSeen = (TextView) itemView.findViewById(R.id.lastSeen);
            mPosterImg = (ImageView) itemView.findViewById(R.id.user_thumbnail);
            mProgress = (ProgressBar) itemView.findViewById(R.id.image_progress);
        }
    }
}
