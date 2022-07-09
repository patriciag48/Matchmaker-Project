import java.util.*;
import java.io.*;

public class Person{

   private String name;
   private List<Map<String,String>> answers;
   
   public Person(String name, List<Map<String,String>> answers){
      this.name = name;
      this.answers = answers;
   }
   
   public String getName(){
      return name;
   }
   
   public List<Map<String,String>> getAnswers(){
      return answers;
   }
   
   public double getImportance(String importance){
      if(importance.equals("IRRELEVANT")){
         return 0;
      }
      if(importance.equals("A LITTLE IMPORTANT")){
         return 1;
      }
      if(importance.equals("SOMEWHAT IMPORTANT")){
         return 10;
      }
      if(importance.equals("VERY IMPORTANT")){
         return 50;
      }
      if(importance.equals("MANDATORY")){
         return 250;
      }
      return -1;
   }
   
   public double answersMatch(String whatIWant, String theirAnswer, double importance){
      if(whatIWant.contains(theirAnswer)){
         return importance;
      }
      return 0;
   }
   
   public double answersSame(String myAnswer, String theirAnswer){
      if(myAnswer.equals(theirAnswer)){
         return 1;
      }
      return 0;
   }
   
   public double getSatisfactory(Person b){
      double numerator = 0;
      double denominator = 0;
      for(int i = 0; i < answers.size(); i++){
         if(getImportance(answers.get(i).get("IMPORTANCE")) != 0){
            numerator += answersMatch(answers.get(i).get("WANT"), b.getAnswers().get(i).get("ANSWER"), getImportance(answers.get(i).get("IMPORTANCE")));
            denominator += getImportance(answers.get(i).get("IMPORTANCE"));
         }
      }
      return (numerator/denominator)*100;
   }
   
   public double getCompatabilityGeo(Person b){
      return Math.sqrt(this.getSatisfactory(b) * b.getSatisfactory(this));
   }
   
   public double getCompatabilityArith(Person b){
      return (this.getSatisfactory(b) + b.getSatisfactory(this))/2;
   }
   
   public double getCompatabilitySimilarity(Person b){
      double numerator = 0;
      for(int i = 0; i < answers.size(); i++){
         if(answers.get(i).get("ANSWER").equals(b.getAnswers().get(i).get("ANSWER"))){
            numerator++;
         }
      }
      return (numerator/30.0)*100;
   }
}