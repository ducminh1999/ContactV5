package com.example.contact_v1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactv4.R;

import java.util.ArrayList;

import androidx.annotation.Nullable;

import androidx.annotation.NonNull;

public class CustomAdapter extends ArrayAdapter<Contact> {
    private Context context;
    private int resourse;
    private ArrayList<Contact> arrayContact;
    private ArrayList<Contact> orig = null;
    private final String TAG = getClass().getSimpleName();

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourse = resource;
        this.arrayContact = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_listview, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imgAvt = convertView.findViewById(R.id.imgAvt);
            viewHolder.imgCall = convertView.findViewById(R.id.imgBtnCall);
            viewHolder.imgMess = convertView.findViewById(R.id.imgBtnMess);
            viewHolder.tv_Name = convertView.findViewById(R.id.tvName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contact contact = arrayContact.get(position);
        viewHolder.tv_Name.setText(contact.getName());
/*        viewHolder.imgAvt.setBackgroundResource(R.drawable.ic_person_black_24dp);
        viewHolder.imgCall.setBackgroundResource(R.drawable.ic_phone_black_24dp);*/

/*        viewHolder.tv_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact", arrayContact.get(position));
                intent.putExtra("package", bundle);
                context.startActivity(intent);
            }
        });*/
//        viewHolder.imgCall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String phone = arrayContact.get(position).getPhone();
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tell", phone, null));
//                context.startActivity(intent);
//            }
//        });
        viewHolder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = arrayContact.get(position).getPhone().toString();
                Log.d("debug1", phoneNo);
                if(!TextUtils.isEmpty(phoneNo)) {
                    String dial = "tel:" + phoneNo;
                    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(context, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewHolder.imgMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Contact> results = new ArrayList<Contact>();
                if (orig == null)
                    orig = arrayContact;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Contact g : orig) {
                            if (g.getName().toLowerCase().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                arrayContact = (ArrayList<Contact>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tv_Name;
        ImageView imgAvt, imgCall, imgMess;
    }
}
