package com.group13.nyseenowbackend.model;
import java.util.Map;

public class Attraction {
    private Map<String, Double> position;
    private String name;
    private String type;
    private AllDetails allDetails;
    private String day;

    // Getters and Setters

    public Map<String, Double> getPosition() {
        return position;
    }

    public void setPosition(Map<String, Double> position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AllDetails getAllDetails() {
        return allDetails;
    }

    public void setAllDetails(AllDetails allDetails) {
        this.allDetails = allDetails;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public static class AllDetails {
        private String type;
        private Map<String, Properties> properties;
        private Geometry geometry;

        // Getters and Setters

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Map<String, Properties> getProperties() {
            return properties;
        }

        public void setProperties(Map<String, Properties> properties) {
            this.properties = properties;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }
    }

    public static class Properties {
        private String name;
        private String tourism;
        private String description;
        private String opening_hours;
        private String website;

        // Getters and Setters

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTourism() {
            return tourism;
        }

        public void setTourism(String tourism) {
            this.tourism = tourism;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getOpening_hours() {
            return opening_hours;
        }

        public void setOpening_hours(String opening_hours) {
            this.opening_hours = opening_hours;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }
    }

    public static class Geometry {
        private String type;
        private double[] coordinates;

        // Getters and Setters

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double[] getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(double[] coordinates) {
            this.coordinates = coordinates;
        }
    }
}
