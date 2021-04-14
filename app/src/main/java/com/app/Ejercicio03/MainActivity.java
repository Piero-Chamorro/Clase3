package com.app.Ejercicio03;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.Ejercicio03.api.ServiceApi;
import com.app.Ejercicio03.entity.User;
import com.app.Ejercicio03.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> data = new ArrayList<String>();
    ListView listViewTitle;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewTitle = findViewById(R.id.idListTitles);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, data);
        listViewTitle.setAdapter(adapter);

        loadData();
    }

    public void loadData(){
        ServiceApi api = ConnectionRest.getConnection().create(ServiceApi.class);
        Call<List<User>> call = api.listTitles();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                showMessage("--> Éxito");
                if(response.isSuccessful()) {
                    List<User> answer = response.body();
                    for (User x : answer){
                        data.add(x.getId() +". " + x.getTitle());
                    }
                    adapter.notifyDataSetChanged();
                } else{
                    showMessage("La respuesta no es satisfactoria");
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                showMessage("--> Error :" + t.getMessage());
            }
        });
    }

    public void showMessage(String msg){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(msg);
        alert.show();
    }
}

