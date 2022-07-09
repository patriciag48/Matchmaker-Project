import java.io.*;
import java.util.*;

public class MatchMaker{
   public static void main (String [] args) throws IOException{
      Scanner sc = new Scanner(System.in);
      System.out.print("Name: ");
      String name = sc.nextLine();
      System.out.print("Gender: ");
      String gender = sc.nextLine().toUpperCase();
      System.out.print("Grade: ");
      int grade = sc.nextInt();
      System.out.println();
      Scanner inputFile = new Scanner(new File(name.replaceAll("\\s+","") + ".txt"));
      List<Map<String,String>> answers = new ArrayList<Map<String,String>>();
      inputFile.useDelimiter("/|\n");
      while(inputFile.hasNextLine()){
         String myAnswer = inputFile.next();
         String myWant = inputFile.next();
         String myImportance = inputFile.next();
         Map<String,String> answer = new TreeMap<String, String>();
         answer.put("ANSWER",myAnswer);
         answer.put("WANT", myWant);
         answer.put("IMPORTANCE", myImportance);
         answers.add(answer);
      }
      Person user = new Person(name, answers);
      String genderPref = "";
      if(gender.equals("MALE")){
         if(grade == 10){
            genderPref = "females10";
         }
         else{
            genderPref = "females";
         }
      }
      else{
         if(grade == 10){
            genderPref = "males10";
         }
         else{
            genderPref = "males";
         }
      }
      Scanner inputFile2 = new Scanner(new File(genderPref + ".txt"));
      inputFile2.useDelimiter("/|\n");
      List<Person> possibleMatches = new ArrayList<Person>();
      while(inputFile2.hasNextLine()){
         String name2 = inputFile2.next();
         List<Map<String,String>> answers2 = new ArrayList<Map<String,String>>();
         for(int i = 0; i < 30; i++){
            Map<String,String> answer = new TreeMap<String, String>();
            answer.put("ANSWER", inputFile2.next());
            answer.put("WANT", inputFile2.next());
            answer.put("IMPORTANCE", inputFile2.next());
            answers2.add(answer);
         }
         possibleMatches.add(new Person(name2, answers2));
      }
      Map<Double, Set<String>> matchesGeo = new TreeMap<Double, Set<String>>(Collections.reverseOrder());
      for(int i = 0; i < possibleMatches.size(); i++){
         double compatability = user.getCompatabilityGeo(possibleMatches.get(i));
         String matchName = possibleMatches.get(i).getName();
         if(!matchesGeo.containsKey(compatability)){
            Set<String> matchSet = new TreeSet<String>();
            matchSet.add(matchName);
            matchesGeo.put(compatability, matchSet);
         }
         else{
            matchesGeo.get(compatability).add(matchName);
         }
      }
      System.out.println("Top 5 Matches (Geometric Mean)");
      int count = 1;
      for(double compatabilityScore : matchesGeo.keySet()){
         if(count <= 5){
            for(String topMatchName : matchesGeo.get(compatabilityScore)){
               System.out.println(count + ". " + topMatchName + " " + Math.round(compatabilityScore*100.0)/100.0 + "%");
               count++;
            }
         }
         else{
            break;
         }
      }
      System.out.println();
      Map<Double, Set<String>> matchesArith = new TreeMap<Double, Set<String>>(Collections.reverseOrder());
      for(int i = 0; i < possibleMatches.size(); i++){
         double compatability = user.getCompatabilityArith(possibleMatches.get(i));
         String matchName = possibleMatches.get(i).getName();
         if(!matchesArith.containsKey(compatability)){
            Set<String> matchSet = new TreeSet<String>();
            matchSet.add(matchName);
            matchesArith.put(compatability, matchSet);
         }
         else{
            matchesArith.get(compatability).add(matchName);
         }
      }
      System.out.println("Top 5 Matches (Arithmetic Mean)");
      count = 1;
      for(double compatabilityScore : matchesArith.keySet()){
         if(count <= 5){
            for(String topMatchName : matchesArith.get(compatabilityScore)){
               System.out.println(count + ". " + topMatchName + " " + Math.round(compatabilityScore*100.0)/100.0 + "%");
               count++;
            }
         }
         else{
            break;
         }
      }
      Map<Double, Set<String>> matchesSimilar = new TreeMap<Double, Set<String>>(Collections.reverseOrder());
      for(int i = 0; i < possibleMatches.size(); i++){
         double compatability = user.getCompatabilitySimilarity(possibleMatches.get(i));
         String matchName = possibleMatches.get(i).getName();
         if(!matchesSimilar.containsKey(compatability)){
            Set<String> matchSet = new TreeSet<String>();
            matchSet.add(matchName);
            matchesSimilar.put(compatability, matchSet);
         }
         else{
            matchesSimilar.get(compatability).add(matchName);
         }
      }
      System.out.println();
      System.out.println("Top 5 Matches (Similarity)");
      count = 1;
      for(double compatabilityScore : matchesSimilar.keySet()){
         if(count <= 5){
            for(String topMatchName : matchesSimilar.get(compatabilityScore)){
               System.out.println(count + ". " + topMatchName + " " + Math.round(compatabilityScore*100.0)/100.0 + "%");
               count++;
            }
         }
         else{
            break;
         }
      }

   }
}