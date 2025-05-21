package com.example.devProject.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.devProject.entities.Registration;
import com.example.devProject.entities.RegistrationFile;
import com.example.devProject.repositories.EducationRepo;
import com.example.devProject.repositories.RegisterFilesRepo;
import com.example.devProject.repositories.RegistrationRepo;
@Service
public class RegisrterationService {
	
	@Autowired
	private RegistrationRepo registrationRepo;
	@Autowired
	private RegisterFilesRepo registerFilesRepo;
	@Autowired
	private RegistrationFileService registrationFileService;
	@Value("${file.upload-dir}")
    private String uploadDir;
	@Autowired
	private EducationRepo educationrepo;


	public Object save(Registration registration, MultipartFile profileImage,List<MultipartFile> additionalFiles) throws IOException {
        if (profileImage != null && !profileImage.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + profileImage.getOriginalFilename();
            Path filePath = Path.of(uploadDir, fileName);
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            Files.write(filePath, profileImage.getBytes());
            registration.setProfileImagePath(filePath.toString());
        }
       // Registration savedRegistration = registrationRepo.save(registration);
        	registrationRepo.saveuser(registration.getFirstName(),
        		registration.getLastName(),registration.getPhnum(),registration.getAddress(),registration.getGender(),
        		registration.getPassword(),registration.getphysicallyHandicaped(),registration.getlegalIssues(),
        		registration.getDob(),registration.getAge(),registration.getEducation(),registration.getEmail(),registration.getProfileImagePath());
        		registrationRepo.saveuser(registration);
        if (additionalFiles != null && !additionalFiles.isEmpty()) {
            for (MultipartFile file : additionalFiles) {
                if (!file.isEmpty()) {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    Path filePath = Path.of(uploadDir, fileName);
                    Files.write(filePath, file.getBytes());
                	RegistrationFile registrationFile = new RegistrationFile();
                	 registrationFile.setOriginalName(file.getOriginalFilename());
                    registrationFile.setFileName(fileName);
                    registrationFile.setFilePath(filePath.toString());
                    //registrationFile.setRegistration(userId);
                	Integer userId = registrationRepo.getIdByEmail(registration.getEmail());

                    registerFilesRepo.save(registrationFile);
                    registerFilesRepo.saveFiles(registrationFile.getFileName(), 
                    registrationFile.getOriginalName(), registrationFile.getFilePath(), userId);
                }
            }
        }
		return registration;
	}
	
	public List<Registration>getAll(){
		return (List<Registration>) registrationRepo.getAllUsers();
	}
	
	@Transactional
	 public Integer authenticate(String email, String password) {

		 Integer id = registrationRepo.isExist(email, password); // Find the user by email
	        if (id == null||id==0) {
	            return 0; 
	        }
	        return id;
	    }

	 public Registration read(Integer id) {
			return registrationRepo.findUserById(id);
		}
	 
		public Page<Registration> getUsers(String keyword,Pageable pageable){

			if (keyword == null || keyword.isEmpty()) {
	            return registrationRepo.finduserwithoutkeyword(pageable);
	        }
	        return registrationRepo.findByKeyword(keyword, pageable);
	 }
		
		public void update(Integer id, Registration registration, MultipartFile profileImage,List<MultipartFile> additionalFiles) throws IOException {
			Registration userDetails = registrationRepo.findUserById(id);
			if (profileImage != null && !profileImage.isEmpty()) {
			    String existingProfileImagePath = registration.getProfileImagePath();

			    String newFileName = System.currentTimeMillis() + "_" + profileImage.getOriginalFilename();
			    if (existingProfileImagePath == null || !existingProfileImagePath.endsWith(newFileName)) {
			        Path newFilePath = Path.of(uploadDir, newFileName);
			        File directory = new File(uploadDir);
			        if (!directory.exists()) {
			            directory.mkdirs();
			        }
			        Files.write(newFilePath, profileImage.getBytes());
			        registration.setProfileImagePath(newFilePath.toString());
			        if (existingProfileImagePath != null) {
			            File oldImageFile = new File(existingProfileImagePath);
			            if (oldImageFile.exists()) {
			                oldImageFile.delete();
			            }
			        }
			    }
			}
			 registration.setFirstName(registration.getFirstName() != null ? registration.getFirstName() : userDetails.getFirstName());
			 registration.setLastName( registration.getLastName() != null ? registration.getLastName() : userDetails.getLastName());
			 registration.setPhnum(registration.getPhnum() != null ? registration.getPhnum():userDetails.getPhnum());
			 registration.setAddress(registration.getAddress() != null ? registration.getAddress(): userDetails.getAddress());
			 registration.setGender(registration.getGender() != null? registration.getGender():userDetails.getGender());
			 registration.setPassword(registration.getPassword() != null ? registration.getPassword():userDetails.getPassword());
			 registration.setPhysicallyHandicaped(registration.getphysicallyHandicaped() != false? registration.getphysicallyHandicaped():userDetails.getphysicallyHandicaped());
			 registration.setLegalissues(registration.getlegalIssues() != false? registration.getlegalIssues():userDetails.getlegalIssues());
			 registration.setDob(registration.getDob() !=null ? registration.getDob():userDetails.getDob());
			 registration.setAge(registration.getAge() !=null ? registration.getAge():userDetails.getAge());
			 registration.setEducation(registration.getEducation() !=null ? registration.getEducation():userDetails.getEducation());
			 registration.setEmail(registration.getEmail() !=null ? registration.getEmail():userDetails.getEmail());
			 registration.setProfileImagePath(registration.getProfileImagePath() !=null ? registration.getProfileImagePath():userDetails.getProfileImagePath());
			 
			registrationRepo.updateUser(id,registration.getFirstName(),
			registration.getLastName(),registration.getPhnum(),registration.getAddress(),
			registration.getGender(),registration.getPassword(),registration.getphysicallyHandicaped(),registration.getlegalIssues(),
			registration.getDob(),registration.getAge(),registration.getEducation(),registration.getEmail(),registration.getProfileImagePath());
			
			 registrationRepo.updateUser(registration);
			 
			 if (additionalFiles != null && !additionalFiles.isEmpty()) {
			        for (MultipartFile file : additionalFiles) {
			            if (!file.isEmpty()) {
			                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			                Path filePath = Path.of(uploadDir, fileName);
			                Files.write(filePath, file.getBytes());

			                RegistrationFile registrationFile = new RegistrationFile();
			                // registrationFile.setOriginalName(file.getOriginalFilename());
			                // registrationFile.setFileName(fileName);
			                // registrationFile.setFilePath(filePath.toString());

			                // registerFilesRepo.saveFiles(registrationFile.getFileName(),registrationFile.getOriginalName(),
			                //    registrationFile.getFilePath(),id);
			            }
			        }
			    }
		}
		public void deleteUserById(Integer id){
			int count = registrationFileService.getCountFiles(id);
			if(count>0) {
				registrationFileService.deleteFileByRegistrationId(id);
			}
			String profilePath=registrationRepo.findProfilePathById(id);
			if(profilePath!=null && !profilePath.isEmpty()) {
				File file = new File(profilePath);
				if(file.exists()) {
					file.delete();
				}
			}
			registrationRepo.deleteUserById(id);
		}
	
		public String SaveByExcelRecord(MultipartFile excelFile) throws IOException {
		    List<Registration> excelRecords = new ArrayList<>();
		    HashSet<String> errorMessages = new HashSet<String>();
		    Workbook workbook = null;
            int skippedCount=0;
	        int savedCount=0;
	        int totalCount=0;
		    try (InputStream inputStream = excelFile.getInputStream()) {
		        if (excelFile.getOriginalFilename().endsWith(".xlsx")) {
		            workbook = new XSSFWorkbook(inputStream);
		        } else if (excelFile.getOriginalFilename().endsWith(".xls")) {
		            workbook = new HSSFWorkbook(new POIFSFileSystem(inputStream));
		        }
		     Sheet sheet = workbook.getSheetAt(0);        
		    for (Row row : sheet) {
		        if (row.getRowNum() == 0) 
		            continue;
		        int rows=row.getRowNum()+1;
		        Registration redg = new Registration();
		        
		        Long userid = null;
		        
		        if(row.getCell(10)!=null) {
		        String mailcheck = row.getCell(10).getStringCellValue();
		        
		        if(mailcheck!=null) {
		        	if(mailcheck.contains("@")) {
		        		
		        	userid=(long) registrationRepo.getCountEmail(mailcheck);
		        	
		        	}else {
		        		errorMessages.add("email should contain @ at "+ (rows));
		        	}
		        }
		        }	
		        
		        if(userid>0) {
		        	skippedCount++;
		        	continue;
		        }
		        if (row.getCell(0) == null||row.getCell(8).getCellType()==CellType.BLANK
		        		||row.getCell(5)==null||row.getCell(10)==null) {
	                errorMessages.add("missing required fields in row:" + rows);
	                continue;
	            }
		        if(row.getCell(2)!=null) {
		        	if(row.getCell(2).getCellType()!=CellType.NUMERIC) {
			        	errorMessages.add("phone number should contain only digits at row:"+ rows);	
			        	break;
		        	}if(String.valueOf((long)row.getCell(2).getNumericCellValue()).length()!=10) {
			        	errorMessages.add("phone number should contain 10 digits at row:"+rows);	
		        	}
		        }
		        if(row.getCell(4)!=null) {
		        if(row.getCell(4).getCellType()!=CellType.STRING) {
		        	errorMessages.add("invalid data for Gender at row:"+row.getRowNum()+1);
		        }
		        }
		        if(row.getCell(5)!=null) {
		        	if(row.getCell(5).getCellType()==CellType.NUMERIC) {
		        	if(String.valueOf((long)row.getCell(5).getNumericCellValue()).length()<4) {
		        		errorMessages.add("Password should have more than 4 letter in it at row:"+ rows);
		        	}
		        	}else if(row.getCell(5).getCellType()==CellType.STRING) {
		        		if(row.getCell(5).getStringCellValue().length()<4){
			        		errorMessages.add("Password should have more than 4 letter in it at row:"+ rows);
		        		}
		        	}else {
		        		errorMessages.add("Password should be Numeric,Alphabetic or Alpha Numeric");
		        	}
		        	
		        }
		        if(row.getCell(6)!=null) {
		        if(row.getCell(6).getCellType()!=CellType.BOOLEAN) {
		        	errorMessages.add("Physically Handicaped column has invalid data at row:"+rows);
		        }
		        }
		        if(row.getCell(7)!=null) {
			        if(row.getCell(7).getCellType()!=CellType.BOOLEAN) {
			        	errorMessages.add("Legal Issue column has invalid data at row:"+rows);
			        }
			        }
		        if(row.getCell(8).getCellType()==CellType.NUMERIC) {
		        	Cell cell = row.getCell(8); 
		        	if(DateUtil.isCellDateFormatted(cell)) {
		        		  String formatString = cell.getCellStyle().getDataFormatString();
		        		  if (!"m/d/yy".equalsIgnoreCase(formatString)) {
		                      errorMessages.add("date format is not in DD/MM/YYYY at:"+rows);
		                  }
		        	}
		        }else {
                    errorMessages.add("date should be in date in DD/MM/YYYY:"+rows);
		        }
//		        if(row.getCell(9) != null) {
//		        	int maxEducation = educationrepo.gerMaxEducation(); 
//		        	double education =row.getCell(9).getNumericCellValue();
//		        	if((int)education>maxEducation) {
//		        		errorMessages.add("Education cannot be mapped with existing records greater at row"+row.getRowNum()+1);
//		        	}else if((int)education<0) {
//		        		errorMessages.add("Education cannot be mapped with existing records lesser at row"+row.getRowNum()+1);
//		        	}
//		        }
		        redg.setFirstName(getCellValue(row, 0));
		        if(redg.getFirstName().equals("others")) {
		        	errorMessages.add("First Name is invalid at row:"+rows);
		        }
		        if(row.getCell(2)!=null) {
			        redg.setLastName(getCellValue(row,1));
			        if(redg.getLastName().equals("others")) {
			        	errorMessages.add("Last Name is invalid at row:"+rows);
			        }
		        }
		        redg.setPhnum(row.getCell(2)!= null ? String.valueOf((long) row.getCell(2).getNumericCellValue()):null);
		        if(row.getCell(3)!=null) {
			        redg.setAddress(getCellValue(row, 3));	
			        if(redg.getAddress().equals("others")) {
			        	errorMessages.add("Address is invalid at row:"+rows);
			        }
		        }
		        if(row.getCell(4)!=null) {
		        	 if(row.getCell(4).getStringCellValue().equals("Male")||row.getCell(4).getStringCellValue().equals("Female")
				        		||row.getCell(4).getCellType()==CellType.BLANK) {
					        redg.setGender(row.getCell(4)!=null? row.getCell(4).getStringCellValue():null);
				        }
		        }else {
			        redg.setGender(row.getCell(4)!=null? row.getCell(4).getStringCellValue().toUpperCase():null);
		        }
		       	if(row.getCell(5)!=null) {        
		        if(row.getCell(5).getCellType()==CellType.NUMERIC) {
			        redg.setPassword( String.valueOf((long)row.getCell(5).getNumericCellValue()));
		        }else if(row.getCell(5).getCellType()==CellType.STRING) {
		        	redg.setPassword( String.valueOf(row.getCell(5).getStringCellValue()));
		        }
		       	}
		       	if(row.getCell(6)!=null) {
		       		if(row.getCell(6).getCellType()==CellType.BOOLEAN||row.getCell(6).getCellType()==CellType.BLANK) {
				        redg.setPhysicallyHandicaped(row.getCell(6) != null ? row.getCell(6).getBooleanCellValue() : false);
			        }
		       	}else {
			        redg.setPhysicallyHandicaped(row.getCell(6) != null ? row.getCell(6).getBooleanCellValue() : false);
		       	}
		       	if(row.getCell(7)!=null) {
		       		if(row.getCell(7).getCellType()==CellType.BOOLEAN||row.getCell(7).getCellType()==CellType.BLANK) {
				        redg.setPhysicallyHandicaped(row.getCell(7) != null ? row.getCell(7).getBooleanCellValue() : false);
			        }
		       	}else {
			        redg.setPhysicallyHandicaped(row.getCell(7) != null ? row.getCell(7).getBooleanCellValue() : false);
		       	}
		        
		        if(row.getCell(8).getCellType()==CellType.NUMERIC) {
			        redg.setDob(row.getCell(8).getLocalDateTimeCellValue().toLocalDate());
		        }
		        
		       // redg.setEducation(row.getCell(9)!=null?(int) row.getCell(9).getNumericCellValue():0);
		        
		        if(row.getCell(9)!=null) {
		        	if(row.getCell(9).getCellType()==CellType.STRING) {
		        		String qualification =row.getCell(9).getStringCellValue();
			        	System.out.println(qualification);
			        	List eduList= new ArrayList<String>();
			        	eduList=educationrepo.getEducation();
			        	if(eduList.contains(qualification)) {
			        	int eduId=educationrepo.getIdByQualification(qualification);
			        	System.out.println(eduId);
			        	redg.setEducation(eduId);
			        	}
		        	}else {
		        		errorMessages.add("Please check education value at row:"+rows);
		        	}
		        }else {
		        	redg.setEducation(0);
		        }
		        
		        redg.setEmail(row.getCell(10).getStringCellValue());
		        
		        savedCount++;

		        totalCount=skippedCount+savedCount;

		        for (Registration record : excelRecords) {
		            if (record.getEmail().equals(redg.getEmail())) {
		                errorMessages.add("Multiple records having same email please check file");
		                break;
		            }
		        }
		        excelRecords.add(redg);		        
		    }
		    workbook.close();
		    }
		    if (errorMessages.isEmpty()) {
		    	String message;
		    	registrationRepo.saveAll(excelRecords);
		    	if(skippedCount>0) {
		    		message="Total Records:"+totalCount+",Skipped Records:"+skippedCount+",Saved Records:"+savedCount;
		    		return message;
		    	
		    	}else {
		    		message="Total Records:"+totalCount+",Saved Records:"+savedCount;
		    		return message;
		    		
		    	}
		    } else {
		        return "something is wrong please check the following: " + String.join(", ", errorMessages);
		    }
		}

		private String getCellValue(Row row, int i) {
			Cell cell = row.getCell(i);
			 switch (cell.getCellType()) {
		        case STRING:
		            return cell.getStringCellValue();
		        case NUMERIC:
		            return String.valueOf((long)cell.getNumericCellValue());
		        default:
		            return "others";
		    }
		}
		private Map<String, XSSFPictureData> mapImagesToCells(Sheet sheet) {
		    Map<String, XSSFPictureData> imageMap = new HashMap<>();
		    Drawing<?> drawing = sheet.getDrawingPatriarch();

		    if (drawing != null) {
		    	if(drawing instanceof XSSFDrawing fDrawing) {
		    		for (Object shape : fDrawing.getShapes()) {
			            if (shape instanceof XSSFPicture picture) {
			                XSSFClientAnchor anchor = picture.getClientAnchor();
			                if(anchor.getRow1()==0) {
				                String cellKey = anchor.getRow1()+1 + "_" + anchor.getCol1();
				                imageMap.put(cellKey, (XSSFPictureData) picture.getPictureData());
				                System.out.println(cellKey);
				                System.out.println(imageMap.size());
			                }else {
				                String cellKey = anchor.getRow1() + "_" + anchor.getCol1();
			    			    XSSFPictureData pictureData = picture.getPictureData();
				                imageMap.put(cellKey, picture.getPictureData());
				                System.out.println(cellKey);
				                System.out.println(imageMap.size());
			                }
			                
			            }
			        }
		    	}
		    }
		    return imageMap;
		}
		
		private Map<String, HSSFPictureData> mapImagesToCells1(Sheet sheet) {
		    Map<String,HSSFPictureData> imageMap1 =new HashMap<>();
		    Drawing<?> drawing = sheet.getDrawingPatriarch();
		    		for (HSSFShape shape : ((HSSFPatriarch) drawing).getChildren()) {
		    			if (shape instanceof HSSFPicture picture) {
		    			    HSSFClientAnchor anchor = (HSSFClientAnchor) picture.getAnchor();
		    			    if(anchor.getRow1()==0) {
		    			    	String cellKey = anchor.getRow1()+1 + "_" + anchor.getCol1();
			    			    HSSFPictureData pictureData = picture.getPictureData();
			    			    imageMap1.put(cellKey, pictureData);
		    			    }else {
		    			    	String cellKey = anchor.getRow1() + "_" + anchor.getCol1();
			    			    HSSFPictureData pictureData = picture.getPictureData();
			    			    imageMap1.put(cellKey, pictureData);	
		    			    }
		    			}
		    		}
		    			return imageMap1;
		}
}

