package ru.nsu.fit.g20203.sinyukov.googlebooksapp.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

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

    private static final int imageSize = 300;

    private DetailsActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle volumeBundle = getIntent().getExtras();
        final Volume volume = (Volume) volumeBundle.getSerializable(VOLUME_KEY);

        binding = DetailsActivityBinding.inflate(getLayoutInflater());

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
        binding.titleTextView.setText(getText(volumeInfo.getTitle()));
        binding.authorTextView.setText(getText(", ", volumeInfo.getAuthors()));
        binding.categoryTextView.setText(getText(", ", getString(R.string.categories) + ": ", volumeInfo.getCategories()));
        binding.descriptionTextView.setText(getText(volumeInfo.getDescription()));
        binding.publisherTextView.setText(getText(volumeInfo.getPublisher()));
        binding.publishedDateTextView.setText(getText(volumeInfo.getPublishedDate()));
        binding.languageTextView.setText(getText(getString(R.string.language) + ": ", volumeInfo.getLanguage()));
        binding.ratingsTextView.setText(getRatingText(volumeInfo));

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
}
