package com.apap.tutorial3.controller;

import java.util.List;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/pilot/add")
	public String add(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "licenseNumber", required = true) String licenseNumber,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "flyHour", required = true) int flyHour) {
		PilotModel pilot = new PilotModel(id, licenseNumber, name, flyHour);
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping("/pilot/view")
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("pilot", archive);
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/viewall")
	public String viewall(Model model) {
		List<PilotModel> archive = pilotService.getPilotList();
		
		model.addAttribute("listPilot", archive);
		return "viewall-pilot";
	}
	
	@RequestMapping("/pilot/view/license-number/{licenseNumber}")
	public String viewByLicenseNumber(@PathVariable String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		if(!archive.equals(null)) 
			model.addAttribute("pilot", archive);
			return "view-license";
		
	}
	
	@RequestMapping("/pilot/view/license-number/{licenseNumber}/fly-hour/{rev_fly_hour}")
	public String updateFlyHour(@PathVariable String licenseNumber, @PathVariable String rev_fly_hour, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		int rev_hour = Integer.parseInt(rev_fly_hour);
		if(!archive.equals(null)) { 
			archive.setFlyHour(rev_hour);
			model.addAttribute("pilot", archive);
			return "ubah-flyHour";
		}
		return "ubah-flyHour";
	}
	
	@RequestMapping("/pilot/delete/id/{id_hapus}")
	public String delete(@PathVariable String id_hapus, Model model) {
		List<PilotModel> archive = pilotService.getPilotList();
		PilotModel removed_pilot;
		
		for (PilotModel pilot : archive) {
			if (pilot.getId().equals(id_hapus)) {
				model.addAttribute("pilot", pilot);
				archive.remove(pilot);
			}
		}
		
		model.addAttribute("listPilot", archive);
		return "remove-pilot";
	}
	
}
