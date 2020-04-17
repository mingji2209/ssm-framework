package com.jsoft.framework.ssm.controller.ssm;

import com.jsoft.framework.ssm.dto.ssm.MoodDTO;
import com.jsoft.framework.ssm.service.ssm.MoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@Slf4j
@Controller
@RequestMapping("/mood")
public class MoodController {

    @Resource
    private MoodService moodService;

    @RequestMapping("/findAll")
    public String findAll(Model modelMap){
        List<MoodDTO> moodDTOList = moodService.findAll();
        modelMap.addAttribute( "moods",moodDTOList );
        log.info( moodDTOList.toString() );
        return "mood";
    }

    @RequestMapping(value = "/{moodId}/praise",method = RequestMethod.GET)
    public String praise(ModelMap modelMap, @PathVariable(value = "moodId") int moodId,@RequestParam("userId") int userId){
        boolean isPraise = moodService.praiseMood( userId,moodId );
        List<MoodDTO> moodDTOList = moodService.findAll();
        modelMap.addAttribute( "moods",moodDTOList );
        modelMap.addAttribute( "isPraise",isPraise );

        return "mood";

    }

    @RequestMapping(value = "/{moodId}/praiseForRedis",method = RequestMethod.GET)
    public String praiseForRedis(Model model, @PathVariable(value = "moodId") int moodId,@RequestParam("userId") int userId){

        //随机生成用户id
        userId = new Random(  ).nextInt(100);

        boolean isPraise = moodService.praiseMoodForRedis( userId,moodId );
        List<MoodDTO> moodDTOList = moodService.findAllForRedis();
        model.addAttribute( "moods",moodDTOList );
        model.addAttribute( "isPraise",isPraise );

        return "mood";

    }



}
