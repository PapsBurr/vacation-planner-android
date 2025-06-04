package com.example.d308vacationschedulernathanpons;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationschedulernathanpons.model.Vacation;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {

    private List<Vacation> mVacations;
    private final Context context;
    private LayoutInflater mInflator;

    public void setFilteredVacations(List<Vacation> filteredVacations) {
        this.mVacations = filteredVacations;
        notifyDataSetChanged();
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {

        private final CardView mVacationItemView;
        private final TextView mVacationCardText;


        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);

            mVacationItemView = itemView.findViewById(R.id.vacation_card);
            mVacationCardText = itemView.findViewById(R.id.card_text_view);
            mVacationItemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    final Vacation mCurrentVacation = mVacations.get(position);
                    Intent intent = new Intent(context, VacationDetailsActivity.class);
                    intent.putExtra("id", mCurrentVacation.getId());
                    intent.putExtra("vacation_title", mCurrentVacation.getVacationTitle());
                    intent.putExtra("hotel_name", mCurrentVacation.getHotelName());
                    intent.putExtra("start_date", mCurrentVacation.getStartDate());
                    intent.putExtra("end_date", mCurrentVacation.getEndDate());
                    intent.putExtra("user_id", mCurrentVacation.getUserId());
                    context.startActivity(intent);
                }
            });
        }
    }

    public VacationAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflator = LayoutInflater.from(context);
        View itemView = mInflator.inflate(R.layout.vacation_list_item_card, parent, false);
        return new VacationAdapter.VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if (mVacations != null) {
            Vacation mCurrentVacation = mVacations.get(position);
            String mVacationTitle = mCurrentVacation.getVacationTitle();
            holder.mVacationCardText.setText(mVacationTitle);
        }
        else {
            holder.mVacationCardText.setText("No Title Found");
        }
    }

    @Override
    public int getItemCount() {
        if (mVacations != null) {
            return mVacations.size();
        }
        else {
            return 0;
        }
    }

    public void setVacations(List<Vacation> vacations) {
        mVacations = vacations;
        notifyDataSetChanged();

    }


}
