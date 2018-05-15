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
import org.json.simple.JSONValue;
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
        practica.printAllMovies();
        System.out.println("---------------------------------");
        practica.budgetBetween(0, 100000000);
        System.out.println("---------------------------------");
        practica.languageAndPopularity("en", 150).forEach((m) -> {
            System.out.println(m.getOriginalTitle());
        });
        System.out.println("---------------------------------");
        System.out.println(practica.totalYearRevenue(2012));
        System.out.println("---------------------------------");
        System.out.println(practica.totalVotesBetweenAverages(5, 8));
        
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
                            FileReader reader = new FileReader("json.txt");
                            BufferedReader bufferreader = new BufferedReader(reader);
                            
                            String string = bufferreader.readLine() + bufferreader.readLine() +  bufferreader.readLine() + bufferreader.readLine() + bufferreader.readLine();
                            String string2 = bufferreader.readLine();
                            string = string2.replace("\"\"", "\"");
                            string2 = tokens[1].replace("\"\"", "\"");
                            String string3 = string2.substring(1,string2.length() -1);
                            Object object1 = parser.parse(string3);
                            JSONArray array = (JSONArray) object1;
                            System.out.println(tokens[1]);
                            JSONObject genres = (JSONObject)  JSONValue.parse(tokens[1]);
                            JSONObject keywords = (JSONObject) JSONValue.parse(tokens[2]);
                            String originalLang = tokens[3];
                            String originalTitle = tokens[4];
                            float popularity = Float.parseFloat(tokens[5]);
                            JSONObject productionCompanies = (JSONObject) JSONValue.parse(tokens[6]);
                            
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
    
    public void genresSet(){
        
    }
    
    public void keywordMaxRevenue(){
        
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
    
    public Map<String, String> companiesMap (){
        return null;   
    }
    
}
