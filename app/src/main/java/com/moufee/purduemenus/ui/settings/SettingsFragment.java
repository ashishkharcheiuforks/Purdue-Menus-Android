package com.moufee.purduemenus.ui.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.moufee.purduemenus.R;
import com.moufee.purduemenus.repository.FavoritesRepository;
import com.moufee.purduemenus.ui.login.LoginActivity;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Displays preferences for the app
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    public static final String KEY_PREF_SHOW_SERVING_TIMES = "show_serving_times";
    public static final String KEY_PREF_USE_NIGHT_MODE = "night_mode";
    public static final String KEY_PREF_LOGGED_IN = "logged_in";
    public static final String KEY_PREF_USERNAME = "username";
    public static final String KEY_PREF_PASSWORD = "password";
    public static final String KEY_PREF_DINING_COURT_ORDER = "dining_court_order";
    public static final String PREF_LOG_IN = "log_in";
    public static final String KEY_PREF_PRIVACY_POLICY = "privacy_policy";

    private static final String TAG = "SettingsFragment";
    @Inject
    SharedPreferences mSharedPreferences;
    @Inject
    FavoritesRepository mFavoritesRepository;
    private Preference mLoginPref;
    private Preference mPrivacyPolicyPref;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_general, rootKey);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        addPreferencesFromResource(R.xml.pref_general);


        mLoginPref = findPreference(PREF_LOG_IN);
        Preference sortOrderPref = findPreference(KEY_PREF_DINING_COURT_ORDER);
        mPrivacyPolicyPref = findPreference(KEY_PREF_PRIVACY_POLICY);
        sortOrderPref.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(getActivity(), CustomOrderActivity.class));
            return true;
        });


        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        updateLoginPreference();

        mLoginPref.setOnPreferenceClickListener(preference -> {
            boolean isLoggedIn = mSharedPreferences.getBoolean(KEY_PREF_LOGGED_IN, false);
            if (isLoggedIn) {
                showLogoutPrompt();
                return true;
            } else {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                return true;
            }
        });

        mPrivacyPolicyPref.setOnPreferenceClickListener(preference -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://android.menus.purdue.tools/privacy"));
            startActivity(browserIntent);
            return true;
        });

        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    private void showLogoutPrompt() {
        AlertDialog logoutDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.title_prompt_clear_favorites)
                .setMessage(R.string.prompt_clear_local_favorites)
                .setPositiveButton(R.string.action_clear_favorites, (dialog, which) -> {
                    mFavoritesRepository.clearLocalFavorites();
                    logout();
                })
                .setCancelable(true)
                .setNegativeButton(R.string.action_only_logout, ((dialog, which) -> logout()))
                .create();
        logoutDialog.setOnShowListener(dialog -> logoutDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.RED));
        logoutDialog.show();
    }

    private void logout() {
        mSharedPreferences
                .edit()
                .putBoolean(KEY_PREF_LOGGED_IN, false)
                .putString(KEY_PREF_USERNAME, null)
                .putString(KEY_PREF_PASSWORD, null)
                .apply();
    }

    private void updateLoginPreference() {
        boolean isLoggedIn = mSharedPreferences.getBoolean(KEY_PREF_LOGGED_IN, false);
        if (isLoggedIn) {
            mLoginPref.setTitle(R.string.action_sign_out);
            mLoginPref.setSummary(getString(R.string.description_signed_in, mSharedPreferences.getString(KEY_PREF_USERNAME, "user")));
        } else {
            mLoginPref.setTitle(R.string.action_login);
            mLoginPref.setSummary(R.string.pref_summary_not_logged_in);
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_PREF_LOGGED_IN))
            updateLoginPreference();
    }
}
