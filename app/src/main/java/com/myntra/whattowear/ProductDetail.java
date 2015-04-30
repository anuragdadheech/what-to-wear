package com.myntra.whattowear;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class ProductDetail extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent intent = getIntent();
        final String image = intent.getStringExtra("picture");
        String brand = intent.getStringExtra("brand");
        final String type = intent.getStringExtra("type");
        Picasso.with(this).load(image).into((ImageView)findViewById(R.id.imageView1));
        Button wearToday = (Button)findViewById(R.id.wear_today);
        wearToday.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences.Editor editor = getSharedPreferences("whattowear", MODE_PRIVATE).edit();
                        if(type.equals("top"))
                            editor.putString("today_top", image);
                        else if(type.equals("bottom"))
                            editor.putString("today_bottom", image);
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"Product added to today's Wardrobe",Toast.LENGTH_LONG).show();
                    }
                }
        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
