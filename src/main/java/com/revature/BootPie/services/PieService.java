package com.revature.BootPie.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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

}
