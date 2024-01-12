package com.example.Controller;

import com.example.Entity.Group;
import com.example.Entity.GroupDto;
import com.example.Service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;


@Tag(name = "Group", description = "operation with groups")
@RestController
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping
    @Operation(
            summary = "Get page Group items",
            description = "Get all Groups"
    )
    Collection<GroupDto> allGroups() {
        return groupService.listAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get by id",
            description = "Get Group by identifier",
            parameters = {@Parameter(name = "id", description = "Group id", example = "1")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
    })
    ResponseEntity<GroupDto> getGroup(@PathVariable("id") Long id) {
        GroupDto group = groupService.getGroupById(id);
        if (group != null) {
            return ResponseEntity.ok(group);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete by id",
            description = "Delete Group by identifier",
            parameters = {@Parameter(name = "id", description = "Group id", example = "1")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Success. No content", content = @Content)
    })
    void deleteGroup(@PathVariable("id") Long id) {
        groupService.deleteGroupById(id);
    }

    @PostMapping
    @Operation(
            summary = "Create new Group",
            description = "Create new Group with all parameters"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Some parameters are incorrect", content = @Content),
    })
    ResponseEntity<GroupDto> create(@RequestBody Group group) {
        if (group.getName() == null || group.getId() == null || group.getId() != 0) {
            return ResponseEntity.badRequest().build();
        }
        if (groupService.getGroupByName(group.getName()) != null) {
            return ResponseEntity.badRequest().build();
        }
        GroupDto group1 = groupService.createGroup(group);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(group1.getId()).toUri();
        return ResponseEntity.created(uri).body(group1);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Change Group by id",
            description = "Change Group by identifier",
            parameters = {@Parameter(name = "id", description = "Group id", example = "1")}

    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Changed"),
            @ApiResponse(responseCode = "400", description = "Some parameters are incorrect", content = @Content),
    })
    public ResponseEntity<GroupDto> edit(@PathVariable("id") Long id, @RequestBody Group groupDto) {
        if (groupDto.getId() != null && !groupDto.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Group group = new Group(id, groupDto.getName());
        return ResponseEntity.ok(groupService.updateGroup(group));
    }
}
