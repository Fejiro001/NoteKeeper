package com.example.notekeeper;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static org.hamcrest.Matchers.*;
import java.util.List;

import static org.junit.Assert.*;

public class NextThroughNotesTest {
    //The testing will start the activity before any of our test is run inside this class
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    //Testing note
    public void NextThroughNotes() {
        //Gets view of drawer layout instance
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        //Selects our note option
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_notes));

        //Perform an action on the recycler view item
        onView(withId(R.id.list_items)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Reference to notes list from our DataManger
        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        //Loops from first note to last note
        for (int index = 0; index < notes.size(); index++) {
            NoteInfo note = notes.get(index);

            //Checks if the spinner has the title of the current course for our note at the specified position
            onView(withId(R.id.spinner_courses)).check(
                    matches(withSpinnerText(note.getCourse().getTitle())));
            //Checks if the text field has the title for our note at the specified position
            onView(withId(R.id.text_note_title)).check(matches(withText(note.getTitle())));
            //Checks if the text field has the text for our note at the specified position
            onView(withId(R.id.text_note_text)).check(matches(withText(note.getText())));

            if (index < notes.size() - 1)
                //Clicks on the next icon in the note activity to got the next note when next item is enabled
                onView(allOf(withId(R.id.action_next), isEnabled())).perform(click());
        }
        onView(withId(R.id.action_next)).check(matches(not(isEnabled())));
        pressBack();
    }
}