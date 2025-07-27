package com.revature.BootPie.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.BootPie.exceptions.ResourceNotFoundException;
import com.revature.BootPie.models.Pie;

@Service
public class PieService {

    private List<Pie> pies = new ArrayList<>();

    {
        pies.add(new Pie("Apple Pie", 250, 8));
        pies.add(new Pie("Cherry Pie", 300, 6));
        pies.add(new Pie("Pumpkin Pie", 200, 10));
        pies.add(new Pie("Blueberry Pie", 280, 5));
    }

    public Pie getRandonPie() {
        if (pies.isEmpty()) {
            return null;
        }
        int randomIndex = (int) (Math.random() * pies.size());
        return pies.get(randomIndex);
    }

    public List<Pie> getAllPies() {
        return new ArrayList<>(pies);
    }

    public Pie findPieByName(String pieName) throws ResourceNotFoundException {
        for (Pie pie : pies) {
            if (pie.getPieName().equalsIgnoreCase(pieName)) {
                return pie;
            }
        }
        throw new ResourceNotFoundException("Pie not found: " + pieName);
    }

    public List<Pie> getPiesByCalories(int limit) throws ResourceNotFoundException {
        List<Pie> filteredPies = new ArrayList<>();
        for (Pie pie : pies) {
            if (pie.getCalories() <= limit) {
                filteredPies.add(pie);
            }
        }
        if (filteredPies.isEmpty()) {
            throw new ResourceNotFoundException("No pies found with calories less than or equal to " + limit);
        }
        return filteredPies;
    }

    public void deletePie(String pieName) throws ResourceNotFoundException {
        Pie pieToDelete = findPieByName(pieName);
        if (pieToDelete != null) {
            pies.remove(pieToDelete);
        } else {
            throw new ResourceNotFoundException("Pie not found: " + pieName);
        }
    }

    public void patchPie(String pieName, int calories, int slices) throws ResourceNotFoundException {
        Pie existingPie = findPieByName(pieName);
        if (existingPie != null) {
            if(calories > 0) {
                existingPie.setCalories(calories);
            }
            if(slices >= 0) {
                existingPie.setSlicesAvailable(slices);
            }
        } else {
            throw new ResourceNotFoundException("Pie not found: " + pieName);
        }
    }   

    public void addPie(Pie pie) {
        if (pie == null || pie.getPieName() == null || pie.getCalories() <= 0 || pie.getSlicesAvailable() < 0) {
            throw new IllegalArgumentException("Invalid pie details");
        }
        pies.add(pie);
    }

    public void updatePie(String pieName, int calories, int slicesAvailable) throws ResourceNotFoundException {
        Pie existingPie = findPieByName(pieName);
        if (existingPie != null) {
            existingPie.setCalories(calories);
            existingPie.setSlicesAvailable(slicesAvailable);
        } else {
            throw new ResourceNotFoundException("Pie not found: " + pieName);
        }
    }

}
