package org.buildmlearn.toolkit.simulator;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.buildmlearn.toolkit.R;
import org.buildmlearn.toolkit.constant.Constants;
import org.buildmlearn.toolkit.model.Template;
import org.buildmlearn.toolkit.model.TemplateInterface;

/**
 * @brief This activity acts as a placeholder for Simulator fragment.
 * Contains a Nexus 6 frame in which the fragments are laodded
 */
public class Simulator extends AppCompatActivity {

    private static final String TAG = "SIMULATOR";
    private int templateId;
    private TemplateInterface selectedTemplate;
    private Template template;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        templateId = getIntent().getIntExtra(Constants.TEMPLATE_ID, -1);
        if (templateId == -1) {
            Toast.makeText(this, "Invalid template ID, closing Template Editor activity", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        if (savedInstanceState == null) {
            setUpTemplateEditor();
        } else {
            restoreTemplateEditor(savedInstanceState);
        }
        getFragmentManager().beginTransaction().replace(R.id.container, selectedTemplate.getSimulatorFragment(getIntent().getStringExtra(Constants.SIMULATOR_FILE_PATH)), selectedTemplate.getTitle()).addToBackStack(null).commit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_simulator, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    /**
     * @brief Fetches the instance from the Template Enum for given template object
     */
    protected void setUpTemplateEditor() {
        Template[] templates = Template.values();
        template = templates[templateId];
        Class templateClass = template.getTemplateClass();
        try {
            Object templateObject = templateClass.newInstance();
            selectedTemplate = (TemplateInterface) templateObject;

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Restores simulator state on configuration change
     */
    protected void restoreTemplateEditor(Bundle savedInstanceState) {
        Log.d(TAG, "Activity Restored");
        selectedTemplate = (TemplateInterface) savedInstanceState.getSerializable(Constants.TEMPLATE_OBJECT);
        if (selectedTemplate == null) {
            Toast.makeText(this, "Unable to restore Activity state, finsihing Template Editor activity", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.TEMPLATE_OBJECT, selectedTemplate);
        super.onSaveInstanceState(outState);
    }

}
