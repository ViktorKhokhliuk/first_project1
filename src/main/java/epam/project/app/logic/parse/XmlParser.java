package epam.project.app.logic.parse;

import epam.project.app.logic.entity.report.ReportParameters;
import epam.project.app.logic.entity.report.ReportTags;
import epam.project.app.logic.exception.ReportException;
import lombok.extern.log4j.Log4j2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


@Log4j2
public class XmlParser {
    private final DocumentBuilderFactory factory;

    public XmlParser() {
         factory = DocumentBuilderFactory.newInstance();
    }

    public ReportParameters parse(String xmlFileName) {
        Document document;
        ReportParameters reportParameters;
        try {
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            document = documentBuilder.parse(xmlFileName);
            Element root = document.getDocumentElement();
            reportParameters = buildReportParameters(root);
        } catch (Exception e) {
            log.error("cannot parse xml file",e);
            throw new ReportException("cannot edit report");
        }
        return reportParameters;
    }

    private ReportParameters buildReportParameters(Element root) {
        ReportParameters reportParameters = new ReportParameters();
        reportParameters.setPerson(getTextContext(root, ReportTags.PERSON.getValue()));
        reportParameters.setNationality(getTextContext(root, ReportTags.NATIONALITY.getValue()));
        reportParameters.setYear(getTextContext(root, ReportTags.YEAR.getValue()));
        reportParameters.setPerson(getTextContext(root, ReportTags.PERSON.getValue()));
        reportParameters.setQuarter(getTextContext(root, ReportTags.QUARTER.getValue()));
        reportParameters.setMonth(getTextContext(root, ReportTags.MONTH.getValue()));
        reportParameters.setGroup(getTextContext(root, ReportTags.GROUP.getValue()));
        reportParameters.setActivity(getTextContext(root, ReportTags.ACTIVITY.getValue()));
        reportParameters.setIncome(getTextContext(root, ReportTags.INCOME.getValue()));
        return reportParameters;
    }

    private String getTextContext(Element root, String value) {
        Node node = root.getElementsByTagName(value).item(0);
        return node.getTextContent();
    }
}
