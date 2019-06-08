package com.example.apperasmus;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

public class TemplatePDF {

    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.ITALIC);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 14);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);

    public TemplatePDF(Context context) {
        this.context = context;
    }

    public void openDocument(){
        createFile();
        try{
            document = new Document(PageSize.A4);
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
        }catch (Exception e){
            Log.e("openDocument", e.toString());
        }
    }

    private void createFile(){
       /* File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), "PDF");

        if(!folder.exists())
            folder.mkdirs();
        pdfFile = new File(folder, "TemplatePDF.pdf");*/
        File root = android.os.Environment.getExternalStorageDirectory();


        File dir = new File (root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, "myData.txt");

    }

    public void closeDocument(){
        document.close();
    }

    public void addMetaData(String title, String subject, String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }

    public void addTitle(String title, String subTitle){
        try {
            paragraph = new Paragraph();
            addChildP(new Paragraph(title, fTitle));
            addChildP(new Paragraph(subTitle, fSubTitle));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addTitle", e.toString());
        }
    }

    private void addChildP(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    public void addParagraph(String text){
        try {
            paragraph = new Paragraph(text, fText);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addParagraph", e.toString());
        }
    }
}
