package se.pontusoberg.kepsjakten;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.List;

/**
 * Created by pontu on 2018-01-11.
 */

public class ReportsAdapter extends RecyclerView.Adapter < ReportsAdapter.ReportViewHolder > {


    private Context mCtx;
    private List < Report > reportList;

    public ReportsAdapter(Context mCtx, List < Report > reportList) {
        this.mCtx = mCtx;
        this.reportList = reportList;
    }

    @Override
    public ReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.report_list, null);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportViewHolder holder, int position) {
        Report report = reportList.get(position);

        holder.textViewStation.setText(report.getStation());
        holder.textViewTimer.setText(report.getFormatted_timer() + " ago!");
        holder.textViewCity.setText(report.getCity() + " - " + ("Kontrollanter: " + report.getAmount()));

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    class ReportViewHolder extends RecyclerView.ViewHolder {

        TextView textViewStation, textViewAmount, textViewTimer, textViewCity;


        public ReportViewHolder(View itemView) {
            super(itemView);

            textViewStation = itemView.findViewById(R.id.textViewStation);
            //textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewTimer = itemView.findViewById(R.id.textViewTimer);
            textViewCity = itemView.findViewById(R.id.textViewCity);

        }
    }
}