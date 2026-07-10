package FactoryMethodPatternExample;

public class ExcelDocument implements Document{
    @Override
    public void open() {
        System.out.println("Opening the Excel document (.xlsx)...");
    }

    @Override
    public void save() {
        System.out.println("Saving the Excel document (.xlsx)...");
    }
}
