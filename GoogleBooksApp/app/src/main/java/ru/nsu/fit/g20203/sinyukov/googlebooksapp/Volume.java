package ru.nsu.fit.g20203.sinyukov.googlebooksapp;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Objects;

public class Volume {

    private String id;
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

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ImageLinks that = (ImageLinks) o;
                return Objects.equals(smallThumbnail, that.smallThumbnail) && Objects.equals(thumbnail, that.thumbnail) && Objects.equals(small, that.small) && Objects.equals(medium, that.medium) && Objects.equals(large, that.large) && Objects.equals(extraLarge, that.extraLarge);
            }

            @Override
            public int hashCode() {
                return Objects.hash(smallThumbnail, thumbnail, small, medium, large, extraLarge);
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

        public void setTitle(String title) {
            this.title = title;
        }

        public void setAuthors(String[] authors) {
            this.authors = authors;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VolumeInfo that = (VolumeInfo) o;
            return Double.compare(that.averageRating, averageRating) == 0 && ratingsCount == that.ratingsCount && Objects.equals(imageLinks, that.imageLinks) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Arrays.equals(authors, that.authors) && Objects.equals(publisher, that.publisher) && Objects.equals(publishedDate, that.publishedDate) && Objects.equals(printType, that.printType) && Objects.equals(mainCategory, that.mainCategory) && Arrays.equals(categories, that.categories) && Objects.equals(language, that.language) && Objects.equals(infoLink, that.infoLink);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(imageLinks, title, description, publisher, publishedDate, printType, mainCategory, averageRating, ratingsCount, language, infoLink);
            result = 31 * result + Arrays.hashCode(authors);
            result = 31 * result + Arrays.hashCode(categories);
            return result;
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

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                PDF pdf = (PDF) o;
                return isAvailable == pdf.isAvailable && Objects.equals(downloadLink, pdf.downloadLink);
            }

            @Override
            public int hashCode() {
                return Objects.hash(isAvailable, downloadLink);
            }
        }

        public PDF getPdf() {
            return pdf;
        }
    }

    public String getId() {
        return id;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public AccessInfo getAccessInfo() {
        return accessInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volume volume = (Volume) o;
        return Objects.equals(id, volume.id) && Objects.equals(volumeInfo, volume.volumeInfo) && Objects.equals(accessInfo, volume.accessInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, volumeInfo, accessInfo);
    }

    public void setVolumeInfo() {
        volumeInfo = new VolumeInfo();
    }


}
