package com.example.erik.eriknevilead340;


import android.content.SharedPreferences;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.erik.eriknevilead340.SharedPreferencesHelper;

@RunWith(MockitoJUnitRunner.class)
public class SharedPreferencesUnitTest {
    @Mock
    SharedPreferences mMockSharedPreferences;

    @Mock
    SharedPreferences.Editor mMockEditor;

    private SharedPreferencesHelper mMockSharedPreferencesHelper;

    private String txt = "Sample Text";

    @Before
    public void initMocks() {
        mMockSharedPreferencesHelper = createMockSharedPreference();
    }

    private SharedPreferencesHelper createMockSharedPreference() {
        when(mMockSharedPreferences.getString(eq("Sample Text"), anyString())).thenReturn(txt);

        when(mMockEditor.commit()).thenReturn(true);

        when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);

        return new SharedPreferencesHelper(mMockSharedPreferences);
    }

    @Test
    private void saveAndReadEntry() {
        boolean success = mMockSharedPreferencesHelper.saveEntry(txt);

        assertThat("SharedPreferenceEntry.save... returns true",
                success, is(true));

        assertEquals(txt, mMockSharedPreferencesHelper.getKeyEntry());
    }
}
