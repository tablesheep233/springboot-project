package org.table.neweims.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.table.neweims.entities.Track;
import org.table.neweims.enums.NatureEnum;
import org.table.neweims.service.StudentService;
import org.table.neweims.service.TrackService;
import org.table.neweims.util.SysContext;

import java.util.List;

@RequiresPermissions("track:*")
@Controller
public class TrackController {

    @Autowired
    private TrackService trackService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/track")
    public String trackList(Model model){
        List<Track> list = trackService.getTrackListFromStu(trackService.getStuId(SysContext.getCurrentUser()));
        model.addAttribute("trackList",list);
        return "student/track/list";
    }

    @GetMapping("/toaddtrack")
    public String toAddTrack(Model model){
        model.addAttribute("nature", NatureEnum.values());
        model.addAttribute("stuId",studentService.getStuId(SysContext.getCurrentUser()));
        return "student/track/track";
    }

    @PostMapping("/track")
    public String addTrack(Track track, RedirectAttributes attr){
        trackService.addOrUpTrack(track);
        attr.addFlashAttribute("tip","添加成功");
        return "redirect:/track";
    }

    @GetMapping("/track/{id}")
    public String toPutTrack(@PathVariable("id") Integer id,Model model){
        model.addAttribute("nature", NatureEnum.values());
        model.addAttribute("track",trackService.getTrackById(id));
        return "student/track/track";
    }

    @PutMapping("/track")
    public String putTrack(Track track, RedirectAttributes attr){
        trackService.addOrUpTrack(track);
        attr.addFlashAttribute("tip","修改成功");
        return "redirect:/track/"+track.getId();
    }

    @DeleteMapping("/track/{id}")
    public String deleteTrack(@PathVariable("id") Integer id,RedirectAttributes attr){
        trackService.delete(id);
        attr.addFlashAttribute("tip","删除成功");
        return "redirect:/track";
    }
}
