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

    /**
     * Intent parameter sent from the MainActivity
     */
    public static final String EXTRA_POSITION = "extra_position";

    /**
     * Default Intent parameter for getting position for the listItem clicked in MainActivity
     */
    private static final int DEFAULT_POSITION = -1;

    /**
     * Variables declaration for various views used in the DetailActivity Layout
     */
    TextView placeOfOrigin,alsoKnownAs,ingredients,description;
    ImageView ingredientsIv;

    /**
     * Declaring Sandwich object variable
     */
    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Referencing and Initializing the views on the Activity Layout
        ingredientsIv = findViewById(R.id.image_iv);
        placeOfOrigin = findViewById(R.id.tv_origin);
        alsoKnownAs = findViewById(R.id.tv_also_known);
        ingredients = findViewById(R.id.tv_ingredients);
        description = findViewById(R.id.tv_description);

        // Receiving intent in an Intent variable for any bundle data from calling activity
        Intent intent = getIntent();

        // Check for null intent, will close the activity
        if (intent == null) {
            closeOnError();
        }

        // Retrieving position of the item clicked on the MainActivity Listview
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        // Check if position variable gets invalid value
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        // Get the string array from strings.xml
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);

        // Get the json string stored in String Array in strings.xml
        String json = sandwiches[position];

        // Call to JSONUtils method, parsing json string into sandwich object
        sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        // Call to helper method that populates values to various views
        populateUI();

        // Rendering images in the Imageview using Picasso Third Party Library
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.sandwich)
                .into(ingredientsIv);

        // Setting Activity title
        setTitle(sandwich.getMainName());
    }

    // Method that closes the activity with a toast message
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Helper method populateUI, places data into views
     */
    private void populateUI() {

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
}
