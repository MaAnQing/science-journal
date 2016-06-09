package com.google.android.apps.forscience.whistlepunk.scalarchart;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.google.android.apps.forscience.javalib.FailureListener;
import com.google.android.apps.forscience.whistlepunk.ActiveSettingsController;
import com.google.android.apps.forscience.whistlepunk.PrefsNewOptionsStorage;
import com.google.android.apps.forscience.whistlepunk.R;
import com.google.android.apps.forscience.whistlepunk.sensorapi.NewOptionsStorage;
import com.google.android.apps.forscience.whistlepunk.sensorapi.WriteableSensorOptions;


public class GraphOptionsController extends ActiveSettingsController {

    /**
     * Prefix for sensor tags to generate a preferences filename.
     */
    private static final String GRAPH_OPTIONS_PREF_FILE = "graph_options";

    private final Context mContext;

    public GraphOptionsController(Context context) {
        super(context);
        mContext = context;
    }

    public WriteableSensorOptions getOptions(FailureListener failureListener) {
        return new PrefsNewOptionsStorage(GRAPH_OPTIONS_PREF_FILE, mContext).load(failureListener);
    }

    public void launchOptionsDialog(ScalarDisplayOptions scalarDisplayOptions,
            FailureListener failureListener) {
        Resources resources = mContext.getResources();
        String optionsTitle = resources.getString(R.string.graph_options_title);
        super.launchOptionsDialog(
                new GraphOptionsManager(scalarDisplayOptions).makeCallbacks(mContext),
                resources.getString(R.string.graph_name), optionsTitle,
                getOptions(failureListener));
    }

    public void loadIntoScalarDisplayOptions(ScalarDisplayOptions scalarDisplayOptions, View view) {
        final GraphOptionsManager manager = new GraphOptionsManager(scalarDisplayOptions);
        manager.loadOptions(
                getOptions(new NewOptionsStorage.SnackbarFailureListener(view)).getReadOnly());
    }
}