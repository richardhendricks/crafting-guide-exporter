package com.craftingguide.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import org.junit.Before;
import org.junit.Test;

public class PatternSwitchTest {

    // Set Up & Tear Down //////////////////////////////////////////////////////////////////////////////////////////////

    @Before
    public void beforeEach() {
        this.items = new ArrayList<String>();
        this.consumer = (item)-> this.items.add(item);
        this.switcher = new PatternSwitcher<String>(this.consumer);
    }

    // Tests ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testDefaultConsumerIsUsedInMatching() {
        PatternSwitcher<String> s = new PatternSwitcher<String>(this.consumer);
        s.addPattern(".*");

        s.match("alpha", "a");
        assertThat(this.items, hasItems("a"));
    }

    @Test
    public void testMultiplePatternsUseDefaultConsumer() {
        PatternSwitcher<String> s = new PatternSwitcher<String>(this.consumer);
        s.addAllPatterns(Arrays.asList("^a.*", ".*o$", "^c.*e$"));

        s.match("alpha", "a");
        s.match("bravo", "b");
        s.match("charlie", "c");

        assertThat(this.items, hasItems("a", "b", "c"));
    }

    @Test
    public void testEachPatternCanHaveConsumer() {
        String[] results = new String[3];
        PatternSwitcher<String> s = new PatternSwitcher<String>();
        s.addPattern("^a.*", (text)-> results[0] = text);
        s.addPattern(".*o$", (text)-> results[1] = text);
        s.addPattern("^c.*e$", (text)-> results[2] = text);

        s.match("alpha", "a");
        s.match("bravo", "b");
        s.match("charlie", "c");

        assertThat(results[0], equalTo("a"));
        assertThat(results[1], equalTo("b"));
        assertThat(results[2], equalTo("c"));
    }

    // Fixtures ////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Consumer<String> consumer = null;

    private ArrayList<String> items = null;

    private PatternSwitcher<String> switcher = null;
}
