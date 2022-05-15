package com.sk.ordinary.fragment;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.material.color.DynamicColors;
import com.sk.ordinary.App;
import com.sk.ordinary.BuildConfig;

import com.sk.ordinary.R;
import com.sk.ordinary.activity.SettingsActivity;
import com.sk.ordinary.databinding.FragmentSettingsBinding;
import com.sk.ordinary.utils.ThemeUtils;



import rikka.core.util.ResourceUtils;
import rikka.material.app.DayNightDelegate;
import rikka.material.app.LocaleDelegate;
import rikka.recyclerview.EdgeSpacing;
import rikka.recyclerview.RecyclerViewKt;
import rikka.widget.borderview.BorderRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends BaseFragment {
    FragmentSettingsBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        binding.appBar.setLiftable(true);
        setupToolbar(binding.toolbar, binding.clickView, R.string.action_settings);
        //binding.toolbar.setNavigationIcon(null);
        /**
         * 判断是否第一次  不是则添加
         */
        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.setting_container, new PreferenceFragment()).commitNow();
        }

        binding.toolbar.setSubtitle(String.format(LocaleDelegate.getDefaultLocale(), "%s", BuildConfig.VERSION_NAME));
        return binding.getRoot();
    }

    /**
     * 生命周期函数
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static class PreferenceFragment extends PreferenceFragmentCompat {
        private SettingsFragment parentFragment;



        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
            parentFragment = (SettingsFragment) requireParentFragment();
        }

        @Override
        public void onDetach() {
            super.onDetach();
            parentFragment = null;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            parentFragment = null;
        }

        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {

            addPreferencesFromResource(R.xml.settings);

            //  获取设置中的夜间主题的值
            Preference theme = findPreference("dark_theme");
            if (theme != null) {
                theme.setOnPreferenceChangeListener(((preference, newValue) -> {
                    if(!App.getPreferences().getString("dark_theme", ThemeUtils.MODE_NIGHT_FOLLOW_SYSTEM).equals(newValue)) {
                        DayNightDelegate.setDefaultNightMode(ThemeUtils.getDarkTheme((String) newValue));
                        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
                        if(settingsActivity != null) {
                            settingsActivity.restart();
                        }
                    }

                    return true;
                }));
            }
            /**
             * 黑色主题的监听
             */
            Preference black_dark_theme = findPreference("black_dark_theme");
            if(black_dark_theme != null) {
                black_dark_theme.setOnPreferenceChangeListener((preference, newValue) -> {
                    SettingsActivity settingsActivity = (SettingsActivity) getActivity();
                    if(settingsActivity != null && ResourceUtils.isNightMode(getResources().getConfiguration())) {
                        settingsActivity.restart();
                    }
                    return true;
                });
            }
            //获取颜色主题得改变得监听处理
            Preference primary_color = findPreference("theme_color");
            if(primary_color != null) {
                primary_color.setOnPreferenceChangeListener(((preference, newValue) -> {
                    SettingsActivity settingsActivity = (SettingsActivity) getActivity();
                    if(settingsActivity != null) {
                        settingsActivity.restart();
                    }
                    return true;
                }));
            }
            /**
             * 检查是否跟随系统是否开启的监听
             */
            SwitchPreference prefFollowSystemAccent = findPreference("follow_system_accent");
            if (prefFollowSystemAccent != null && DynamicColors.isDynamicColorAvailable()) {
                if (primary_color != null) {
                    primary_color.setVisible(!prefFollowSystemAccent.isChecked());
                }
                prefFollowSystemAccent.setVisible(true);
                prefFollowSystemAccent.setOnPreferenceChangeListener((preference, newValue) -> {
                    SettingsActivity settingsActivity = (SettingsActivity) getActivity();
                    if(settingsActivity != null) {
                        settingsActivity.restart();
                    }
                    return true;
                });
            }

        }


        @NonNull
        @Override
        public RecyclerView onCreateRecyclerView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, Bundle savedInstanceState) {
            BorderRecyclerView recyclerView =  (BorderRecyclerView)super.onCreateRecyclerView(inflater, parent, savedInstanceState);
            RecyclerViewKt.fixEdgeEffect(recyclerView, false, true);
            //RecyclerViewKt.addEdgeSpacing(8f, TypedValue.COMPLEX_UNIT_DIP);
            recyclerView.getBorderViewDelegate().setBorderVisibilityChangedListener((top, oldTop, bottom, oldBottom) -> parentFragment.binding.appBar.setLifted(!top));
            var fragment = getParentFragment();
            if (fragment instanceof SettingsFragment) {
                var settingsFragment = (SettingsFragment) fragment;
                View.OnClickListener l = v -> {
                    settingsFragment.binding.appBar.setExpanded(true, true);
                    recyclerView.smoothScrollToPosition(0);
                };
                settingsFragment.binding.toolbar.setOnClickListener(l);
                settingsFragment.binding.clickView.setOnClickListener(l);
            }
            return recyclerView;
        }

    }

}