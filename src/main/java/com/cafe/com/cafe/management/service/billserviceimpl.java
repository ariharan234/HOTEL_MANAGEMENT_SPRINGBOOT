package com.cafe.com.cafe.management.service;

import com.cafe.com.cafe.management.Entity.BillEntity;
import com.cafe.com.cafe.management.JWT.JWTFilter;
import com.cafe.com.cafe.management.dto.billdto;
import com.cafe.com.cafe.management.repository.Billrepository;
import com.cafe.com.cafe.management.service.impl.Billserviceinterface;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class billserviceimpl implements Billserviceinterface {
    @Autowired
    private BillEntity bill;
    @Autowired
    private JWTFilter jwtFilter;
    @Autowired
    private Billrepository billrepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ResponseEntity<String> generatereport(Map<String, Object> map) throws Exception{
        String filename;
        if(validategeneraterequest(map)){
            if(map.containsKey("isGenerate") && !(Boolean) map.get("isGenerate")){
                filename=(String) map.get("uuid");
            }
            else{
                filename=getuuid();
                map.put("uuid",filename);
                insertbill(map);
            }
            String data="Name: "+map.get("name")+"\n"+
                    "ContactNumber: "+map.get("contactNumber")+"\n"+
                    "Email: "+map.get("email")+"\n"+
                    "Payment Method: "+map.get("paymentMethod")+"\n";

            Document document=new Document();
            PdfWriter.getInstance(document,new FileOutputStream("/Users/ariharansellappan/Downloads/Billpdfs"+"/"+filename+".pdf"));
            document.open();

            setRectangleinPdf(document);
            Paragraph paragraph=new Paragraph("Cafe managment system",getFont("Header"));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            Paragraph paragraph1=new Paragraph(data+"\n \n",getFont("Data"));
            document.add(paragraph1);

            PdfPTable table=new PdfPTable(5);
            table.setWidthPercentage(100);
            addTableheader(table);

            JSONArray jsonArray=getjsonarrayfromstring((String) map.get("productDetails"));
            for(int i=0;i< jsonArray.length();i++){
                 addrow(table,getmapfromjson(jsonArray.getString(i)));
            }
            document.add(table);
            Paragraph footer =new Paragraph("Total :"+map.get("totalAmount")+"\n"
            +"Thankyou fro visiting please visit again!!",getFont("Data"));
            document.add(footer);
            document.close();
            return new ResponseEntity<>("{\"uuid\":"+filename+"\"}",HttpStatus.OK);

        }
        else{
            return new ResponseEntity<>("required data not found", HttpStatus.BAD_REQUEST);
        }

    }



    private void addrow(PdfPTable table, Map<String, Object> data) {
        log.info("Inside add rows");
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));
    }

    public JSONArray getjsonarrayfromstring(String data) throws JSONException{
        JSONArray jsonArray=new JSONArray(data);
        return jsonArray;
    }
    public static Map<String,Object> getmapfromjson(String data){
        if(!Strings.isNullOrEmpty(data)){
            return new Gson().fromJson(data,new TypeToken<Map<String,Object>>(){

            }.getType());
        }
        return new HashMap<>();
    }

    private void addTableheader(PdfPTable table) {
        log.info("Inside table header");
        Stream.of("Name","Category","Quantity","Price","Sub Total").forEach(columtitle->{
            PdfPCell pdfPCell=new PdfPCell();
            pdfPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            pdfPCell.setBorderWidth(2);
            pdfPCell.setPhrase(new Phrase(columtitle));
            pdfPCell.setBackgroundColor(BaseColor.YELLOW);
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(pdfPCell);
        });
    }

    private Font getFont(String header) {
        switch (header){
            case "Header":
                Font headerfont=FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK);
                headerfont.setStyle(Font.BOLD);
                return headerfont;
            case "Data":
                Font datafont=FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
                datafont.setStyle(Font.BOLD);
                return datafont;
            default:
                return new Font();
        }

    }

    private void setRectangleinPdf(Document document) throws DocumentException {
        log.info("Inside setrectangledocument");
        Rectangle rectangle=new Rectangle(577,825,18,15);
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBackgroundColor(BaseColor.WHITE);

        rectangle.setBorderWidth(1);
        document.add(rectangle);
    }

    private void insertbill(Map<String, Object> map) throws Exception {

        bill.setName((String) map.get("name"));
        bill.setUuid((String)map.get("uuid"));
        bill.setEmail((String) map.get("email"));
        bill.setContactnumber((String) map.get("contactNumber"));
        bill.setPaymentmehtod((String) map.get("paymentMethod"));
        bill.setCreatedby(jwtFilter.getCurrentUser());
        bill.setTotal(Integer.valueOf((String) map.get("totalAmount")));
        bill.setProductdetails((String) map.get("productDetails"));
        billrepository.save(bill);


    }

    private boolean validategeneraterequest(Map<String, Object> map) {
        return  map.containsKey("name") &&
                map.containsKey("contactNumber") &&
                map.containsKey("email") &&
                map.containsKey("paymentMethod") &&
                map.containsKey("productDetails") &&
                map.containsKey("totalAmount");
    }
    public static String getuuid(){
        Date date =new Date();
        long time=date.getTime();
        return "BILL-"+time;
    }
    @Override
    public ResponseEntity<List<billdto>> getallbill() {
        if(jwtFilter.isAdmin()){
            List<BillEntity> list=billrepository.findAllByOrderByIdDesc();
            List<billdto> billlist=list.stream().map(e->modelMapper.map(e, billdto.class)).collect(Collectors.toList());
            return new ResponseEntity<>(billlist,HttpStatus.OK);
        }
        else if(jwtFilter.isUser()){
            List<BillEntity> list=billrepository.findByCreatedbyOrderByIdDesc(jwtFilter.getCurrentUser());
            List<billdto> billlist=list.stream().map(e->modelMapper.map(e, billdto.class)).collect(Collectors.toList());
            return new ResponseEntity<>(billlist,HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<byte[]> getpdf(Map<String, Object> map) throws Exception {
        byte[] bytearray=new byte[0];
        if(!map.containsKey("uuid") && validategeneraterequest(map)){
            return new ResponseEntity<>(bytearray,HttpStatus.BAD_REQUEST);
        }
        String path="/Users/ariharansellappan/Downloads/Billpdfs"+"/"+(String) map.get("uuid")+".pdf";
        File file=new File(path);
        if(file.exists() && file!=null){
            bytearray=getbytearray(path);
            return new ResponseEntity<>(bytearray,HttpStatus.OK);

        }
        else{
            map.put("isGenerate",false);

            generatereport(map);
            bytearray=getbytearray(path);
            return new ResponseEntity<>(bytearray,HttpStatus.OK);
        }


    }


    private byte[] getbytearray(String path) throws IOException {
        File file1=new File(path);
        InputStream inputStream=new FileInputStream(file1);
        byte[] bytes= IOUtils.toByteArray(inputStream);
        inputStream.close();
        return bytes;
    }


    @Override
    public ResponseEntity<String> deletebyid(Integer id) {
        Optional<BillEntity> billEntity=billrepository.findById(id);
        if(!billEntity.isEmpty()){
            billrepository.deleteById(id);
            return new ResponseEntity<>("Bill is deleted",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Bill Id does not exists",HttpStatus.NOT_FOUND);
        }
    }
}
