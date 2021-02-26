package com.example.shoppinglist;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class list_adapter extends RecyclerView.Adapter {
    private ArrayList<parentItem> listItems;
    private ArrayList<ListItem> copy;
    private Context context;
    public list_adapter(ArrayList<parentItem> listItem ,ArrayList<ListItem> copy1, final Context context){
        this.listItems = listItem;
        this.context = context;
        this.copy = copy1;
        MainActivity.floating.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //ListItem a = new ListItem();
                //listItems.add(a);
                final ListItem temp = new ListItem();
                editDialog myDialog = new editDialog(context,R.layout.dialog,temp,new editDialog.sendMessage(){
                    @Override
                    public void send() {
                        listItems.add(temp);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void change() {
                        //do nothing
                    }
                });
                myDialog.show();
            }
        });

    }
    public class itemHolder extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView name;
        private TextView price;
        private CheckBox purchased;
        private Button edit;
        private Button detail;


        // itemView layout
        public itemHolder(View itemView){
            super(itemView);
            this.icon = (ImageView) itemView.findViewById(R.id.icon);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.price = (TextView) itemView.findViewById(R.id.price);
            this.purchased = (CheckBox) itemView.findViewById(R.id.is_purchased);
            this.edit = (Button) itemView.findViewById(R.id.edit);
            this.detail = (Button) itemView.findViewById(R.id.click_info);
            Log.d("list_adapter","itemHolder");

        }

        public TextView getName() {
            return name;
        }

        public ImageView getIcon() {
            return icon;
        }

        public TextView getPrice() {
            return price;
        }

        public Button getEdit() {
            return edit;
        }

        public CheckBox getPurchased() {
            return purchased;
        }

        public Button getDetail() {
            return detail;
        }

    }
    public class secondaryHolder extends RecyclerView.ViewHolder{
        private TextView description;
        public secondaryHolder(View itemView){
            super(itemView);
            this.description = (TextView) itemView.findViewById(R.id.description);
            Log.d("list_adapter","secondaryHolder");
        }

        public void setDescription(TextView description) {
            this.description = description;
        }

        public TextView getDescription() {
            return description;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                //main item
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
                Log.d("list_adapter","type"+viewType);
                return new itemHolder(view);
            case 1:
                //description item
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.secondary_layout,parent,false);
                Log.d("list_adapter","type"+viewType);
                return new secondaryHolder(view);
            case 3:
                //
                view = null;
                Log.d("list_adapter","type error"+viewType);
                return null;
            default:
                Log.d("list_adapter","type error"+viewType);
                view = null;
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        int type = getItemViewType(position);
        if(type == 0){
            // main_info
            final itemHolder mainInfo = (itemHolder) holder;
            ListItem target = (ListItem) listItems.get(position);
            mainInfo.getName().setText(target.getName());
            mainInfo.getPrice().setText(target.getPriceInFormat());
            mainInfo.getPurchased().setChecked(target.getPurchased());

            if(target.getType().equals("Food")){
                mainInfo.getIcon().setImageResource(R.drawable.food);
            }else if(target.getType().equals("Book")){
                mainInfo.getIcon().setImageResource(R.drawable.book1);
            }else if(target.getType().equals("Electronic")){
                mainInfo.getIcon().setImageResource(R.drawable.dianzi);
            }else {
                mainInfo.getIcon().setImageResource(R.drawable.game);
            }
            //mainInfo.getIcon().setImageIcon(R.drawable);
            mainInfo.getEdit().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo
                    editDialog myDialog = new editDialog(context,R.layout.dialog,(ListItem) listItems.get(position),new editDialog.sendMessage(){
                        @Override
                        public void send() {
                            notifyDataSetChanged();
                        }

                        @Override
                        public void change() {
                            if(((ListItem) listItems.get(position)).getType()==""){
                                //delete;
                                if(((ListItem) listItems.get(position)).getExpanded()){
                                    listItems.remove(position+1);

                                }
                                listItems.remove(position);
                            }
                            notifyDataSetChanged();
                        }
                    });
                    myDialog.show();


                }
            });
            mainInfo.getDetail().setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //todo
                    if(((ListItem) listItems.get(position)).getExpanded()){ //if expanded
                        listItems.remove(position+1);
                        mainInfo.getDetail().setText(R.string.show);
                        ((ListItem) listItems.get(position)).setExpanded(false);



                    }else{ // not expanded
                        listItems.add(position+1,new descriptionItem(((ListItem) listItems.get(position)).getDescription()));

                        mainInfo.getDetail().setText(R.string.hide);
                        ((ListItem) listItems.get(position)).setExpanded(true);

                        //mainInfo.getDetail().setText(R.string.show);
                    }
                    notifyDataSetChanged();
                }
            });
            //todo: set Icon for specific type
            Log.d("list_adapter","BindViewHolder"+position);

        }
        else{
            secondaryHolder secondaryHolder = (secondaryHolder) holder;
            descriptionItem target = (descriptionItem) listItems.get(position);
            secondaryHolder.getDescription().setText(target.getDescription());
        }

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
    public void showDialog(int position){
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog,null);
    }

    @Override
    public int getItemViewType(int position) {
        if(listItems.get(position) instanceof ListItem){
            return 0;
        }
        else if(listItems.get(position) instanceof descriptionItem){
            return 1;
        }
        return 3;
    }
}
