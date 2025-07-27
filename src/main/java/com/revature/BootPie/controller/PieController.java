package com.revature.BootPie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.BootPie.exceptions.ResourceNotFoundException;
import com.revature.BootPie.models.Pie;
import com.revature.BootPie.services.PieService;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pies")
public class PieController {
    private PieService pieService;

    @Autowired
    public PieController(PieService pieService) {
        this.pieService = pieService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Pie> getAllPies() {
        return pieService.getAllPies();
    }

    @GetMapping(params = "pieName")
    public @ResponseBody ResponseEntity<Pie> getPieByName(@RequestParam String pieName) {
        try {
            return new ResponseEntity<>(pieService.findPieByName(pieName), HttpStatus.ACCEPTED);
        } catch (ResourceNotFoundException e) {
            return null; // Handle the exception as needed
        }
    }

    @GetMapping(params = "limit", value = "/calories")
    public @ResponseBody ResponseEntity<List<Pie>> getPiesByCalories(@RequestParam int limit) throws ResourceNotFoundException {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(pieService.getPiesByCalories(limit));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("No pies found with calories less than or equal to " + limit);
        }
    }

    @DeleteMapping(params = "pieName")
    public @ResponseBody ResponseEntity<String> deletePie(@RequestParam String pieName) {
        try {
            pieService.deletePie(pieName);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Pie deleted successfully: " + pieName);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pie not found: " + pieName);
        }
    }

    @PatchMapping("/patchPie")
    public @ResponseBody ResponseEntity<String> patchPie(@RequestParam String pieName,
                                                         @RequestParam(defaultValue="0", required = false) Integer calories,
                                                         @RequestParam(defaultValue = "0", required = false) Integer slicesAvailable) {
        try {
            pieService.patchPie(pieName, calories, slicesAvailable);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Pie updated successfully: " + pieName);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pie not found: " + pieName);
        }
    }

    @PutMapping("/updatePie")
    public @ResponseBody ResponseEntity<String> updatePie(@RequestParam String pieName,
                                                          @RequestParam int calories,
                                                          @RequestParam int slicesAvailable) {
        try {
            pieService.updatePie(pieName, calories, slicesAvailable);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Pie updated successfully: " + pieName);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pie not found: " + pieName);
        }
    }

    @PostMapping("/addPie")
    public @ResponseBody ResponseEntity<String> addPie(@RequestBody Pie pie) {
        try {
            pieService.addPie(pie);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pie added successfully: " + pie.getPieName());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid pie details: " + e.getMessage());
        }
    }
}
