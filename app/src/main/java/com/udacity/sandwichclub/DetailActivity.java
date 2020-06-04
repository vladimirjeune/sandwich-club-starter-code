package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);  // TODO: Make this work
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);  // TODO: Make this work


        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich aSandwich) {

        // Obtain View Elements
        TextView name_tv = findViewById((R.id.name_label_tv));

        TextView aka_tv = findViewById(R.id.also_known_tv);

        TextView origin_tv = findViewById(R.id.origin_tv);

        TextView description_tv = findViewById(R.id.description_tv);

        TextView ingredients_tv = findViewById(R.id.ingredients_tv);

        ImageView ingredientsIv = findViewById(R.id.image_iv);


        // Populate
        Picasso.get()
                .load(aSandwich.getImage())
                .into(ingredientsIv);

        name_tv.setText(aSandwich.getMainName());

        populateAliases(aSandwich, aka_tv);

        origin_tv.setText(aSandwich.getPlaceOfOrigin());

        description_tv.setText(aSandwich.getDescription());

        populateIngredients(aSandwich, ingredients_tv);

    }


    /**
     * POPULATEINGREDIENTS - Turns array of aliases into a readable string to be input into the
     * appropriate TextView
     * @param aSandwich - Sandwich we are getting data from
     * @param ingredients_tv    - TextView to populate
     */
    private void populateIngredients(Sandwich aSandwich, TextView ingredients_tv) {
        List<String> ingredient_list = aSandwich.getIngredients();  // Get list
        String ingredient_str = "";  // Final String

        if (0 != ingredient_list.size()) {  // If there is anything

            StringBuilder ingredients_sb = new StringBuilder();  // Collector
            for (int i = 0; i < ingredient_list.size(); i++) {

                if (1 == (ingredient_list.size() - i)) {
                    ingredients_sb.append(ingredient_list.get(i));
                } else {
                    ingredients_sb.append(ingredient_list.get(i) + "\n\n");
                }

            }

            ingredient_str = ingredients_sb.toString();  // String to add to TV

        }

        ingredients_tv.setText(ingredient_str);
    }

    /**
     * POPULATEALIASES - Turns array of aliases into a readable string to be input into the
     * appropriate TextView
     * @param aSandwich - Sandwich we are getting data from
     * @param aka_tv    - TextView to populate
     */
    private void populateAliases(Sandwich aSandwich, TextView aka_tv) {
        // Get list of Aliases if any
        List<String> aka_list = aSandwich.getAlsoKnownAs();
        String aka_str = "";  // Final output

        if (0 != aka_list.size()) {  // Have at least one

            StringBuilder aka_sb = new StringBuilder();

            for (int i = 0; i < aka_list.size(); i++) {
                if (1 == aka_list.size() - i) {
                    aka_sb.append(aka_list.get(i));  // Final alias
                } else {
                    aka_sb.append(aka_list.get(i) + ", ");  // Middle stuff
                }
            }

            aka_str = aka_sb.toString();
        }

        aka_tv.setText(aka_str);
    }
}
