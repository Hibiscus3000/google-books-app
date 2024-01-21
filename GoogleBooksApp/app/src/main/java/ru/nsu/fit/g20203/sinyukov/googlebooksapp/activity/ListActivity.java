package ru.nsu.fit.g20203.sinyukov.googlebooksapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.RecyclerView;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.R;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.VolumeComparator;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.VolumesAdapter;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.databinding.DetailedSearchBinding;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.databinding.ListActivityBinding;
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
                        createText(R.string.noResultsQuery);
                    }
                });

        activityBinding.clearButton.setOnClickListener(v -> {
            createText(R.string.queryPrompt);
            clear();
        });

        final VolumesAdapter volumesAdapter = new VolumesAdapter(new VolumeComparator(), volume -> {
            Intent i = new Intent(this, DetailsActivity.class);
            i.putExtra(DetailsActivity.VOLUME_KEY, volume);
            startActivity(i);
        });
        final RecyclerView recyclerView = activityBinding.recyclerView;
        recyclerView.setAdapter(volumesAdapter);
        volumesViewModel.getPagerLiveData().observe(this, volumePagingData ->
                volumesAdapter.submitData(getLifecycle(), volumePagingData));

        volumesAdapter.addLoadStateListener(loadState -> {
            if (loadState.getSource().getRefresh() instanceof LoadState.Error) {
                volumesViewModel.setErrorOccurred(true);
            } else {
                volumesViewModel.setErrorOccurred(true);
            }
            if (loadState.getSource().getRefresh() instanceof LoadState.NotLoading
                    && loadState.getAppend().getEndOfPaginationReached()) {
                if (volumesAdapter.getItemCount() >= 1) {
                    volumesViewModel.setEndOfPaginationReached(true);
                    volumesViewModel.setNoResults(false);
                } else {
                    volumesViewModel.setEndOfPaginationReached(false);
                    volumesViewModel.setNoResults(true);
                }
            } else {
                volumesViewModel.setEndOfPaginationReached(false);
                volumesViewModel.setNoResults(false);
            }
            return null;
        });

        volumesViewModel.getVolumesRequestLiveData().observe(this, volumePagingData -> volumesAdapter.refresh());

        createText(R.string.queryPrompt);
    }

    private void setEndOfPagingOrErrorTextView(@StringRes Integer textRes, boolean b) {
        activityBinding.endOfPagingOrErrorTextView.setText(b ? getString(textRes) : "");
    }

    private void createText(@StringRes Integer textRes) {
        activityBinding.recyclerView.setVisibility(View.GONE);
        activityBinding.searchTextView.setVisibility(View.VISIBLE);
        activityBinding.searchTextView.setText(getString(textRes));
    }

    private void createRecyclerViewFragment() {
        activityBinding.recyclerView.setVisibility(View.VISIBLE);
        activityBinding.searchTextView.setVisibility(View.GONE);
    }

    private void clear() {
        activityBinding.titleEditText.setText("");
        detailedSearchBinding.publisherEditText.setText("");
        detailedSearchBinding.authorEditText.setText("");
        detailedSearchBinding.categoryEditText.setText("");
        detailedSearchBinding.anyRadioButton.setChecked(true);

        volumesViewModel.clear();
    }

    private void search() {
        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        final View currentFocus = getCurrentFocus();
        if (null != currentFocus) {
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
        createCheckAndDispatchRequest();
    }

    private void createCheckAndDispatchRequest() {
        final VolumesRequest volumesRequest = createRequest();
        if (volumesRequest.isValid()) {
            volumesViewModel.setVolumesRequest(volumesRequest);
            createRecyclerViewFragment();
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