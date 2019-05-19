package com.anioncode.drzewostan.aainne;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.anioncode.drzewostan.aainne.EditTrees;
import com.anioncode.drzewostan.R;

import java.util.ArrayList;

public class CustomeAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<EditTrees> editModelArrayList;

    public CustomeAdapter(Context context, ArrayList<EditTrees> editModelArrayList) {

        this.context = context;
        this.editModelArrayList = editModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return editModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return editModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {



        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listrow, null, true);

         //   holder.editText = (EditText) convertView.findViewById(R.id.editid);
            holder.textView = (TextView) convertView.findViewById(R.id.zakres);
            holder.ilosc = (TextView) convertView.findViewById(R.id.ilosc);
            holder.button = (Button) convertView.findViewById(R.id.button_plus);
            holder.butto_minus = (Button) convertView.findViewById(R.id.button_minus);

            holder.spinner =(Spinner) convertView.findViewById(R.id.Spinner);

            ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(convertView.getContext(),R.array.numbers,android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spinner.setAdapter(adapter);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

       // holder.editText.setText(editModelArrayList.get(position).getEditTextValue());
        holder.textView.setText(editModelArrayList.get(position).getZakres());
        holder.ilosc.setText(String.valueOf(editModelArrayList.get(position).getIlosc()));

        holder.butto_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int zmienna=1;
                holder.ilosc.setText(String.valueOf(editModelArrayList.get(position).getIlosc()-zmienna));
                editModelArrayList.get(position).setIlosc(editModelArrayList.get(position).getIlosc()-zmienna);

            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int zmienna=1;
                holder.ilosc.setText(String.valueOf(editModelArrayList.get(position).getIlosc()+zmienna));
                editModelArrayList.get(position).setIlosc(editModelArrayList.get(position).getIlosc()+zmienna);

            }
        });

//        holder.editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                editModelArrayList.get(position).setEditTextValue(holder.editText.getText().toString());
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        return convertView;
    }

    private class ViewHolder {

      //  protected EditText editText;
        protected TextView textView;
        protected TextView ilosc;
        protected Button button;
        protected Button butto_minus;
        protected Spinner spinner;

    }


}