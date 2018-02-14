package com.example.steli.hw_2_papoutsakis;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
/*
creates a program that draws a face with eyes, hair.
 User can change the color of these features plus the skin tone
 user can also change hair styles and hit a random button to get random faces

 @Author Stelios Papoutsakis
 */
public class MainActivity extends AppCompatActivity {

    private FaceCanvas faceCanvas; // faceCanvas that is given to the listener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Creating a view and ArrayList of seekbars that will be added to the listener
        faceCanvas = (FaceCanvas) findViewById(R.id.FaceCanvas);
        faceCanvas.setBackgroundColor(Color.WHITE);
        ArrayList<SeekBar> seekBars = new ArrayList<>();

        // Finding the seekbars on the XML file and adding them to an ArrayList
        SeekBar red = (SeekBar) findViewById(R.id.RedSeek);
        SeekBar blue = (SeekBar) findViewById(R.id.BlueSeek);
        SeekBar green = (SeekBar) findViewById(R.id.GreenSeek);
        seekBars.add(red);
        seekBars.add(green);
        seekBars.add(blue);

        // creating button
        Button random = (Button) findViewById(R.id.RandomButton);


        // finding the spinner and radio group
        Spinner hairTypes = (Spinner) findViewById(R.id.HairTypeSpinner);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioFeaturesButton);
        radioGroup.check(R.id.RadioEyesbutton);
        // creating Listener that will be used for all widgest and adding listener to radio group
        EventListener eventListener = new EventListener(faceCanvas, seekBars, hairTypes);
        radioGroup.setOnCheckedChangeListener(eventListener);
        random.setOnClickListener(eventListener);


        // setting up the seekbars and spinner
        seekBarSetup(red, eventListener);
        seekBarSetup(blue, eventListener);
        seekBarSetup(green, eventListener);
        spinnerSetup(hairTypes, eventListener);
    }

    /*
    method is called to set up start attributes of each seek bar
    @param SeekBar, EventListener
    @return void
     */
    public void seekBarSetup(SeekBar seekbar, EventListener eventListener) {
        seekbar.setMax(255);
        if (seekbar.getId() == R.id.RedSeek)
            seekbar.setProgress(Color.red(faceCanvas.getEyeColor()));
        else if (seekbar.getId() == R.id.GreenSeek)
            seekbar.setProgress(Color.green(faceCanvas.getEyeColor()));
        else if (seekbar.getId() == R.id.BlueSeek)
            seekbar.setProgress(Color.blue(faceCanvas.getEyeColor()));
        seekbar.setOnSeekBarChangeListener(eventListener);

    }


    /*
    method is called to set up start attributes of the spinner
    @param Spinner, EventListener
    @return void
    */
    public void spinnerSetup(Spinner spinner, EventListener eventListener) {
        // creating a list to add to the adapter
        List<CharSequence> hairTypes = new ArrayList<>();
        hairTypes.add("Side Sweep");
        hairTypes.add("Balding:(");
        hairTypes.add("Mohawk");
        /*
          External Citation
            Date: 13 Feb 2018
            Problem: didn't know how to select default value for spinner
            Resource:https://stackoverflow.com/questions/17063611/show-default-value-in-spinner-in-android
            Solution: I used code from here
         */
        spinner.setSelection(faceCanvas.getHairStyle());
        spinner.setOnItemSelectedListener(eventListener);

        /*
        External Citation
        Date: 11 Feb 2018
        Problem: couldn't remember how to add values to the spinner
        Resource:  https://www.mkyong.com/android/android-spinner-drop-down-list-example/
        Solution: this post showed me how to do it

         */
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, hairTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

}
