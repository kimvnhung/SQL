package com.kimvan.hung.sqlitesimple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText inputText;
    Button add;
    Button delete;
    TextView showData;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setID();

        dbHandler = new MyDBHandler(this,null,null,1);

        printDatabase();
    }

    //add a product to database
    public void addButtonClick(View view){
        Products products = new Products(inputText.getText().toString());
        dbHandler.addProduct(products);
        printDatabase();
    }

    //delete items
    public void deleteButtonClick(View view){
        String deleteString = inputText.getText().toString();
        dbHandler.deleteProduct(deleteString);
        printDatabase();
    }

    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        showData.setText(dbString);
        inputText.setText("");
    }

    public void setID(){
        inputText = (EditText) findViewById(R.id.input_text);
        add = (Button) findViewById(R.id.addButton);
        delete = (Button) findViewById(R.id.delete_button);
        showData = (TextView) findViewById(R.id.show_content);
    }


}
