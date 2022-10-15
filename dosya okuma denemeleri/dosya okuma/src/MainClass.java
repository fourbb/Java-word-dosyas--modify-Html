import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

public class MainClass {

    public static void DosyaOlustur(String URL){
        File f = new File(URL);
        if(!f.exists()){ // eğer dosya yoksa
            f.mkdir(); // dosyamızı oluşturur.
            System.out.println(f.getName()+ " adlı dosya Oluşturuldu..");
        }else{
            System.out.println("Dosya olduğundan oluşturma işlemi yapılmayacaktır. ");
        }
    }

    public static String DosyaArama(String Url){
        Scanner konsolVeri = new Scanner(System.in);
        File dir = new File(Url);
        String arama;
        File[] fileList = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".docx");
            }
        });
        int i=1;
        for(File file : fileList) {
            System.out.println(i+". "+file.getName());
            i++;
        }
        System.out.println("Hangi Dosyayı İstiyorsun");
        arama= konsolVeri.nextLine();
        return arama;
    }
    public static void DosyaOkuma(String docx,String download2){
        try {
            XWPFDocument doc = new XWPFDocument(OPCPackage.open(docx));
            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains("softtech")) {
                            text = text.replaceAll("softtech", "Barış berker Bayram");
                            r.setText(text, 0);
                        }
                    }
                }
            }

            for (XWPFTable tbl : doc.getTables()) {
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            for (XWPFRun r : p.getRuns()) {
                                String text = r.getText(0);
                                if (text != null && text.contains("softtech")) {
                                    text = text.replace("softtech", "Barış berker Bayram");
                                    r.setText(text, 0);
                                }
                            }
                        }
                    }
                }
            }
            String download ="C:\\Users\\berke\\AppData\\Local\\Temp\\SofttechDowloand\\";
            String dwn =download+download2;
            doc.write(new FileOutputStream(dwn));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }

    public static void main(String[] args)throws IOException,
            org.apache.poi.openxml4j.exceptions.InvalidFormatException{
        String tmp=System.getProperty("java.io.tmpdir");//java temp dosyasını bulur

        String Upload =tmp+"softtechdeneme";//dosya uzantımızı ekler
        String Dowloand=tmp+"softtechdowloand";
        //DosyaOlustur(Upload);//temp uzantısında upload dosyamızı oluşturur
        //DosyaOlustur(Dowloand);//temp uzantısında dowloand dosyamızı oluşturur
        System.out.println(Upload);
        String arama=DosyaArama(Upload);// dosyamızda docx dosyalarını bulur ve isteidğimiz dosya adını geri döndürür.
        String s="\\";
        Upload=Upload+s+ arama;
        DosyaOkuma(Upload,arama);
    }
}
