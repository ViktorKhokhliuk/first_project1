package epam.project.app.infra.web.exception;

import epam.project.app.infra.web.ModelAndView;

public class ExceptionHandler {
    public ModelAndView processingException(Exception exception) {

        if (exception instanceof AppException) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setView("/error/exception.jsp");
            modelAndView.addAttribute("message", exception.getMessage());
            return modelAndView;
        }
        return ModelAndView.withView("/error/internalError.jsp");
    }
}
