package com.example.shoppinglist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    public static FloatingActionButton floating;
    private RecyclerView recyclerView;
    private ImageButton setting;
    public  ArrayList<parentItem> arrayList = new ArrayList<>();
    public ArrayList<ListItem> copy = new ArrayList<>();
    private list_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main","before"+arrayList.size());
        initCard();
        Log.d("Main","after"+arrayList.size());
        floating = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new list_adapter(arrayList,copy,this);

        recyclerView.setAdapter(adapter);
        setting = findViewById(R.id.imageButton);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<arrayList.size();i++){
                    if(arrayList.get(i) instanceof descriptionItem){
                        arrayList.remove(i);
                        ((ListItem)arrayList.get(i-1)).setExpanded(false);
                        adapter.notifyDataSetChanged();
                    }
                }
                Intent intent = new Intent(MainActivity.this,Setting.class);
////                Bundle data = new Bundle();
////                data.putSerializable("arrayList",(Serializable) arrayList);
////                intent.putExtra("data",data);
                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
            {
                if(resultCode == RESULT_OK){
                    String sortBy = data.getStringExtra("Sort_by");
                    String order = data.getStringExtra("order");
                    if(sortBy.equals("name")){
                        if(order.equals("ascending")){
                            Comparator<parentItem> ss = new Comparator<parentItem>() {
                                @Override
                                public int compare(parentItem o1, parentItem o2) {
                                    return ((ListItem) o1).getName().compareTo(((ListItem)o2).getName());
                                }
                            };
                            Log.d("asc","ok");
                            Collections.sort(arrayList,ss);
                            Collections.reverse(arrayList);
                        }else{
                            Comparator<parentItem> ss = new Comparator<parentItem>() {
                                @Override
                                public int compare(parentItem o1, parentItem o2) {
                                    return ((ListItem) o1).getName().compareTo(((ListItem)o2).getName());
                                }
                            };
                            Collections.sort(arrayList,ss);
                            //Collections.reverse(arrayList);
                        }
                    }else if(sortBy.equals("price")){
                        if(order.equals("ascending")){
                            Comparator<parentItem> ss = new Comparator<parentItem>() {
                                @Override
                                public int compare(parentItem o1, parentItem o2) {
                                    if(((ListItem)o1).getPrice() > ((ListItem)o2).getPrice()){
                                        return 1;
                                    }else if(((ListItem)o1).getPrice() < ((ListItem)o2).getPrice()){
                                        return -1;
                                    }else{
                                        return 0;
                                    }
                                }
                            };Log.d("asc p","ok");
                            Collections.sort(arrayList,ss);
                        }else{
                            Comparator<parentItem> ss = new Comparator<parentItem>() {
                                @Override
                                public int compare(parentItem o1, parentItem o2) {
                                    if( ((ListItem)o1).getPrice() > ((ListItem)o2).getPrice()){
                                        return -1;
                                    }else if(((ListItem)o1).getPrice() < ((ListItem)o2).getPrice()){
                                        return 1;
                                    }else{
                                        return 0;
                                    }
                                }

                            };Log.d("asc pd","ok");
                            Collections.sort(arrayList,ss);
                            //Collections.reverse(arrayList);

                        }
                    }else{
                        if(order.equals("ascending")){
                            Comparator<parentItem> ss = new Comparator<parentItem>() {
                                @Override
                                public int compare(parentItem o1, parentItem o2) {
                                    return ((ListItem)o1).getPurchased().compareTo(((ListItem)o2).getPurchased());
                                }
                            };
                            Collections.sort(arrayList,ss);
                            Collections.reverse(arrayList);
                        }else{
                            Comparator<parentItem> ss = new Comparator<parentItem>() {
                                @Override
                                public int compare(parentItem o1, parentItem o2) {
                                    return ((ListItem)o1).getPurchased().compareTo(((ListItem)o2).getPurchased());
                                }
                            };
                            Collections.sort(arrayList,ss);

                        }
                    }
                }
                break;
            }
            default:
                break;
        }
        Log.d("dd","dddd");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        addItem();
        Log.d("Main","start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Main","stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Main","Destroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Main","Restart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Main","Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        addItem();
        Log.d("Main","Pause");
    }
    public void initCard(){
        try {
            File is = new File(getFilesDir()+"/data_one.json");
            BufferedReader bufr = new BufferedReader(new FileReader(is));

            String line;
            StringBuilder builder = new StringBuilder();
            while((line = bufr.readLine())!= null){
                builder.append(line);
            }
            Log.d("Main",builder.toString());
            bufr.close();
            try {
                JSONObject root = new JSONObject(builder.toString());
                JSONArray array = root.getJSONArray("shoppingList");
                String name = "";
                double price = 0.0;
                String description = "";
                boolean isPurchased = true;
                String type = "";
                for(int i=0;i<array.length();i++){
                    JSONObject obj1 = (JSONObject)(array.get(i));
                    name = (String)obj1.get("name");
                    if(((String)obj1.get("price")).equals("0.0")){
                        price = 0.0;
                    }else {
                        price = Double.parseDouble((String) obj1.get("price"));
                    }
                    isPurchased = (boolean)obj1.get("isPurchased");
                    description = (String)obj1.get("description");
                    type = (String)obj1.get("type");
                    arrayList.add(new ListItem(name,price,isPurchased,description,type));
                }

            }catch (JSONException e){
                e.printStackTrace();
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void addItem(){
        String filename = "data_one.json";
        File file = new File(getFilesDir(),filename);
        try{
            if(!file.exists()){
                Log.d("Main","not exist");
                FileOutputStream outputStream = openFileOutput(filename,MODE_PRIVATE);
                JSONObject obj = new JSONObject();
                JSONArray shoppingList = obj.getJSONArray("ShoppingList");
                outputStream.write(obj.toString().getBytes());
                outputStream.close();
            }else {
                JSONObject obj = new JSONObject();
                JSONArray shoppingList = new JSONArray();
                for(int i=0;i<arrayList.size();i++){
                    if(arrayList.get(i) instanceof ListItem) {
                        JSONObject example = new JSONObject();
                        example.put("name", ((ListItem) arrayList.get(i)).getName());
                        example.put("price",  ((ListItem) arrayList.get(i)).getPrice()+"");
                        example.put("description", ((ListItem) arrayList.get(i)).getDescription());
                        example.put("isPurchased", ((ListItem) arrayList.get(i)).getPurchased());
                        example.put("type",((ListItem) arrayList.get(i)).getType());
                        shoppingList.put(example);
                    }
                }
                obj.put("shoppingList",shoppingList);
                String strFormat = obj.toString();
                FileOutputStream out = new FileOutputStream(file);
                out.write(strFormat.getBytes());
                out.flush();
            }

        }catch (JSONException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
