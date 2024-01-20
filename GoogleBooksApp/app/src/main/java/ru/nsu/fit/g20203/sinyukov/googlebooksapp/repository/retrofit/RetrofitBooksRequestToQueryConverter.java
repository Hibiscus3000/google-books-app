package ru.nsu.fit.g20203.sinyukov.googlebooksapp.repository.retrofit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.request.PagedVolumesRequest;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.request.VolumesRequest;

public class RetrofitBooksRequestToQueryConverter {

    private static final String Q_KEY = "q";
    private static final String TITLE_KEY = "intitle";
    private static final String AUTHOR_KEY = "inauthor";
    private static final String PUBLISHER_KEY = "inpublisher";
    private static final String CATEGORY_KEY = "subject";
    private static final String PRINT_TYPE_KEY = "printType";
    private static final String START_INDEX_KEY = "startIndex";
    private static final String MAX_RESULTS_KEY = "maxResultsKey";
    private static final String PAIRS_DELIMITER = "+";
    private static final String KEY_VALUE_DELIMITER = ":";

    public static Map<String, String> booksRequest2queryMap(PagedVolumesRequest booksRequest) {
        final Map<String, String> queryMap = new HashMap<>();
        putIfNotEmptyNorNull(queryMap, Q_KEY, createQValue(booksRequest.getVolumesRequest()));
        putIfNotEmptyNorNull(queryMap, PRINT_TYPE_KEY, booksRequest.getVolumesRequest().getPrintType());
        putIfNotEmptyNorNull(queryMap, START_INDEX_KEY, booksRequest.getStartIndex().toString());
        putIfNotEmptyNorNull(queryMap, MAX_RESULTS_KEY, booksRequest.getMaxResults().toString());
        return queryMap;
    }

    private static void putIfNotEmptyNorNull(Map<String, String> map, String key, String value) {
        if (null != value && !value.isEmpty()) {
            map.put(key, value);
        }
    }

    private static String createQValue(VolumesRequest volumesRequest) {
        final List<String> qParts = new ArrayList<>();
        appendIfNotEmptyNorNull(qParts, TITLE_KEY, volumesRequest.getTitle());
        appendIfNotEmptyNorNull(qParts, AUTHOR_KEY, volumesRequest.getAuthor());
        appendIfNotEmptyNorNull(qParts, PUBLISHER_KEY, volumesRequest.getPublisher());
        appendIfNotEmptyNorNull(qParts, CATEGORY_KEY, volumesRequest.getCategory());
        return String.join(PAIRS_DELIMITER, qParts.toArray(new String[0]));
    }

    private static void appendIfNotEmptyNorNull(List<String> stringList, String key, String value) {
        if (null != value && !value.isEmpty()) {
            stringList.add(key + KEY_VALUE_DELIMITER + value);
        }
    }
}
