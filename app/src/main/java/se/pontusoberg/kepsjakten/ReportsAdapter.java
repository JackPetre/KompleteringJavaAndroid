package se.pontusoberg.kepsjakten;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


        if(report.getNumber() != "") {
            Log.d( "Hej","Nummer = "+report.getNumber());
            holder.textViewNumber.setText("Kollektivlinje: " + report.getNumber());
            holder.textViewNumber.setVisibility(View.VISIBLE);
        }
        if(report.getWay() != "") {
            holder.textViewWay.setText("Riktning: " + report.getWay());
            holder.textViewWay.setVisibility(View.VISIBLE);

        }
        if(report.getOtherinfo() != "") {
            holder.textViewOtherinfo.setText("Övrig info: " + report.getOtherinfo());
            holder.textViewOtherinfo.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    class ReportViewHolder extends RecyclerView.ViewHolder {

        TextView textViewStation, textViewAmount, textViewTimer, textViewCity, textViewNumber, textViewWay, textViewOtherinfo;


        public ReportViewHolder(View itemView) {
            super(itemView);

            textViewStation = itemView.findViewById(R.id.textViewStation);
            //textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewTimer = itemView.findViewById(R.id.textViewTimer);
            textViewCity = itemView.findViewById(R.id.textViewCity);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewNumber.setVisibility(View.INVISIBLE);

            textViewWay = itemView.findViewById(R.id.textViewWay);
            textViewWay.setVisibility(View.INVISIBLE);

            textViewOtherinfo = itemView.findViewById(R.id.textViewOtherinfo);
            textViewOtherinfo.setVisibility(View.INVISIBLE);


        }
    }
}