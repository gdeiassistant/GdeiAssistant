package com.gdeiassistant.gdeiassistant.Controller.CetQuery;

import com.gdeiassistant.gdeiassistant.Service.CetQuery.CetQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gdeiassistant on 2017/7/21.
 */

@Controller
public class CetQueryController {

    @Autowired
    private CetQueryService cetQueryService;

    @RequestMapping(value = "/cet", method = RequestMethod.GET)
    public ModelAndView ResolveCetPage() {
        return new ModelAndView("Cet/cet");
    }

    @RequestMapping(value = "/cet/save", method = RequestMethod.GET)
    public ModelAndView ResolveCetNumberSavePage(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Cet/cetSave");
        String username = (String) WebUtils.getSessionAttribute(request, "username");
        Long number = cetQueryService.getCetNumber(username);
        modelAndView.addObject("CetNumber", number);
        return modelAndView;
    }

}
