package com.example.notekeeper;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.hamcrest.Matchers.*;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.*;

@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {
    static DataManager sDataManager;
    //Get data manager
    @BeforeClass
    public static void classSetup() {
        sDataManager = DataManager.getInstance();
    }
    //field of rule created
    //Will take care of launching the activity
    @Rule
    public ActivityTestRule<NoteListActivity> mNoteListActivityRule =
            new ActivityTestRule<>(NoteListActivity.class);

    @Test
    public void createNewNote() {
        //Declared and initialized our course, title and text body variables
        final CourseInfo course = sDataManager.getCourse("java_lang");
        final String noteTitle = "Test note title";
        final String noteText = "This is the body of our test note";
//        ViewInteraction fabNewNote = onView(withId(R.id.fab));
//        fabNewNote.perform(click());

        //This performs a click action on the fab
        onView(withId(R.id.fab)).perform(click());

        //The spinner for courses is clicked
        onView(withId(R.id.spinner_courses)).perform(click());
        /*This will look through the adapter views on this activity and
         *then find the one that holds CourseInfo instance who's equal to
         *our course variable
         */
        onData(allOf(instanceOf(CourseInfo.class), equalTo(course))).perform(click());
        //Checking UI behavior of spinner text
        onView(withId(R.id.spinner_courses)).check(matches(withSpinnerText(
                containsString(course.getTitle()))));

        //This types a title for the note in the text field b
        onView(withId(R.id.text_note_title)).perform(typeText(noteTitle))
                //Checking UI behavior of the note title using ViewAssertion
                .check(matches(withText(containsString(noteTitle))));

        //This types a text for the body of the note in the text field
        onView(withId(R.id.text_note_text)).perform(typeText(noteText),
                //another action
                closeSoftKeyboard());
        //Checking UI behavior of the note text using ViewAssertion, not really
        // needed because we can rely on our typeText to put in the correct text we want
        onView(withId(R.id.text_note_text)).check(matches(withText(containsString(noteText))));

        pressBack();

        //Got the index of the last note in the list
        int noteIndex = sDataManager.getNotes().size() - 1;
        //Used index to get the last note
        NoteInfo note = sDataManager.getNotes().get(noteIndex);
        //Assert method to check the values inside the notes correspond
        // to all the values put in the screen
        assertEquals(course, note.getCourse());
        assertEquals(noteTitle, note.getTitle());
        assertEquals(noteText, note.getText());
    }
}