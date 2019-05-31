package com.example.projekat10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PrognozaAdapter extends BaseAdapter {

    private ArrayList<Prognoza> mPrognoze;
    private Context mContext;

    public PrognozaAdapter(Context context){
        mPrognoze=new ArrayList<Prognoza>();
        mContext=context;
    }

    @Override
    public int getCount() {
        return mPrognoze.size();
    }

    @Override
    public Object getItem(int position) {
        return mPrognoze.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(Prognoza[] prognoze){
        mPrognoze.clear();
        if(prognoze!=null){
            for(Prognoza prognoza:prognoze){
                mPrognoze.add(prognoza);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_element_prognoza, null);

            ViewHolder2 holder = new ViewHolder2();
            holder.mDatum = (TextView) convertView.findViewById(R.id.elementRowDatum);
            holder.mDan = (TextView) convertView.findViewById(R.id.elementRowDan);
            holder.mGrad = (TextView) convertView.findViewById(R.id.elementRowGrad);
            holder.mTemperatra = (TextView) convertView.findViewById(R.id.elementRowTemperatura);
            holder.mPritisak = (TextView) convertView.findViewById(R.id.elementRowPritisak);
            holder.mVlaznost = (TextView) convertView.findViewById(R.id.elementRowVlaznost);
            holder.mIzlazak = (TextView) convertView.findViewById(R.id.elementRowIzlazak);
            holder.mZalazak = (TextView) convertView.findViewById(R.id.elementRowZalazak);
            holder.mBrzina = (TextView) convertView.findViewById(R.id.elementRowBrzina);
            holder.mPravac = (TextView) convertView.findViewById(R.id.elementRowSmer);
            convertView.setTag(holder);
        }
        Prognoza prognoza=(Prognoza) getItem(position);
        ViewHolder2 holder=(ViewHolder2) convertView.getTag();

        holder.mDatum.setText(prognoza.getmDatum());
        holder.mDan.setText(prognoza.getmDan());
        holder.mGrad.setText(prognoza.getmGrad());
        holder.mTemperatra.setText(prognoza.getmTemperatura().toString());
        holder.mPritisak.setText(prognoza.getmPritisak());
        holder.mVlaznost.setText(prognoza.getmVlaznost());
        holder.mIzlazak.setText(prognoza.getmIzlazak());
        holder.mZalazak.setText(prognoza.getmZalazak());
        holder.mBrzina.setText(prognoza.getmBrzina().toString());
        holder.mPravac.setText(prognoza.getmPravac());

        return convertView;
    }

    private class ViewHolder2{
        public TextView mDatum=null;
        public TextView mDan=null;
        public TextView mGrad=null;
        public TextView mTemperatra=null;
        public TextView mPritisak=null;
        public TextView mVlaznost=null;
        public TextView mIzlazak=null;
        public TextView mZalazak=null;
        public TextView mBrzina=null;
        public TextView mPravac=null;

    }
}

