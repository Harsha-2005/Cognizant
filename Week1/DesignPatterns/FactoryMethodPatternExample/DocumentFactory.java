package FactoryMethodPatternExample;

public abstract class DocumentFactory {
    public abstract Document createDocument();

    public void manageDocument(){
        Document doc=createDocument();
        doc.open();
        doc.save();
    }
}
