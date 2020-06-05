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
    public static final String ALIAS_DELIMITER = ", ";
    public static final String INGREDIENT_DELIMITER = "\n\n";

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
        TextView name_tv = findViewById(R.id.name_tv);

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

        populateLists(aSandwich.getAlsoKnownAs(), aka_tv, ALIAS_DELIMITER);

        origin_tv.setText(aSandwich.getPlaceOfOrigin());

        description_tv.setText(aSandwich.getDescription());

        populateLists(aSandwich.getIngredients(), ingredients_tv, INGREDIENT_DELIMITER);

    }

    /**
     * POPULATELISTS - Turns array of data into a readable string with the passed
     * in delimiters. This will be displayed in the passed in TextView
     * @param textView_list - A list of text that is to be displayed on a TextView | Not Null
     * @param display_tv    - The TextView that should display the inputted list   | Not Null
     * @param delimiter     - The delimiter between each element of the list, when displayed | Not Null
     */
    private void populateLists( List<String> textView_list, TextView display_tv, String delimiter) {
        assert textView_list != null : "List<String> is null";
        assert display_tv != null : "TextView is null";
        assert delimiter != null : "String is null";

        String textView_list_str = "";  // Final String output

        if (0 != textView_list.size()) {  // If there is anything in the list

            StringBuilder textView_sb = new StringBuilder();  // Collector

            for (int i = 0; i < textView_list.size(); i++) {

                if (1 == textView_list.size() - i) {  // In middle of list, need to delimit
                    textView_sb.append(textView_list.get(i));
                } else {
                    textView_sb.append(textView_list.get(i) + delimiter);
                }
            }

            textView_list_str = textView_sb.toString();  // String to add to TextView

        }

        display_tv.setText(textView_list_str);
    }

}
