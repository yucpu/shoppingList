package com.example.shoppinglist;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class editDialog extends Dialog {
    private static final String[] mutiText = {"Food","Book","Electronic","Entertainment"};
    private AutoCompleteTextView category = findViewById(R.id.selector);
    private EditText name = findViewById(R.id.edit_name);
    private EditText price = findViewById(R.id.edit_cost);
    private EditText descripition = findViewById(R.id.edit_description);
    private CheckBox isPurchased = findViewById(R.id.edit_purchased);
    private Button save = findViewById(R.id.edit_button);
    private double priceDouble;
    private Button delete;
    private Context context ;
    private int layout;
    private ListItem listItem;
    public interface sendMessage{
        public void send();
        public void change();
    }
    private sendMessage sendMessage;
    public editDialog(Context context,int layout, ListItem item, sendMessage sendMessage){
        super(context);
        this.context = context;
        this.layout = layout;
        this.listItem = item;
        this.sendMessage = sendMessage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window dialog = getWindow();
        dialog.setGravity(Gravity.CENTER_HORIZONTAL);

        setContentView(layout);
        setCanceledOnTouchOutside(true);
        category = findViewById(R.id.selector);
        name = findViewById(R.id.edit_name);
        price = findViewById(R.id.edit_cost);
        descripition = findViewById(R.id.edit_description);
        isPurchased = findViewById(R.id.edit_purchased);
        save = findViewById(R.id.edit_button);
        delete = findViewById(R.id.delete_btn);
        category.setText(listItem.getType());
        name.setText(listItem.getName());
        price.setText(listItem.getPrice()+"");
        priceDouble = listItem.getPrice();
        descripition.setText(listItem.getDescription());
        isPurchased.setChecked(listItem.getPurchased());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,mutiText);
        category.setAdapter(adapter);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category.showDropDown();
            }
        });
        WindowManager.LayoutParams layout= getWindow().getAttributes();
        //layout.height=getWindow().getWindowManager().getDefaultDisplay().getHeight()*4/10;
        layout.width=getWindow().getWindowManager().getDefaultDisplay().getWidth()*75/100;;
        getWindow().setAttributes(layout);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItem.setName(name.getText().toString());
                if(price.getText().toString().length()>0) {
                    listItem.setPrice(Double.parseDouble(price.getText().toString()));
                }else{
                    listItem.setPrice(0.0);
                }
                Log.d("dialog",listItem.getPrice()+"");
                listItem.setDescription(descripition.getText().toString());
                listItem.setType(category.getText().toString());
                listItem.setPurchased(isPurchased.isChecked());
                dismiss();
                sendMessage.send();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItem.setType("");
                dismiss();
                sendMessage.change();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Dialog","start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Dialog","stop");
    }

}
