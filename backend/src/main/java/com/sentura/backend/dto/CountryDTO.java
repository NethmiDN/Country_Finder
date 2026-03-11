package com.sentura.backend.dto;

public class CountryDTO {
    private String name;
    private String capital;
    private String region;
    private long population;
    private String flag;

    public CountryDTO() {
    }

    public CountryDTO(String name, String capital, String region, long population, String flag) {
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.population = population;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String capital;
        private String region;
        private long population;
        private String flag;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder capital(String capital) {
            this.capital = capital;
            return this;
        }

        public Builder region(String region) {
            this.region = region;
            return this;
        }

        public Builder population(long population) {
            this.population = population;
            return this;
        }

        public Builder flag(String flag) {
            this.flag = flag;
            return this;
        }

        public CountryDTO build() {
            return new CountryDTO(name, capital, region, population, flag);
        }
    }
}
