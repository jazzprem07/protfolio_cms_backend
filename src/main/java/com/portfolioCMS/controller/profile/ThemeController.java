/**
 * @author Prem kumar
 * @date 4/4/26
 * Copyright © 2026
 */
package com.portfolioCMS.controller.profile;


import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.dto.ThemeSettingsDTO;
import com.portfolioCMS.service.profile.IThemeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Theme")
public class ThemeController {
    private final IThemeService service;

    public ThemeController(IThemeService service) {
        super();
        this.service = service;
    }

    @PostMapping("create")
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody ThemeSettingsDTO req){
        ResponseDTO res = service.create(req);
        return ResponseEntity.ok(res);
    }

    @GetMapping("getall")
    public ResponseEntity<?> getAll(){
        ResponseDTO res = service.getAll();
        return ResponseEntity.ok(res);
    }

    @GetMapping("getbyid/{id}")
    public ResponseEntity<?> getById(@PathVariable int id){
        ResponseDTO res = service.getById(id);
        return ResponseEntity.ok(res);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody ThemeSettingsDTO req){
        ResponseDTO res = service.update(id,req);
        return ResponseEntity.ok(res);
    }
}
