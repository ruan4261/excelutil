package org.ruan.excelutil.controller;

import org.ruan.excelutil.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WebController {

    @Autowired
    private PictureService pictureService;

    /**
     * 请求渲染页面
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object index() {
        return "index";
    }

    /**
     * 操作请求
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/v1", method = RequestMethod.POST)
    public String opera(HttpServletRequest httpServletRequest) {
        try {
            String origin = httpServletRequest.getParameter("origin");
            Integer sheet = Integer.parseInt(httpServletRequest.getParameter("sheet"));
            Integer signcol = Integer.parseInt(httpServletRequest.getParameter("signcol"));
            String target = httpServletRequest.getParameter("target");
            Integer ver = Integer.parseInt(httpServletRequest.getParameter("version"));
            if (ver != 7 && ver != 3) throw new NumberFormatException();
            return pictureService.savePicture(origin, sheet, signcol, target, ver);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "接口参数校验错误。";
        }
    }
}
