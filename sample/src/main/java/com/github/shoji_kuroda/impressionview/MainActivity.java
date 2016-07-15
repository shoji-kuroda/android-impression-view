package com.github.shoji_kuroda.impressionview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.content,
                new ScrollViewFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scrollView:
                getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
                        new ScrollViewFragment()).commit();
                break;
            case R.id.ListView:
                getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
                        new ListViewFragment()).commit();
                break;
            case R.id.recyclerView:
                getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
                        new RecyclerViewFragment()).commit();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
