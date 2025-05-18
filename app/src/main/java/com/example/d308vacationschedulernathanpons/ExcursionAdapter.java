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

import com.example.d308vacationschedulernathanpons.model.Excursion;
import com.example.d308vacationschedulernathanpons.model.Vacation;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {

    private List<Excursion> mExcursions;
    private final Context context;
    private LayoutInflater mInflator;

    public class ExcursionViewHolder extends RecyclerView.ViewHolder {

        private final CardView mVacationItemView;
        private final TextView mVacationCardText;


        public ExcursionViewHolder(@NonNull View itemView) {
            super(itemView);

            mVacationItemView = itemView.findViewById(R.id.vacation_card);
            mVacationCardText = itemView.findViewById(R.id.card_text_view);
            mVacationItemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    final Excursion mCurrentExcursion = mExcursions.get(position);
                    Intent intent = new Intent(context, ExcursionDetailsActivity.class);
                    intent.putExtra("id", mCurrentExcursion.getId());
                    intent.putExtra("excursion_name", mCurrentExcursion.getExcursionName());
                    intent.putExtra("excursion_date", mCurrentExcursion.getExcursionDate());
                    intent.putExtra("vacation_id", mCurrentExcursion.getVacationId());
                    context.startActivity(intent);
                }
            });
        }
    }

    public ExcursionAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ExcursionAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.vacation_list_item_card, parent, false);
        return new ExcursionAdapter.ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {
        if (mExcursions != null) {
            Excursion mCurrentExcursion = mExcursions.get(position);
            String mExcursionName = mCurrentExcursion.getExcursionName();
            holder.mVacationCardText.setText(mExcursionName);
        }
        else {
            holder.mVacationCardText.setText("No Name Found");
        }
    }

    @Override
    public int getItemCount() {
        if (mExcursions != null) {
            return mExcursions.size();
        }
        else {
            return 0;
        }
    }

    public void setmExcursions(List<Excursion> excursions) {
        mExcursions = excursions;
        notifyDataSetChanged();

    }
}
