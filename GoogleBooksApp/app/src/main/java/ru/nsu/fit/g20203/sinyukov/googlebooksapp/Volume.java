package ru.nsu.fit.g20203.sinyukov.googlebooksapp;

import com.google.gson.annotations.SerializedName;

public class Volume {

    private VolumeInfo volumeInfo;
    private AccessInfo accessInfo;

    public static class VolumeInfo {
        private ImageLinks imageLinks;
        private String title;
        private String description;
        private String[] authors;
        private String publisher;
        private String publishedDate;
        private String printType;
        private String mainCategory;
        private String[] categories;
        private double averageRating;
        private int ratingsCount;
        private String language;
        private String infoLink;

        public static class ImageLinks {
            private String smallThumbnail;
            private String thumbnail;
            private String small;
            private String medium;
            private String large;
            private String extraLarge;

            public String getSmallThumbnail() {
                return smallThumbnail;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public String getSmall() {
                return small;
            }

            public String getMedium() {
                return medium;
            }

            public String getLarge() {
                return large;
            }

            public String getExtraLarge() {
                return extraLarge;
            }
        }

        public ImageLinks getImageLinks() {
            return imageLinks;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String[] getAuthors() {
            return authors;
        }

        public String getPublisher() {
            return publisher;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public String getPrintType() {
            return printType;
        }

        public String getMainCategory() {
            return mainCategory;
        }

        public String[] getCategories() {
            return categories;
        }

        public double getAverageRating() {
            return averageRating;
        }

        public int getRatingsCount() {
            return ratingsCount;
        }

        public String getLanguage() {
            return language;
        }

        public String getInfoLink() {
            return infoLink;
        }
    }

    public static class AccessInfo {

        private PDF pdf;

        public static class PDF {
            private boolean isAvailable;
            private String downloadLink;

            public boolean isAvailable() {
                return isAvailable;
            }

            public String getDownloadLink() {
                return downloadLink;
            }
        }

        public PDF getPdf() {
            return pdf;
        }
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public AccessInfo getAccessInfo() {
        return accessInfo;
    }
}
