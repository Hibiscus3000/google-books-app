package ru.nsu.fit.g20203.sinyukov.googlebooksapp.activity;

import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.R;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.databinding.DetailedSearchBinding;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.databinding.ListActivityBinding;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.fragment.RecyclerViewFragment;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.fragment.TextFragment;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.request.PrintType;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.request.VolumesRequest;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.viewmodel.VolumesViewModel;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.viewmodel.RetrofitVolumesViewModelFactory;

public class ListActivity extends AppCompatActivity {

    private ListActivityBinding activityBinding;
    private DetailedSearchBinding detailedSearchBinding;

    private VolumesViewModel volumesViewModel;

    private static final String SEARCH_MENU_EXPANDED_KEY = "EXPANDED";
    private boolean searchMenuExpanded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBinding = ListActivityBinding.inflate(getLayoutInflater());
        detailedSearchBinding = activityBinding.detailedSearch;
        final View view = activityBinding.getRoot();
        setContentView(view);

        volumesViewModel = new ViewModelProvider(this, new RetrofitVolumesViewModelFactory())
                .get(VolumesViewModel.class);

        activityBinding.titleEditText.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search();
                handled = true;
            }
            return handled;
        });

        activityBinding.searchButton.setOnClickListener(v -> search());

        if (null == savedInstanceState) {
            searchMenuExpanded = false;
        } else {
            searchMenuExpanded = savedInstanceState.getBoolean(SEARCH_MENU_EXPANDED_KEY);
        }
        activityBinding.moreButton.setOnClickListener(v -> {
            searchMenuExpanded = !searchMenuExpanded;
            if (searchMenuExpanded) {
                activityBinding.titleEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                detailedSearchBinding.getRoot().setVisibility(View.VISIBLE);
                activityBinding.moreButton.setImageDrawable(getDrawable(R.drawable.up));
            } else {
                activityBinding.titleEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                detailedSearchBinding.getRoot().setVisibility(View.GONE);
                activityBinding.moreButton.setImageDrawable(getDrawable(R.drawable.down));
            }
        });

        volumesViewModel.getEndOfPaginationReachedLiveData().observe(this,
                endOfPaginationReached -> setEndOfPagingOrErrorTextView(R.string.endOfPagination, endOfPaginationReached));
        volumesViewModel.getErrorOccurredLiveData().observe(this,
                errorOccurred -> setEndOfPagingOrErrorTextView(R.string.networkError, errorOccurred));
        volumesViewModel.getNoResultsLiveData().observe(this,
                noResults -> {
                    if (noResults) {
                        createTextFragment(R.string.noResultsQuery);
                    }
                });

        activityBinding.clearButton.setOnClickListener(v -> {
            createTextFragment(R.string.queryPrompt);
            clear();
        });

        createTextFragment(R.string.queryPrompt);
    }

    private void setEndOfPagingOrErrorTextView(@StringRes Integer textRes, boolean b) {
        activityBinding.endOfPagingOrErrorTextView.setText(b ? getString(textRes) : "");
    }

    private void createTextFragment(@StringRes Integer textRes) {
        final Bundle bundle = new Bundle();
        bundle.putString(TextFragment.TEXT_KEY, getString(textRes));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recyclerViewContainer, TextFragment.class, bundle)
                .setReorderingAllowed(true)
                .commit();
    }

    private void createRecyclerViewFragment() {
        final Bundle bundle = new Bundle();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recyclerViewContainer, new RecyclerViewFragment())
                .setReorderingAllowed(true)
                .commit();
    }

    private void clear() {
        activityBinding.titleEditText.setText("");
        detailedSearchBinding.publisherEditText.setText("");
        detailedSearchBinding.authorEditText.setText("");
        detailedSearchBinding.categoryEditText.setText("");
        detailedSearchBinding.anyRadioButton.setChecked(true);
    }

    private void search() {
        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        final IBinder windowToken = getCurrentFocus().getWindowToken();
        if (null != windowToken) {
            imm.hideSoftInputFromWindow(windowToken, 0);
        }
        createRecyclerViewFragment();
        createCheckAndDespatchRequest();
    }

    private void createCheckAndDespatchRequest() {
        final VolumesRequest volumesRequest = createRequest();
        if (volumesRequest.isValid()) {
            volumesViewModel.setVolumesRequest(volumesRequest);
        }
    }

    private VolumesRequest createRequest() {
        return new VolumesRequest(activityBinding.titleEditText.getText().toString(),
                detailedSearchBinding.authorEditText.getText().toString(),
                detailedSearchBinding.publisherEditText.getText().toString(),
                getPrintType(),
                detailedSearchBinding.categoryEditText.getText().toString());
    }

    private PrintType getPrintType() {
        @IdRes Integer checkedRadioButtonId = detailedSearchBinding.printTypeRadioGroup.getCheckedRadioButtonId();
        if (R.id.booksRadioButton == checkedRadioButtonId) {
            return PrintType.BOOKS;
        }
        if (R.id.magazinesRadioButton == checkedRadioButtonId) {
            return PrintType.MAGAZINES;
        }
        return PrintType.ANY;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(SEARCH_MENU_EXPANDED_KEY, searchMenuExpanded);
    }
}