package com.example.mymoviecatalogue.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.example.mymoviecatalogue.BuildConfig;
import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.reminder.NotifReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotifSettingActivity extends AppCompatActivity {
    @BindView(R.id.switch2)
    SwitchCompat dailyReminderSwitch;
    @BindView(R.id.switch1)
    SwitchCompat releaseReminderSwitch;
    @BindView(R.id.toolbar_setting)
    Toolbar toolbar;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferenceEdit;
    private NotifReceiver notifReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_setting);

        sharedPreferences = getSharedPreferences(BuildConfig.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        notifReceiver = new NotifReceiver(this);

        ButterKnife.bind(this);
        initToolbar();
        listenSwitchChanged();
        setPreferences();
    }

    private void listenSwitchChanged() {
        dailyReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferenceEdit = sharedPreferences.edit();
                sharedPreferenceEdit.putBoolean("daily_reminder", isChecked);
                sharedPreferenceEdit.apply();
                if (isChecked) {
                    notifReceiver.setDailyReminder();
                } else {
                    notifReceiver.cancelDailyReminder(getApplicationContext());
                }
            }
        });
        releaseReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferenceEdit = sharedPreferences.edit();
                sharedPreferenceEdit.putBoolean("release_reminder", isChecked);
                sharedPreferenceEdit.apply();
                if (isChecked) {
                    notifReceiver.setReleaseTodayReminder();
                } else {
                    notifReceiver.cancelReleaseToday(getApplicationContext());
                }
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.notification_setting_title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }

    private void setPreferences() {
        boolean dailyReminder = sharedPreferences.getBoolean("daily_reminder", false);
        boolean releaseReminder = sharedPreferences.getBoolean("release_reminder", false);

        dailyReminderSwitch.setChecked(dailyReminder);
        releaseReminderSwitch.setChecked(releaseReminder);
    }

}
