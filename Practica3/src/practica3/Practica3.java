/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 *
 * @author h.dominguezd
 */
public class Practica3 {

    private List<Movie> movies;
    
    public Practica3(String path){
        readDatabase(path);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Practica3 practica = new Practica3("./movies_db.csv");
        /*practica.printAllMovies();
        System.out.println("---------------------------------");
        practica.budgetBetween(0, 100000000);
        System.out.println("---------------------------------");
        practica.languageAndPopularity("en", 150).forEach((m) -> {
            System.out.println(m.getOriginalTitle());
        });*/
        System.out.println("---------------------------------");
        JSONArray genres = new JSONArray();
        JSONObject genre1 = new JSONObject();		
		genre1.put("id", "12");
		genre1.put("name", "Adventure");
          
        JSONObject genre2 = new JSONObject();		
		genre2.put("id", "28");
		genre2.put("name", "Action");
                
        genres.add(genre1);
        genres.add(genre2);
        practica.genresSet(genres);
        System.out.println("---------------------------------");
        JSONObject keyword1 = new JSONObject();		
		genre1.put("id", "1463");
		genre1.put("name", "culture clash");
        System.out.println(practica.keywordMaxRevenue(keyword1));
        //System.out.println("---------------------------------");
        //System.out.println(practica.totalYearRevenue(2012));
        //System.out.println("---------------------------------");
        //System.out.println(practica.totalVotesBetweenAverages(5, 8));
        
    }


    private void readDatabase(String path) {
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            movies = bf.lines()
                    .skip(1)
                    .map(line -> line.split(";"))
                    .filter(tokens -> tokens.length == 12)
                    .map(tokens ->  {
                        try {
                            
                            int budget = Integer.parseInt(tokens[0]);
                            JSONParser parser = new JSONParser();
                            JSONArray genres = (JSONArray)  parser.parse(toJSONStringFormat(tokens[1]));
                            JSONArray keywords = (JSONArray)  parser.parse(toJSONStringFormat(tokens[2]));
                            String originalLang = tokens[3];
                            String originalTitle = tokens[4];
                            float popularity = Float.parseFloat(tokens[5]);
                            JSONArray productionCompanies = (JSONArray)  parser.parse(toJSONStringFormat(tokens[6]));
                            
                            String[] date = tokens[7].split("/");
                            long revenue = Long.parseLong(tokens[8]);
                            String status = tokens[9];
                            float voteAverage = Float.parseFloat(tokens[10]);
                            int voteCount = Integer.parseInt(tokens[11]);
                            return new Movie(budget, genres, keywords, originalLang, originalTitle, popularity, productionCompanies, date, revenue, status, voteAverage, voteCount);
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void budgetBetween(int min, int max) {
        movies.parallelStream()
                .filter(m -> m.getBudget() >= min && m.getBudget() <= max)
                .map(m -> m.getOriginalTitle())
                .forEach(System.out::println);
    }
    
    public void printAllMovies(){
        movies.parallelStream().forEach(System.out::println);
    }
    
    public void genresSet(JSONArray genres){
        for(int i = 0; i < genres.size(); i++){
            JSONObject genre=(JSONObject) genres.get(i);
            movies.parallelStream()
                    .filter(m -> {
                        for(Object g : m.getGenres()){
                            JSONObject gen =(JSONObject) g;
                            if((gen.get("name").equals(genre.get("name")))){
                                return true;
                            }
                        }
                        return false;    
                    })
                    .forEach(m -> System.out.println(getTitlesAndGenres(m)));        
        }
    }
    
    private static String getTitlesAndGenres (Movie movie){
        String string = "Title: " + movie.getOriginalTitle()+ ", Genres: ";
        for(Object genre : movie.getGenres()){
            genre = (JSONObject) genre;
            string = string + genre.toString();
        }
        return string;
    }
    
    public long keywordMaxRevenue(JSONObject keyword){
        return movies.parallelStream()
                .filter(m -> {
                            JSONArray keys = m.getKeywords();
                            for(Object key : keys){
                                JSONObject k = (JSONObject) key;
                                if(k.get("name").equals(keyword.get("name")) /*&& k.get("id").equals(keyword.get("id"))*/){
                                    return true;
                                }
                            }
                            return false;
                        })
                .map(m -> m.getRevenue())
                .reduce(Math::max)
                .get();
    }
    
    public List<Movie> languageAndPopularity(String lang, float popularity){
        return movies.parallelStream()
                .filter(m -> m.getOriginalLang().equals(lang) && m.getPopularity() >= popularity)
                .collect(Collectors.toList());
    }
    
    public BigInteger totalYearRevenue(int year){
        return movies.parallelStream()
            .filter(m -> Integer.parseInt(m.getOriginalDate()[2]) == year)
            .map(m -> BigInteger.valueOf(m.getRevenue()))
            .reduce(BigInteger::add)
            .get();   
    }
    public BigInteger totalVotesBetweenAverages(float min, float max){
        return movies.parallelStream()
                .filter(m -> m.getVoteAverage() >= min && m.getVoteAverage() <= max)
                .map(m -> BigInteger.valueOf(m.getVoteCount()))
                .reduce(BigInteger::add)
                .get();
    }
    
    public Map<String, Set<String>> companiesMap (){
        Map<String, Set<String>> result = null; /* =
        movies.parallelStream()
                .collect(Collectors.groupingBy(Movie::getProductionCompany,
                        Collectors.mapping(Movie::getOriginalTitle, Collectors.toSet())));*/
        return result;
    }
    
    private static String toJSONStringFormat(String s){
        String string = s.replace("\"\"", "\"");
        string = string.substring(1, string.length() -1);
        return string;
    }
}
