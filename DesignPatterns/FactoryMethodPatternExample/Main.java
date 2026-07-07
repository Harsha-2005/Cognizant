package FactoryMethodPatternExample;

public class Main {
    public static void main(String[] args) {

        System.out.println("--- Testing Word Factory ---");
        DocumentFactory wordFactory = new WordDocumentFactory();
        Document myWordDoc = wordFactory.createDocument();
        myWordDoc.open();
        myWordDoc.save();

        System.out.println("\n--- Testing PDF Factory ---");
        DocumentFactory pdfFactory = new PdfDocumentFactory();

        pdfFactory.manageDocument();

        System.out.println("\n--- Testing Excel Factory ---");
        DocumentFactory excelFactory = new ExcelDocumentFactory();
        Document myExcelDoc = excelFactory.createDocument();
        myExcelDoc.open();
        myExcelDoc.save();
    }
}
