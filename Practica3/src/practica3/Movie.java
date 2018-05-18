/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3;

import org.json.simple.JSONArray;

/**
 *
 * @author h.dominguezd
 */
public class Movie {
    private int budget;
    private JSONArray genres;
    private JSONArray keywords;  
    private String originalLang;
    private String originalTitle;
    private float popularity;
    private JSONArray productionCompany;
    private String[] originalDate;
    private long revenue;
    private String status;
    private float voteAverage;
    private int voteCount;

    public Movie(int budget, JSONArray genres, JSONArray keywords, String originalLang, String originalTitle, float popularity, JSONArray productionCompany, String[] originalDate, long revenue, String status, float voteAverage, int voteCount) {
        this.budget = budget;
        this.genres = genres;
        this.keywords = keywords;
        this.originalLang = originalLang;
        this.originalTitle = originalTitle;
        this.popularity = popularity;
        this.productionCompany = productionCompany;
        this.originalDate = originalDate;
        this.revenue = revenue;
        this.status = status;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    Movie() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public JSONArray getGenres() {
        return genres;
    }

    public void setGenres(JSONArray genres) {
        this.genres = genres;
    }

    public JSONArray getKeywords() {
        return keywords;
    }

    public void setKeywords(JSONArray keywords) {
        this.keywords = keywords;
    }

    public String getOriginalLang() {
        return originalLang;
    }

    public void setOriginalLang(String originalLang) {
        this.originalLang = originalLang;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public JSONArray getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(JSONArray productionCompany) {
        this.productionCompany = productionCompany;
    }

    public String[] getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(String[] originalDate) {
        this.originalDate = originalDate;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
    
    
}
