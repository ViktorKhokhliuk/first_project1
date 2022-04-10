package epam.project.app.logic.controller;

import epam.project.app.infra.web.ModelAndView;
import epam.project.app.infra.web.exception.AppException;
import epam.project.app.logic.entity.dto.ReportCreateDto;
import epam.project.app.logic.entity.user.User;
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
public class ReportUploadController {
    private static final String UPLOAD_DIRECTORY = "upload\\id";
    private static final String DEFAULT_FILENAME = "default.file";

    private final ReportService reportService;

    @SneakyThrows
    public ModelAndView uploadFile(HttpServletRequest request) {
        User userFromSession = getUserFromSession(request.getSession());
        String uploadPath = request.getServletContext().getRealPath("") + UPLOAD_DIRECTORY + userFromSession.getId().toString();
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String fileName;
        Part part = request.getPart("part");
        fileName = getFileName(part, uploadPath);
        String path = uploadPath + File.separator + fileName;

        if (fileName.endsWith(".xml")) {
            part.write(path);
            reportService.xmlValidation(path);
        } else if (fileName.endsWith(".json")) {
            part.write(path);
            reportService.jsonValidation(path);
        } else {
            throw new AppException("Please choose allowed file type: XML or JSON");
        }
        String type = request.getParameter("type");
        log.log(Level.INFO, "File " + fileName + " has uploaded successfully!");
        reportService.uploadReport(new ReportCreateDto(fileName, path, userFromSession.getId(), type));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setRedirect(true);
        modelAndView.setView("/client/homePage.jsp");
        return modelAndView;
    }

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    private String getFileName(Part part, String uploadPath) {
        String fileName;
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                fileName = content.substring(content.indexOf("=") + 2, content.length() - 1);
                int i = 1;
                String name = fileName;
                while (new File(uploadPath + "/" + fileName).exists()) {
                    fileName = "(" + i + ")" + name;
                    i++;
                }
                return fileName;
            }
        }
        return DEFAULT_FILENAME;
    }
}
