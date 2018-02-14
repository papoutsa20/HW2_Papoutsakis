package com.example.steli.hw_2_papoutsakis;


import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.ArrayList;


/**
 * Class that listens to events for buttons, seekbars, spinners and radio groups
 * Class changes the corresponding feature on face based on user input
 *
 * @Author Stelios Papoutsakis
 */

public class EventListener
        implements SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener,
        Button.OnClickListener, Spinner.OnItemSelectedListener {

    private FaceCanvas view;// face canvas variable
    private int featureSelect = 0; // number that represents which feature to change, 0->eyes,1->hair,2->skin
    ArrayList<SeekBar> seekBars = null; // array list of SeekBars
    private Spinner hairTypes; // Spinner


    // constructor takes in a Face Canvas, ArrayList of SeekBars and a Spinner
    public EventListener(FaceCanvas view, ArrayList<SeekBar> seekBars, Spinner hairTypes) {

        this.view = view;
        this.seekBars = seekBars;
        this.hairTypes = hairTypes;
    }

    @Override
    /*
    listener for the SeekBars
    depending on the feature select, the eye, hair or skin color will change
     */
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // which seek bar is it?
        int colorSelect = 0;
        if (seekBar.getId() == R.id.RedSeek)
            colorSelect = 0;
        else if (seekBar.getId() == R.id.GreenSeek)
            colorSelect = 1;
        else if (seekBar.getId() == R.id.BlueSeek)
            colorSelect = 2;

        // picks which feature to modify based on featureSelect
        if (this.featureSelect == 0) {
            this.view.setEyeColor(this.view.featureColorConverter(progress, colorSelect, this.featureSelect));
        } else if (this.featureSelect == 1) {

            view.setHairColor(this.view.featureColorConverter(progress, colorSelect, this.featureSelect));
        } else if (this.featureSelect == 2) {
            this.view.setSkinTone(this.view.featureColorConverter(progress, colorSelect, this.featureSelect));
        }
        //repaint
        this.view.invalidate();
    }


    /*
    don't care about these two methods
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }


    /*
    see which button is checked, and assign feature select an appropriate value
    and assign seekbars its appropriate color values
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //eyes radio button
        if (checkedId == R.id.RadioEyesbutton) {
            this.featureSelect = 0;
            seekBarReset();
            //Hair radio button
        } else if (checkedId == R.id.RadioHairButton) {
            this.featureSelect = 1;
            seekBarReset();
            //skin radio button
        } else if (checkedId == R.id.RadioSkinButton) {
            this.featureSelect = 2;
            seekBarReset();

        }


    }


    /*
    if the randomize button is clicked, run the randomize method
    reset seekbars to their color value
     */
    public void onClick(View v) {
        if (v.getId() == R.id.RandomButton) {
            this.view.randomize();
            seekBarReset();
            // make sure the spinner displays the correct hairstyle string
            this.hairTypes.setSelection(this.view.getHairStyle());

        }
        //repaint
        this.view.invalidate();
    }

    /*
    resets progress of the seekbars based on what feature is selected
     */

    public void seekBarReset() {
        switch (this.featureSelect) {
            // for eyes
            case 0:
                this.seekBars.get(0).setProgress(Color.red(view.getEyeColor()));
                this.seekBars.get(1).setProgress(Color.green(view.getEyeColor()));
                this.seekBars.get(2).setProgress(Color.blue(view.getEyeColor()));
                break;
            //for hair
            case 1:
                this.seekBars.get(0).setProgress(Color.red(view.getHairColor()));
                this.seekBars.get(1).setProgress(Color.green(view.getHairColor()));
                this.seekBars.get(2).setProgress(Color.blue(view.getHairColor()));
                break;
            // for skin tone
            case 2:
                this.seekBars.get(0).setProgress(Color.red(view.getSkinTone()));
                this.seekBars.get(1).setProgress(Color.green(view.getSkinTone()));
                this.seekBars.get(2).setProgress(Color.blue(view.getSkinTone()));
                break;
        }
    }

    /*
    draw the correct hairstyle based on what the user picks on the spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.view.setHairStyle(position);
        this.view.invalidate();


    }

    @Override  // don't care about this
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
