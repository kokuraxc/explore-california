package com.zhengjin.guo.explorecalifornia.domain;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Tour {
    @Id
    private String id;

    @Indexed
    private String title;

    @Indexed
    private String tourPackageCode;

    private String tourPackageName;

    private Map<String, String> details;

    protected Tour() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTourPackageCode() {
        return tourPackageCode;
    }

    public String getTourPackageName() {
        return tourPackageName;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public Tour(String title, TourPackage tourPackage, Map<String, String> details) {
        this.title = title;
        this.tourPackageCode = tourPackage.getCode();
        this.tourPackageName = tourPackage.getName();
        this.details = details;
    }

    @Override
    public String toString() {
        return "Tour{" + "id='" + id + '\'' + ", details=" + details + '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((details == null) ? 0 : details.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((tourPackageCode == null) ? 0 : tourPackageCode.hashCode());
        result = prime * result + ((tourPackageName == null) ? 0 : tourPackageName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tour other = (Tour) obj;
        if (details == null) {
            if (other.details != null)
                return false;
        } else if (!details.equals(other.details))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (tourPackageCode == null) {
            if (other.tourPackageCode != null)
                return false;
        } else if (!tourPackageCode.equals(other.tourPackageCode))
            return false;
        if (tourPackageName == null) {
            if (other.tourPackageName != null)
                return false;
        } else if (!tourPackageName.equals(other.tourPackageName))
            return false;
        return true;
    }

}
