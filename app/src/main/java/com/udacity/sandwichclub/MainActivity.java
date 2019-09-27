package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Get the Sandwich data in an array */
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_names);


        // Setting the Array Adapter with simple list layout and Sandwich Array
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, sandwiches);

        /* Simplification: Using a ListView instead of a RecyclerView */
        ListView listView = findViewById(R.id.sandwiches_listview);


        /* Setting adapter for the listView */
        listView.setAdapter(adapter);

        /* Setting the OnItemClickLister for the listview */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailActivity(position);
            }
        });
    }

    // Calling DetailActivity on List Item Click using Intent
    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        startActivity(intent);
    }
}
