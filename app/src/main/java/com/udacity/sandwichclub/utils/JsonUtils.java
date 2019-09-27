package com.udacity.sandwichclub.utils;

import android.text.TextUtils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * JSONUtils Class contains json related methods
 */
public class JsonUtils {

    /**
     * parseSandwichJson method for parsing json string properly
     * and returns Sandwich object
     *
     * @param sandwichJSON json string to be parsed
     * @return Sandwich class object
     */
    public static Sandwich parseSandwichJson(String sandwichJSON) {

        List<String> alsoKnownAsList;
        List<String> ingredientsList;
        Sandwich sandwich;

        if (TextUtils.isEmpty(sandwichJSON)) {
            return null;
        }

        try {
            sandwich = new Sandwich();
            JSONObject baseJsonResponse = new JSONObject(sandwichJSON);
            JSONObject name = baseJsonResponse.getJSONObject("name");
            String sandwichName = name.getString("mainName");
            sandwich.setMainName(sandwichName);

            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            alsoKnownAsList = new ArrayList<>();
            if(alsoKnownAsArray.length()>0){
                for(int i=0;i<alsoKnownAsArray.length();i++){
                    alsoKnownAsList.add(alsoKnownAsArray.getString(i));
                }
            }
            sandwich.setAlsoKnownAs(alsoKnownAsList);

            String placeOfOriginString = baseJsonResponse.getString("placeOfOrigin");
            sandwich.setPlaceOfOrigin(placeOfOriginString);

            String description = baseJsonResponse.getString("description");
            sandwich.setDescription(description);

            String image = baseJsonResponse.getString("image");
            sandwich.setImage(image);

            JSONArray ingredients = baseJsonResponse.getJSONArray("ingredients");

            ingredientsList = new ArrayList<>();
            if(ingredients.length()>0){
                for(int i=0;i<ingredients.length();i++){
                    ingredientsList.add(ingredients.getString(i));
                }
            }
            sandwich.setIngredients(ingredientsList);
            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
