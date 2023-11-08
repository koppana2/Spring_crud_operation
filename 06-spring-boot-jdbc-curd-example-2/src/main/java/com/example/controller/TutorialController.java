package com.example.controller;

import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Tutorial;
import com.example.repository.TutorialRepository;


//@CrossOrigin(origins="http://localhost:9596")
@RestController
@RequestMapping("/api")
public class TutorialController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	TutorialRepository tutorialRepository;
	
	@GetMapping("/tutorials")
	  public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
		logger.info("GetAll Method from Controller class");
	    try {
	      List<Tutorial> tutorials = new ArrayList<Tutorial>();

	      if (title == null)
	        tutorialRepository.findAll().forEach(tutorials::add);
	      else
	        tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

	      if (tutorials.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(tutorials, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	  @GetMapping("/tutorials/{id}")
	  public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
		  logger.info("GetById Method from Controller class");
	    Tutorial tutorial = tutorialRepository.findById(id);

	    if (tutorial != null) {
	      return new ResponseEntity<>(tutorial, HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	  
	  @PostMapping("/save")
		public ResponseEntity<String> saveTutorial(@RequestBody Tutorial tuto){
		  logger.info("Save Method from Controller Class");
		try {
			tutorialRepository.save(new Tutorial(tuto.getTitle(),tuto.getDescription(),false));
			return new ResponseEntity<>("Tutorial was created successfully",HttpStatus.CREATED);
		}catch(Exception ex) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}

	  /*@PostMapping("/tutorials")
	  public ResponseEntity<String> createTutorial(@RequestBody Tutorial tutorial) {
		  logger.info("Save Method from Controller Class!!");
	    try {
	      tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(),false));
	      return new ResponseEntity<>("Tutorial was created successfully.", HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }*/

	  @PutMapping("/tutorials/{id}")
	  public ResponseEntity<String> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
		  logger.info("Put Method from Controller class");
	    Tutorial _tutorial = tutorialRepository.findById(id);

	    if (_tutorial != null) {
	      _tutorial.setId(id);
	      _tutorial.setTitle(tutorial.getTitle());
	      _tutorial.setDesciption(tutorial.getDescription());
	      _tutorial.setPublished(tutorial.isPublished());

	      tutorialRepository.update(_tutorial);
	      return new ResponseEntity<>("Tutorial was updated successfully.", HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>("Cannot find Tutorial with id=" + id, HttpStatus.NOT_FOUND);
	    }
	  }

	  @DeleteMapping("/tutorials/{id}")
	  public ResponseEntity<String> deleteTutorial(@PathVariable("id") long id) {
		  logger.info("Delete Method from Controller class");
	    try {
	      int result = tutorialRepository.deleteById(id);
	      if (result == 0) {
	        return new ResponseEntity<>("Cannot find Tutorial with id=" + id, HttpStatus.OK);
	    }
	      return new ResponseEntity<>("Tutorial was deleted successfully.", HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>("Cannot delete tutorial.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	  @DeleteMapping("/tutorials")
	  public ResponseEntity<String> deleteAllTutorials() {
	    try {
	      int numRows = tutorialRepository.deleteAll();
	      return new ResponseEntity<>("Deleted " + numRows + " Tutorial(s) successfully.", HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>("Cannot delete tutorials.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	  }

	  @GetMapping("/tutorials/published")
	  public ResponseEntity<List<Tutorial>> findByPublished() {
	    try {
	      List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

	      if (tutorials.isEmpty()) {
	    	  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	      return new ResponseEntity<>(tutorials, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }


}
