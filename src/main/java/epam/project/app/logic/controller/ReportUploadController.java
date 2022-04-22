package epam.project.app.logic.controller;

import epam.project.app.infra.web.ModelAndView;
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
        User userFromSession = getUserFromSession(request.getSession(false));
        String uploadPath = request.getServletContext().getRealPath("") + UPLOAD_DIRECTORY + userFromSession.getId().toString();
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        Part part = request.getPart("part");
        String fileName = getFileName(part, uploadPath);
        String path = uploadPath + File.separator + fileName;
        reportService.validateFile(part,path);
        String type = request.getParameter("type");
        reportService.uploadReport(new ReportCreateDto(fileName, path, userFromSession.getId(), type));
        log.log(Level.INFO, "File " + fileName + " has uploaded successfully!");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setRedirect(true);
        modelAndView.setView("/client/homePage.jsp");
        return modelAndView;
    }

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    private String getFileName(Part part, String uploadPath) {
        String fileName = part.getSubmittedFileName();
        int i = 1;
        String name = fileName;
        while (new File(uploadPath + "/" + fileName).exists()) {
            fileName = "(" + i + ")" + name;
            i++;
        }
        return fileName.isEmpty() ? DEFAULT_FILENAME : fileName;
    }
}
