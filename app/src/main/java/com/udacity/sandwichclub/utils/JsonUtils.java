package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        // One object
        final String SC_NAME_OB = "name";
        final String SC_MAIN_NAME = "mainName";
        final String SC_ALSO_KNOWN_AS_AR = "alsoKnownAs";

        final String SC_PLACE_OF_ORIGIN = "placeOfOrigin";

        final String SC_DESCRIPTION = "description";

        final String SC_IMAGE = "image";

        final String SC_INGREDIENTS ="ingredients";

        try {
            JSONObject jsonNameObject = new JSONObject(json);

            // Name(s)
            String mainName = jsonNameObject.getString(SC_MAIN_NAME);

            JSONArray akaArray = jsonNameObject.getJSONArray(SC_ALSO_KNOWN_AS_AR);
            ArrayList<String> alsoKnownAsArr = new ArrayList<>();
            for (int i = 0; i < akaArray.length(); i++) {
                alsoKnownAsArr.add( akaArray.getString(i) );
            }

            // Place of Origin
            String placeOfOrigin = jsonNameObject.getString(SC_PLACE_OF_ORIGIN);

            // Description
            String description = jsonNameObject.getString(SC_DESCRIPTION);

            // Image URL
            String image = jsonNameObject.getString(SC_IMAGE);

            // Ingredients
            JSONArray jsonIngredientsArray = jsonNameObject.getJSONArray(SC_INGREDIENTS);
            ArrayList<String> ingredientsArr = new ArrayList<>();
            for (int i = 0; i < jsonIngredientsArray.length(); i++ ) {
                ingredientsArr.add( jsonIngredientsArray.getString(i));
            }

            return new Sandwich(
                    mainName,
                    alsoKnownAsArr,
                    placeOfOrigin,
                    description,
                    image,
                    ingredientsArr
            );

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
