package epam.project.app.infra.web.exception;

import epam.project.app.infra.web.ModelAndView;

public class ExceptionHandler {
    public ModelAndView processingException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("/error/internalError.jsp");
        modelAndView.addAttribute("message", exception.getMessage());
        return modelAndView;
    }
}
