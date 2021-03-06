package org.buildmlearn.toolkit.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.viewpagerindicator.CirclePageIndicator;

import org.buildmlearn.toolkit.R;
import org.buildmlearn.toolkit.adapter.TutorialAdapter;
import org.buildmlearn.toolkit.constant.Constants;

/**
 * @brief Shows the tutorial related to BuildmLearn toolkit usage.
 */
public class TutorialActivity extends AppCompatActivity {

    private static final String TAG = "Tutorial Activity";
    private TutorialAdapter mAdapter;
    private ViewPager mPager;
    private CirclePageIndicator mIndicator;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        mAdapter = new TutorialAdapter(this, getIntent().getBooleanExtra(Constants.START_ACTIVITY, false));

        mPager = (ViewPager) findViewById(R.id.pager);
        //  mPager.setAdapter(mAdapter);

        mPager.setAdapter(mAdapter);


        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }

}
