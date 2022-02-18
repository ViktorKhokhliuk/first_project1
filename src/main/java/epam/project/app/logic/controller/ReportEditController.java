package epam.project.app.logic.controller;

import epam.project.app.infra.web.ModelAndView;
import epam.project.app.infra.web.QueryParameterResolver;
import epam.project.app.logic.entity.dto.ReportEditDto;
import epam.project.app.logic.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

@RequiredArgsConstructor
public class ReportEditController {
    private final ReportService reportService;
    private final QueryParameterResolver queryParameterResolver;

    public ModelAndView updateStatusOfReportAfterEdit(HttpServletRequest request) {
        ReportEditDto reportEditDto = queryParameterResolver.getObject(request, ReportEditDto.class);
        reportService.updateStatusOfReportAfterEdit(reportEditDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setRedirect(true);
        modelAndView.setView("/service/filterClientReports?date=" + reportEditDto.getDate() + "&status=" + reportEditDto.getStatusFilter()
                    + "&type=" + reportEditDto.getType() + "&clientId=" + reportEditDto.getClientId());
        return modelAndView;
    }

    public ModelAndView editReport(HttpServletRequest request) {
        ReportEditDto reportEditDto =  queryParameterResolver.getObject(request, ReportEditDto.class);
        long clientId = reportEditDto.getClientId();
        String name = reportEditDto.getName();
        String path = "webapp/upload/id"+clientId+"/"+name;
        File file = new File(path);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document document = null;
        try {
            docBuilder = builderFactory.newDocumentBuilder();
            document  = docBuilder.parse(file);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        String report = documentToString(document);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAttribute("dto",reportEditDto);;
        modelAndView.addAttribute("report",report);
        modelAndView.setView("/client/edit.jsp");
        return modelAndView;
    }

    public ModelAndView saveReportChanges(HttpServletRequest request) {
        ReportEditDto reportEditDto = queryParameterResolver.getObject(request, ReportEditDto.class);
        long clientId = reportEditDto.getClientId();
        String name = reportEditDto.getName();
        String report = reportEditDto.getReport();
        String path = "webapp/upload/id"+clientId+"/"+name;
        Document doc = stringToDocument(report);
        DOMSource source = new DOMSource(doc);
        try {
            FileWriter writer = new FileWriter(path);
            StreamResult result = new StreamResult(writer);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(source, result);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }
        return updateStatusOfReportAfterEdit(request);
    }

    private static Document stringToDocument(String xmlStr) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = builderFactory.newDocumentBuilder();
            // парсим переданную на вход строку с XML разметкой
            return docBuilder.parse(new InputSource(new StringReader(xmlStr)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // конвертируем XML Document в строку
    private static String documentToString(Document doc) {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transfObject;
        try {
            transfObject = tFactory.newTransformer();
            // здесь мы указываем, что хотим убрать XML declaration:
            // тег <?xml ... ?> и его содержимое
            transfObject.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transfObject.transform(new DOMSource(doc), new StreamResult(writer));
            // возвращаем преобразованный  в строку XML Document
            return writer.getBuffer().toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
