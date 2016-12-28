package com.hogervries.beaconscanner.beacondetail.log;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hogervries.beaconscanner.data.model.LogItem;
import com.hogervries.beaconscanner.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Beacon BeaconScannerService, file created on 07/03/16.
 *
 * @author Boyd Hogerheijde.
 * @author Mitchell de Vries.
 */
public class LogItemListAdapter extends RecyclerView.Adapter<LogItemListAdapter.LogHolder> {

    private List<LogItem> logItems;
    private OnLogItemSelected logItemSelected;
    private Context context;

    /**
     * Creates a new Log adapter.
     *
     * @param logItems        List of log items.
     * @param logItemSelected OnBeaconSelected callback.
     * @param context         Context.
     */
    public LogItemListAdapter(List<LogItem> logItems, OnLogItemSelected logItemSelected, Context context) {
        this.logItems = logItems;
        this.logItemSelected = logItemSelected;
        this.context = context;
    }

    @Override
    public LogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View logItemView = LayoutInflater.from(context).inflate(R.layout.list_item_log, parent, false);
        return new LogHolder(logItemView);
    }

    @Override
    public void onBindViewHolder(LogHolder holder, int position) {
        // Binds log to view holder.
        holder.bindLogItem(logItems.get(position));
    }

    @Override
    public int getItemCount() {
        return logItems.size();
    }

    /**
     * Callback which has to be implemented by the hosting activity.
     * <p/>
     * Callback interface allows for a component to be a completely self-contained,
     * modular component that defines its own layout and behaviour.
     */
    public interface OnLogItemSelected {

        /**
         * Handles on logItem selected event.
         *
         * @param logItem Selected logItem.
         */
        void onLogItemSelected(LogItem logItem);
    }

    /**
     * View holder for list items displaying log data.
     */
    class LogHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.log_type_text) TextView logTypeText;
        @BindView(R.id.log_icon_image) ImageView logIconImage;
        @BindView(R.id.log_time_stamp_text) TextView logTimeStampText;

        /**
         * Creates a new Log holder.
         *
         * @param itemView View holder layout.
         */
        public LogHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        /**
         * Binds logItems to view holder to display its data.
         *
         * @param logItem logItem
         */
        public void bindLogItem(LogItem logItem) {
            logIconImage.setImageResource(logItem.getLogo());
            logTypeText.setText(logItem.getAction());
            logTimeStampText.setText(logItem.getTimeStamp());
        }

        @Override
        public void onClick(View v) {
            // TODO: 09/06/16 implement listener action.
        }
    }
}
