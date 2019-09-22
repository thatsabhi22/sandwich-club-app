package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView placeOfOrigin,alsoKnownAs,ingredients,description;
    ImageView ingredientsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        placeOfOrigin = findViewById(R.id.tv_origin);
        alsoKnownAs = findViewById(R.id.tv_also_known);
        ingredients = findViewById(R.id.tv_ingredients);
        description = findViewById(R.id.tv_description);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());

        List<String> alsoKnownAsList =  sandwich.getAlsoKnownAs();
        if(alsoKnownAsList != null && alsoKnownAsList.size()>0) {
            String alsoKnownAsString = TextUtils.join(", ", alsoKnownAsList);
            alsoKnownAs.setText(alsoKnownAsString);
        }

        List<String> ingredientsList =  sandwich.getIngredients();
        if(ingredientsList != null && ingredientsList.size()>0) {
            String ingredientsString = TextUtils.join(", ", ingredientsList);
            ingredients.setText(ingredientsString);
        }

        description.setText(sandwich.getDescription());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
