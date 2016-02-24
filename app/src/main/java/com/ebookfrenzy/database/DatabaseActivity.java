package com.ebookfrenzy.database;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class DatabaseActivity extends AppCompatActivity {

    TextView idView;
    EditText productBox;
    EditText quantityBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        idView = (TextView)findViewById(R.id.productID);
        productBox = (EditText)findViewById(R.id.productName);
        quantityBox = (EditText)findViewById(R.id.productQuantity);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                         .setAction("Action", null).show();
             }
         });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_database, menu);
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

    private int getQuantity() {
        try {
            return Integer.parseInt(quantityBox.getText().toString());
        }
        catch (NumberFormatException ex) {
            return -1;
        }
    }

    public void newProduct(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String productName = productBox.getText().toString();
        if (productName.length() < 1) {
            idView.setText("Name required");
            return;
        }

        if (dbHandler.findProduct(productName) != null) {
            idView.setText("Product exists");
            return;
        }

        int quantity = getQuantity();

        if (quantity <= 0) {
            idView.setText("Quantity required");
            return;
        }

        Product product = new Product(productName, quantity);

        dbHandler.addProduct(product);
        productBox.setText("");
        quantityBox.setText("");
    }

    public void lookupProduct(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        Product product = dbHandler.findProduct(productBox.getText().toString());

        if (product != null) {
            idView.setText(String.valueOf(product.getID()));

            quantityBox.setText(String.valueOf(product.getQuantity()));
        }
        else {
            idView.setText("No Match Found");
        }
    }

    public void removeProduct(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        boolean result = dbHandler.deleteProduct(productBox.getText().toString());

        if (result) {
            idView.setText("Record Deleted");
            productBox.setText("");
            quantityBox.setText("");
        }
        else {
            idView.setText("No Match Found");
        }
    }

}
