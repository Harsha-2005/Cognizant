package FactoryMethodPatternExample;

public class WordDocument implements Document {
    @Override
    public void open(){
        System.out.println("Opening the Word document(.docx format)...");
    }

    @Override
    public void save(){
        System.out.println("Saving the Word document(.docx format)...");
    }
}
