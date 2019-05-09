package com.example.projekat10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class listAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Character> mCharacters;
    private RadioButton selected = null;

    public listAdapter(Context context){
        this.mContext=context;
        this.mCharacters=new ArrayList<Character>();
    }

    public void addCharacter(Character character){
        mCharacters.add(character);
        notifyDataSetChanged();
    }
    public void removeChaacter(int position){
        mCharacters.remove(position);
        notifyDataSetChanged();
    }
    @Override
    public int getCount(){
        return mCharacters.size();
    }

    @Override
    public Object getItem(int position){
        Object rv=null;
        try{
            rv=mCharacters.get(position);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return rv;
    }
    @Override
    public long getItemId(int postion){
        return postion;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= convertView;

        if(view==null){
            LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.row_element,null);
            ViewHolder holder=new ViewHolder();
            holder.button=(RadioButton) view.findViewById(R.id.rowElementRadioButton);
            holder.name=(TextView) view.findViewById(R.id.rowElementTextView);
            view.setTag(holder);
        }

        Character character=(Character) getItem(position);
        final ViewHolder holder=(ViewHolder) view.getTag();
        holder.name.setText(character.mName);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,page2.class);
                Bundle b=new Bundle();
                b.putString("text1",holder.name.getText().toString());
                i.putExtras(b);
                mContext.startActivity(i);

                selected=(RadioButton) v;
                selected.setChecked(false);
            }
        });
        return view;
    }



}
