package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText getName = (EditText) findViewById(R.id.name_view);
        String name = getName.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);

        if (quantity != 0) {
            String priceMessage = "Name: " + name + "\n\nAdd whipped cream? "
                    + (whippedCreamCheckBox.isChecked() ? "Yes" : "No") + "\n\nAdd chocolate? " +
                    (chocolateCheckBox.isChecked() ? "Yes" : "No") + "\n\nTotal : $" +
                    calculatePrice() +
                    "\n\nThank You!\n\nVisit Again. :)";
            displayPriceText("Order summary :");
            displayMessage(priceMessage);
            final ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollview));
            scrollview.post(new Runnable() {
                @Override
                public void run() {
                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
            // Use an intent to launch an email app.
            // Send the order summary in the email body.
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
            intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

            Context context = getApplicationContext();
            CharSequence text = "Your order has been placed.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            String priceMessage = "Please select a number greater than 0";
            displayPriceText("Order summary :");
            displayMessage(priceMessage);
        }
    }

    public void select(View view) {
        displayQuantity(quantity);
        displayPrice();
        displayPriceText("price :");
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
            select(view);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Max No. of coffees that can be ordered is 100";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void decrement(View view) {
        quantity--;
        if (quantity < 0) {
            quantity = 0;
            Context context = getApplicationContext();
            CharSequence text = "You cannot select a number less than 0";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        select(view);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayPrice() {
        TextView quantityTextView = (TextView) findViewById(
                R.id.order_summary_text_view);
        int price = calculatePrice();
        quantityTextView.setText("$" + price);
    }

    private int calculatePrice() {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        return (quantity * (5 + (whippedCreamCheckBox.isChecked() ? 1 : 0) + (2 * (chocolateCheckBox.isChecked() ? 1 : 0))));
    }

    private void displayPriceText(String text) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.price_text);
        quantityTextView.setText(text);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String msg) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(msg);
    }
}