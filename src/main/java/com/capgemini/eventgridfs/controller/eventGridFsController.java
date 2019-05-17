package com.capgemini.eventgridfs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.eventgridfs.entity.Event;
import com.capgemini.eventgridfs.entity.FileResources;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

@RestController
@CrossOrigin("*")
public class eventGridFsController {


	@Autowired
	private GridFsOperations gridFsOperations;
	
	String fileId=" ";
	
	@PostMapping("/upload/{email}")
	public ResponseEntity<Event> saveFile(@RequestParam("filePath") MultipartFile file, @PathVariable String email,@RequestParam String eventName
			,@RequestParam String eventVenue, @RequestParam String eventhostedBy,@RequestParam String eventDate) throws IOException{
		System.out.println(email);
		
		if(!file.isEmpty()) {
			System.out.println(file.getInputStream());
		
		}
		
		DBObject metaData = new BasicDBObject();
		metaData.put("email", email);
		metaData.put("eventName",eventName);
		metaData.put("eventVenue", eventVenue);
		metaData.put("eventDate", eventDate);
		metaData.put("eventhostedBy", eventhostedBy);
		
		InputStream inputStream = file.getInputStream();
		metaData.put("type", "images");
		

		
		fileId = gridFsOperations.store(inputStream, file.getOriginalFilename(), metaData).getId().toString();
		return new ResponseEntity<Event>(HttpStatus.OK);
	}
	
	 @GetMapping("/save/{username}") 
	  public ResponseEntity retrieveSingleVideoFileUsingUsername(@PathVariable String username) throws IOException {
		/*
		 * GridFSDBFile dbFile = gridFsOperations.findOne(new
		 * Query(Criteria.where("_id").is("5cd3c37b3ff9da403c2f01b3")));
		 */
		  GridFSDBFile dbFile = gridFsOperations.findOne(new
				  Query(Criteria.where("metadata.userName").is(username)));
	  InputStreamResource inputStreamResource = new InputStreamResource(dbFile.getInputStream());
	  //byte[] b = IOUtils.toByteArray(dbFile.getInputStream());
	  System.out.println(inputStreamResource);
	  return new ResponseEntity(inputStreamResource, HttpStatus.OK);
	  
	  }
	  
	  @GetMapping("/retrieve/{id}")
	  public ResponseEntity retrieveVideoUsingId(@PathVariable String id) {
		  GridFSDBFile dbFile = gridFsOperations.findOne(new
				  Query(Criteria.where("_id").is(id)));
		  InputStreamResource inputStreamResource = new InputStreamResource(dbFile.getInputStream());
		  return new ResponseEntity(inputStreamResource, HttpStatus.OK);
		  
	  }
	  
	  @GetMapping("/retrieveMetaDataById/{id}")
	  public ResponseEntity<FileResources> retrieveMetaDataUsingId(@PathVariable String id) {
		  GridFSDBFile dbFile = gridFsOperations.findOne(new
				  Query(Criteria.where("_id").is(id)));
		  DBObject metaData = dbFile.getMetaData();
		  FileResources fr = new FileResources(dbFile.getId().toString(), metaData.get("eventName").toString(),metaData.get("eventVenue").toString(),
				  metaData.get("eventDate").toString(),metaData.get("eventhostedBy").toString());
		  return new ResponseEntity<FileResources>(fr, HttpStatus.OK);
	  }
	  
	  @GetMapping("/save")
	  public ResponseEntity<List<FileResources>> retrieveVideoFileForHomepage() throws IOException {
		  System.out.println("inside save");
		  List<GridFSDBFile> dbFileList = gridFsOperations.find(null);
		  List<FileResources> fileResource = new ArrayList<>();
		  //List<InputStreamResource> inputStreamResources = new ArrayList<>();
		  //List<byte[]> byteFiles = new ArrayList<byte[]>();
		  int index = 0;
		  
		  for(GridFSDBFile dbFL: dbFileList )
		  {
			  DBObject metaData = dbFL.getMetaData();
			  FileResources fr = new FileResources(dbFL.getId().toString(),metaData.get("eventName").toString(),metaData.get("eventVenue").toString(),
					  metaData.get("eventDate").toString(),metaData.get("eventhostedBy").toString());
			  fileResource.add(fr);
			  //byteFiles.add(IOUtils.toByteArray(dbFL.getInputStream()));
			  System.out.println(dbFL.getInputStream());
			  //inputStreamResources.add(new InputStreamResource(dbFL.getInputStream()));
		  }
		  //for(int i=0;i<inputStreamResources.size();i++)
		//	  System.out.println(inputStreamResources.get(i));
		  return new ResponseEntity<List<FileResources>>(fileResource, HttpStatus.OK);
	  }
}
