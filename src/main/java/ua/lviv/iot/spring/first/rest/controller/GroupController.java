package ua.lviv.iot.spring.first.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.lviv.iot.spring.first.rest.business.GroupService;
import ua.lviv.iot.spring.first.rest.model.Group;

import java.util.List;

@RequestMapping("/groups")
@RestController
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<Group> getAllGroups() {
        return groupService.findAll();
    }
}
