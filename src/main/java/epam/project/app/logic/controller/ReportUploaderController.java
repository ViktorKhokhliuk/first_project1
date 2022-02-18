package epam.project.app.logic.controller;

import epam.project.app.infra.web.ModelAndView;
import epam.project.app.infra.web.exception.AppException;
import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.exception.ReportException;
import epam.project.app.logic.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;

import java.io.File;


@Log4j2
@RequiredArgsConstructor
public class ReportUploaderController {
    public static final String UPLOAD_DIRECTORY = "upload\\id";
    public static final String DEFAULT_FILENAME = "default.file";
    private final ReportService reportService;

    @SneakyThrows
    public ModelAndView uploadFile(HttpServletRequest request) {
        User userFromSession = getUserFromSession(request.getSession());
        String uploadPath = request.getServletContext().getRealPath("") + UPLOAD_DIRECTORY + userFromSession.getId().toString();
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String fileName = "";
//        for (Part part : request.getParts()) {
//            fileName = getFileName(part);
//            if(new File(uploadPath+"/"+fileName).exists())
//                throw new ReportException("you already have a report with that name");
//            if (fileName.endsWith(".xml") || fileName.endsWith(".json")) {
//                part.write(uploadPath + File.separator + fileName);
//                break;
//            } else {
//                throw new AppException("Please choose allowed file type: XML or JSON");
//            }
//        }

        Part part = request.getPart("part");
        fileName = getFileName(part);
        if (new File(uploadPath + "/" + fileName).exists())
            throw new ReportException("you already have a report with that name");
        if (fileName.endsWith(".xml") || fileName.endsWith(".json")) {
            part.write(uploadPath + File.separator + fileName);
        } else {
            throw new AppException("Please choose allowed file type: XML or JSON");
        }
            String type = request.getParameter("type");
            log.log(Level.INFO, "File " + fileName + " has uploaded successfully!");
            reportService.uploadReport(fileName, uploadPath, userFromSession.getId(), type);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setRedirect(true);
            modelAndView.setView("/client/homePage.jsp");
            return modelAndView;
        }

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return DEFAULT_FILENAME;
    }
}
