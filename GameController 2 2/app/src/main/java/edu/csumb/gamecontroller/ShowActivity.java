package edu.csumb.gamecontroller;

        import android.app.Activity;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;

public class ShowActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);

        /** Getting the references to the textview object of the show layout */
        final TextView tv_checkbox_preference1 = (TextView) findViewById(R.id.tv_checkbox_preference1);
        TextView tv_checkbox_preference2 = (TextView) findViewById(R.id.tv_checkbox_preference2);
        TextView tv_checkbox_preference3 = (TextView) findViewById(R.id.tv_checkbox_preference3);

        TextView tv_list_preference = (TextView) findViewById(R.id.tv_list_preference);


        /** Getting the shared preference object that points to preferences resource available in this context */
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        /** Getting the values stored in the shared object via preference activity */
        boolean cb1 = sp.getBoolean("cbp_checkbox_preference1", false);
        boolean cb2 = sp.getBoolean("cbp_checkbox_preference2", false);
        boolean cb3 = sp.getBoolean("cbp_checkbox_preference3", false);

        String list = sp.getString("lp_list_preference","None Selected");




        /** Setting the values on textview objects to display in the ShowActivity */
        tv_checkbox_preference1.setText("Checkbox Preference1 : " + Boolean.toString(cb1));
        tv_checkbox_preference2.setText("Checkbox Preference2 : " + Boolean.toString(cb2));
        tv_checkbox_preference3.setText("Checkbox Preference3 : " + Boolean.toString(cb3));
        tv_list_preference.setText("List Preference : " + list);

        Toast.makeText(ShowActivity.this, list, Toast.LENGTH_SHORT).show();

    }

    public void selectedItems(View view)
    {
        boolean checked = ((CheckBox)view).isChecked();
        switch(view.getId())
        {
            case R.id.tv_checkbox_preference1:
                if(checked)
                {
                    Toast.makeText(ShowActivity.this, "checked", Toast.LENGTH_SHORT).show();
                }
                else
                {
                }
                break;
        }
    }
}