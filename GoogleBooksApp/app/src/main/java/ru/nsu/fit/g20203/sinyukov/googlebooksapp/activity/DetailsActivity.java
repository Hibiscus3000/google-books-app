package ru.nsu.fit.g20203.sinyukov.googlebooksapp.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.R;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.Volume;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.VolumeImageLoader;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.databinding.DetailsActivityBinding;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";

    public static final String VOLUME_KEY = "VOLUME";

    private static final int imageSize = 400;

    private DetailsActivityBinding binding;

    private static final String DESCRIPTION_EXPANDED_KEY = "DESCRIPTION";
    private boolean descriptionExpanded;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle volumeBundle = getIntent().getExtras();
        final Volume volume = (Volume) volumeBundle.getSerializable(VOLUME_KEY);

        binding = DetailsActivityBinding.inflate(getLayoutInflater());

        if (null == savedInstanceState) {
             descriptionExpanded = true;
        } else {
            descriptionExpanded = savedInstanceState.getBoolean(DESCRIPTION_EXPANDED_KEY);
        }

        processVolumeInfo(volume.getVolumeInfo());
        processAccessInfo(volume.getAccessInfo());

        setContentView(binding.getRoot());
    }

    private void processVolumeInfo(Volume.VolumeInfo volumeInfo) {
        if (null == volumeInfo) {
            return;
        }

        VolumeImageLoader.loadImage(binding.imageView, volumeInfo.getImageLinks(),
                imageLinks -> imageLinks.get(), imageSize);

        binding.printTypeTextView.setText(getText(volumeInfo.getPrintType()));

        binding.titleTextView.textView.setText(getText(volumeInfo.getTitle()));
        binding.titleTextView.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        binding.titleTextView.textView.setTypeface(Typeface.DEFAULT_BOLD);

        binding.authorTextView.textView.setText(getText(", ", volumeInfo.getAuthors()));
        binding.authorTextView.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        binding.categoryTextView.textView.setText(getText(", ", getString(R.string.categories) + ": ", volumeInfo.getCategories()));
        binding.categoryTextView.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        binding.descriptionTextView.textView.setText(getText(volumeInfo.getDescription()));
        binding.descriptionTextView.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        binding.descriptionTextView.getRoot().setOnClickListener(v -> {
            descriptionExpanded = !descriptionExpanded;
            if (descriptionExpanded) {
                binding.descriptionTextView.textView.setMaxLines(numberOfLinesExapanded);
            } else {
                binding.descriptionTextView.textView.setMaxLines(numberOfLinesCollapsed);
            }
        });
        binding.descriptionTextView.getRoot().callOnClick();


        binding.publisherTextView.textView.setText(getText(volumeInfo.getPublisher()));
        binding.publisherTextView.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        binding.publishedDateTextView.textView.setText(getText(volumeInfo.getPublishedDate()));
        binding.publisherTextView.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        binding.languageTextView.textView.setText(getText(getString(R.string.language) + ": ", volumeInfo.getLanguage()));
        binding.languageTextView.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        binding.ratingsTextView.textView.setText(getRatingText(volumeInfo));
        binding.ratingsTextView.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        binding.goToGoogleBooksButton.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(volumeInfo.getInfoLink()))));
    }

    private String getRatingText(Volume.VolumeInfo volumeInfo) {
        final String averageRating = getText(String.valueOf(volumeInfo.getAverageRating()));
        final String ratingsCount = getText(String.valueOf(volumeInfo.getRatingsCount()));
        final String averageRatingWithPrefix = "Average rating: " + averageRating;
        final String ratingsCountWithPrefix = "Number of ratings: " + ratingsCount;
        if (!averageRating.isEmpty() && !ratingsCount.isEmpty()) {
            return averageRatingWithPrefix + "\n" + ratingsCountWithPrefix;
        }
        if (!averageRating.isEmpty()) {
            return averageRatingWithPrefix;
        }
        if (!ratingsCount.isEmpty()) {
            return ratingsCountWithPrefix;
        }
        return "";
    }

    private void processAccessInfo(Volume.AccessInfo accessInfo) {
        final Volume.AccessInfo.PDF pdf = accessInfo.getPdf();
        final String downloadLink = pdf.getDownloadLink();
        if (!pdf.isAvailable() || null == downloadLink) {
            binding.downloadPdfButton.setEnabled(false);
        } else {
            binding.downloadPdfButton.setOnClickListener(v -> {
                final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(downloadLink);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                final long reference = manager.enqueue(request);
                Log.i(TAG, "Started downloading PDF: " + reference);
            });
        }
    }

    @NonNull
    private String getText(String text) {
        return getText(text, "");
    }

    @NonNull
    private String getText(String prefix, String text) {
        return null != text ? prefix + text : "";
    }

    @NonNull
    private String getText(String delimiter, String... array) {
        return getText(delimiter, "", array);
    }

    @NonNull
    private String getText(String delimiter, String prefix, String... array) {
        return null != array ? prefix + String.join(delimiter, array) : "";
    }

    private static final int numberOfLinesCollapsed = 8;
    private static final int numberOfLinesExapanded = 100;

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(DESCRIPTION_EXPANDED_KEY, descriptionExpanded);
    }
}
