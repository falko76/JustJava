package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void increment (View view) {
        if (quantity==100) {
            Toast.makeText(getApplicationContext(), R.string.morethan100, Toast.LENGTH_SHORT).show();
            return;
        }

        quantity++;
        displayQuantity(quantity);
    }

    public void decrement (View view) {
        if (quantity==1) {
                Toast.makeText(getApplicationContext(), R.string.lessthan1, Toast.LENGTH_SHORT).show();
            return;
        }


        quantity--;
        displayQuantity(quantity);
    }

    public void submitOrder (View view) {
        String clientName;
        int price;
        boolean addWhippedCream, addChocolate;

        addWhippedCream= ((CheckBox) findViewById (R.id.whipped_cream_checkbox)).isChecked();
        addChocolate=((CheckBox) findViewById (R.id.chocolate_checkbox)).isChecked();
        clientName=((EditText) findViewById (R.id.name_editbox)).getText().toString();

        price=calculatePrice(addWhippedCream,addChocolate);

        displayMessage(createOrderSummary(price, addWhippedCream, addChocolate, clientName));
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice=5;

        if (addWhippedCream) basePrice+=1;
        if (addChocolate) basePrice+=2;
        return basePrice*quantity;
    }

    private String createOrderSummary (int price, boolean addWhippedCream, boolean addChocolate, String clientName) {
        String orderSummary;

        orderSummary=getResources().getString(R.string.name,clientName) + "\n";
        orderSummary+=getResources().getString(R.string.cAddWhippedCream,addWhippedCream )  + "\n";
        orderSummary+=getResources().getString(R.string.cAddChocolate,addChocolate ) + "\n";
        orderSummary+=getResources().getString(R.string.cQuantity, quantity) + "\n";
        orderSummary+=getResources().getString(R.string.cTotal,price)+ "\n";
        orderSummary+=getResources().getString(R.string.cThankYou)+ "\n";
        return orderSummary;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.format("%s",number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    public void submitMap(View view) {
        String clientName, emailMessage;
        int price;
        boolean addWhippedCream, addChocolate;

        addWhippedCream = ((CheckBox) findViewById(R.id.whipped_cream_checkbox)).isChecked();
        addChocolate = ((CheckBox) findViewById(R.id.chocolate_checkbox)).isChecked();
        clientName = ((EditText) findViewById(R.id.name_editbox)).getText().toString();

        price = calculatePrice(addWhippedCream, addChocolate);

        emailMessage = createOrderSummary(price, addWhippedCream, addChocolate, clientName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.emailSubject,clientName));
        intent.putExtra(Intent.EXTRA_TEXT, emailMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else Log.e("MainActivity","! resolveActivity");

    }
}