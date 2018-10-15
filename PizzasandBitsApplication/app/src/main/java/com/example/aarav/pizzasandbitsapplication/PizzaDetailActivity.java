package com.example.aarav.pizzasandbitsapplication;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class PizzaDetailActivity extends Activity {
    private ShareActionProvider shareActionProvider;
    public static final String EXTRA_PIZZANO = "pizzaNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        int pizzaNo = (Integer) getIntent().getIntExtra("EXTRA_PIZZANO",1);
        String pizzaName = Pizza.pizzas[pizzaNo].getName();
        TextView textView = findViewById(R.id.pizza_text);
        textView.setText(pizzaName);

        int pizzaImage = Pizza.pizzas[pizzaNo].getImageResourceId();
        ImageView imageView = findViewById(R.id.pizza_image);
        imageView.setImageDrawable(getResources().getDrawable(pizzaImage));
        imageView.setContentDescription(pizzaName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order:
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_share:
                TextView textView = findViewById(R.id.pizza_text);
                CharSequence pizzaName = textView.getText();
              //  MenuItem menuItem = menu.findItem(R.id.action_share);
                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                shareActionProvider = new ShareActionProvider(this);
                MenuItemCompat.setActionProvider(item, shareActionProvider);

                Intent intent1 = new Intent(android.content.Intent.ACTION_SEND);
                intent1.setType("text/plain");
                intent1.putExtra(Intent.EXTRA_TEXT, pizzaName);
                shareActionProvider.setShareIntent(intent1);
                startActivity(Intent.createChooser(intent1, "Share Via"));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}