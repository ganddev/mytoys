package de.ahlfeld.mytoys.navigation;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.ahlfeld.mytoys.R;
import de.ahlfeld.mytoys.data.NavigationEntry;
import de.ahlfeld.mytoys.databinding.NavigationEntryBinding;
import de.ahlfeld.mytoys.databinding.NavigationHeaderBinding;

/**
 * Created by bjornahlfeld on 01.02.18.
 */

public class NavigationEntryAdapter extends ArrayAdapter<NavigationEntry> {
    private static final int VIEWTYPES = 2; //Header + items
    private static final int HEADER_ELEMENT = 0;
    private static final int ITEM_ELEMENT = 1;
    private NavigationEntryItemNavigator mNavigationEntryItemNavigator;
    private List<NavigationEntry> mValues;

    public NavigationEntryAdapter(@NonNull Context context,
                                  @NonNull NavigationEntryItemNavigator navigationEntryItemNavigator) {
        super(context, R.layout.navigation_entry, new ArrayList<NavigationEntry>());
        mValues = Collections.emptyList();
        mNavigationEntryItemNavigator = navigationEntryItemNavigator;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == HEADER_ELEMENT) {
            return HEADER_ELEMENT;
        }
        return ITEM_ELEMENT;
    }

    @Override
    public int getViewTypeCount() {
        return VIEWTYPES;
    }

    @Override
    public NavigationEntry getItem(int position) {
        return mValues.get(position);
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    public void onDestroy() {
        mNavigationEntryItemNavigator = null;
    }

    public void replaceData(@NonNull NavigationEntry parent,
                            @NonNull List<NavigationEntry> values) {
        setList(parent, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        final NavigationEntry navigationEntry = getItem(position);
        final LayoutInflater inflater = (LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (type) {
            case HEADER_ELEMENT:
                return reanderHeader(navigationEntry, inflater, parent);
            case ITEM_ELEMENT:
                return renderItemElement(navigationEntry, inflater, parent, convertView);
            default:
                throw new IllegalStateException("Type is not supported");
        }
    }

    private View renderItemElement(@NonNull NavigationEntry navigationEntry,
                                   @NonNull LayoutInflater inflater,
                                   @NonNull ViewGroup parent,
                                   @NonNull View convertView) {
        NavigationEntryBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(inflater,
                    R.layout.navigation_entry,
                    parent,
                    false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        setNavigationEntry(binding, navigationEntry);
        setupChildrens(binding.containerChildren, navigationEntry.getChildren());
        return binding.getRoot();
    }

    private View reanderHeader(@NonNull NavigationEntry navigationEntry,
                               @NonNull LayoutInflater inflater,
                               @NonNull ViewGroup parent) {
        NavigationHeaderBinding headerBinding = DataBindingUtil.inflate(inflater,
                R.layout.navigation_header,
                parent,
                false);
        setHeaderEntry(headerBinding, navigationEntry);

        return headerBinding.getRoot();
    }

    private void setHeaderEntry(@NonNull NavigationHeaderBinding binding,
                                @NonNull NavigationEntry navigationEntry) {
        if (binding.getViewModel() == null) {
            NavigationHeaderViewModel viewModel = new NavigationHeaderViewModel(navigationEntry);
            viewModel.setmItemNavigator(mNavigationEntryItemNavigator);
            binding.setViewModel(viewModel);
        }
    }

    private void setList(NavigationEntry parent, List<NavigationEntry> values) {
        mValues = new ArrayList<>(values);
        mValues.add(HEADER_ELEMENT, parent);
        notifyDataSetChanged();
    }

    private void setNavigationEntry(@NonNull NavigationEntryBinding binding,
                                    @NonNull NavigationEntry navigationEntry) {
        if (binding.getNavigationEntryViewModel() == null) {
            NavigationEntryViewModel viewModel = new NavigationEntryViewModel(navigationEntry);
            viewModel.setItemNavigator(mNavigationEntryItemNavigator);
            binding.setNavigationEntryViewModel(viewModel);
        } else {
            binding.getNavigationEntryViewModel().setNavigationEntry(navigationEntry);
        }
        binding.executePendingBindings();
    }

    private void setupChildrens(LinearLayout containerChildren, List<NavigationEntry> children) {
        containerChildren.removeAllViews();
        for (NavigationEntry entry : children) {
            NavigationEntryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                    R.layout.navigation_entry,
                    containerChildren,
                    false);
            NavigationEntryViewModel navigationEntryViewModel = new NavigationEntryViewModel(entry);
            navigationEntryViewModel.setItemNavigator(mNavigationEntryItemNavigator);
            binding.setNavigationEntryViewModel(navigationEntryViewModel);
            containerChildren.addView(binding.getRoot());
        }
    }
}
