package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG = DetailActivity.class.getSimpleName();

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    private TextView mItemAlsoKnowAsTextView;
    private TextView mItemIngredientsTextView;
    private TextView mPlaceOfOriginLabelTextView;
    private TextView mItemDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView mItemImageView = findViewById(R.id.image_iv);
        mItemAlsoKnowAsTextView = findViewById(R.id.also_known_as_label_tv);
        mItemIngredientsTextView = findViewById(R.id.ingredients_label_tv);
        mPlaceOfOriginLabelTextView = findViewById(R.id.place_of_origin_label_tv);
        mItemDescriptionTextView = findViewById(R.id.descriptions_label_tv);

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
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_no_image_error)
                .into(mItemImageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        //place of origin
        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().isEmpty()) {
            mPlaceOfOriginLabelTextView.append(" " + sandwich.getPlaceOfOrigin());
        } else {
            mPlaceOfOriginLabelTextView.append(" " +
                    getResources().getString(R.string.place_of_origin_unknown));
        }

        //description
        if (sandwich.getDescription() != null && !sandwich.getDescription().isEmpty()) {
            mItemDescriptionTextView.append(" " + sandwich.getDescription());
        } else {
            mItemDescriptionTextView.append(" " +
                    getResources().getString(R.string.no_description));
        }

        //also known as
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            mItemAlsoKnowAsTextView.append(" " +
                    TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        } else {
            mItemAlsoKnowAsTextView.append(" " +
                    getResources().getString(R.string.no_alternative_name));
        }

        //ingredients
        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            mItemIngredientsTextView.append(" " +
                    TextUtils.join(", ", sandwich.getIngredients()));
        } else {
            mItemIngredientsTextView.append(" " +
                    getResources().getString(R.string.no_ingredients));
        }
    }
}
