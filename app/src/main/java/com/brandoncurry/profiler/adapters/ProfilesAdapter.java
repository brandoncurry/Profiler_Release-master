package com.brandoncurry.profiler.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brandoncurry.profiler.R;
import com.brandoncurry.profiler.Profiler;
import com.brandoncurry.profiler.models.Profile;
import com.brandoncurry.profiler.ui.profile.ProfileActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ProfilesAdapter extends RecyclerView.Adapter<ProfilesAdapter.ProfileViewHolder> {
    private ArrayList<Profile> profiles;
    private Context context;
    private FirebaseUser thisUser;

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rlProfileRow;
        public ImageView ivProfilePhoto, ivGender;
        public TextView txtProfileName, txtProfileAge, txtProfileId;

        public ProfileViewHolder(View view) {
            super(view);
            rlProfileRow = view.findViewById(R.id.profileRow);
            ivProfilePhoto =  view.findViewById(R.id.ivProfileImage);
            txtProfileName = view.findViewById(R.id.tvProfileName);
            txtProfileAge = view.findViewById(R.id.tvAge);
            txtProfileId = view.findViewById(R.id.tvUserId);
            ivGender = view.findViewById(R.id.ivGender);
        }
    }


    public ProfilesAdapter(Context context, ArrayList<Profile> myDataset) {
        profiles = myDataset;
        this.context = context;
    }


    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_profile, parent, false);
        ProfileViewHolder profileViewHolder = new ProfileViewHolder(view);
        return profileViewHolder;
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, final int position) {
        holder.rlProfileRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisUser = FirebaseAuth.getInstance().getCurrentUser();

                if(thisUser!=null){
                    Intent profileIntent = new Intent(context, ProfileActivity.class);
                    profileIntent.putExtra(ProfileActivity.PROFILE_ID, profiles.get(position).id);
                    profileIntent.putExtra(ProfileActivity.PROFILE_TYPE_SELF, false);
                    context.startActivity(profileIntent);
                } else {
                    Profiler.showLoginDialog(context, "view profiles");
                }


            }
        });

        Glide.with(context)
                .load(profiles.get(position).imageUrl)
                .into(holder.ivProfilePhoto);

        holder.txtProfileName.setText(profiles.get(position).name);
        holder.txtProfileAge.setText(profiles.get(position).age);
        holder.txtProfileId.setText(profiles.get(position).id.substring(0,8));

        switch (profiles.get(position).gender){
            case "Male":
                holder.ivGender.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_male));
                break;
            case "Female":
                holder.ivGender.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_female));
                break;
            default:
        }

    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

}