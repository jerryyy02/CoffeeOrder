/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */

package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.duration;
import static android.R.attr.y;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.items_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.items_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWithCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasWithChocolate = chocolateCheckbox.isChecked();

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        EditText emailField = (EditText) findViewById(R.id.email_field);
        String email = emailField.getText().toString();

        int price = calculatePrice(quantity, hasWithCream, hasWithChocolate);
        String priceMessage = createOrderSummary(price, hasWithCream, hasWithChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" +email)); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject) +" "+ name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void increment(View view) {
        if(quantity<100) {
            quantity = quantity + 1;
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.toast_two);
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText( context, text, duration).show();
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if(quantity>1) {
            quantity = quantity - 1;
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.toast_one);
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText( context, text, duration).show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfQuantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfQuantity);
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity, boolean hasWithCream, boolean hasWithChocolate) {
        int price;
        if(hasWithCream==true && hasWithChocolate==false) {
             price = quantity * 25;
        }
        else if(hasWithChocolate==true && hasWithCream==false){
             price = quantity * 25;
        }
        else if(hasWithCream && hasWithChocolate){
            price = quantity * 30;
        }
        else {
            price = quantity * 20;
        }
        return price;
    }

    /**
     * Creates the summary of the order.
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String nameOfCustomer){
        String OrderSummary = getString(R.string.customer_name)+": "+ nameOfCustomer +"\n"+ getString(R.string.add_wc) +" "+ addWhippedCream +"\n"+ getString(R.string.add_choco) +" "+ addChocolate +"\n"+ getString(R.string.quantity) +": "+ quantity +"\n"+ getString(R.string.total) + price +"\n"+ getString(R.string.thank_you);
        return OrderSummary;
    }

}

