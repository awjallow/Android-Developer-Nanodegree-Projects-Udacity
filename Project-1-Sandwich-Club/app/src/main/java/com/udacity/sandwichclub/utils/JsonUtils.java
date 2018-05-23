package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String ITEM_IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {

        JSONObject sandwichDataJsonObj;
        JSONObject sandwichNameJsonObj;

        String mainName = null;
        List<String> alsoKnowAs = null;
        String placeOfOrigin = null;
        String descriptions = null;
        String image = null;
        List<String> ingredients = null;

        try {
            sandwichDataJsonObj = new JSONObject(json);
            sandwichNameJsonObj = sandwichDataJsonObj.optJSONObject(NAME);

            mainName = sandwichNameJsonObj.optString(MAIN_NAME);
            alsoKnowAs =  createListFromJsonArray(sandwichNameJsonObj.optJSONArray(ALSO_KNOWN_AS));
            placeOfOrigin = sandwichDataJsonObj.optString(PLACE_OF_ORIGIN);
            descriptions = sandwichDataJsonObj.optString(DESCRIPTION);
            image = sandwichDataJsonObj.optString(ITEM_IMAGE);
            ingredients = createListFromJsonArray(sandwichDataJsonObj.optJSONArray(INGREDIENTS));

        }catch (JSONException e){
            e.printStackTrace();
        }

        return new Sandwich(mainName,
                alsoKnowAs,
                placeOfOrigin,
                descriptions,
                image,
                ingredients);

    }

    private static List<String> createListFromJsonArray(JSONArray jsonArray) throws JSONException {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            list.add(jsonArray.optString(i));
        }
        return list;
    }
}
