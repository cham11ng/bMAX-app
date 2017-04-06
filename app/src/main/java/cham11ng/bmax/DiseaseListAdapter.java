package cham11ng.bmax;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sudesh chamling on 7/2/2016.
 */
public class DiseaseListAdapter extends BaseAdapter {

    Context context;
    List<DiseaseListAdapterClass> list;

    DiseaseListAdapter(Context context,List<DiseaseListAdapterClass> list) {
        this.context=context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.disease_sample_list,null);

        TextView txtDiseaseName = (TextView)convertView.findViewById(R.id.txtDiseaseName);
        TextView txtShortDefinition = (TextView)convertView.findViewById(R.id.txtShortDefination);
        Button btnStatus=(Button)convertView.findViewById(R.id.btnStatus);

        txtDiseaseName.setText(list.get(position).getDiseaseName());
        txtShortDefinition.setText(list.get(position).getShortDefinition());

        if(list.get(position).getMarkStatus()) {
            btnStatus.setBackgroundResource(R.drawable.bookmark_true);
        }
        else {
            btnStatus.setBackgroundResource(R.drawable.bookmark_false);
        }

        return convertView;
    }
}
